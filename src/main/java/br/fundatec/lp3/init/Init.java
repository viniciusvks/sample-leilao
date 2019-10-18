package br.fundatec.lp3.init;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.fundatec.lp3.junit.dao.LanceDao;
import br.fundatec.lp3.junit.dao.LeilaoDao;
import br.fundatec.lp3.junit.dao.UsuarioDao;

public class Init {

	public static void main(String[] args) {
		
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {
			
			UsuarioDao tabelaUsuarios = (UsuarioDao) ctx.getBean("usuarioDao");
			LanceDao tabelaLances = (LanceDao) ctx.getBean("lanceDao");
			LeilaoDao tabelaLeiloes = (LeilaoDao) ctx.getBean("leilaoDao");
			
			tabelaUsuarios.criaTabela();
			tabelaLeiloes.criaTabela();
			tabelaLances.criaTabela();
			
		}

	}

}
