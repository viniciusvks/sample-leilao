package br.fundatec.lp3.junit.leilao;

public class Usuario {

	private int id;
	private String nome;

	public Usuario(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "ID: "+id+", Nome: " + nome;
	}

}
