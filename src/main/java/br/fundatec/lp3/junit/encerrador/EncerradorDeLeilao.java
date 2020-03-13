package br.fundatec.lp3.junit.encerrador;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.fundatec.lp3.junit.dao.LeilaoDao;
import br.fundatec.lp3.junit.leilao.Leilao;

public class EncerradorDeLeilao {

	private LeilaoDao tabelaLeilao;
	private int total = 0;

	public EncerradorDeLeilao(LeilaoDao tabelaLeilao) {
		this.tabelaLeilao = tabelaLeilao;
	}

	public void encerraLeiloesAntigos() throws Exception {

		List<Leilao> todosLeiloesCorrentes = tabelaLeilao.ativos();

		for(Leilao leilao : todosLeiloesCorrentes) {

			if(leilao.getLances().isEmpty()) {
				throw new Exception("Leilao deve ter pelo menos um lance para ser encerrado");
			}
			
			if(comecouSemanaPassada(leilao)) {
				leilao.encerra();
				tabelaLeilao.atualiza(leilao);
				total++;
			}

		}
	}

	private boolean comecouSemanaPassada(Leilao leilao) {

		LocalDateTime inicioDoLeilao = leilao.getData().atStartOfDay();
		LocalDateTime hoje = LocalDate.now().atStartOfDay();

		return Duration.between(inicioDoLeilao, hoje).toDays() >= 7.0;
	}

	public int getTotalEncerrados() {
		return total;
	}

}
