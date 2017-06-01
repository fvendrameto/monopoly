public abstract class Compravel extends Espaco {
    private Jogador propietario;
    private int preco;
    private int hipoteca;
    
    public Compravel(String nome, int preco, int hipoteca) {
        super(nome);
        this.hipoteca = hipoteca;
        this.propietario = null;
        this.preco = preco;
    }
    
    public Jogador getDono() {
        return propietario;
    }

    public void setDono(Jogador propietario) {
        this.propietario = propietario;
    }
   
    public boolean temDono(){
    	return this.propietario != null;
    }
    
    public int getPreco(){
    	return this.preco;
    }
    
    public int getValorHipoteca(){
    	return this.hipoteca;
    }
    
    public boolean propriedade(){
    	if(this instanceof Propriedade)
    		return true;
    	return false;
    }
    
    public abstract int getAluguel();
   
}
