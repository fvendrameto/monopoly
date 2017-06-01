
public class Propriedade extends Compravel {
    private int n_casas;
    private int[] tabela_precos;
    private int cor;
    private static int[] quantidade_cor; //PRECISA CRIAR SETTER PARA ISSO
    
    Propriedade(String nome, int preco, int hipoteca,int[] tabela_precos, int cor) {
        super(nome,preco,hipoteca);
        this.tabela_precos = tabela_precos;
        this.n_casas = 0;
        this.cor = cor;
    }
    
    public int getAluguel() {
        return tabela_precos[n_casas];
    }
    
    public boolean adicionarCasa(){
    	if(n_casas <= 5){
    		n_casas++;
    		return true;
    	}
    	return false;
    }
    
    public int getCor(){
    	return this.cor;
    }
     
    public static int getTotalCor(int cor){
    	return quantidade_cor[cor];
    }
    
}
