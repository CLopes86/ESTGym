package modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import pds.util.Validator;

/**
 * Sala de aula: aceita membros até à capacidade máxima. A aula começa quando
 * entra o primeiro membro e quem entra a meio não reinicia a aula.
 */
public class SalaAula extends Equipamento {

	private final int capacidade;
	private final Duration duracaoAula;
	private LocalDateTime inicioAula;
	private int inscritos;

	/**
	 * @param id           identificador do equipamento
	 * @param nome         nome da aula
	 * @param servico      o serviço (nível da aula)
	 * @param exigeEntrada true se o membro tem de ter entrado no ginásio
	 * @param capacidade   número máximo de alunos (tem de ser positivo)
	 * @param duracaoAula  duração da aula
	 */
	public SalaAula(int id, String nome, Servico servico, boolean exigeEntrada, int capacidade,
			Duration duracaoAula) {
		super(id, nome, servico, exigeEntrada);
		this.capacidade = Validator.requirePositive(capacidade);
		this.duracaoAula = Objects.requireNonNull(duracaoAula);
	}

	/** Indica se há uma aula a decorrer no momento indicado. */
	private boolean aulaADecorrer(LocalDateTime agora) {
		return inicioAula != null && agora.isBefore(inicioAula.plus(duracaoAula));
	}

	/** A sala só está ocupada se a aula estiver a decorrer e cheia. */
	@Override
	public boolean estaOcupado(LocalDateTime agora) {
		return aulaADecorrer(agora) && inscritos >= capacidade;
	}

	/** Quem entra a meio da aula fica só o tempo que falta. */
	@Override
	protected Duration getDuracao(Membro m, LocalDateTime agora) {
		if (aulaADecorrer(agora))
			return Duration.between(agora, inicioAula.plus(duracaoAula));
		return duracaoAula;
	}

	/** O primeiro membro inicia a aula; cada membro conta como um inscrito. */
	@Override
	protected void aoUsar(Membro m, LocalDateTime agora) {
		if (!aulaADecorrer(agora)) {
			inicioAula = agora;
			inscritos = 0;
		}
		inscritos++;
	}
}