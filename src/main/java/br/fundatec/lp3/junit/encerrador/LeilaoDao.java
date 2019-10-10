package br.fundatec.lp3.junit.encerrador;

import java.util.ArrayList;
import java.util.List;

import br.fundatec.lp3.junit.leilao.Leilao;

public class LeilaoDao {

	public List<Leilao> correntes() {
		return new ArrayList<Leilao>();
	}

	public void atualiza(Leilao leilao) throws Exception {

		if(leilao.getLances().isEmpty()) {
			throw new Exception("Erro ao atualizar Leilao: deve ter pelo menos um lance");
		}

	}

}
