package br.fundatec.lp3.junit.encerrador;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import br.fundatec.lp3.junit.leilao.Leilao;

public class EncerradorDeLeilaoTest {

	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
		
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);
		Leilao leilaoTv = new Leilao("TV de plasma");
		leilaoTv.setData(antiga);
		
		Leilao leilaoGeladeira = new Leilao("Geladeira");
		leilaoGeladeira.setData(antiga);
		
		assertTrue(leilaoTv.estaEncerrado());
		assertTrue(leilaoGeladeira.estaEncerrado());
		
	}

}
