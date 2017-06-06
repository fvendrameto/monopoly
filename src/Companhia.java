public class Companhia extends Compravel {
    int aluguel;
    
    Companhia(String nome, int preco, int hipoteca, int aluguel) {
        super(nome,preco,hipoteca);
        this.aluguel = aluguel;
    }
    
    public int getAluguel() {
        return aluguel;
    }
    
    @Override
    public String toString(){
    	String str = this.getNome() + "\n";
    	str += this.getPreco() + "\n";
    	str += this.getValorHipoteca() + "\n";
    	str += this.aluguel + "\n"; 
    	return str;
    }
    
}