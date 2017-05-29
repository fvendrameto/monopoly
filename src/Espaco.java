
import java.util.LinkedList;

/**
 *
 * @author guimontemovo
 */
public abstract class Espaco {
    private String nome;
    LinkedList<Jogador> jogadores;
        
    Espaco(String nome, int posicao) {
        this.nome = nome;
        jogadores = new LinkedList<>();
    }

    public void addJogador(Jogador jogador){
        jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador){
        jogadores.remove(jogador);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   
      
}
