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
		leilaoComputador.setData(diasAtras(0));
		leilaoComputador.propoe(lanceDaMaria);
		
		List<Leilao> listaLeiloesAtivos = new ArrayList<>();
		listaLeiloesAtivos.add(leilaoVideoGame);
		listaLeiloesAtivos.add(leilaoComputador);
		
//		LeilaoDao tabelaDeLeiloes = new LeilaoDao();
		LeilaoDao tabelaFalsa = Mockito.mock(LeilaoDao.class);
		Mockito.when(tabelaFalsa.ativos()).thenReturn(listaLeiloesAtivos);
		
		Mockito.doThrow(new NullPointerException("mensagem")).when(tabelaFalsa).atualiza(leilaoComputador);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(tabelaFalsa);
		encerrador.encerraLeiloesAntigos();
		
		int numeroDeLeiloesEncerrados = 1;
		
		assertTrue(leilaoVideoGame.estaEncerrado());
		assertFalse(leilaoComputador.estaEncerrado());
		assertEquals(numeroDeLeiloesEncerrados, encerrador.getTotalEncerrados());
		
	}

	@Test
	public void testaEncerramentoParaListaVaziaDeLeiloes() throws Exception {

		
	}

	@Test
	public void testaEncerramentoParaLeiloesPrestesASeremEncerrados() throws Exception {


	}

	@Test
	public void testaEncerramentoParaLeilaoSemLances() {


	}

	private LocalDate diasAtras(int dias) {
		return LocalDate.now().minusDays(dias);
	}

}
