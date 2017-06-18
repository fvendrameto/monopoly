package monopoly_elements;

import java.io.Serializable;

/**
 * Responsavel pelas propriedade, ou seja, os compraveis que podem ter casas associadas
 */
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

    /**
     * @return Aluguel que deve ser pago ao parar na propriedade
     */
    public int getAluguel() {
        return tabela_precos[n_casas];
    }

    public int getAluguel(int index){
        return tabela_precos[index];
    }
    /**
     * Adiciona uma casa na propriedade
     */
    public void adicionarCasa(){
    	if(n_casas <= 5)
    		n_casas++;
    }

    /**
     * @return Preço para adicionar uma casa na propriedade
     */
    public int getPrecoCasa(){
    	return this.preco_casa;
    }

    /**
     * @return Numero de casas na propriedade
     */
    public int getNumeroCasas(){
    	return this.n_casas;
    }

    /**
     * @return Cor da propriedade
     */
    public int getCor(){
    	return this.cor;
    }

    /**
     * @return Boolean indicando se existe ao menos uma casa na propriedade
     */
    public boolean temCasa() {
    	return this.n_casas > 0;
    }

    /**
     * Remove uma casa da propriedade
     */
    public void removerCasa(){
    	this.n_casas--;
    }

    /**
     * Metodo estatico que indica o total de propriedades de uma determinada cor
     * @param cor Cor que será consultada
     * @return Total de propriedades da cor indicada
     */
    public static int getTotalCor(int cor){
       // System.out.println("n " + cor + "=" + quantidade_cor[cor]);
        return quantidade_cor[cor];
    }
    
    @Override
    public String toString(){
    	return this.getNome();
    }
    
}
