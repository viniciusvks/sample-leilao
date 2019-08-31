package br.fundatec.lp3.junit.leilao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LeilaoTest {

	@Test
	public void deveArmazenarUmLance() {

		Leilao leilao = new Leilao("Playstation 4");
		assertTrue(leilao.getLances().isEmpty());

		Lance primeiroLance = new Lance(new Usuario("Joao"), 2000.0);
		leilao.propoe(primeiroLance);
		List<Lance> lances = leilao.getLances();
		int quantidadeDeLancesEsperada = 1;

		assertEquals(quantidadeDeLancesEsperada, lances.size());

	}

	@Test
	public void deveArmazenarMaisDeUmLance() {

		Leilao leilao = new Leilao("Playstation 4");
		assertTrue(leilao.getLances().isEmpty());

		Lance primeiroLance = new Lance(new Usuario("Joao"), 2000.0);
		Lance segundoLance = new Lance(new Usuario("Maria"), 3000.0);

		leilao.propoe(primeiroLance);
		leilao.propoe(segundoLance);

		List<Lance> lances = leilao.getLances();
		int quantidadeDeLanceEsperada = 2;

		assertEquals(quantidadeDeLanceEsperada, lances.size());

	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {

		Leilao leilao = new Leilao("Playstation 4");
		Usuario joao = new Usuario("Joao");
		Lance lanceDoJoao = new Lance(joao, 2000.0);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDoJoao);

		List<Lance> lances = leilao.getLances();
		int quantidadeDeLancesEsperada = 1;

		assertEquals(quantidadeDeLancesEsperada, lances.size());

	}

	@Test
	public void naoDeveAceitarMaisQueCincoLancesDeUmMesmoUsuario() {


		Leilao leilao = new Leilao("Playstation 4");
		Usuario joao = new Usuario("Joao");
		Usuario maria = new Usuario("maria");

		Lance lanceDoJoao = new Lance(joao, 2000.0);
		Lance lanceDaMaria = new Lance(maria, 3000.0);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);

		List<Lance> lances = leilao.getLances();
		int quantidadeEsperadaDeLances = 10;
		int numeroMaximoDeLances = 5;

		assertEquals(quantidadeEsperadaDeLances, lances.size());

		assertEquals(numeroMaximoDeLances, numeroDeLancesDoProponente(lances, joao));
		assertEquals(numeroMaximoDeLances, numeroDeLancesDoProponente(lances, maria));

	}

	private int numeroDeLancesDoProponente(List<Lance> listaDeLances, Usuario usuario) {

		int numeroDeLances = 0;

		for(Lance lance : listaDeLances) {
			Usuario proponenteDoLance = lance.getProponente();
			if(proponenteDoLance.getNome() == usuario.getNome()) {
				numeroDeLances ++;
			}
		}

		return numeroDeLances;

	}

}
