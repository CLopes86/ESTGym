package modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Testes dos equipamentos com registos de uso.
 */
public class TesteEquipamento {

	public static void main(String[] args) {

		// hora fixa para os testes: 14/11/2026 às 10h00
		LocalDateTime agora = LocalDateTime.of(2026, 11, 14, 10, 0);

		// o serviço e o equipamento
		Servico bebida = new Servico("Bebida vitaminada");
		Equipamento dispensador = new EquipamentoDuracaoFixa(12, "Dispensador de bebida", bebida, true,
				Duration.ofSeconds(20));

		// a Iris, com o plano "2 por dia, 30 minutos entre eles"
		Membro iris = new Membro(2, "Iris Irreal");
		iris.addPlano(bebida, new Plano(
				new MaxUsosPorPeriodo(2, Periodo.DIA),
				new IntervaloMinimo(30, ChronoUnit.MINUTES)));

		// Teste 1: o dispensador exige entrada no ginásio
		System.out.println("--- Teste 1: exige entrada ---");
		System.out.println("Fora do ginasio: " + dispensador.podeUsar(iris, agora));
		iris.entrar();
		System.out.println("Dentro do ginasio: " + dispensador.podeUsar(iris, agora));
		

		// Teste 2: bebidas com registos
		System.out.println("--- Teste 2: bebidas ---");
		dispensador.usar(iris, agora);
		System.out.println("Registos de bebida: " + iris.getRegistos(bebida).size());
		System.out.println("Dispensador ocupado aos 10s: " + dispensador.estaOcupado(agora.plusSeconds(10)));
		System.out.println("Dispensador ocupado aos 20s: " + dispensador.estaOcupado(agora.plusSeconds(20)));
		System.out.println("10h10 (intervalo): " + dispensador.podeUsar(iris, agora.plusMinutes(10)));
		System.out.println("10h30: " + dispensador.podeUsar(iris, agora.plusMinutes(30)));
		dispensador.usar(iris, agora.plusMinutes(30));
		System.out.println("11h30 (3a do dia): " + dispensador.podeUsar(iris, agora.plusMinutes(90)));
		System.out.println("Dia seguinte: " + dispensador.podeUsar(iris, agora.plusDays(1)));

		// Teste 3: portas
		System.out.println("--- Teste 3: portas ---");
		Servico entrada = new Servico("Entrada");
		Servico saida = new Servico("Saída");
		Equipamento portaEntrada = new PortaEntrada(10, "Porta de entrada", entrada, Duration.ofSeconds(3));
		Equipamento portaSaida = new PortaSaida(11, "Porta de saída", saida, Duration.ofSeconds(3));
		Membro vitor = new Membro(1, "Vítor Virtual");
		vitor.addPlano(entrada, new Plano());
		vitor.addPlano(saida, new Plano());
		System.out.println("Sair sem entrar: " + portaSaida.podeUsar(vitor, agora));
		portaEntrada.usar(vitor, agora);
		System.out.println("Dentro: " + vitor.estaDentro());
		System.out.println("Entrar outra vez: " + portaEntrada.podeUsar(vitor, agora.plusSeconds(5)));
		portaSaida.usar(vitor, agora.plusSeconds(5));
		System.out.println("Dentro depois de sair: " + vitor.estaDentro());

		// Teste 4: personal trainer (duração do plano)
		System.out.println("--- Teste 4: personal trainer ---");
		Servico pt = new Servico("Personal Trainer");
		Equipamento manel = new EquipamentoDuracaoPlano(20, "Manel Mandão", pt, true);
		iris.addPlano(pt, new Plano(Duration.ofMinutes(45), null,
				new MaxUsosPorPeriodo(1, Periodo.DIA), new MaxUsosPorPeriodo(3, Periodo.SEMANA)));
		LocalDateTime tarde = agora.plusHours(5);
		manel.usar(iris, tarde);
		System.out.println("Iris ocupada aos 44 min: " + iris.estaOcupado(tarde.plusMinutes(44)));
		System.out.println("Iris ocupada aos 45 min: " + iris.estaOcupado(tarde.plusMinutes(45)));
		System.out.println("Plano PT: " + iris.getPlano(pt).getDescricao());
		
		

		// Teste 5: duche com tempo extra (plano 5 + 2 min, 1 por dia)
		System.out.println("--- Teste 5: duche ---");
		Servico duche = new Servico("Duche");
		Equipamento duche1 = new Duche(30, "Duche 1", duche);
		Equipamento duche2 = new Duche(31, "Duche 2", duche);
		iris.addPlano(duche, new Plano(Duration.ofMinutes(5), Duration.ofMinutes(2),
				new MaxUsosPorPeriodo(1, Periodo.DIA)));
		LocalDateTime t = agora.plusHours(6);
		duche1.usar(iris, t);
		System.out.println("Duche ocupado aos 4 min: " + duche1.estaOcupado(t.plusMinutes(4)));
		System.out.println("Extra aos 8 min: " + duche1.podeUsar(iris, t.plusMinutes(8)));
		duche1.usar(iris, t.plusMinutes(8));
		System.out.println("Registos de duche: " + iris.getRegistos(duche).size());
		System.out.println("Duche ocupado aos 9 min (extra): " + duche1.estaOcupado(t.plusMinutes(9)));
		System.out.println("Segundo extra: " + duche1.podeUsar(iris, t.plusMinutes(11)));
		System.out.println("Outro duche no mesmo dia: " + duche2.podeUsar(iris, t.plusMinutes(20)));

		// Teste 6: aula com lotação (capacidade 2, 30 minutos)
		System.out.println("--- Teste 6: aula ---");
		Servico aula1 = new Servico("Aula nível 1");
		Equipamento step = new SalaAula(40, "Aula Step", aula1, true, 2, Duration.ofMinutes(30));
		Membro beatriz = new Membro(6, "Beatriz Bytes");
		Membro diogo = new Membro(5, "Diogo Digital");
		Membro filipe = new Membro(4, "Filipe Fictício");
		beatriz.addPlano(aula1, new Plano());
		diogo.addPlano(aula1, new Plano());
		filipe.addPlano(aula1, new Plano());
		beatriz.entrar();
		diogo.entrar();
		filipe.entrar();
		System.out.println("Iris sem plano de aula: " + step.podeUsar(iris, t.plusHours(1)));
		step.usar(beatriz, t);
		step.usar(diogo, t.plusMinutes(10));
		System.out.println("Diogo ocupado aos 29 min: " + diogo.estaOcupado(t.plusMinutes(29)));
		System.out.println("Diogo ocupado aos 30 min: " + diogo.estaOcupado(t.plusMinutes(30)));
		System.out.println("Aula cheia aos 15 min: " + step.estaOcupado(t.plusMinutes(15)));
		System.out.println("Filipe entra com a aula cheia: " + step.podeUsar(filipe, t.plusMinutes(15)));
		System.out.println("Aula ocupada depois de acabar: " + step.estaOcupado(t.plusMinutes(30)));
		System.out.println("Filipe entra na aula seguinte: " + step.podeUsar(filipe, t.plusMinutes(40)));
		

		// Teste 7: ginásio
		System.out.println("--- Teste 7: ginasio ---");
		Ginasio g = new Ginasio("Castelo Branco");
		g.addMembro(iris);
		g.addMembro(vitor);
		g.addEquipamento(dispensador);
		System.out.println("Membro 2: " + g.getMembro(2).getNome());
		System.out.println("Membro 99: " + g.getMembro(99));
		System.out.println("Equipamento 12: " + g.getEquipamento(12).getNome());
		System.out.println("Numero de membros: " + g.getMembros().size());
		try {
			g.addMembro(new Membro(2, "Outro com o mesmo id"));
			System.out.println("ERRO: aceitou um id repetido!");
		} catch (IllegalArgumentException e) {
			System.out.println("OK: " + e.getMessage());
		}
	}
}