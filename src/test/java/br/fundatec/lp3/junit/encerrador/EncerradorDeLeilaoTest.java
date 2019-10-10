package br.fundatec.lp3.junit.encerrador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;
import br.fundatec.lp3.junit.leilao.Usuario;

public class EncerradorDeLeilaoTest {

	@Test
	public void testaEncerramentoParaMultiplosLeiloes() throws Exception {

		Leilao leilao1 = new Leilao("Produto 1");
		leilao1.setData(LocalDate.now().minusDays(10));
		leilao1.propoe(new Lance(new Usuario("fulano"), 10.0));

		Leilao leilao2 = new Leilao("Produto 2");
		leilao2.setData(LocalDate.now().minusDays(10));
		leilao1.propoe(new Lance(new Usuario("beltrano"), 20.0));

		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		listaDeLeiloes.add(leilao1);
		listaDeLeiloes.add(leilao2);

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.correntes()).thenReturn(listaDeLeiloes);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 2;

		assertTrue(leilao1.estaEncerrado());
		assertTrue(leilao2.estaEncerrado());
		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test
	public void testaEncerramentoParaListaVaziaDeLeiloes() throws Exception {

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.correntes()).thenReturn(new ArrayList<Leilao>());

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 0;

		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test
	public void testaEncerramentoParaLeiloesPrestesASeremEncerrados() throws Exception {

		Leilao leilao1 = new Leilao("Produto 1");
		leilao1.setData(LocalDate.now().minusDays(6));
		leilao1.propoe(new Lance(new Usuario("fulano"), 10.0));

		Leilao leilao2 = new Leilao("Produto 2");
		leilao2.setData(LocalDate.now().minusDays(7));
		leilao1.propoe(new Lance(new Usuario("beltrano"), 20.0));

		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		listaDeLeiloes.add(leilao1);
		listaDeLeiloes.add(leilao2);

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.correntes()).thenReturn(listaDeLeiloes);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 1;

		assertFalse(leilao1.estaEncerrado());
		assertTrue(leilao2.estaEncerrado());
		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test(expected=Exception.class)
	public void testaEncerramentoParaLeilaoInvalido() throws Exception {

		Leilao leilao1 = new Leilao("Produto 1");
		leilao1.setData(LocalDate.now().minusDays(6));
		leilao1.propoe(new Lance(new Usuario("fulano"), 10.0));

		Leilao leilao2 = new Leilao("Produto 2");
		leilao2.setData(LocalDate.now().minusDays(7));
		leilao1.propoe(new Lance(new Usuario("beltrano"), 20.0));

		List<Leilao> listaDeLeiloes = new ArrayList<Leilao>();
		listaDeLeiloes.add(leilao1);
		listaDeLeiloes.add(leilao2);

		LeilaoDao dao = Mockito.mock(LeilaoDao.class);
		Mockito.when(dao.correntes()).thenReturn(listaDeLeiloes);
		Exception exception = new Exception("Erro ao atualizar Leilao: deve ter pelo menos um lance");

		try {

			Mockito.doThrow(exception).when(dao).atualiza(Mockito.any(Leilao.class));
			EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
			encerrador.encerraLeiloesAntigos();

		} catch (Exception e) {
			assertEquals("Erro ao atualizar Leilao: deve ter pelo menos um lance", e.getMessage());
			throw e;
		}

	}

}
