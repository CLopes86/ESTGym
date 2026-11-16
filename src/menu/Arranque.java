package menu;

import java.awt.Point;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import modelo.Duche;
import modelo.Equipamento;
import modelo.EquipamentoDuracaoFixa;
import modelo.EquipamentoDuracaoPlano;
import modelo.Ginasio;
import modelo.IntervaloMinimo;
import modelo.MaxUsosPorPeriodo;
import modelo.Membro;
import modelo.Periodo;
import modelo.Plano;
import modelo.PortaEntrada;
import modelo.PortaSaida;
import modelo.Restricao;
import modelo.SalaAula;
import modelo.Servico;
import pds.util.RelogioSimulado;

/**
 * Ponto de entrada da aplicação: cria o ginásio com a configuração de teste
 * do enunciado e abre a janela que o simula.
 */
public class Arranque {

	// ícone de cada equipamento (id do equipamento -> nome da imagem), pela
	// ordem em que os equipamentos são criados
	private static final Map<Integer, String> ICONES = new LinkedHashMap<>();

	public static void main(String[] args) {
		// Criar o relógio simulado e "acertá-lo" para a hora atual
		RelogioSimulado relogio = RelogioSimulado.getRelogioSimulado();
		relogio.setTempoAtual(LocalDateTime.now());

		Ginasio ginasio = criarGinasio();

		SwingUtilities.invokeLater(() -> {
			JanelaGinasio main = new JanelaGinasio(ginasio);

			// adicionar os equipamentos ao simulador
			for (Map.Entry<Integer, String> e : ICONES.entrySet()) {
				main.addEquipamento(e.getKey(), getIcons(e.getValue()));
			}

			main.setVisible(true);
			RelogioDialog relog = new RelogioDialog(main, new Point(200, 20));
			relog.setVisible(true);

		});
	}

	/**
	 * Cria o ginásio com a configuração de teste do enunciado: serviços,
	 * equipamentos, membros e os planos de cada membro. Outro ginásio, com
	 * outros planos, cria-se da mesma forma, sem alterar as classes do modelo.
	 * 
	 * @return o ginásio já configurado
	 */
	static Ginasio criarGinasio() {
		Ginasio g = new Ginasio("Castelo Branco");

		// ---------- serviços ----------
		Servico entrada = new Servico("Entrada");
		Servico saida = new Servico("Saída");
		Servico aula1 = new Servico("Aula nível 1");
		Servico aula2 = new Servico("Aula nível 2");
		Servico piscina = new Servico("Piscina");
		Servico duche = new Servico("Duche");
		Servico bebida = new Servico("Bebida vitaminada");
		Servico pt = new Servico("Personal Trainer");
		Servico nutricao = new Servico("Consulta nutrição");
		Servico avaliacao = new Servico("Avaliação física");
		Servico massagem = new Servico("Cadeira de Massagem");
		Servico[] servicos = { entrada, saida, aula1, aula2, piscina, duche, bebida, pt, nutricao, avaliacao,
				massagem };
		for (Servico s : servicos)
			g.addServico(s);

		// ---------- equipamentos ----------
		// todos, exceto a porta de entrada, obrigam à entrada prévia no ginásio
		Duration tresSegundos = Duration.ofSeconds(3);
		addEquipamento(g, new PortaEntrada(10, "Porta de entrada", entrada, tresSegundos), "porta");
		addEquipamento(g, new PortaSaida(11, "Porta de saída", saida, tresSegundos), "porta");
		addEquipamento(g, new SalaAula(12, "Aula Step", aula1, true, 30, Duration.ofMinutes(30)), "aula");
		addEquipamento(g, new SalaAula(13, "Aula Cycling", aula1, true, 25, Duration.ofMinutes(50)), "aula");
		addEquipamento(g, new SalaAula(14, "Aula Body combat", aula2, true, 20, Duration.ofMinutes(30)), "aula");
		addEquipamento(g, new SalaAula(15, "Aula Ioga", aula2, true, 15, Duration.ofMinutes(60)), "aula");
		addEquipamento(g, new EquipamentoDuracaoFixa(16, "Dispensador bebida", bebida, true, Duration.ofSeconds(20)),
				"bebida");
		addEquipamento(g, new Duche(17, "Duche 1", duche), "duche");
		addEquipamento(g, new Duche(18, "Duche 2", duche), "duche");
		addEquipamento(g, new Duche(19, "Duche 3", duche), "duche");
		addEquipamento(g, new EquipamentoDuracaoPlano(20, "Manel Mandão", pt, true), "pt");
		addEquipamento(g, new EquipamentoDuracaoPlano(21, "Rita Rígida", pt, true), "pt");
		addEquipamento(g, new EquipamentoDuracaoFixa(22, "Carlos Comilão", nutricao, true, Duration.ofMinutes(30)),
				"nutricionista");
		addEquipamento(g, new EquipamentoDuracaoFixa(23, "Marta Magra", nutricao, true, Duration.ofMinutes(30)),
				"nutricionista");
		addEquipamento(g, new EquipamentoDuracaoFixa(24, "Sala Avaliação", avaliacao, true, Duration.ofMinutes(20)),
				"fisica");
		addEquipamento(g, new EquipamentoDuracaoFixa(25, "Cadeira 1", massagem, true, Duration.ofMinutes(10)),
				"poltrona");
		addEquipamento(g, new EquipamentoDuracaoFixa(26, "Cadeira 2", massagem, true, Duration.ofMinutes(10)),
				"poltrona");
		addEquipamento(g, new EquipamentoDuracaoFixa(27, "Entrada Piscina", piscina, true, tresSegundos),
				"porta_piscina");

		// ---------- membros e respetivos planos ----------
		Membro vitor = new Membro(1, "Vítor Virtual");
		vitor.addPlano(entrada, new Plano(Duration.ofHours(1), null, porDia(1), porSemana(3)));
		vitor.addPlano(saida, new Plano());
		vitor.addPlano(aula1, new Plano(porSemana(1)));
		vitor.addPlano(duche, new Plano(minutos(3), minutos(2), porDia(1)));
		vitor.addPlano(bebida, new Plano(porDia(1)));
		vitor.addPlano(nutricao, new Plano(deXemXMeses(6)));
		vitor.addPlano(avaliacao, new Plano(deXemXMeses(3)));
		g.addMembro(vitor);

		Membro iris = new Membro(2, "Iris Irreal");
		iris.addPlano(entrada, new Plano(porSemana(3)));
		iris.addPlano(saida, new Plano());
		iris.addPlano(piscina, new Plano(minutos(45), null, porDia(1), porSemana(2)));
		iris.addPlano(duche, new Plano(minutos(5), minutos(2), porDia(1)));
		iris.addPlano(bebida, new Plano(porDia(2), intervaloMinutos(30)));
		iris.addPlano(pt, new Plano(minutos(45), null, porDia(1), porSemana(3)));
		iris.addPlano(nutricao, new Plano(deXemXMeses(6)));
		iris.addPlano(avaliacao, new Plano(deXemXMeses(3)));
		g.addMembro(iris);

		Membro ines = new Membro(3, "Inês Inexistente");
		ines.addPlano(entrada, new Plano());
		ines.addPlano(saida, new Plano());
		ines.addPlano(aula1, new Plano(porSemana(1)));
		ines.addPlano(duche, new Plano(minutos(5), minutos(2), porDia(1)));
		ines.addPlano(bebida, new Plano(porDia(2), intervaloMinutos(30)));
		ines.addPlano(nutricao, new Plano(porMes(1)));
		ines.addPlano(avaliacao, new Plano(deXemXMeses(3)));
		g.addMembro(ines);

		Membro filipe = new Membro(4, "Filipe Fictício");
		filipe.addPlano(entrada, new Plano(porSemana(3)));
		filipe.addPlano(saida, new Plano());
		filipe.addPlano(aula1, new Plano(porSemana(2)));
		filipe.addPlano(aula2, new Plano(porSemana(1)));
		filipe.addPlano(piscina, new Plano(minutos(45), null, porDia(1), porSemana(2)));
		filipe.addPlano(duche, new Plano(minutos(5), minutos(2), porDia(1)));
		filipe.addPlano(bebida, new Plano(porDia(2), intervaloMinutos(30)));
		filipe.addPlano(pt, new Plano(minutos(45), null, porDia(1), porSemana(3)));
		filipe.addPlano(nutricao, new Plano(deXemXMeses(6)));
		filipe.addPlano(avaliacao, new Plano(deXemXMeses(3)));
		filipe.addPlano(massagem, new Plano(porDia(1), porSemana(3)));
		g.addMembro(filipe);

		Membro diogo = new Membro(5, "Diogo Digital");
		diogo.addPlano(entrada, new Plano());
		diogo.addPlano(saida, new Plano());
		diogo.addPlano(aula1, new Plano());
		diogo.addPlano(aula2, new Plano(porSemana(3)));
		diogo.addPlano(piscina, new Plano(Duration.ofHours(1), null, porDia(1), porSemana(5)));
		diogo.addPlano(duche, new Plano(minutos(6), minutos(3), porDia(2)));
		diogo.addPlano(bebida, new Plano(porDia(4), intervaloMinutos(20)));
		diogo.addPlano(nutricao, new Plano(deXemXMeses(3)));
		diogo.addPlano(avaliacao, new Plano(deXemXMeses(3)));
		diogo.addPlano(massagem, new Plano(porDia(1), porSemana(7)));
		g.addMembro(diogo);

		Membro beatriz = new Membro(6, "Beatriz Bytes");
		beatriz.addPlano(entrada, new Plano());
		beatriz.addPlano(saida, new Plano());
		beatriz.addPlano(aula1, new Plano());
		beatriz.addPlano(aula2, new Plano());
		beatriz.addPlano(duche, new Plano(minutos(6), minutos(3), porDia(2)));
		beatriz.addPlano(bebida, new Plano(porDia(4), intervaloMinutos(20)));
		beatriz.addPlano(pt, new Plano(Duration.ofHours(1), null, porDia(1), porSemana(5)));
		beatriz.addPlano(nutricao, new Plano(porMes(1)));
		beatriz.addPlano(avaliacao, new Plano(porMes(1)));
		beatriz.addPlano(massagem, new Plano(porDia(1), porSemana(3)));
		g.addMembro(beatriz);

		return g;
	}

	/**
	 * Adiciona um equipamento ao ginásio e regista qual a imagem a usar no
	 * respetivo botão.
	 */
	private static void addEquipamento(Ginasio g, Equipamento e, String icone) {
		g.addEquipamento(e);
		ICONES.put(e.getId(), icone);
	}

	// ---------- métodos auxiliares para tornar os planos mais legíveis ----------

	private static Restricao porDia(int max) {
		return new MaxUsosPorPeriodo(max, Periodo.DIA);
	}

	private static Restricao porSemana(int max) {
		return new MaxUsosPorPeriodo(max, Periodo.SEMANA);
	}

	private static Restricao porMes(int max) {
		return new MaxUsosPorPeriodo(max, Periodo.MES);
	}

	private static Restricao intervaloMinutos(int minutos) {
		return new IntervaloMinimo(minutos, ChronoUnit.MINUTES);
	}

	private static Restricao deXemXMeses(int meses) {
		return new IntervaloMinimo(meses, ChronoUnit.MONTHS);
	}

	private static Duration minutos(int minutos) {
		return Duration.ofMinutes(minutos);
	}

	/**
	 * Lê os ficheiros com os icons para um dado equipamento
	 * 
	 * @param nome nome da imagem representativa do equipamento
	 * @return os icones com a imagem livre e ocupado
	 */
	private static ImageIcon[] getIcons(String nome) {
		String pasta = "art/";
		ImageIcon icons[] = { new ImageIcon(pasta + nome + "_livre.png"),
				new ImageIcon(pasta + nome + "_ocupado.png") };
		return icons;
	}
}
