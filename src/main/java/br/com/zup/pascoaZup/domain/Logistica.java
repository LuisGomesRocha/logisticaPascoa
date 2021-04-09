
package br.com.zup.pascoaZup.domain;

import java.util.ArrayList;
import java.util.List;

public class Logistica {
	
	private String nomeEmpresa;
	private String cepOrigem;
	private String cepDestino;
	private List<Modalidade> listModalidades;
	private String peso;
	private String comprimento;
	private String altura;
	private String largura;
	private String nomeCidadeOrigem;
	private String nomeCidadeDestino;
	
	@Deprecated
	public Logistica() {
		
	}

	public Logistica(String nomeEmpresa, String cepOrigem, String cepDestino,
			String peso, String comprimento, String altura, String largura, String nomeCidadeOrigem,
			String nomeCidadeDestino) {
		super();
		this.nomeEmpresa = nomeEmpresa;
		this.cepOrigem = cepOrigem;
		this.cepDestino = cepDestino;
		this.listModalidades = new ArrayList<>();
		this.peso = peso;
		this.comprimento = comprimento;
		this.altura = altura;
		this.largura = largura;
		this.nomeCidadeOrigem = nomeCidadeOrigem;
		this.nomeCidadeDestino = nomeCidadeDestino;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCepOrigem() {
		return cepOrigem;
	}

	public void setCepOrigem(String cepOrigem) {
		this.cepOrigem = cepOrigem;
	}

	public String getCepDestino() {
		return cepDestino;
	}

	public void setCepDestino(String cepDestino) {
		this.cepDestino = cepDestino;
	}

	public List<Modalidade> getListModalidades() {
		return listModalidades;
	}

	public void setListModalidades(List<Modalidade> listModalidades) {
		this.listModalidades = listModalidades;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getComprimento() {
		return comprimento;
	}

	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getLargura() {
		return largura;
	}

	public void setLargura(String largura) {
		this.largura = largura;
	}

	public String getNomeCidadeOrigem() {
		return nomeCidadeOrigem;
	}

	public void setNomeCidadeOrigem(String nomeCidadeOrigem) {
		this.nomeCidadeOrigem = nomeCidadeOrigem;
	}

	public String getNomeCidadeDestino() {
		return nomeCidadeDestino;
	}

	public void setNomeCidadeDestino(String nomeCidadeDestino) {
		this.nomeCidadeDestino = nomeCidadeDestino;
	}
	
			
}
