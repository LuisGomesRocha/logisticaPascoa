package br.com.zup.pascoaZup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.pascoaZup.domain.Logistica;
import br.com.zup.pascoaZup.domain.Modalidade;



@Service
public class EmpresasLogisticaService {
	
	@Autowired
	DhlService dhlService;
	
	@Autowired
	CorreiosService correiosService;
	
	@Autowired
	AzulCargoService azulCargoService;
	
	public List<Logistica> consultaServicosLogistica(String cepOrigem, String cepDestino, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {
		
		
		Logistica logisticaAzul = azulCargoService.logisticaAzulCargo(cepOrigem,  cepDestino,  peso,
				 comprimento,  altura,  largura,  nomeCidadeDestino,  nomeCidadeOrigem);
		
		Logistica logisticaCorreios = correiosService.pesquisaCorreios(cepOrigem,  cepDestino,  peso,
				 comprimento,  altura,  largura);
		
		Logistica logisticaDhl = dhlService.pesquisaDhl(cepOrigem,  cepDestino,  peso,
				 comprimento,  altura,  largura,  nomeCidadeDestino,  nomeCidadeOrigem);
		
		List<Logistica> servicos = new ArrayList<Logistica>();
		servicos.add(logisticaAzul);
		servicos.add(logisticaCorreios);
		servicos.add(logisticaDhl);
		return servicos;
	}
	
	public Modalidade procuraMelhorModalidade (String cepOrigem, String cepDestino, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {
		
		List<Logistica> servicos = consultaServicosLogistica(cepOrigem, cepDestino, peso, comprimento, altura, largura, nomeCidadeDestino, nomeCidadeOrigem);
		
		List<Modalidade> todasModalidades = new ArrayList<Modalidade>();
		
		for(Logistica l : servicos)
			todasModalidades.addAll(l.getListModalidades());	
		
		Modalidade melhorModalidade = todasModalidades.get(0);
		
		for(Modalidade l : todasModalidades) {
			if((l.getValorFinal() < melhorModalidade.getValorFinal()) && l.getValorFinal() != 0.0)
				melhorModalidade = l;
		}
		
		return melhorModalidade;		
	}
	
	public Modalidade procuraMelhorPrazo (String cepOrigem, String cepDestino, String peso,
			String comprimento, String altura, String largura, String nomeCidadeDestino, String nomeCidadeOrigem) {
		
		List<Logistica> servicos = consultaServicosLogistica(cepOrigem, cepDestino, peso, comprimento, altura, largura, nomeCidadeDestino, nomeCidadeOrigem);
		
		List<Modalidade> todasModalidades = new ArrayList<Modalidade>();
		
		for(Logistica l : servicos)
			todasModalidades.addAll(l.getListModalidades());	
		
		Modalidade melhorPrazo = todasModalidades.get(0);
		
		for(Modalidade l : todasModalidades) {
			if((Double.valueOf(l.getTempoEntrega()) < Double.valueOf(melhorPrazo.getTempoEntrega())) && Double.valueOf(l.getTempoEntrega()) != 0)
				melhorPrazo = l;
		}
		
		return melhorPrazo;		
	}
	
	

}
