package modelo;

import java.time.LocalDateTime;

/**
 * Duche: dura o tempo definido no plano e permite ativar um tempo extra. O
 * extra tem de ser ativado pelo mesmo membro, depois de terminar a fase
 * inicial e no máximo até passar um tempo igual à duração inicial. O extra
 * não conta como um duche novo.
 */
public class Duche extends EquipamentoDuracaoPlano {

	private Membro ultimoMembro;
	private LocalDateTime fimFaseInicial;
	private boolean extraUsado;

	/**
	 * Cria um duche. Os duches exigem sempre a entrada no ginásio.
	 *
	 * @param id      identificador do equipamento
	 * @param nome    nome do equipamento
	 * @param servico o serviço de duche
	 */
	public Duche(int id, String nome, Servico servico) {
		super(id, nome, servico, true);
	}

	/**
	 * Indica se este pedido é para ativar o tempo extra: tem de ser o último
	 * membro que usou o duche, ainda sem ter usado o extra, com um plano que
	 * tenha tempo extra e dentro da janela de ativação.
	 */
	private boolean pedeExtra(Membro m, LocalDateTime agora) {
		if (m != ultimoMembro || extraUsado || fimFaseInicial == null)
			return false;
		Plano p = m.getPlano(getServico());
		if (p == null || p.getDuracaoExtra().isZero())
			return false;
		LocalDateTime limite = fimFaseInicial.plus(p.getDuracao());
		return !agora.isBefore(fimFaseInicial) && !agora.isAfter(limite);
	}

	/**
	 * Um pedido de tempo extra não volta a verificar as restrições do plano
	 * (senão um plano de "1 duche por dia" impedia o extra).
	 */
	@Override
	protected boolean verificaPlano(Membro m, LocalDateTime agora) {
		return pedeExtra(m, agora) || super.verificaPlano(m, agora);
	}

	@Override
	public void usar(Membro m, LocalDateTime agora) {
		if (pedeExtra(m, agora)) {
			if (!podeUsar(m, agora))
				throw new IllegalStateException("o membro " + m.getId() + " não pode usar o tempo extra");
			ocupar(m, agora, m.getPlano(getServico()).getDuracaoExtra());
			extraUsado = true; // o tempo extra não cria um registo novo
			return;
		}
		super.usar(m, agora);
		ultimoMembro = m;
		fimFaseInicial = agora.plus(m.getPlano(getServico()).getDuracao());
		extraUsado = false;
	}
}
