package br.fundatec.lp3.junit.leilao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leilao {

	private List<Lance> lances;
	private String produto;
	private final Integer NUMERO_MAXIMO_LANCES_POR_PROPONENTE = 5;
	private Map<String, Integer> lancesDoProponente;
	private Calendar data;
	private boolean estaEncerrado;

	public Leilao(String produto) {
		lances = new ArrayList<Lance>();
		lancesDoProponente = new HashMap<>();
		this.produto = produto;
	}

	public void propoe(Lance novoLance) {

		if(novoLanceEhValido(novoLance)) {
			lances.add(novoLance);
			registraNumeroDeLancesDoProponente(novoLance.getProponente());
		}

	}

	private void registraNumeroDeLancesDoProponente(Usuario proponente) {
		Integer numeroDeLances = lancesDoProponente.getOrDefault(proponente.getNome(), 0) + 1;
		lancesDoProponente.put(proponente.getNome(), numeroDeLances);
	}

	private boolean novoLanceEhValido(Lance novoLance) {

		if(lances.isEmpty()) {
			return true;
		}

		Usuario proponente = novoLance.getProponente();

		if(lanceTemMesmoProponente(proponente)) {
			return false;
		}

		if(excedeLancesPorProponente(proponente)) {
			return false;
		}

		return true;

	}

	private boolean lanceTemMesmoProponente(Usuario proponente) {
		Lance ultimoLance = lances.get(lances.size() - 1);
		Usuario proponenteDoUltimoLance = ultimoLance.getProponente();
		return proponenteDoUltimoLance.getNome().equals(proponente.getNome());
	}

	private boolean excedeLancesPorProponente(Usuario proponente) {
		Integer numeroDeLancesJaRegistrados = lancesDoProponente.getOrDefault(proponente.getNome(), 0);
		return numeroDeLancesJaRegistrados >= NUMERO_MAXIMO_LANCES_POR_PROPONENTE;
	}

	public List<Lance> getLances() {
		return lances;
	}

	public String getProduto() {
		return produto;
	}

	public void encerra() {
		estaEncerrado = true;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public boolean estaEncerrado() {
		return estaEncerrado;
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
