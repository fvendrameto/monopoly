
/**
 *
 * @author guimontemovo
 */
public abstract class Compravel extends Espaco {
    private Jogador propietario;
    
    Compravel(String nome, int posicao) {
        super(nome, posicao);
    }
    
    public Jogador getJogador() {
        return propietario;
    }

    public void setJogador(Jogador propietario) {
        this.propietario = propietario;
    }
    
    abstract public int getPreco();
    
    public void pagar(Jogador pagante) {
        propietario.depositarDinheiro(pagante.sacarDinheiro(getPreco()));
    }
}
