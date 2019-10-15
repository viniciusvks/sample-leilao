package br.fundatec.lp3.junit.leilao;

public class Lance {

	private int id;
	private Usuario proponente;
	private double valor;

	public Lance(Usuario proponente, double valor) {
		this.proponente = proponente;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public Usuario getProponente() {
		return proponente;
	}

	@Override
	public String toString() {
		return String.format("Proponente: %s. Valor: %.2f", proponente.getNome(), valor);
	}

}
