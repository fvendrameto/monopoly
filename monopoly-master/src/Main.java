import java.util.ArrayList;

public class Main {
	ArrayList<Jogador> jogadores;
	Tabuleiro tabuleiro = new Tabuleiro();

	//SÓ UMA IDEIA DE IMPLEMENTAÇÃO MUITO SIMPLES, AINDA É PRECISO PENSAR NO SISTEMA DE SERVIDOR E CLIENTE
	public static void main(){
		int idx_jogador_atual;
		Jogador jogador_atual;
		int dados;
		
		initComponentes();
		while(tabuleiro.jogoContinua()){
			idx_jogador_atual = tabuleiro.getJogadorAtual();
			jogador_atual = jogadores[jogador_atual];
			
			dados = Dados.rolar(2, new int[2]);
			int posicao = jogador_atual.andarPosicaoTabuleiro(dados);
			Espaco espaco_atual = tabuleiro.getEspacoPosicao(posicao);
			
			if(espaco_atual instanceof Propriedade){
				if(espaco_atual.getJogador() != null){
					aluguel = espaco_atual.getAluguel();
					jogador_atual.sacarDinheiro(aluguel);
					espaco_atual.getJogador().sacarDinheiro(aluguel);
				}else{
					quer_comprar = //input do usuario
					if(quer_comprar){
						Banco.comprarPropriedade(espaco_atual, jogador_atual);
					}
				}
				
			}else{
				//realiza ação
			}
			
		}
	}
}	
