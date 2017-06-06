/*
 * CODIGO DAS AÇÕES:
 * 
 * 0 - mudar posicao
 * 1 - pagar ao banco
 * 2 - receber do banco
 * 3 - mudar posicao e pagar
 * 4 - pagar aos jogadores
 * 5 - receber dos jogadores 
 * 
 */


public class Acao {

	private int acao;
	private int quantia;
	private int posicao;

	
	public Acao(int acao, int quantia, int posicao){
		this.acao = acao;
		this.quantia = quantia;
		this.posicao = posicao;
	}
	
	public int getAcao(){
		return this.acao;
	}
	
	public int getQuantia(){
		return this.quantia;
	}

	public int getPosicao(){
		return this.posicao;
	}
	
	
	public void realizarAcao (Tabuleiro t,  Jogador j){
		
		switch (acao){
		case 0:									//Ir a uma determinada posicao do tabuleiro
			mudarPosicao(j, posicao);
		case 1:									//Pagar ao banco
			pagar(j, quantia);
		case 2:									//Receber
			receber(j, quantia);		
		case 3:									//EX: "vá ao ponto de partida e receba 200 reais"
			mudarPosicao(j, posicao);
			receber(j, quantia);
		case 4:									//EX: Pagar a todos os jogadores
			for (Jogador j2: t.getJogadores() ){
				pagar(j, quantia);
				receber(j2, quantia);
			}
		case 5:									//EX: Receber de todos os jogadores
			for (Jogador j2: t.getJogadores() ){
				pagar(j2, quantia);
				receber(j, quantia);
			}
		}
	}
	
	private static void mudarPosicao (Jogador j, int posicao){
		j.setPosicaoTabuleiro(posicao);
	}
	
	private static void pagar (Jogador j, int quantia){
		j.sacarDinheiro(quantia);
	}
	
	private static void receber (Jogador j, int quantia){
		j.depositarDinheiro(quantia);
	}
}