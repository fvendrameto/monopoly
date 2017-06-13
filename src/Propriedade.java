import java.io.Serializable;

public class Propriedade extends Compravel implements Serializable {
    private int n_casas;
    private int[] tabela_precos;
    private int cor;
    private int preco_casa;
    private static int[] quantidade_cor = new int[10];
    
    Propriedade(String nome, int preco, int hipoteca,int[] tabela_precos,int preco_casa, int cor) {
        super(nome,preco,hipoteca);
        this.tabela_precos = tabela_precos;
        this.n_casas = 0;
        this.cor = cor;
        this.preco_casa = preco_casa;
        quantidade_cor[cor]++;
    }
    
    public static void setCorPropriedade(int cor){
    	quantidade_cor[cor]++;
    }
    
    public int getAluguel() {
        return tabela_precos[n_casas];
    }
    
    public void adicionarCasa(){
    	if(n_casas <= 5)
    		n_casas++;
    }
    
    public int getPrecoCasa(){
    	return this.preco_casa;
    }
    
    public int getNumeroCasas(){
    	return this.n_casas;
    }
    
    public int getCor(){
    	return this.cor;
    }
    
    public boolean temCasa() {
    	return this.n_casas > 0;
    }
    
    public void removerCasa(){
    	this.n_casas--;
    }
    
    public static int getTotalCor(int cor){
       // System.out.println("n " + cor + "=" + quantidade_cor[cor]);
        return quantidade_cor[cor];
    }
    
    @Override
    public String toString(){
    	return this.getNome();
    }
    
}
