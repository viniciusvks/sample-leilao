package br.fundatec.lp3.junit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Usuario;

public class LanceRowMapper implements RowMapper<Lance> {

	@Override
	public Lance mapRow(ResultSet rs, int rowNum) throws SQLException {

		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml")) {

			UsuarioDao usuarios = (UsuarioDao) ctx.getBean("usuarioDao");
			Usuario usuario = usuarios.buscaPorId(rs.getInt("proponente_id"));

			Lance lance = new Lance(usuario, rs.getDouble("valor"));
			lance.setId(rs.getInt("id"));
			return lance;

		}
	}

}
