package br.com.zup.pascoaZup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.pascoaZup.domain.Logistica;
import br.com.zup.pascoaZup.domain.Modalidade;
import br.com.zup.pascoaZup.dto.LogisticaRequest;
import br.com.zup.pascoaZup.service.AzulCargoService;
import br.com.zup.pascoaZup.service.CorreiosService;
import br.com.zup.pascoaZup.service.DhlService;
import br.com.zup.pascoaZup.service.EmpresasLogisticaService;

@RestController
@RequestMapping(value = "/logistica")
public class LogisticaController {

	@Autowired
	DhlService dhlService;

	@Autowired
	CorreiosService correiosService;

	@Autowired
	AzulCargoService azulCargoService;

	@Autowired
	EmpresasLogisticaService empresasLogisticaService;

	// Criar Cadastro
	@PostMapping("/azul")
	public ResponseEntity<Logistica> azulCargo(@Validated @RequestBody LogisticaRequest logisticaRequest)
			throws Exception {

		Logistica logistica = azulCargoService.logisticaAzulCargo(logisticaRequest.getCepOrigem(),
				logisticaRequest.getCepDestino(), logisticaRequest.getPeso(), logisticaRequest.getComprimento(),
				logisticaRequest.getAltura(), logisticaRequest.getLargura(), logisticaRequest.getNomeCidadeDestino(),
				logisticaRequest.getNomeCidadeOrigem());

		return ResponseEntity.status(HttpStatus.OK).body(logistica);

	}

	@PostMapping("/correios")
	public ResponseEntity<Logistica> pesquisaCorreios(@Validated @RequestBody LogisticaRequest logisticaRequest)
			throws Exception {

		Logistica logistica = correiosService.pesquisaCorreios(logisticaRequest.getCepOrigem(),
				logisticaRequest.getCepDestino(), logisticaRequest.getPeso(), logisticaRequest.getComprimento(),
				logisticaRequest.getAltura(), logisticaRequest.getLargura());

		return ResponseEntity.status(HttpStatus.OK).body(logistica);

	}

	@PostMapping("/dhl")
	public ResponseEntity<Logistica> logisticaDhl(@Validated @RequestBody LogisticaRequest logisticaRequest)
			throws Exception {

		Logistica logistica = dhlService.pesquisaDhl(logisticaRequest.getCepOrigem(), logisticaRequest.getCepDestino(),
				logisticaRequest.getPeso(), logisticaRequest.getComprimento(), logisticaRequest.getAltura(),
				logisticaRequest.getLargura(), logisticaRequest.getNomeCidadeDestino(),
				logisticaRequest.getNomeCidadeOrigem());

		return ResponseEntity.status(HttpStatus.OK).body(logistica);

	}

	@PostMapping
	public ResponseEntity<Map> consultaMelhorModalidade(@Validated @RequestBody LogisticaRequest logisticaRequest)
			throws Exception {
		
		List logisticas = empresasLogisticaService.consultaServicosLogistica(logisticaRequest.getCepOrigem(),
				logisticaRequest.getCepDestino(), logisticaRequest.getPeso(), logisticaRequest.getComprimento(),
				logisticaRequest.getAltura(), logisticaRequest.getLargura(),
				logisticaRequest.getNomeCidadeDestino(), logisticaRequest.getNomeCidadeOrigem());
		
		Modalidade melhorModalidade = empresasLogisticaService.procuraMelhorModalidade(logisticaRequest.getCepOrigem(),
				logisticaRequest.getCepDestino(), logisticaRequest.getPeso(), logisticaRequest.getComprimento(),
				logisticaRequest.getAltura(), logisticaRequest.getLargura(),
				logisticaRequest.getNomeCidadeDestino(), logisticaRequest.getNomeCidadeOrigem());
		
		Modalidade melhorprazo = empresasLogisticaService.procuraMelhorPrazo(logisticaRequest.getCepOrigem(),
				logisticaRequest.getCepDestino(), logisticaRequest.getPeso(), logisticaRequest.getComprimento(),
				logisticaRequest.getAltura(), logisticaRequest.getLargura(),
				logisticaRequest.getNomeCidadeDestino(), logisticaRequest.getNomeCidadeOrigem());
				
		Map response = new HashMap<String, Object>();
		
		response.put( "logisticas", logisticas);
		response.put( "melhorModalidade", melhorModalidade);
		response.put( "melhorPrazo", melhorprazo);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

}