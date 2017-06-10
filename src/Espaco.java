import java.io.Serializable;
import java.util.LinkedList;

public abstract class Espaco implements Serializable {

	private static final long serialVersionUID = 1859539012678164624L;
	private String nome;
    LinkedList<Jogador> jogadores;
        
    Espaco(String nome) {
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
    
    public boolean compravel(){
    	if(this instanceof Compravel)
    		return true;
    	return false;
    }
     
    
}
