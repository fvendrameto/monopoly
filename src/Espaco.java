import java.io.Serializable;
import java.util.LinkedList;

/**
 * Classe que representa os espaços do tabuleiro
 */
public abstract class Espaco implements Serializable {

	private static final long serialVersionUID = 1859539012678164624L;
	private String nome;
    LinkedList<Jogador> jogadores;
        
    Espaco(String nome) {
        this.nome = nome;
        jogadores = new LinkedList<>();
    }

    /**
     * @param jogador Jogador que será adicionado no espaço, indicando que ele esta parado ali
     */
    public void addJogador(Jogador jogador){
        jogadores.add(jogador);
    }

    /**
     * @param jogador Jogador que deve ser removido da lista de parados no espaço
     */
    public void removeJogador(Jogador jogador){
        jogadores.remove(jogador);
    }

    /**
     * @return Nome do espaço
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome Nome do espaço
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return Boolean indicando se o espaço é um compravel
     */
    public boolean compravel(){
    	if(this instanceof Compravel)
    		return true;
    	return false;
    }
     
    
}
