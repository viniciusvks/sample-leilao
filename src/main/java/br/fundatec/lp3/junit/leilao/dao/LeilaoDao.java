package br.fundatec.lp3.junit.leilao.dao;

import java.util.List;

import javax.sql.DataSource;

import br.fundatec.lp3.junit.leilao.Leilao;

public interface LeilaoDao {

	public void setDataSource(DataSource ds);
	public boolean create(Leilao leilao);
	public boolean update(Leilao leilao);
	public boolean delete(Leilao leilao);
	public Leilao getLeilao(Integer id);
	public List<Leilao> getAll();
	public void truncate();

}
