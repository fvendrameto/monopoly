public class Jogavel extends Espaco {
    private String descricao;
    private int acao;
    private int quantia;
    private int posicao;
    
	
	public Jogavel(String nome, String descricao, int acao, int quantia, int posicao) {
        super(nome);
        this.acao = acao;
        this.quantia = quantia;
        this.posicao = posicao;
        this.descricao = descricao;
    }
	
	public Jogavel(String nome, String descricao, int acao, int quantia_ou_posicao) {
        super(nome);
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
	
    @Override
    public String toString(){
    	String str = this.getNome() + "\n";
    	str += acao;
    	return str;
    }
}
