/**
 *
 * @author guimontemovo
 */
public class Companhia extends Compravel {
    int n_companhias;
    int[] tabela_precos;
    
    Companhia(String nome, int posicao) {
        super(nome, posicao);
    }
    
    public int getPreco() {
        return tabela_precos[n_companhias];
    }

    public void setTabela_precos(int[] tabela_precos) {
        this.tabela_precos = tabela_precos;
    }
}
