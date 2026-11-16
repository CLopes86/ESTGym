package modelo;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Equipamento cujo uso dura o tempo definido no plano do membro
 * (ex.: personal trainer).
 */
public class EquipamentoDuracaoPlano extends Equipamento {

	/**
	 * @param id           identificador do equipamento
	 * @param nome         nome do equipamento
	 * @param servico      o serviço que o equipamento fornece
	 * @param exigeEntrada true se o membro tem de ter entrado no ginásio
	 */
	public EquipamentoDuracaoPlano(int id, String nome, Servico servico, boolean exigeEntrada) {
		super(id, nome, servico, exigeEntrada);
	}

	@Override
	protected Duration getDuracao(Membro m, LocalDateTime agora) {
		return m.getPlano(getServico()).getDuracao();
	}
}