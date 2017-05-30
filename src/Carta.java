
public class Carta {
	
	private boolean sorte;
	private String descricao; //Ex: "Volte ao ponto de partida e recebeba R$200,00"
	private int acao; //(0)->Ir a uma determinada posicao do tabuleiro;(1)Pagar ao banco;(2)Receber do banco, etc...
	
	public Carta(boolean s, String desc) {
		this.sorte = s;
		this.descricao = desc;
	}
	
	public void realizarAcao (int acao, Jogador j, int quantia, int posicao){
		switch (acao){
		case 0:									//Ir a uma determinada posicao do tabuleiro (sorte/revés)
			j.setPosicaoTabuleiro(posicao);
		case 1:									//Pagar ao banco (revés)
			j.sacarDinheiro(quantia);
		case 2:									//Receber (sorte)
			j.depositarDinheiro(quantia);		
		case 3:									//EX: "vá ao ponto de partida e receba 200 reais"
			j.setPosicaoTabuleiro(posicao);
			j.depositarDinheiro(quantia);
		}
	}

}
