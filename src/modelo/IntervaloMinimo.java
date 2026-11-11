package modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;
import java.util.List;

import pds.util.Validator;

/**
 * Restrição "tem de passar um tempo mínimo desde o último uso"
 * (ex.: "30 minutos entre eles", "de 6 em 6 meses").
 */

public class IntervaloMinimo implements Restricao {
	
	private final long quantidade;
	private final ChronoUnit unidade;
	
	/**
	 * Cria a restrição.
	 *
	 * @param quantidade quanto tempo tem de passar (tem de ser positivo)
	 * @param unidade    a unidade de tempo (minutos, meses, ...)
	 */
	public IntervaloMinimo(long quantidade, ChronoUnit unidade) {
		this.quantidade = Validator.requirePositive(quantidade);
		this.unidade = Objects.requireNonNull(unidade);
	}

	@Override
	public boolean permite(List<Registo> usos, LocalDateTime agora) {
		// TODO Auto-generated method stub
		if (usos.isEmpty())
			return true;
		LocalDateTime ultimo = usos.get(usos.size() -1).getDataHora();
		return !agora.isBefore(ultimo.plus(quantidade, unidade));
	
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return "intervalo minimo de " + quantidade + "" + nomeUnidade();
	}
	
	
	/**
	 * @return o nome da unidade de tempo em português, no singular ou no
	 *         plural conforme a quantidade
	 */
	private String nomeUnidade() {
		return switch (unidade) {
		case MINUTES -> "min";
		case HOURS -> quantidade == 1 ? "hora" : "horas";
		case DAYS -> quantidade == 1 ? "dia" : "dias";
		case MONTHS -> quantidade == 1 ? "mês" : "meses";
		default -> unidade.toString().toLowerCase();
		};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
