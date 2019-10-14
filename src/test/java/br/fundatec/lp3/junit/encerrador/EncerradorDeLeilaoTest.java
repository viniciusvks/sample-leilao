package br.fundatec.lp3.junit.encerrador;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.fundatec.lp3.junit.leilao.Leilao;
import br.fundatec.lp3.junit.leilao.dao.LeilaoDaoImpl;

public class EncerradorDeLeilaoTest {

	@BeforeClass
	public void setUp() {

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {
			LeilaoDaoImpl dao = (LeilaoDaoImpl) ctx.getBean("dao");
			dao.truncate();
		}

	}

	@Test
	public void testTruncate() {
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {
			LeilaoDaoImpl dao = (LeilaoDaoImpl) ctx.getBean("dao");
			dao.truncate();
		}
	}

	@Test
	public void test() {

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {

			Leilao leilao = new Leilao("Novo Produto");
			leilao.setData(LocalDate.now());

			LeilaoDaoImpl dao = (LeilaoDaoImpl) ctx.getBean("dao");
			assertTrue(dao.create(leilao));
		}

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

	@Test(expected=Exception.class)
	public void testaEncerramentoParaLeilaoInvalido() throws Exception {


	}

}
