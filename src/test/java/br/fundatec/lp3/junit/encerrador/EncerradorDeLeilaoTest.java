package br.fundatec.lp3.junit.encerrador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import br.fundatec.lp3.junit.dao.LeilaoDao;
import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;
import br.fundatec.lp3.junit.leilao.Usuario;

public class EncerradorDeLeilaoTest {

	@Test
	public void testaEncerramentoParaMultiplosLeiloes() throws Exception {

		Usuario joao = new Usuario("Joao");
		Lance lanceDoJoao = new Lance(joao, 10.0);
		Leilao leilaoVideoGame = new Leilao("Video Game");
		leilaoVideoGame.setData(diasAtras(10));
		leilaoVideoGame.propoe(lanceDoJoao);

		Usuario maria = new Usuario("Maria");
		Lance lanceDaMaria = new Lance(maria, 20.0);
		Leilao leilaoComputador = new Leilao("Computador");
		leilaoComputador.setData(diasAtras(10));
		leilaoComputador.propoe(lanceDaMaria);

		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		listaDeLeiloes.add(leilaoVideoGame);
		listaDeLeiloes.add(leilaoComputador);

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.ativos()).thenReturn(listaDeLeiloes);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 2;

		assertTrue(leilaoVideoGame.estaEncerrado());
		assertTrue(leilaoComputador.estaEncerrado());
		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test
	public void testaEncerramentoParaListaVaziaDeLeiloes() throws Exception {

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.ativos()).thenReturn(new ArrayList<Leilao>());

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 0;

		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test
	public void testaEncerramentoParaLeiloesPrestesASeremEncerrados() throws Exception {

		Usuario joao = new Usuario("Joao");
		Lance lanceDoJoao = new Lance(joao, 10.0);
		Leilao leilaoVideoGame = new Leilao("Video Game");
		leilaoVideoGame.setData(diasAtras(6));
		leilaoVideoGame.propoe(lanceDoJoao);

		Usuario maria = new Usuario("Maria");
		Lance lanceDaMaria = new Lance(maria, 20.0);
		Leilao leilaoComputador = new Leilao("Computador");
		leilaoComputador.setData(diasAtras(7));
		leilaoComputador.propoe(lanceDaMaria);

		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		listaDeLeiloes.add(leilaoVideoGame);
		listaDeLeiloes.add(leilaoComputador);

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.ativos()).thenReturn(listaDeLeiloes);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 1;

		assertFalse(leilaoVideoGame.estaEncerrado());
		assertTrue(leilaoComputador.estaEncerrado());
		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test(expected=Exception.class)
	public void testaEncerramentoParaLeilaoSemLances() throws Exception {

		Leilao leilaoVideoGame = new Leilao("Video Game");
		leilaoVideoGame.setData(diasAtras(6));

		Leilao leilaoComputador = new Leilao("Computador");
		leilaoComputador.setData(diasAtras(7));

		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		listaDeLeiloes.add(leilaoVideoGame);
		listaDeLeiloes.add(leilaoComputador);

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.ativos()).thenReturn(listaDeLeiloes);

		try {

			EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
			encerrador.encerraLeiloesAntigos();

		} catch (Exception e) {
			assertEquals("Leilao deve ter pelo menos um lance para ser encerrado", e.getMessage());
			throw e;
		}

	}

	private LocalDate diasAtras(int dias) {
		return LocalDate.now().minusDays(dias);
	}

}
