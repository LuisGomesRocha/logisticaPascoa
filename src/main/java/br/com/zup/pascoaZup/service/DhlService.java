package br.com.zup.pascoaZup.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.zup.pascoaZup.domain.Modalidade;
import br.com.zup.pascoaZup.domain.Logistica;

@Service
public class DhlService {

	JsonMapper jsonMapper = new JsonMapper();

	public Logistica pesquisaDhl(String cepOrigem, String cepDestino, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {

		Logistica logisticaDhl = new Logistica("DHL", cepOrigem, cepDestino, peso, comprimento, altura, largura,
				nomeCidadeDestino, nomeCidadeOrigem);
		
		System.out.println(cepDestino);
		
		Modalidade dhl = consultaDhl(cepOrigem, cepDestino, "DHL - Express", peso, comprimento, altura, largura, nomeCidadeDestino,
				nomeCidadeOrigem);

		logisticaDhl.getListModalidades().add(dhl);
		
		System.out.println(dhl);

		return logisticaDhl;

	}

	public Modalidade consultaDhl(String cepOrigem, String cepDestino, String nomeModalidade, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {
		String url = "https://cj-gaq.dhl.com/api/quote?";
		String finalURI = UriComponentsBuilder.fromUriString(url)
				.queryParam("destinationCity", nomeCidadeDestino)
				.queryParam("destinationCountry", "BR")
				.queryParam("destinationUseFallback", false)
				.queryParam("destinationZip", cepDestino)
				.queryParam("originCity", nomeCidadeOrigem)				
				.queryParam("originCountry", "BR")
				.queryParam("originUseFallback", false)
				.queryParam("originZip", cepOrigem)
				.queryParam("receiverAddressType", "BUSINESS")
				.queryParam("receiverType", "CONSUMER")
				.queryParam("senderType", "CONSUMER")
				.queryParam("items(0).weight", peso)
				.queryParam("items(0).height", altura)
				.queryParam("items(0).length", comprimento)
				.queryParam("items(0).width", largura)
				.queryParam("items(0).quantity", 1)
				.queryParam("items(0).presetSize", "m")
				.queryParam("items(0).unitSystem", "metric")
				.queryParam("language", "pt").toUriString();
		
		

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(finalURI).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setRequestProperty("User-Agent", "PostmanRuntime/7.26.8");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "keep-alive");
						
			System.out.println("MSG " + conn.getResponseCode());
			InputStream obj = (InputStream) conn.getContent();

			String text = new BufferedReader(new InputStreamReader(obj, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));

			JsonNode jsonNode = jsonMapper.readTree(text);

			System.out.println(jsonNode);
			String prazoDeEntregaInicial = jsonNode.findValues("cutOffDateTime").get(0).asText();
			Date date1 = (Date) new SimpleDateFormat("DD").parse(prazoDeEntregaInicial);
			String prazoDeEntrega = String.valueOf(date1.getDate());
			String valorTotal = jsonNode.findValues("amountWithTax").get(0).asText();
			Modalidade modalidadeServico = new Modalidade(nomeModalidade, Double.valueOf(valorTotal), prazoDeEntrega);

			conn.disconnect();
			return modalidadeServico;

		} catch (Exception ex) {
			System.out.println("Erro " + ex.getMessage() + " ao obter dados da URL " + url);

		}
		return null;
	}

}
