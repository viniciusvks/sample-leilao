package br.fundatec.lp3.junit.encerrador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.fundatec.lp3.junit.dao.LeilaoDao;
import br.fundatec.lp3.junit.dao.UsuarioDao;
import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;
import br.fundatec.lp3.junit.leilao.Usuario;

public class EncerradorDeLeilaoTest {

	private static ClassPathXmlApplicationContext ctx;
	private static UsuarioDao tabelaUsuarios;
	private static LeilaoDao tabelaLeiloes;

	@BeforeClass
	public static void initAppContext() {

		ctx = new ClassPathXmlApplicationContext("beans.xml");
		tabelaUsuarios = (UsuarioDao) ctx.getBean("usuarioDao");
		tabelaLeiloes = (LeilaoDao) ctx.getBean("leilaoDao");

	}

	@AfterClass
	public static void closeAppContext() {

		ctx.close();

	}

	@Before
	public void setUp() {

		tabelaUsuarios.limpa();

		tabelaUsuarios.cria(new Usuario("Joao"));
		tabelaUsuarios.cria(new Usuario("Maria"));
		tabelaUsuarios.cria(new Usuario("Jose"));

	}

	@After
	public void tearDown() {

		tabelaUsuarios.limpa();
		tabelaLeiloes.limpa();

	}

	@Test
	public void testaEncerramentoParaMultiplosLeiloes() throws Exception {
 
		Usuario joao = tabelaUsuarios.buscaComNome("Joao");
		Usuario maria = tabelaUsuarios.buscaComNome("Maria");
		
		Leilao leilaoVideoGame = new Leilao("Video Game");
		leilaoVideoGame.setData(diasAtras(0));
		Lance lanceDoJoao = new Lance(joao, 10.0);
		leilaoVideoGame.propoe(lanceDoJoao);
		tabelaLeiloes.cria(leilaoVideoGame);

		Leilao leilaoComputador = new Leilao("Computador");
		leilaoComputador.setData(diasAtras(30));
		Lance lanceDaMaria = new Lance(maria, 20.0);
		leilaoComputador.propoe(lanceDaMaria);
		tabelaLeiloes.cria(leilaoComputador);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(tabelaLeiloes);
		
		encerrador.encerraLeiloesAntigos();
		
		List<Leilao> leiloesAtivos = tabelaLeiloes.ativos();
		int numeroEsperadoDeLeiloesEncerrados = 1;

		assertEquals(1, leiloesAtivos.size());
		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());
		
	}

	@Test
	public void testaEncerramentoParaListaVaziaDeLeiloes() throws Exception {
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(tabelaLeiloes);
		encerrador.encerraLeiloesAntigos();

		int numeroEsperadoDeLeiloesEncerrados = 0;

		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());

	}

	@Test
	public void testaEncerramentoParaLeiloesPrestesASeremEncerrados() throws Exception {

		Usuario joao = tabelaUsuarios.buscaComNome("Joao");
		Usuario maria = tabelaUsuarios.buscaComNome("Maria");
		
		Leilao leilaoVideoGame = new Leilao("Video Game");
		leilaoVideoGame.setData(diasAtras(6));
		Lance lanceDoJoao = new Lance(joao, 10.0);
		leilaoVideoGame.propoe(lanceDoJoao);
		tabelaLeiloes.cria(leilaoVideoGame);

		Leilao leilaoComputador = new Leilao("Computador");
		leilaoComputador.setData(diasAtras(7));
		Lance lanceDaMaria = new Lance(maria, 20.0);
		leilaoComputador.propoe(lanceDaMaria);
		tabelaLeiloes.cria(leilaoComputador);
		
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(tabelaLeiloes);
		
		encerrador.encerraLeiloesAntigos();
		
		List<Leilao> leiloesAtivos = tabelaLeiloes.ativos();
		int numeroEsperadoDeLeiloesEncerrados = 1;

		assertEquals(1, leiloesAtivos.size());
		assertEquals(numeroEsperadoDeLeiloesEncerrados, encerrador.getTotalEncerrados());
		
	}

	@Test
	public void testaEncerramentoParaLeilaoSemLances() {
 
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(tabelaLeiloes);
		
		try {
		
			Leilao leilaoVideoGame = new Leilao("Video Game");
			leilaoVideoGame.setData(diasAtras(10));
			tabelaLeiloes.cria(leilaoVideoGame);
			
			encerrador.encerraLeiloesAntigos();
			fail("Excecao nao lancada");
			
		} catch (Exception e) {
			String mensagemEsperada = "Leilao deve ter pelo menos um lance para ser encerrado";
			assertEquals(mensagemEsperada, e.getMessage());
		}

	}
	
	private LocalDate diasAtras(int dias) {
		return LocalDate.now().minusDays(dias);
	}

}
