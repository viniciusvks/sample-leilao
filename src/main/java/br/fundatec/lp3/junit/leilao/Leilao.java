package br.fundatec.lp3.junit.leilao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leilao {

	private List<Lance> lances;
	private String produto;
	private Map<String, Integer> lancesDoUsuario;

	public Leilao(String produto) {
		lances = new ArrayList<Lance>();
		lancesDoUsuario = new HashMap<>();
		this.produto = produto;
	}

	public void propoe(Lance novoLance) {

		if(lances.isEmpty()) {

			String nome = novoLance.getProponente().getNome();

			Integer numeroDeLances = lancesDoUsuario.getOrDefault(nome, 0);
			if(numeroDeLances < 5) {
				lances.add(novoLance);
				lancesDoUsuario.put(nome, numeroDeLances+1);
			}

			return;
		}

		int posicaoUltimoLances = lances.size() - 1;
		Lance ultimoLance = lances.get(posicaoUltimoLances);

		Usuario proponenteLanceAtual = novoLance.getProponente();
		Usuario proponenteUltimoLance = ultimoLance.getProponente();

		if(proponenteLanceAtual.getNome() != proponenteUltimoLance.getNome()) {
			String nome = novoLance.getProponente().getNome();
			Integer numeroDeLances = lancesDoUsuario.getOrDefault(nome, 0);
			if(numeroDeLances < 5) {
				lances.add(novoLance);
				lancesDoUsuario.put(nome, numeroDeLances+1);
			}
		}

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
