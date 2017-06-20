package monopoly_elements;

/**
 * Classe que realiza as funcionalidades de jogo relacionadas as compras de bens
 */
public class Banco {
	/**
	 * Realiza as ações necessarias para um bem pertencer a um jogador
	 * @param compravel Compravel que será atribuido a um jogador
	 * @param jogador Jogador que irá adiquirir a propriedade
	 */
	public static void comprarCompravel(Compravel compravel, Jogador jogador){
		if(jogador.getSaldo() >= compravel.getPreco()){ //caso o jogador tenha dinheiro
			jogador.adicionarCompravel(compravel);
			jogador.sacarDinheiro(compravel.getPreco());
			compravel.setDono(jogador);
		}
	}

	/**
	 * Realiza as ações necessarias para hipotecar um bem de um jogador
	 * @param compravel Compravel que deve ser hipotecado
	 * @param jogador Jogador que irá hipotecar um bem
	 */
	public static void hipotecaCompravel(Compravel compravel, Jogador jogador){
		if(compravel.getDono().getNome().equals(jogador.getNome())){ //caso o jogador seja o dono da compravel
			System.out.println("TA AQUI");
			compravel.setDono(null); //compravel fica sem nome
			jogador.removerCompravel(compravel); 
			jogador.depositarDinheiro(compravel.getValorHipoteca());
		}
	}

	/**
	 * Adiciona uma casa em uma propriedade do jogador
	 * @param propriedade Propriedade que terá uma casa adicionada
	 * @param jogador Jogador que está comprando a casa
	 */
	public static void comprarCasa(Propriedade propriedade, Jogador jogador){
		if(jogador.getSaldo() > propriedade.getPrecoCasa() && propriedade.getNumeroCasas() < 5){
			propriedade.adicionarCasa();
			jogador.sacarDinheiro(propriedade.getPrecoCasa());
		}
}

	/**
	 * Remove uma casa na propriedade de um jogador
	 * @param propriedade Propriedade que terá casa removida
	 * @param jogador Jogador que está removendo a casa
	 */
	public static void venderCasa(Propriedade propriedade, Jogador jogador){
		if(propriedade.getNumeroCasas() > 0){
			propriedade.removerCasa();
			jogador.depositarDinheiro(propriedade.getPrecoCasa()/2);
		}
		
	}
}
