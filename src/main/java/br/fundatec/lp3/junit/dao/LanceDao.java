package br.fundatec.lp3.junit.dao;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;
import br.fundatec.lp3.junit.leilao.Usuario;

public class LanceDao implements Dao {

	private JdbcTemplate jdbcTemplate;

	@Override
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public Lance create(Lance lance) {

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {

			UsuarioDao usuarios = (UsuarioDao) ctx.getBean("usuarioDao");
			Usuario usuario = usuarios.findByNome(lance.getProponente().getNome());

			jdbcTemplate.update(
				"insert into lances (proponente_id, valor) values (?, ?)",
				usuario.getId(),
				lance.getValor()
			);

			SqlRowSet lastCreated = jdbcTemplate.queryForRowSet("select * from lances order by id desc limit 1");
			lastCreated.next();
			int id = lastCreated.getInt("id");

			lance.setId(id);

			return lance;

		}

	}

	public Leilao getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void truncate() {
		jdbcTemplate.execute("delete from lances");
	}

}
