package br.fundatec.lp3.junit.dao;

import java.util.ArrayList;
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

	public List<Leilao> correntes() {
		return new ArrayList<Leilao>();
	}

	@Override
	@Autowired
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public boolean create(Leilao leilao) {

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
				lance = lanceDao.create(lance);
				attach(leilao, lance);
			}

		}

		return true;

	}


	private void attach(Leilao leilao, Lance lance) {

		jdbcTemplate.update(
			"insert into lances_leiloes (id_leilao, id_lance) values (?, ?)",
			leilao.getId(),
			lance.getId()
		);
	}

	public boolean update(Leilao leilao) {
//		if(leilao.getLances().isEmpty()) {
//			throw new Exception("Erro ao atualizar Leilao: deve ter pelo menos um lance");
//		}
		return false;
	}

	public boolean delete(Leilao leilao) {
		// TODO Auto-generated method stub
		return false;
	}

	public Leilao getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void truncate() {
		jdbcTemplate.execute("delete from lances_leiloes");
		jdbcTemplate.execute("delete from lances");
		jdbcTemplate.execute("delete from leiloes");
	}

}
