
public class Banco {
	public static void comprarCompravel(Compravel compravel, Jogador jogador){
		if(jogador.getSaldo() >= compravel.getPreco()){ //caso o jogador tenha dinheiro
			jogador.adicionarCompravel(compravel);
			jogador.sacarDinheiro(compravel.getPreco());
			compravel.setDono(jogador);
		}
	}
	
	
	public static void hipotecaCompravel(Compravel compravel, Jogador jogador){
		if(compravel.getDono() == jogador){ //caso o jogador seja o dono da compravel
			compravel.setDono(null); //compravel fica sem nome
			jogador.removerCompravel(compravel); 
			jogador.sacarDinheiro(compravel.getValorHipoteca());
		}
	}
	
	public static void comprarCasa(Propriedade propriedade, Jogador jogador){
		if(jogador.getSaldo() > propriedade.getPrecoCasa() && propriedade.getNumeroCasas() < 5){
			propriedade.adicionarCasa();
			jogador.sacarDinheiro(propriedade.getPrecoCasa());
		}
}
	
	public static void venderCasa(Propriedade propriedade, Jogador jogador){
		if(propriedade.getNumeroCasas() > 0){
			propriedade.removerCasa();
			jogador.depositarDinheiro(propriedade.getPrecoCasa()/2);
		}
		
	}
}
