package br.com.zup.pascoaZup.dto;

public class LogisticaRequest {

	private String cepOrigem;
	private String cepDestino;
	private String modalidade;
	private String peso;
	private String comprimento;
	private String altura;
	private String largura;
	private String nomeCidadeOrigem;
	private String nomeCidadeDestino;
	
	public LogisticaRequest(String cepOrigem, String cepDestino, String modalidade, String peso,
			String comprimento, String altura, String largura, String nomeCidadeOrigem, String nomeCidadeDestino) {
		super();
	
		this.cepOrigem = cepOrigem;
		this.cepDestino = cepDestino;
		this.modalidade = modalidade;
		this.peso = peso;
		this.comprimento = comprimento;
		this.altura = altura;
		this.largura = largura;
		this.nomeCidadeOrigem = nomeCidadeOrigem;
		this.nomeCidadeDestino = nomeCidadeDestino;
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

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
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
