package modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Equipamento cujo uso tem sempre a mesma duração, definida pelo ginásio
 * (ex.: dispensador de bebida, cadeira de massagem, nutricionista).
 */
public class EquipamentoDuracaoFixa extends Equipamento {

	private final Duration duracao;

	/**
	 * Cria um equipamento com duração fixa.
	 *
	 * @param id           identificador do equipamento
	 * @param nome         nome do equipamento
	 * @param servico      o serviço que o equipamento fornece
	 * @param exigeEntrada true se o membro tem de ter entrado no ginásio
	 * @param duracao      quanto tempo dura cada uso
	 */
	public EquipamentoDuracaoFixa(int id, String nome, Servico servico, boolean exigeEntrada,
			Duration duracao) {
		super(id, nome, servico, exigeEntrada);
		this.duracao = Objects.requireNonNull(duracao);
	}

	@Override
	protected Duration getDuracao(Membro m, LocalDateTime agora) {
		return duracao;
	}
}