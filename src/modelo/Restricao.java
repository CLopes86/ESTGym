package modelo;



import java.time.LocalDateTime;
import java.util.List;

/**
 * Uma regra de um plano. Responde à pergunta: "com estes usos anteriores,
 * o membro pode usar o serviço agora?"
 */

public interface Restricao {
	/**
	 * Verifica se a regra permite um novo uso.
	 *
	 * @param usos  registos anteriores do membro para o serviço, por ordem
	 *              cronológica
	 * @param agora o momento do pedido
	 * @return true se a regra permite o uso
	 */
	boolean permite(List<Registo> usos, LocalDateTime agora);
	
	/**
	 * @return descrição da regra para mostrar ao utilizador
	 */
	String getDescricao();
	
	

}
