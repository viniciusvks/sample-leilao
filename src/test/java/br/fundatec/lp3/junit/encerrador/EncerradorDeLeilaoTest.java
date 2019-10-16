package br.fundatec.lp3.junit.encerrador;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

		tabelaUsuarios.truncate();

		tabelaUsuarios.create(new Usuario("Joao"));
		tabelaUsuarios.create(new Usuario("Maria"));
		tabelaUsuarios.create(new Usuario("Jose"));

	}

	@After
	public void tearDown() {

		tabelaUsuarios.truncate();
		tabelaLeiloes.truncate();

	}

	@Test
	public void test() {

		UsuarioDao usuarios = (UsuarioDao) ctx.getBean("usuarioDao");

		Usuario joao = usuarios.findByNome("Joao");
		Usuario maria = usuarios.findByNome("Maria");
		Usuario jose = usuarios.findByNome("Jose");

		Lance lanceDoJoao = new Lance(joao, 10.0);
		Lance lanceDaMaria = new Lance(maria, 20.0);
		Lance lanceDoJose = new Lance(jose, 30.0);

		Leilao leilao = new Leilao("Novo Produto");
		leilao.setData(LocalDate.now());

		leilao.propoe(lanceDoJoao);
		leilao.propoe(lanceDaMaria);
		leilao.propoe(lanceDoJose);

		LeilaoDao leiloes = (LeilaoDao) ctx.getBean("leilaoDao");

		leiloes.create(leilao);

	}

	@Test
	public void testaEncerramentoParaMultiplosLeiloes() throws Exception {

	}

	@Test
	public void testaEncerramentoParaListaVaziaDeLeiloes() throws Exception {

	}

	@Test
	public void testaEncerramentoParaLeiloesPrestesASeremEncerrados() throws Exception {

	}

	@Ignore
	public void testaEncerramentoParaLeilaoInvalido() throws Exception {


	}

}
