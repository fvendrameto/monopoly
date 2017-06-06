
public class Carta {
	
	private boolean sorte;
	private String descricao; //Ex: "Volte ao ponto de partida e recebeba R$200,00"
	Acao acao;
	
	public Carta(boolean sorte, String descricao, int acao, int quantia, int posicao) {
        this.sorte = sorte;
        this.acao = new Acao(acao,quantia,posicao);
        this.descricao = descricao;
        
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
	public boolean getSorteOuReves(){
		return this.sorte;
	}
	
	public Acao getAcao(){
		return this.acao;
	}

	@Override
	public String toString(){
		String str = "";
		if(this.sorte) str += "SORTE\n";
		else str += "REVES\n";
		str += this.descricao + "\n";
		str += this.acao.getAcao() + "\n" + this.acao.getQuantia() + "\n" + this.acao.getPosicao();
		return str;
	}
	
}
