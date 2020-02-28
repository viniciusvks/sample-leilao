package br.fundatec.lp3.junit.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;

@Repository
public class LeilaoDao implements Dao {

	private JdbcTemplate jdbcTemplate;

	public List<Leilao> ativos() {

		List<Leilao> leiloes = jdbcTemplate.query("select * from leiloes where encerrado = false", new LeilaoRowMapper());

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {

			LanceDao lanceDao = (LanceDao) ctx.getBean("lanceDao");

			for(Leilao leilao : leiloes) {

				List<Lance> lancesDoLeilao = lanceDao.getByLeilao(leilao);

				for (Lance lance : lancesDoLeilao) {
					leilao.propoe(lance);
				}

			}

		}

		return leiloes;
	}
	
	public void criaTabela() {
		String query = "create table leiloes\n" + 
				"(\n" + 
				"    id          int auto_increment primary key,\n" + 
				"    produto     varchar(255) null,\n" + 
				"    data_inicio date         null,\n" + 
				"    encerrado   tinyint(1)   null\n" + 
				");";
		jdbcTemplate.update(query);
	}

	@Override
	@Autowired
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public void cria(Leilao leilao) {

		jdbcTemplate.update(
			"insert into leiloes (produto, data_inicio, encerrado) values (?, ?, ?)",
			leilao.getProduto(),
			leilao.getData(),
			leilao.estaEncerrado()
		);

		SqlRowSet leiloes = jdbcTemplate.queryForRowSet("select * from leiloes order by id desc limit 1;");
		leiloes.next();
		int id = leiloes.getInt("id");

		leilao.setId(id);

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {

			LanceDao lanceDao = (LanceDao) ctx.getBean("lanceDao");

			List<Lance> lances = leilao.getLances();

			for(Lance lance : lances) {
				lanceDao.create(lance, leilao);
			}

		}

	}

	public void atualiza(Leilao leilao) throws Exception {
		
		jdbcTemplate.update(
			"update leiloes set produto = ?, data_inicio = ?, encerrado = ? where id = ?",
			leilao.getProduto(),
			leilao.getData(),
			leilao.estaEncerrado(),
			leilao.getId()
		);
		
	}

	public Leilao getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void limpa() {
		jdbcTemplate.execute("delete from leiloes");
	}

}
