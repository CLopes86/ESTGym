package modelo;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Porta de saída do ginásio: exige ter entrado, o que já garante que o
 * membro está dentro.
 */
public class PortaSaida extends EquipamentoDuracaoFixa {

	/**
	 * @param id      identificador do equipamento
	 * @param nome    nome do equipamento
	 * @param servico o serviço de saída
	 * @param duracao tempo que a porta fica aberta
	 */
	public PortaSaida(int id, String nome, Servico servico, Duration duracao) {
		super(id, nome, servico, true, duracao);
	}

	@Override
	protected void aoUsar(Membro m, LocalDateTime agora) {
		m.sair();
	}
}