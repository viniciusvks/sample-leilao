package br.fundatec.lp3.junit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

import org.springframework.jdbc.core.RowMapper;

import br.fundatec.lp3.junit.leilao.Leilao;

public class LeilaoRowMapper implements RowMapper<Leilao> {

	@Override
	public Leilao mapRow(ResultSet rs, int rowNum) throws SQLException {

		Leilao leilao = new Leilao(rs.getString("produto"));

		leilao.setData(
			rs.getDate("data_inicio")
			  .toInstant()
			  .atZone(ZoneId.systemDefault())
			  .toLocalDate()
		);

		if(rs.getBoolean("encerrado")) {
			leilao.encerra();
		}

		return leilao;
	}

}
