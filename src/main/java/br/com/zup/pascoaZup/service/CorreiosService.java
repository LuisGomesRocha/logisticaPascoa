package br.com.zup.pascoaZup.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.zup.pascoaZup.domain.Modalidade;
import br.com.zup.pascoaZup.domain.Logistica;

@Service
public class CorreiosService {

	XmlMapper xmlMapper = new XmlMapper();

	public Logistica pesquisaCorreios(String cepOrigem, String cepDestino, String peso, String comprimento,
			String altura, String largura) {
		
		Logistica logisticaCorreios = new Logistica ( "Correios",  cepOrigem,  cepDestino, peso,  comprimento,  altura,  largura,  null, null); 
		
		Modalidade sedex =  consultaCorreios(cepOrigem, cepDestino, "SEDEX" ,"4014",  peso,  comprimento,altura,  largura);
		Modalidade pac =  consultaCorreios(cepOrigem, cepDestino, "PAC" ,"4510",  peso,  comprimento,altura,  largura);
		Modalidade sedex12 =  consultaCorreios(cepOrigem, cepDestino, "SEDEX12" ,"4782",  peso,  comprimento,altura,  largura);
		Modalidade sedex10 =  consultaCorreios(cepOrigem, cepDestino, "SEDEX10" ,"4790",  peso,  comprimento,altura,  largura);
		Modalidade sedexHoje =  consultaCorreios(cepOrigem, cepDestino, "SEDEXHoje" ,"4804",  peso,  comprimento,altura,  largura);
		
		logisticaCorreios.getListModalidades().add(sedex);
		logisticaCorreios.getListModalidades().add(pac);
		logisticaCorreios.getListModalidades().add(sedex12);
		logisticaCorreios.getListModalidades().add(sedex10);
		logisticaCorreios.getListModalidades().add(sedexHoje);
		
		
		return logisticaCorreios;
		
	}

	public Modalidade consultaCorreios(String cepOrigem, String cepDestino,String nomeModalidade, String modalidade, String peso,
			String comprimento, String altura, String largura) {
		String url = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx";
		String finalURI = UriComponentsBuilder.fromUriString(url).queryParam("nCdEmpresa", "08082650")
				.queryParam("sDsSenha", 564321).queryParam("sCepOrigem", cepOrigem)
				.queryParam("sCepDestino", cepDestino).queryParam("nVlPeso", peso).queryParam("nCdFormato", 1)
				.queryParam("nVlComprimento", comprimento).queryParam("nVlAltura", altura)
				.queryParam("nVlLargura", largura).queryParam("sCdMaoPropria", "n").queryParam("nVlValorDeclarado", 0)
				.queryParam("sCdAvisoRecebimento", "n").queryParam("nCdServico", modalidade)
				.queryParam("nVlDiametro", 0).queryParam("StrRetorno", "xml").queryParam("nIndicaCalculo", 3)
				.toUriString();

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(finalURI).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			System.out.println("MSG " + conn.getResponseCode());
			InputStream obj = (InputStream) conn.getContent();

			String text = new BufferedReader(new InputStreamReader(obj, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));

			Map responseMap = (Map<String,String>) xmlMapper.readValue(text, Map.class).get("cServico");

			System.out.println(responseMap);
			
			Modalidade modalidadeServico = new Modalidade (nomeModalidade, Double.valueOf(((String)responseMap.get("Valor")).replace(",", ".")),(String)responseMap.get("PrazoEntrega"));

			conn.disconnect();
			return modalidadeServico;

		} catch (Exception ex) {
			System.out.println("Erro " + ex.getMessage() + " ao obter dados da URL " + url);

		}
		return null;
	}

}
