package br.fundatec.lp3.junit.encerrador;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.fundatec.lp3.junit.dao.LeilaoDao;
import br.fundatec.lp3.junit.dao.UsuarioDao;
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
