package monopoly_elements;

import java.io.Serializable;

/**
 * Representa as posiçoes do tabuleiro que não são compraveis
 */
public class Jogavel extends Espaco implements Serializable {

	private static final long serialVersionUID = -3031858277739808111L;
	private String descricao;
    private Acao acao;

    public Jogavel() {
    	super(null);
    }
	
	public Jogavel(String nome, String descricao, int acao, int quantia, int posicao){
        super(nome);
        this.acao = new Acao(acao, quantia, posicao);
        this.descricao = descricao;
    }

    /**
     * @return Ação associada ao espaço
     */
	public Acao getAcao(){
		return this.acao;
	}

    @Override
    public String toString(){
    	return this.getNome();
    }
}
