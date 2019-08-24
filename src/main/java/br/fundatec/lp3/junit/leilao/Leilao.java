package br.fundatec.lp3.junit.leilao;

import java.util.ArrayList;
import java.util.List;

public class Leilao {

	private List<Lance> lances;
	private String produto;

	public Leilao(String produto) {
		lances = new ArrayList<Lance>();
		this.produto = produto;
	}

	public void propoe(Lance novoLance) {
		lances.add(novoLance);
	}

	public List<Lance> getLances() {
		return lances;
	}

	public String getProduto() {
		return produto;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Produto: " + produto + "\n")
					 .append("Lances: \n");

		for(Lance lance : lances) {
			stringBuilder.append(lance.toString()+"\n");
		}

		return stringBuilder.toString();

	}

}
