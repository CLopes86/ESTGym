package modelo;

import java.time.LocalDateTime;
import java.util.Objects;
import java.time.Duration;

import pds.util.Validator;

/**
 * Equipamento de acesso a um serviço (porta, duche, dispensador, ...).
 * Guarda o que é comum a todos os equipamentos; as subclasses definem o que
 * é diferente em cada um.
 */

public abstract class Equipamento {
	private final int id;
	private final String nome;
	private final Servico servico;
	private final boolean exigeEntrada;
	private LocalDateTime ocupadoAte = LocalDateTime.MIN;
	
	/**
	 * Cria um equipamento, inicialmente livre.
	 *
	 * @param id           identificador do equipamento (tem de ser positivo)
	 * @param nome         nome do equipamento (não pode ser vazio)
	 * @param servico      o serviço que o equipamento fornece
	 * @param exigeEntrada true se o membro tem de ter entrado no ginásio para
	 *                     usar este equipamento
	 */
	protected Equipamento(int id, String nome, Servico servico, boolean exigeEntrada) {
		this.id = Validator.requirePositive(id);
		this.nome = Validator.requireNonBlankTrimmed(nome);
		this.servico = Objects.requireNonNull(servico);
		this.exigeEntrada = exigeEntrada;
		
	}
	
	/**
	 * @return o identificador do equipamento
	 */
	public int getId() {
		return id;
		
	}
	
	/**
	 * @return o nome do equipamento
	 */
	public  String getNome() {
		return nome;
	}
	
	
	/**
	 * @return o serviço que o equipamento fornece
	 */
	public Servico getServico() {
		return servico;
	}
	
	/**
	 * @return true se o membro tem de ter entrado no ginásio para usar este
	 *         equipamento
	 */
	public boolean exigeEntrada() {
		return exigeEntrada;
	}
	
	/**
	 * Indica se o equipamento está ocupado no momento indicado.
	 *
	 * @param agora o momento a verificar
	 * @return true se o equipamento não pode ser usado por estar ocupado
	 */
	public boolean estaOcupado(LocalDateTime agora) {
		return agora.isBefore(ocupadoAte);
	}
	
	/**
	 * Verifica se o membro pode usar o equipamento no momento indicado. As
	 * verificações comuns a todos os equipamentos são feitas aqui, das mais
	 * simples para as mais pesadas; no fim é feita a verificação específica
	 * de cada equipamento.
	 *
	 * @param m     o membro que quer usar o equipamento
	 * @param agora o momento do pedido
	 * @return true se o membro pode usar o equipamento
	 */
	public final boolean podeUsar(Membro m, LocalDateTime agora) {
		if (estaOcupado(agora))
			return false;
		if(m.estaOcupado(agora))
			return false;
		if(exigeEntrada && !m.estaDentro())
			return false;
		if(!verificaPlano(m,agora))
			return false;
		return podeUsarEspecifico(m, agora);
	}
	
	/**
	 * Verifica se o membro tem plano para o serviço deste equipamento e se
	 * esse plano permite um novo uso.
	 *
	 * @param m     o membro
	 * @param agora o momento do pedido
	 * @return true se o membro tem plano e o plano permite
	 */
	protected boolean verificaPlano(Membro m, LocalDateTime agora) {
		Plano p = m.getPlano(servico);
		return p != null && p.permite(m.getRegistos(servico), agora);
	}
	
	/**
	 * Verificação específica de cada equipamento. Por omissão não há nenhuma
	 * regra adicional; as subclasses redefinem este método se precisarem.
	 *
	 * @param m     o membro
	 * @param agora o momento do pedido
	 * @return true se a regra específica do equipamento permite o uso
	 */
	protected boolean podeUsarEspecifico(Membro m, LocalDateTime agora) {
		return true;
	}
	
	
	/**
	 * O membro usa o equipamento: o equipamento e o membro ficam ocupados
	 * durante o tempo do uso, é criado o registo e é executada a ação
	 * específica do equipamento.
	 *
	 * @param m     o membro que usa o equipamento
	 * @param agora o momento do uso
	 * @throws IllegalStateException se o membro não puder usar o equipamento
	 */
	public void usar(Membro m, LocalDateTime agora) {
		if (!podeUsar(m, agora))
			throw new IllegalStateException("o membro " + m.getId() + " não pode usar " + nome);
		ocupar(m, agora, getDuracao(m, agora));
		m.addRegistos(new Registo(servico, this, agora));
		aoUsar(m, agora);
	}
	
	/**
	 * Ocupa o equipamento e o membro durante o tempo indicado.
	 *
	 * @param m       o membro
	 * @param agora   o momento em que o uso começa
	 * @param duracao quanto tempo dura o uso
	 */
	protected void ocupar(Membro m, LocalDateTime agora, Duration duracao) {
		ocupadoAte = agora.plus(duracao);
		m.ocupar(ocupadoAte);
		}
	
	
	/**
	 * Indica quanto tempo dura o uso deste equipamento pelo membro. Cada tipo
	 * de equipamento define a sua duração.
	 *
	 * @param m     o membro
	 * @param agora o momento em que o uso começa
	 * @return a duração do uso
	 */
	protected abstract Duration getDuracao(Membro m, LocalDateTime agora);
	
	/**
	 * Ação executada depois de o equipamento ser usado. Por omissão não faz
	 * nada; as subclasses redefinem este método se precisarem.
	 *
	 * @param m     o membro
	 * @param agora o momento do uso
	 */
	protected void aoUsar(Membro m, LocalDateTime agora) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
