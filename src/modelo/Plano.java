package modelo;

import java.time.Duration;
import java.util.List;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * Plano de acesso de um membro a um serviço. É formado por um conjunto de
 * restrições (composição): o uso só é permitido se todas permitirem.
 * Alguns serviços (piscina, personal trainer, duche) têm a duração definida
 * no plano.
 */

public class Plano {
	
	private final List<Restricao> restricoes;
	private final Duration duracao;
	private final Duration duracaoExtra;
	
	/**
	 * Cria um plano com duração definida.
	 *
	 * @param duracao      duração de cada uso (null se o plano não a definir)
	 * @param duracaoExtra tempo extra, só usado pelo duche (null = sem extra)
	 * @param restricoes   as regras do plano (nenhuma = sem limites)
	 */
	public Plano (Duration duracao, Duration duracaoExtra, Restricao...restricoes ) {
		this.duracao = duracao;
		this.duracaoExtra = duracaoExtra ==  null ? Duration.ZERO : duracaoExtra;
		this.restricoes = List.of(restricoes);
	}
	
	/**
	 * Cria um plano sem duração definida.
	 *
	 * @param restricoes as regras do plano (nenhuma = sem limites)
	 */
	public Plano(Restricao...restricoes ) {
		this(null, null, restricoes);
	}
	
	/**
	 * Verifica se o plano permite um novo uso do serviço. Todas as restrições
	 * têm de permitir; a verificação pára na primeira que recusar.
	 *
	 * @param usos  registos anteriores do membro para este serviço, por ordem
	 *              cronológica
	 * @param agora o momento do pedido
	 * @return true se todas as restrições permitirem (ou se não houver
	 *         restrições)
	 */
	public boolean permite(List<Registo> usos, LocalDateTime agora) {
		for (Restricao r: restricoes)
			if(!r.permite(usos, agora))
					return false;
		return true;
	}
	
	/**
	 * @return a descrição do plano, gerada a partir da duração e das
	 *         restrições (ex.: "45 min, 1 por dia, 2 por semana")
	 */
	
	public String getDescricao() {
		if (duracao == null && restricoes.isEmpty())
			return "Sempre";
		StringJoiner sj = new StringJoiner(", ");
		if (duracao != null) {
			if (duracaoExtra.isZero())
				sj.add(duracao.toMinutes() + " min");
			else
				sj.add(duracao.toMinutes() + " " + duracaoExtra.toMinutes() + " min");
		}
		for (Restricao r: restricoes)
			sj.add(getDescricao());
		return sj.toString();
	}
	
	/**
	 * @return a duração de cada uso, ou null se o plano não a definir
	 */
	public Duration getDuracao() {
		return duracao;
	}
	
	/**
	 * @return o tempo extra (zero se o plano não tiver tempo extra)
	 */
	public Duration getDuracaoExtra() {
		return duracaoExtra;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
