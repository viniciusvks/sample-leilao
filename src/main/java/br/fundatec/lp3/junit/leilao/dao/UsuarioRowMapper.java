package br.fundatec.lp3.junit.leilao.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.fundatec.lp3.junit.leilao.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Usuario(rs.getString("nome"));
	}

}
