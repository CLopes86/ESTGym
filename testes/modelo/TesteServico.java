package modelo;

public class TesteServico {

	public static void main(String[] args) {
		
		// Teste 1: criar um serviço normal
		Servico s = new Servico("Duche");
		System.out.println("Teste 1 - Nome: " + s.getNome());
		
		// Teste 2: os espaços nas pontas são retirados
		Servico s2 = new Servico("  Bebida  ");
		System.out.println("Teste 2 - Nome: " +s2.getNome());
		
		// Teste 3: nome só com espaços tem de dar erro
		try {
			new Servico("  ");
			System.out.println("Teste 3 - ERRO: aceitou um nome vazio!");
		} catch (IllegalArgumentException e) {
			System.out.println("Teste 3 - OK: nome vazio recusado");
			
		}
		
		// Teste 4: nome null tem de dar erro
		try {
			new Servico(null);
			System.out.println("Teste 4 - ERRO: aceitou null");
		} catch (NullPointerException e) {
			System.out.println("Teste 4 - OK: null recusado");
		}

	}

}
