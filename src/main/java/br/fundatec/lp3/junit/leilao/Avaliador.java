package br.fundatec.lp3.junit.leilao;

public class Avaliador {
	
	//seta o valor inicial da variavel com o menor valor possível
	private Lance maiorLance;
	
	public Avaliador() {
		maiorLance = new Lance(new Usuario(""), Double.NEGATIVE_INFINITY);
	}
	
	public void avalia(Leilao leilao) {
		
		for(Lance lance : leilao.getLances()) {
			
			if(lance.getValor() > maiorLance.getValor()) {
				maiorLance = lance;
			}
		}
	}
	
	public Lance getMaiorLance() {
		return maiorLance;
	}

}
