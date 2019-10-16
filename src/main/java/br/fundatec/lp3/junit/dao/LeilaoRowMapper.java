package br.fundatec.lp3.junit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import br.fundatec.lp3.junit.leilao.Leilao;

public class LeilaoRowMapper implements RowMapper<Leilao> {

	@Override
	public Leilao mapRow(ResultSet rs, int rowNum) throws SQLException {

		Leilao leilao = new Leilao(rs.getString("produto"));

		leilao.setId(rs.getInt("id"));
		Date dataInicio = rs.getDate("data_inicio");
		String strDate = new SimpleDateFormat("dd/MM/yyyy").format(dataInicio);
		
		LocalDate fmtDataInicio = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		leilao.setData(fmtDataInicio);
		
		if(rs.getBoolean("encerrado")) {
			leilao.encerra();
		}

		return leilao;
	}

}
