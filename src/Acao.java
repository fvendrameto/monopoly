import java.io.Serializable;

/**
 * Possui as informações responsaveis pelas ações de cartas e espaços do jogo
 */
public class Acao implements Serializable {
	private int codigo;
	private int quantia;
	private int posicao;

	/*
 	* CODIGOS DAS AÇÕES:
 	*
 	* 0 - mudar posicao
 	* 1 - pagar ao banco
 	* 2 - receber do banco
 	* 3 - mudar posicao e pagar
 	* 4 - pagar aos jogadores
 	* 5 - receber dos jogadores
 	*
 	*/

	public Acao(int codigo, int quantia, int posicao){
		this.codigo = codigo;
		this.quantia = quantia;
		this.posicao = posicao;
	}

	public void realizarAcao (Tabuleiro t,  Jogador j){
		
		switch (codigo){
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

	/**
	 * @return Código referente a ação
	 */
	public int getCodigo(){
		return this.codigo;
	}

	/**
	 * @return Posição que o jogador deve se deslocar de acordo com a ação
	 */
	public int getPosicao(){
		return this.posicao;
	}

	/**
	 * @return Quantia que deve ser paga ou recebida pelo jogador de acordo com a ação
	 */
	public int getQuantia(){
		return this.quantia;
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