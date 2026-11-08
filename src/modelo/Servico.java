package modelo;

import pds.util.Validator;

/**
 * Representa um serviço disponibilizado pelo ginásio (ex.: Entrada, Duche,
 * Aula nível 1). Um serviço pode ser fornecido por vários equipamentos e é
 * usado como chave para encontrar o plano de um membro.
 */
public class Servico {
	
	private final String nome;
	
	/**
	 * Cria um serviço.
	 *
	 * @param nome nome do serviço, não pode ser vazio
	 * @throws IllegalArgumentException se o nome for vazio
	 */
	public Servico(String nome) {
		this.nome = Validator.requireNonBlankTrimmed(nome);
	}
	
	/**
	 * @return o nome do serviço
	 */
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}

}
