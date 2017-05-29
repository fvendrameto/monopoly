/**
 *
 * @author guimontemovo
 */
public abstract class Espaco {
    private String nome;
    private int posicao_tabuleiro;
    
    Espaco(String nome, int posicao) {
        this.nome = nome;
        posicao_tabuleiro = posicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPosicao_tabuleiro() {
        return posicao_tabuleiro;
    }

    public void setPosicao_tabuleiro(int posicao_tabuleiro) {
        this.posicao_tabuleiro = posicao_tabuleiro;
    }

    
      
}
