package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

}
