package modelo;

import java.time.LocalDateTime;

import pds.util.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Objects;

/**
 * Representa um membro do ginásio. Cada membro tem um plano por cada serviço
 * contratado e guarda o histórico (registos) dos serviços que usou.
 */

public class Membro {
	private final int id;
	private final String nome;
	private boolean dentro;
	private LocalDateTime ocupadoAte = LocalDateTime.MIN;
	private final Map<Servico, Plano> planos = new LinkedHashMap<>();
	private final List<Registo> registos = new ArrayList<>();
	private final Map<Servico, List<Registo>> registosPorServico = new HashMap<>(); 
	
	/**
	 * Cria um membro, inicialmente fora do ginásio e sem planos.
	 *
	 * @param id   número do cartão do membro (tem de ser positivo)
	 * @param nome nome do membro (não pode ser vazio)
	 * @throws IllegalArgumentException se o id não for positivo ou o nome for
	 *                                  vazio
	 */
	public Membro(int id, String nome) {
		this.id = Validator.requirePositive(id);
		this.nome = Validator.requireNonBlankTrimmed(nome);
		}
	
	/**
	 * @return o número do cartão do membro
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return o nome do membro
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @return true se o membro já processou a entrada no ginásio
	 */
	public boolean estaDentro() {
		return dentro;
	}
	
	/**
	 * Indica se o membro está a usar algum serviço no momento indicado.
	 *
	 * @param agora o momento a verificar
	 * @return true se ainda está ocupado nesse momento
	 */
	public boolean estaOcupado(LocalDateTime agora) {
		return agora.isBefore(ocupadoAte);
	}
	
	/**
	 * Associa um plano a um serviço. Se já existir um plano para esse serviço,
	 * é substituído.
	 *
	 * @param servico o serviço contratado
	 * @param plano   o plano de acesso a esse serviço
	 * @throws NullPointerException se algum dos parâmetros for null
	 */
	public void addPlano(Servico servico, Plano plano) {
		planos.put(Objects.requireNonNull(servico), Objects.requireNonNull(plano));
	}
	
	/**
	 * @param servico o serviço
	 * @return o plano do membro para esse serviço, ou null se não o tiver
	 *         contratado
	 */
	public Plano getPlano(Servico servico) {
		return planos.get(servico);
	}
	
	/**
	 * @return todos os planos do membro (só de leitura), pela ordem em que
	 *         foram adicionados
	 */
	public Map<Servico, Plano> getPlanos()	{
		return Collections.unmodifiableMap(planos);
	}
	
	/**
	 * @return todos os registos do membro por ordem cronológica (só de leitura)
	 */
	public List<Registo> getRegistos() {
		return Collections.unmodifiableList(registos);
	}
	
	/**
	 * @param servico o serviço
	 * @return os registos desse serviço por ordem cronológica (só de leitura);
	 *         lista vazia se o membro nunca o usou
	 */
	public List<Registo> getRegistos(Servico servico) {
		List<Registo> lista = registosPorServico.get(servico);
		if (lista == null)
			return List.of();
		return Collections.unmodifiableList(lista);
	}
	
	/**
	 * Acrescenta um registo de uso ao histórico do membro. Só os equipamentos
	 * (mesmo pacote) podem registar usos.
	 *
	 * @param registo o registo a acrescentar
	 */
	void addRegistos(Registo registo) {
		registos.add(registo);
		List<Registo> lista = registosPorServico.get(registo.getServico());
		if (lista == null) {
			lista = new ArrayList<>();
			registosPorServico.put(registo.getServico(), lista);
		}
		lista.add(registo);
	}
	
	/**
	 * Marca o membro como ocupado até ao momento indicado.
	 *
	 * @param ate o momento em que o membro volta a ficar livre
	 */
	void ocupar(LocalDateTime ate) {
		ocupadoAte = Objects.requireNonNull(ate);
	}
	
	/**
	 * Regista a entrada do membro no ginásio.
	 *
	 * @throws IllegalStateException se o membro já estiver dentro
	 */
	void entrar() {
		if (dentro)
			throw new IllegalStateException("o membro " + id + " já está dentro do ginásio");
		dentro = true;
	}
	
	/**
	 * Regista a saída do membro do ginásio.
	 *
	 * @throws IllegalStateException se o membro não estiver dentro
	 */
	void sair() {
		if(!dentro)
			throw new IllegalStateException("o membro " + id + " não está dentro do ginásio");
		dentro = false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
