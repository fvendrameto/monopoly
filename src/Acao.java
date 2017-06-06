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
	
	public static void realizarAcao(Tabuleiro tabuleiro, Jogador jogador, Jogavel jogavel){
		realizarAcao(tabuleiro,jogador,jogavel.getAcao(),jogavel.getQuantia(),jogavel.getPosicao());
	}
	
	public static void realizarAcao(Tabuleiro tabuleiro, Jogador jogador, Carta carta){
		realizarAcao(tabuleiro,jogador,carta.getAcao(),carta.getQuantia(),carta.getPosicao());
	}
	
	public static void realizarAcao (Tabuleiro t,  Jogador j, int acao, int quantia, int posicao){
		
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
	
	public static void mudarPosicao (Jogador j, int posicao){
		j.setPosicaoTabuleiro(posicao);
	}
	
	public static void pagar (Jogador j, int quantia){
		j.sacarDinheiro(quantia);
	}
	
	public static void receber (Jogador j, int quantia){
		j.depositarDinheiro(quantia);
	}
}