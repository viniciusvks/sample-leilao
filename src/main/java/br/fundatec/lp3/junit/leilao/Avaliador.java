package br.fundatec.lp3.junit.leilao;

public class Avaliador {

	//seta o valor inicial da variavel com o menor valor possível
	private Lance maiorLance;
	private Lance menorLance;

	public Avaliador() {
		maiorLance = new Lance(new Usuario(""), Double.NEGATIVE_INFINITY);
		menorLance = new Lance(new Usuario(""), Double.POSITIVE_INFINITY);
	}

	public void avalia(Leilao leilao) throws Exception {

		if(leilao.getLances().isEmpty()) {
			throw new Exception("Leilao deve conter pelo menos um lance");
		}

		for(Lance lance : leilao.getLances()) {

			if(lance.getValor() > maiorLance.getValor()) {
				maiorLance = lance;
			}
			if (lance.getValor() < menorLance.getValor()) {
				menorLance = lance;
			}
		}
	}

	public Lance getMaiorLance() {
		return maiorLance;
	}

	public Lance getMenorLance() {
		return menorLance;
	}

}
