import java.io.Serializable;

/**
 * Classe que representa os espaços que um jogador pode comprar
 */
public abstract class Compravel extends Espaco implements Serializable {
    private Jogador propietario;
    private int preco;
    private int hipoteca;
    
    public Compravel(String nome, int preco, int hipoteca) {
        super(nome);
        this.hipoteca = hipoteca;
        this.propietario = null;
        this.preco = preco;
    }

    /**
     * @return Jogador dono do espaço
     */
    public Jogador getDono() {
        return propietario;
    }

    /**
     * @param propietario Jogador que será adicionado como proprietario
     */
    public void setDono(Jogador propietario) {
        this.propietario = propietario;
    }

    /**
     * @return Boolean indicando se o espaço possui um dono
     */
    public boolean temDono(){
    	return this.propietario != null;
    }

    /**
     * @return Preço do espaço
     */
    public int getPreco(){
    	return this.preco;
    }

    /**
     * @return Valor da hipoteca do espaço
     */
    public int getValorHipoteca(){
    	return this.hipoteca;
    }

    /**
     * @return Boolean indicando se o espaço e do tipo propriedade
     */
    public boolean propriedade(){
    	if(this instanceof Propriedade)
    		return true;
    	return false;
    }
    
    public abstract int getAluguel();
   
}
