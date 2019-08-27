package br.fundatec.lp3.junit.leilao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class LeilaoTest {

	@Test
	public void deveArmazenarUmLance() {

		Leilao leilao = new Leilao("Macboook Pro 15");
		assertTrue(leilao.getLances().isEmpty());

		Lance primeiroLance = new Lance(new Usuario("Steve Jobs"), 2000);
		leilao.propoe(primeiroLance);

		assertFalse(leilao.getLances().isEmpty());
		Lance lanceSalvo = leilao.getLances().get(0);
		assertEquals(primeiroLance.getValor(), lanceSalvo.getValor(), 0);
		assertEquals(primeiroLance.getProponente(), lanceSalvo.getProponente());

	}

	@Test
	public void deveArmazenarMaisDeUmLance() {

		Leilao leilao = new Leilao("Macboook Pro 15");
		assertTrue(leilao.getLances().isEmpty());

		Lance primeiroLance = new Lance(new Usuario("Steve Jobs"), 2000);
		Lance segundoLance = new Lance(new Usuario("Steve Wozniak"), 3000);

		leilao.propoe(primeiroLance);
		leilao.propoe(segundoLance);

		assertEquals(2, leilao.getLances().size());

		Lance primeirolanceSalvo = leilao.getLances().get(0);
		Lance segundolanceSalvo = leilao.getLances().get(1);

		assertEquals(primeiroLance.getValor(), primeirolanceSalvo.getValor(), 0);
		assertEquals(primeiroLance.getProponente(), primeirolanceSalvo.getProponente());

		assertEquals(segundoLance.getValor(), segundolanceSalvo.getValor(), 0);
		assertEquals(segundoLance.getProponente(), segundolanceSalvo.getProponente());

	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {

		Leilao leilao = new Leilao("Macboook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");

		Lance primeiroLance = new Lance(steveJobs, 2000);
		Lance segundoLance = new Lance(steveJobs, 3000);

		leilao.propoe(primeiroLance);
		leilao.propoe(segundoLance);

		List<Lance> lances = leilao.getLances();

		assertEquals(1, lances.size());

		Lance primeiroLanceSalvo = lances.get(0);
		assertEquals(primeiroLance.getValor(), primeiroLanceSalvo.getValor(), 0);
		assertEquals(primeiroLance.getProponente(), steveJobs);

	}

	@Test
	public void naoDeveAceitarMaisDoQueCincoLancesDeUmMesmoUsuario() {

		Leilao leilao = new Leilao("Macboook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario steveWozniak = new Usuario("Steve Wozniak");

		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveWozniak, 3000.0));
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveWozniak, 3000.0));
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveWozniak, 3000.0));
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveWozniak, 3000.0));
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveWozniak, 3000.0));
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveWozniak, 3000.0));

		List<Lance> lances = leilao.getLances();

		assertEquals(10, lances.size());

		assertEquals(5, numeroDeLancesDoProponente(lances, steveJobs));
		assertEquals(5, numeroDeLancesDoProponente(lances, steveWozniak));

	}

	private int numeroDeLancesDoProponente(List<Lance> lances, Usuario proponente) {

		int numeroDeLances = 0;

		for(Lance lance : lances) {

			Usuario proponenteDoLance = lance.getProponente();

			if(proponenteDoLance.getNome().equals(proponente.getNome())) {
				numeroDeLances++;
			}
		}

		return numeroDeLances;
	}

}
