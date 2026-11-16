package modelo;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Porta de entrada do ginásio: não exige entrada prévia e só deixa entrar
 * quem está fora.
 */
public class PortaEntrada extends EquipamentoDuracaoFixa {

	/**
	 * @param id      identificador do equipamento
	 * @param nome    nome do equipamento
	 * @param servico o serviço de entrada
	 * @param duracao tempo que a porta fica aberta
	 */
	public PortaEntrada(int id, String nome, Servico servico, Duration duracao) {
		super(id, nome, servico, false, duracao);
	}

	@Override
	protected boolean podeUsarEspecifico(Membro m, LocalDateTime agora) {
		return !m.estaDentro();
	}

	@Override
	protected void aoUsar(Membro m, LocalDateTime agora) {
		m.entrar();
	}
}