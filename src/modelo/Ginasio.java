package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import pds.util.Validator;

/**
 * Representa um ginásio da ESTGym: os seus serviços, membros e equipamentos.
 * É o ponto de acesso ao modelo para a interface gráfica.
 */
public class Ginasio {

	private final String nome;
	private final List<Servico> servicos = new ArrayList<>();
	private final Map<Integer, Membro> membros = new LinkedHashMap<>();
	private final Map<Integer, Equipamento> equipamentos = new LinkedHashMap<>();

	/**
	 * Cria um ginásio, inicialmente sem serviços, membros nem equipamentos.
	 *
	 * @param nome nome do ginásio (não pode ser vazio)
	 */
	public Ginasio(String nome) {
		this.nome = Validator.requireNonBlankTrimmed(nome);
	}

	/**
	 * @return o nome do ginásio
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Acrescenta um serviço aos serviços disponibilizados pelo ginásio.
	 *
	 * @param servico o serviço a acrescentar
	 */
	public void addServico(Servico servico) {
		servicos.add(Objects.requireNonNull(servico));
	}

	/**
	 * Inscreve um membro no ginásio.
	 *
	 * @param membro o membro a inscrever
	 * @throws IllegalArgumentException se já existir um membro com o mesmo id
	 */
	public void addMembro(Membro membro) {
		if (membros.putIfAbsent(membro.getId(), membro) != null)
			throw new IllegalArgumentException("já existe um membro com id " + membro.getId());
	}

	/**
	 * Acrescenta um equipamento ao ginásio.
	 *
	 * @param equipamento o equipamento a acrescentar
	 * @throws IllegalArgumentException se já existir um equipamento com o
	 *                                  mesmo id
	 */
	public void addEquipamento(Equipamento equipamento) {
		if (equipamentos.putIfAbsent(equipamento.getId(), equipamento) != null)
			throw new IllegalArgumentException("já existe um equipamento com id " + equipamento.getId());
	}

	/**
	 * @param id o número do cartão do membro
	 * @return o membro com esse id, ou null se não existir
	 */
	public Membro getMembro(int id) {
		return membros.get(id);
	}

	/**
	 * @param id o identificador do equipamento
	 * @return o equipamento com esse id, ou null se não existir
	 */
	public Equipamento getEquipamento(int id) {
		return equipamentos.get(id);
	}

	/**
	 * @return os serviços do ginásio (só de leitura)
	 */
	public List<Servico> getServicos() {
		return Collections.unmodifiableList(servicos);
	}

	/**
	 * @return os membros do ginásio, pela ordem de inscrição (só de leitura)
	 */
	public Collection<Membro> getMembros() {
		return Collections.unmodifiableCollection(membros.values());
	}

	/**
	 * @return os equipamentos do ginásio, pela ordem em que foram
	 *         acrescentados (só de leitura)
	 */
	public Collection<Equipamento> getEquipamentos() {
		return Collections.unmodifiableCollection(equipamentos.values());
	}
}
