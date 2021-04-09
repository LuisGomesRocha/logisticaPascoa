package br.com.zup.pascoaZup.domain;

public class Modalidade {
	
	private String nome;
	private Double valorFinal;
	private String tempoEntrega;
	
	public Modalidade(String nome, Double valorFinal, String tempoEntrega) {
		super();
		this.nome = nome;
		this.valorFinal = valorFinal;
		this.tempoEntrega = tempoEntrega;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValorFinal() {
		return valorFinal;
	}
	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}
	public String getTempoEntrega() {
		return tempoEntrega;
	}
	public void setTempoEntrega(String tempoEntrega) {
		this.tempoEntrega = tempoEntrega;
	}
	
	
	
	
}