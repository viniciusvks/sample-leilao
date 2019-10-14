package br.fundatec.lp3.junit.leilao.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.fundatec.lp3.junit.leilao.Lance;
import br.fundatec.lp3.junit.leilao.Leilao;

@Repository
public class LeilaoDaoImpl implements LeilaoDao {

	private JdbcTemplate jdbcTemplate;

	public List<Leilao> correntes() {
		return new ArrayList<Leilao>();
	}

	@Override
	@Autowired
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public boolean create(Leilao leilao) {

		String findUsuario = "select * from usuarios where nome = ?";
		String findLastLance = "select * from lances order by id desc limit 1";
		String newLeilao = "insert into leiloes (produto, data_inicio, encerrado) values (?, ?, ?)";
		String newLance = "insert into lance (proponente_id, valor) values (?, ?)";
		String findLastLeilao = "select * from leiloes order by id desc limit 1";
		String linkLeilaoToLance = "insert into lances_leiloes (id_leilao, id_lance) values (?, ?)";

		List<Integer> lancesIds = new ArrayList<Integer>();

		List<Lance> lances = leilao.getLances();

		for(Lance lance : lances) {

			String nome = lance.getProponente().getNome();
			int usuarioId = jdbcTemplate.queryForRowSet(findUsuario, nome).getInt("id");
			jdbcTemplate.update(newLance, usuarioId, lance.getValor());
			int lanceId = jdbcTemplate.queryForRowSet(findLastLance).getInt("id");
			lancesIds.add(new Integer(lanceId));

		}

		jdbcTemplate.update(newLeilao, leilao.getProduto(), leilao.getData(), leilao.estaEncerrado());
		int leilaoId = jdbcTemplate.queryForRowSet(findLastLeilao).getInt("id");

		for(Integer lanceId : lancesIds) {
			jdbcTemplate.update(linkLeilaoToLance, leilaoId, lanceId.intValue());
		}

		return true;

	}

	@Override
	public boolean update(Leilao leilao) {
//		if(leilao.getLances().isEmpty()) {
//			throw new Exception("Erro ao atualizar Leilao: deve ter pelo menos um lance");
//		}
		return false;
	}

	@Override
	public boolean delete(Leilao leilao) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Leilao getLeilao(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Leilao> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void truncate() {
		jdbcTemplate.execute("delete from lances_leiloes");
		jdbcTemplate.execute("delete from lances");
		jdbcTemplate.execute("delete from usuarios");
		jdbcTemplate.execute("delete from leiloes");
	}

}
