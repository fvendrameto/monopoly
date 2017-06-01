
public class Propriedade extends Compravel {
    int n_casas;
    int[] tabela_precos;
    int cor;
    
    Propriedade(String nome, int preco, int hipoteca,int[] tabela_precos, int cor) {
        super(nome,preco,hipoteca);
        this.tabela_precos = tabela_precos;
        this.n_casas = 0;
        this.cor = cor;
    }
    
    public int getAluguel() {
        return tabela_precos[n_casas];
    }
    
    public int getCor(){
    	return this.cor;
    }
     

}
