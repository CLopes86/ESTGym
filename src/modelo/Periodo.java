package modelo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Períodos de calendário usados nas restrições ("por dia", "por semana",
 * "por mês").
 */

public enum Periodo {
	DIA("dia"), SEMANA("semana"), MES("mês");
	
	private final String nome;
	
	
	Periodo(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Calcula o início do período que contém o momento indicado. A semana
	 * começa à segunda-feira.
	 *
	 * @param agora o momento atual
	 * @return o início (às 00h00) do período atual
	 */
	public LocalDateTime inicio(LocalDateTime agora) {
		LocalDate hoje = agora.toLocalDate();
		return switch (this ) {
		case  DIA -> hoje.atStartOfDay();
		case SEMANA -> hoje.with(DayOfWeek.MONDAY).atStartOfDay();
		case MES -> hoje.withDayOfMonth(1).atStartOfDay();
		
		};
	}
		
		/**
		 * @return o nome do período, para as descrições ("dia", "semana", "mês")
		 */
		public String getNome() {
			return nome;
		}
	}
	


