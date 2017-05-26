
public class Banco {
	public static void comprarPropriedade(Propriedade propriedade, Jogador jogador){
		if(propriedade.getJogador() == null){ //caso a propriedade não tenha dono
			if(jogador.getSaldo() >= propriedade.getPreco()){ //caso o jogador tenha dinheiro
				jogador.adicionarPropriedade(propriedade);
				jogador.sacarDinheiro(propriedade.getPreco());
				propriedade.setJogador(jogador);
			}else{
				System.out.println("Saldo insuficiente para fazer a compra");
			}
		}
	}
	
	
	public static void hipotecaPropriedade(Propriedade propriedade, Jogador jogador){
		if(propriedade.getJogador() == jogador){ //caso o jogador seja o dono da propriedade
			propriedade.setJogador(null); //propriedade fica sem nome
			jogador.removerPropriedade(propriedade); 
			jogador.sacarDinheiro(propriedade.getValorHipoteca());
		}
	}
}
