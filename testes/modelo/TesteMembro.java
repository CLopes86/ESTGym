package modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TesteMembro {
	
	/**
	 * Testes simples da classe Membro.
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// hora fixa para os testes: 12/11/2026 às 10h00
		LocalDateTime agora = LocalDateTime.of(2026, 11, 12, 10, 0);
		
		// Teste 1: estado inicial de um membro novo
		Membro m = new Membro(1, "Vitor Virtual");
		System.out.println("---Teste 1: estado inicial---");
		System.out.println("Id " + m.getId());
		System.out.println("Nome: " + m.getNome());
		System.out.println("Dentro: " + m.estaDentro());
		System.out.println("Ocupado: " + m.estaOcupado(agora));
		System.out.println("Registos: " + m.getRegistos().size());
		
		// Teste 2: entrar e sair
		System.out.println("---- Teste 2: entrar e sair ---");
		m.entrar();

		System.out.println("Depois de entrar, dentro: " +  m.estaDentro());
		
		try {
			m.entrar();
			System.out.println("Erro: entrou duas vezes");
		} catch (IllegalStateException e) {
			System.out.println("OK: " + e.getMessage());
		}
		
		m.sair();
		System.out.println("Depois de sair, dentro:  " + m.estaDentro());
		
		try {
			m.sair();
			System.out.println("ERRO: saiu duas vezes");
		} catch (IllegalStateException e) {
			System.out.println("OK: " + e.getMessage());
		}
		
		// Teste 3: ficar ocupado (duche de 5 minutos às 10h00)
		System.out.println("---Teste 3: ocupado---");
		m.ocupar(agora.plusMinutes(5));
		System.out.println("10h00: " + m.estaOcupado(agora));
		System.out.println("10h03 " + m.estaOcupado(agora.plusMinutes(3)));
		System.out.println("10h05 " + m.estaOcupado(agora.plusMinutes(5)));
		System.out.println("10h20: " + m.estaOcupado(agora.plusMinutes(20)));
		
		// Teste 4: planos
		System.out.println("--- Teste 4: planos ----");
		Servico bebida = new Servico("Bebida vitaminada");
		Servico massagem = new Servico("Cadeira de Massagem");
		
		Plano planoBebida = new Plano(
				new MaxUsosPorPeriodo(2, Periodo.DIA),
				new IntervaloMinimo(30, ChronoUnit.MINUTES));
		m.addPlano(bebida, planoBebida);
		
		System.out.println("Plano bebida: " + m.getPlano(bebida).getDescricao());
		System.out.println("Plano massagem: " + m.getPlano(massagem));
		System.out.println("Numero de planos: " + m.getPlanos().size());
		System.out.println("Pode beber (sem usos): " + m.getPlano(bebida).permite(m.getRegistos(bebida), agora));
		 

		try {
			m.getPlanos().clear();
			System.out.println("ERRO: apagou os planos por fora!");
		} catch (UnsupportedOperationException e) {
			System.out.println("OK: os planos estao protegidos");
		}

	}

}
