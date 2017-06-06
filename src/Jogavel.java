public class Jogavel extends Espaco {
    private String descricao;
    private Acao acao;
	
	public Jogavel(String nome, String descricao, int acao, int quantia, int posicao) {
        super(nome);
        this.acao = new Acao(acao,quantia,posicao);
        this.descricao = descricao;
    }
	
	public Acao getAcao(){
		return this.acao;
	}
	
    @Override
    public String toString(){
    	String str = this.getNome() + "\n";
    	str += this.descricao + "\n";
    	str += acao.getAcao();
    	return str;
    }
}
