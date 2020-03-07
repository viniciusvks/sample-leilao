package br.fundatec.lp3.junit.leilao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LeilaoTest {

	@Test
	public void deveArmazenarUmLance() {
		
		Leilao leilao = new Leilao("Playstation 4");
		assertTrue(leilao.getLances().isEmpty());
		
		Lance lance = new Lance(new Usuario("joao"), 2000);
		leilao.propoe(lance);
		
		List<Lance> lances = leilao.getLances();
		assertFalse(lances.isEmpty());
		int numeroDeLancesEsperado = 1;
		int numeroDeLancesSalvos = lances.size();
		assertEquals(numeroDeLancesEsperado, numeroDeLancesSalvos);
		Lance lanceSalvo = lances.get(0);
		assertEquals(lance.getProponente(), lanceSalvo.getProponente());
		assertEquals(lance.getValor(), lanceSalvo.getValor(), 0);
		
	}
	
	
	@Test
	public void deveArmazenarVariosLance() {
		
		Leilao leilao = new Leilao("Playstation 4");
		assertTrue(leilao.getLances().isEmpty());
		
		Lance primeiroLance = new Lance(new Usuario("joao"), 2000);
		Lance segundoLance = new Lance(new Usuario("maria"), 3000);
		leilao.propoe(primeiroLance);
		leilao.propoe(segundoLance);
		
		List<Lance> lances = leilao.getLances();
		
		assertFalse(lances.isEmpty());
		
		int numeroDeLancesEsperado = 2;
		int numeroDeLancesSalvos = lances.size();
		assertEquals(numeroDeLancesEsperado, numeroDeLancesSalvos);
		
		Lance primeiroLanceSalvo = lances.get(0);
		Lance segundoLanceSalvo = lances.get(1);
		
		assertEquals(primeiroLance.getProponente(), primeiroLanceSalvo.getProponente());
		assertEquals(primeiroLance.getValor(), primeiroLanceSalvo.getValor(), 0);
		
		assertEquals(segundoLance.getProponente(), segundoLanceSalvo.getProponente());
		assertEquals(segundoLance.getValor(), segundoLanceSalvo.getValor(), 0);
		
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		
		Leilao leilao = new Leilao("Playstation 4");
		Usuario fulano = new Usuario("fulano");
		
		Lance primeiroLance = new Lance(fulano, 2000);
		Lance segundoLance = new Lance(fulano, 3000);
		
		leilao.propoe(primeiroLance);
		leilao.propoe(segundoLance);
		
		int numeroDeLancesEsperado = 1;
		int numeroDeLances = leilao.getLances().size();
		
		assertEquals(numeroDeLancesEsperado, numeroDeLances);
		
	}
	
	@Test
	public void naoDeveAceitarMaisDoQueCincoLancesDoMesmoUsuario() {
		
		Leilao leilao = new Leilao("Playstation 4");
		Usuario fulano = new Usuario("fulano");
		Usuario beltrano = new Usuario("beltrano");
		
		leilao.propoe(new Lance(fulano, 2000.0));
		leilao.propoe(new Lance(beltrano, 3000.0));
		leilao.propoe(new Lance(fulano, 2000.0));
		leilao.propoe(new Lance(beltrano, 3000.0));
		leilao.propoe(new Lance(fulano, 2000.0));
		leilao.propoe(new Lance(beltrano, 3000.0));
		leilao.propoe(new Lance(fulano, 2000.0));
		leilao.propoe(new Lance(beltrano, 3000.0));
		leilao.propoe(new Lance(fulano, 2000.0));
		leilao.propoe(new Lance(beltrano, 3000.0));
		
		leilao.propoe(new Lance(fulano, 2000.0));
		leilao.propoe(new Lance(beltrano, 3000.0));
		
		List<Lance> lances = leilao.getLances();
		int numeroEsperadoDeLances = 10;
		int numeroDeLances = lances.size();
		
		int numeroEsperadoDeLancesDoFulano = 5;
		int numeroEsperadoDeLancesDoBeltrano = 5;
		int numeroDeLancesDoFulano = numeroDeLancesDoProponente(lances, fulano);
		int numeroDeLancesDoBeltrano = numeroDeLancesDoProponente(lances, fulano);
		
		assertEquals(numeroEsperadoDeLances, numeroDeLances);
		assertEquals(numeroEsperadoDeLancesDoFulano, numeroDeLancesDoFulano);
		assertEquals(numeroEsperadoDeLancesDoBeltrano, numeroDeLancesDoBeltrano);
		
	}
	
	private int numeroDeLancesDoProponente(List<Lance> lances, Usuario proponente) {
		
		int numeroDeLances = 0;
		
		for(Lance lance : lances) {
			
			Usuario proponenteDoLance = lance.getProponente();
			
			if(proponenteDoLance.getNome().equals(proponente.getNome())) {
				numeroDeLances ++;
			}
			
		}
		
		return numeroDeLances;
		
	}
	

}
