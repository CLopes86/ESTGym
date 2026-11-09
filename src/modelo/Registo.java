package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Registo de um uso de um serviço por um membro: qual o serviço, em que
 * equipamento e quando. Os registos servem para mostrar o histórico do
 * membro e para verificar as restrições dos planos.
 */

public class Registo {
	private final Servico servico;
	private final Equipamento equipamento;
	private final LocalDateTime dataHora;
	
	/**
	 * Cria um registo de uso.
	 *
	 * @param servico     o serviço usado
	 * @param equipamento o equipamento onde foi usado
	 * @param dataHora    a data e hora do uso
	 * @throws NullPointerException se algum dos parâmetros for null
	 */
	public Registo(Servico servico, Equipamento equipamento, LocalDateTime dataHora) {
		this.servico = Objects.requireNonNull(servico);
		this.equipamento = Objects.requireNonNull(servico);
		this.dataHora = Objects.requireNonNull(dataHora);
	}
	
	/**
	 * @return o serviço usado
	 */
	public Servico getServico() {
		return servico;
	}
	
	/**
	 * @return o equipamento onde o serviço foi usado
	 */
	public Equipamento getEquipamento() {
		return equipamento;
	}
	
	/**
	 * @return a data e hora do uso
	 */
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	

}
