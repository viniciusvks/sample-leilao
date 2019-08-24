package br.fundatec.lp3.junit.leilao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AvaliadorTest {
	
	private Usuario joao;
	private Usuario maria;
	private Usuario jose;
	private Leilao leilao;
	private Lance lanceDoJoao;
	private Lance lanceDaMaria;
	private Lance lanceDoJose;
	
	@Before
	public void setUp() {
		joao = new Usuario("Joao");
		maria = new Usuario("Maria");
		jose = new Usuario("Jose");
		leilao = new Leilao("Playstation 4 Novo");	
		lanceDoJoao = new Lance(joao, 300.0);
		lanceDaMaria = new Lance(maria, 400.0);
		lanceDoJose = new Lance(jose, 250.0);
	}

	@Test
	public void deveReceberLancesEmOrdemCrescente() throws Exception {
		
    	leilao.propoe(lanceDoJose);
    	leilao.propoe(lanceDoJoao);
    	leilao.propoe(lanceDaMaria);
    	
    	Avaliador avaliador = new Avaliador();
    	avaliador.avalia(leilao);
    	
    	double menorValorEsperado = 250.0;
    	double maiorValorEsperado = 400.0;
    	double valorMenorLance = avaliador.getMenorLance().getValor();
    	double valorMaiorLance = avaliador.getMaiorLance().getValor();
    	    	
    	assertEquals(menorValorEsperado, valorMenorLance, 0);
    	assertEquals(maiorValorEsperado, valorMaiorLance, 0);

	}
	
	@Test
	public void deveEntenderLancesEmOrdemDecrescente() throws Exception {
    	
    	leilao.propoe(lanceDaMaria);
    	leilao.propoe(lanceDoJoao);
    	leilao.propoe(lanceDoJose);
    	
    	Avaliador avaliador = new Avaliador();
    	avaliador.avalia(leilao);
    	
    	double menorValorEsperado = 250.0;
    	double maiorValorEsperado = 400.0;
    	double valorMenorLance = avaliador.getMenorLance().getValor();
    	double valorMaiorLance = avaliador.getMaiorLance().getValor();
    	    	
    	assertEquals(menorValorEsperado, valorMenorLance, 0);
    	assertEquals(maiorValorEsperado, valorMaiorLance, 0);
		
	}
	
	@Test
	public void deveEntenderLancesSemOrdemDefinida() throws Exception {
    	
    	leilao.propoe(lanceDoJoao);
    	leilao.propoe(lanceDoJose);
    	leilao.propoe(lanceDaMaria);
    	
    	Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);
    	
    	double valorMenorLance = avaliador.getMenorLance().getValor();
    	double valorMaiorLance = avaliador.getMaiorLance().getValor();
    	double menorValorEsperado = 250.0;
    	double maiorValorEsperado = 400.0;
    	    	
    	assertEquals(menorValorEsperado, valorMenorLance, 0);
    	assertEquals(maiorValorEsperado, valorMaiorLance, 0);
	
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() throws Exception {
    	
    	leilao.propoe(lanceDoJoao);
    	
    	Avaliador avaliador = new Avaliador();	
    	avaliador.avalia(leilao);
    	
		int quantidadeEsperadaDeLances = 1;
		double valorMenorLance = avaliador.getMenorLance().getValor();
    	double valorMaiorLance = avaliador.getMaiorLance().getValor();
    	List<Lance> lances = leilao.getLances();
		
    	assertFalse(lances.isEmpty());
    	assertEquals(quantidadeEsperadaDeLances, lances.size());
    	assertEquals(valorMenorLance, valorMaiorLance, 0);
 
	}
	
	@Test(expected=Exception.class)
	public void deveLancarExcecaoSeLeilaoNaoTiverLances() throws Exception {
		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);		
	}
	
	@Test
	public void excecaoDeveConterMensagemCorretaSeLeilaoEstiverVazio(){
		
		Avaliador avaliador = new Avaliador();
		String mensagemEsperada = "Leilao deve conter pelo menos um lance";
		
		try {
			avaliador.avalia(leilao);
		} catch (Exception e) {
			assertEquals(mensagemEsperada, e.getMessage());
		}
	}

}
