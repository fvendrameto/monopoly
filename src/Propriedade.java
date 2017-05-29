
import java.util.LinkedList;

/**
 *
 * @author guimontemovo
 */
public class Propriedade extends Compravel {
    int n_casas;
    int n_predios;
    int[][] tabela_precos;
    int[][] tabela_hipoteca;
    
    Propriedade(String nome, int posicao) {
        super(nome, posicao);
    }
    
    @Override
    public int getPreco() {
        return tabela_precos[n_casas][n_predios];
    }

    public void setTabela_precos(int[][] tabela_precos) {
        this.tabela_precos = tabela_precos;
    }
    
    public int getValorHipoteca() {
        return tabela_hipoteca[n_casas][n_predios];
    }

    public void setTabela_hipoteca(int[][] tabela_hipoteca) {
        this.tabela_hipoteca = tabela_hipoteca;
    }
}
