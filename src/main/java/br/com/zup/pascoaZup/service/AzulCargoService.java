package br.com.zup.pascoaZup.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

import br.com.zup.pascoaZup.domain.Modalidade;
import br.com.zup.pascoaZup.domain.Logistica;
import io.netty.handler.codec.json.JsonObjectDecoder;

@Service
public class AzulCargoService {

	JsonMapper mapper = new JsonMapper();

	public Logistica logisticaAzulCargo(String cepOrigem, String cepDestino, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {

		Logistica logisticaAzulCargo = new Logistica("AzulCargo", cepOrigem, cepDestino, peso, comprimento, altura,
				largura, nomeCidadeDestino, nomeCidadeOrigem);

		System.out.println(cepDestino);

		Modalidade dhl = consultaAzulCargo(cepOrigem, cepDestino, "AzulCargo", peso, comprimento, altura, largura,
				nomeCidadeDestino, nomeCidadeOrigem);

		logisticaAzulCargo.getListModalidades().add(dhl);

		System.out.println(dhl);

		return logisticaAzulCargo;

	}

	public Modalidade consultaAzulCargo(String cepOrigem, String cepDestino, String nomeModalidade, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {
		String url = "https://www.azulcargoexpress.com.br/Cotacao/Cotacao/EnviarCotacao";

		ObjectNode body = mapper.createObjectNode();

		// {"DadosOrigem":{"CEP":"15060-100","CidadeUF":"SAO JOSE DO RIO PRETO -
		// SP","SolicitarColeta":true},
		ObjectNode dadosOrigemBody = mapper.createObjectNode();
		dadosOrigemBody.put("CEP", cepOrigem);
		dadosOrigemBody.put("CidadeUF", nomeCidadeOrigem);
		dadosOrigemBody.put("SolicitarColeta", true);
		body.put("DadosOrigem", dadosOrigemBody);

		// "DadosDestino":{"CEP":"12570-000","CidadeUF":"APARECIDA -
		// SP","EntregaDomicilio":true},
		ObjectNode dadosDestinoBody = mapper.createObjectNode();
		dadosDestinoBody.put("CEP", cepDestino);
		dadosDestinoBody.put("CidadeUF", nomeCidadeDestino);
		dadosDestinoBody.put("EntregaDomicilio", true);
		body.put("DadosDestino", dadosDestinoBody);

		// "DadosCarga":{"TipoMercadoria":"NOR","Valor":"100,00","Seguro":"1","Volumes":[{"Embalagem":"EMBALAGEM
		// PROPRIA","TipoEmbalagem":"5","Altura":50,
		// "Largura":50,"Comprimento":50,"QuantidadeVolumes":"1","Peso":1,
		// "PesoCubado":20.833333333333332}]},
		ArrayNode volumesArray = mapper.createArrayNode();
		ObjectNode volume1 = mapper.createObjectNode();
		volume1.put("Embalagem", "EMBALAGEM PROPRIA");
		volume1.put("TipoEmbalagem", "5");
		volume1.put("Altura", altura);
		volume1.put("Largura", largura);
		volume1.put("Comprimento", comprimento);
		volume1.put("QuantidadeVolumes", "1");
		volume1.put("Peso", peso);
		volumesArray.add(volume1);
		ObjectNode dadosCargaBody = mapper.createObjectNode();
		dadosCargaBody.put("TipoMercadoria", "NOR");
		dadosCargaBody.put("Valor", "100,00");
		dadosCargaBody.put("Seguro", "1");
		dadosCargaBody.put("Volumes", volumesArray);
		body.put("DadosCarga", dadosCargaBody);

		// "DadosContato":{"Nome":"Luis Augusto Gomes
		// Rocha","Email":"luis.augusto.gomes.rocha@gmail.com","Telefone":"(14)
		// 99716-3859"}}
		ObjectNode dadosContatoBody = mapper.createObjectNode();
		dadosContatoBody.put("Nome", "Luis Augusto Gomes Rocha");
		dadosContatoBody.put("Email", "luis.augusto.gomes.rocha@gmail.com");
		dadosContatoBody.put("Telefone", "(14) 99716-3859");
		body.put("DadosContato", dadosContatoBody);
		
		try {
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body); // arvore completa no string
		
		 
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	
		
		conn.setDoOutput(true);
		try (OutputStream os = conn.getOutputStream()) {
			byte[] input = json.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		System.out.println("MSG " + conn.getResponseCode());
		InputStream obj = (InputStream) conn.getContent();

		String text = new BufferedReader(new InputStreamReader(obj, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));

		JsonNode jsonNode = mapper.readTree(text);

		System.out.println(jsonNode);

		String prazoDeEntrega = jsonNode.findValues("DeliveryDateBaseonRouteDate").get(0).asText();
		String valorTotal = jsonNode.findValues("Total").get(0).asText();
		prazoDeEntrega = extractNumber(prazoDeEntrega);
	
		Instant prazoDeEntregaInstant = Instant.ofEpochMilli(Long.valueOf(prazoDeEntrega));
		Instant agora = Instant.now();		
		Instant diferenca = prazoDeEntregaInstant.minusMillis(agora.toEpochMilli());
		
		String tempoEntrega = String.valueOf(diferenca.atZone(ZoneId.systemDefault()).getDayOfYear());
		
		Modalidade modalidadeServico = new Modalidade(nomeModalidade, Double.valueOf(valorTotal), tempoEntrega);

		conn.disconnect();
		return modalidadeServico;

	}catch(Exception ex)
	{
			System.out.println("Erro " + ex.getMessage() + " ao obter dados da URL " + url);

		}return null;
}
	
	private String extractNumber(final String str) {                
	    
	    if(str == null || str.isEmpty()) return "";
	    
	    StringBuilder sb = new StringBuilder();
	    boolean found = false;
	    for(char c : str.toCharArray()){
	        if(Character.isDigit(c)){
	            sb.append(c);
	            found = true;
	        } else if(found){
	            // If we already found a digit before and this char is not a digit, stop looping
	            break;                
	        }
	    }
	    
	    return sb.toString();
	}

}
