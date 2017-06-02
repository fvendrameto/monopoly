
public class Carta {
	
	private boolean sorte;
	private String descricao; //Ex: "Volte ao ponto de partida e recebeba R$200,00"
	private int acao; //(0)->Ir a uma determinada posicao do tabuleiro;(1)Pagar ao banco;(2)Receber do banco, etc...
	private int quantia;
    private int posicao;
	
	public Carta(boolean sorte, String descricao, int acao, int quantia, int posicao) {
        this.sorte = sorte;
        this.acao = acao;
        this.quantia = quantia;
        this.posicao = posicao;
        this.descricao = descricao;
    }
	
	public Carta(boolean sorte, String descricao, int acao, int quantia_ou_posicao) {
        this.sorte = sorte;
        this.acao = acao;
        if(acao == 0){
        	this.posicao = quantia_ou_posicao;
        	this.quantia = 0;
        }else{
        	this.quantia = quantia_ou_posicao;
        	this.posicao = 0;
        }
        this.descricao = descricao;
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
	
}
