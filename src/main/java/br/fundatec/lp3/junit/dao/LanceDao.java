package br.fundatec.lp3.junit.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;
import br.fundatec.lp3.junit.leilao.Usuario;

public class LanceDao implements Dao {

	private JdbcTemplate jdbcTemplate;

	@Override
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public void create(Lance lance, Leilao leilao) {

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {

			UsuarioDao usuarios = (UsuarioDao) ctx.getBean("usuarioDao");
			Usuario usuario = usuarios.buscaComNome(lance.getProponente().getNome());

			jdbcTemplate.update(
				"insert into lances (proponente_id, valor, id_leilao) values (?, ?, ?)",
				usuario.getId(),
				lance.getValor(),
				leilao.getId()
			);

		}

	}

	public List<Lance> getByLeilao(Leilao leilao) {
		return jdbcTemplate.query("select * from lances where id_leilao = ?", new LanceRowMapper(), leilao.getId());
	}

	@Override
	public void limpa() {
		jdbcTemplate.execute("delete from lances");
	}

}
