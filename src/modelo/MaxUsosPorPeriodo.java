package modelo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;
import java.util.List;

import pds.util.Validator;

/**
 * Restrição "no máximo N usos por período" (ex.: "2 por dia",
 * "3 por semana").
 */

public class MaxUsosPorPeriodo implements Restricao{
	private final int max;
	private final Periodo periodo;
	
	/**
	 * Cria a restrição.
	 *
	 * @param max     número máximo de usos no período (tem de ser positivo)
	 * @param periodo o período a considerar (dia, semana ou mês)
	 */
	public MaxUsosPorPeriodo(int max, Periodo periodo) {
		this.max = Validator.requirePositive(max);
		this.periodo = Objects.requireNonNull(periodo);
	}

	@Override
	public boolean permite(List<Registo> usos, LocalDateTime agora) {
		// TODO Auto-generated method stub
		LocalDateTime inicio = periodo.inicio(agora);
		int conta = 0;
		// os registos estão por ordem cronológica: percorre de trás para a
				// frente e pára no primeiro registo anterior ao início do período
		for (int i = usos.size() -1; i >= 0; i--) {
			if ((usos.get(i).getDataHora().isBefore(inicio)));
			break;
		}
		return conta < max;
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return max + " por " + periodo.getNome();
	}
	
	
	

}
