
public class Banco {
	public static void comprarcompravel(Compravel compravel, Jogador jogador){
		if(compravel.getDono() == null){ //caso a compravel não tenha dono
			if(jogador.getSaldo() >= compravel.getPreco()){ //caso o jogador tenha dinheiro
				jogador.adicionarCompravel(compravel);
				jogador.sacarDinheiro(compravel.getPreco());
				compravel.setDono(jogador);
			}else{
				System.out.println("Saldo insuficiente para fazer a compra");
			}
		}
	}
	
	
	public static void hipotecacompravel(Compravel compravel, Jogador jogador){
		if(compravel.getDono() == jogador){ //caso o jogador seja o dono da compravel
			compravel.setDono(null); //compravel fica sem nome
			jogador.removerCompravel(compravel); 
			jogador.sacarDinheiro(compravel.getValorHipoteca());
		}
	}
}
