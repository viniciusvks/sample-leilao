package br.fundatec.lp3.junit.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.fundatec.lp3.junit.leilao.Usuario;

public class UsuarioDao implements Dao {

	private JdbcTemplate jdbcTemplate;

	@Override
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public boolean create(Usuario usuario) {
		String query = "insert into usuarios (nome) values (?)";
		return jdbcTemplate.update(query, usuario.getNome()) == 1;
	}

	public Usuario findByNome(String nome) {
		String query = "select * from usuarios where nome = ?";
		return jdbcTemplate.queryForObject(query, new UsuarioRowMapper(), nome);
	}

	public Usuario findById(int id) {
		String query = "select * from usuarios where id = ?";
		return jdbcTemplate.queryForObject(query, new UsuarioRowMapper(), id);
	}

	@Override
	public void truncate() {
		jdbcTemplate.execute("delete from usuarios");
	}

}
