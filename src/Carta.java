import java.io.Serializable;

/**
 * Carta que contem as ações de jogo
 */
public class Carta implements Serializable {

	private static final long serialVersionUID = -4488372956600050772L;
	private boolean sorte;
	private String descricao; //Ex: "Volte ao ponto de partida e recebeba R$200,00"
	private Acao acao;
	
	public Carta(boolean sorte, String descricao, int acao, int quantia, int posicao) {
        this.sorte = sorte;
        this.acao = new Acao(acao, quantia, posicao);
        this.descricao = descricao;
    }

	/**
	 * Retorna descrição da carta
	 * @return descrição da carta
	 */
	public String getDescricao(){
		return this.descricao;
	}

	/**
	 * Retorna se carta é de sorte(true) ou reves(false)
	 * @return Boolean indicando ase a carta é de sorte
	 */
	public boolean getSorteOuReves(){
		return this.sorte;
	}

	/**
	 * @return Ação da carta
	 */
	public Acao getAcao(){
		return this.acao;
	}

	@Override
	public String toString(){
		String str = "";
		if(this.sorte) str += "SORTE ";
		else str += "REVES ";
		str += this.descricao + " ";
		str += this.acao.getCodigo() + " " + this.acao.getQuantia() + " " + this.acao.getPosicao();
		return str;
	}
	
}
