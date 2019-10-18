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
	
	public void criaTabela() {
		String query = "create table usuarios(id   int auto_increment,nome varchar(255) not null,constraint usuarios_pk unique (id))";
		jdbcTemplate.update(query);
	}

	public boolean cria(Usuario usuario) {
		String query = "insert into usuarios (nome) values (?)";
		return jdbcTemplate.update(query, usuario.getNome()) == 1;
	}

	public Usuario buscaComNome(String nome) {
		String query = "select * from usuarios where nome = ?";
		return jdbcTemplate.queryForObject(query, new UsuarioRowMapper(), nome);
	}

	public Usuario buscaPorId(int id) {
		String query = "select * from usuarios where id = ?";
		return jdbcTemplate.queryForObject(query, new UsuarioRowMapper(), id);
	}

	@Override
	public void limpa() {
		jdbcTemplate.execute("delete from usuarios");
	}

}
