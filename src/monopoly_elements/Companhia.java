package monopoly_elements;

import java.io.Serializable;

/**
 * Classe que representa as companhias do jogo
 */
public class Companhia extends Compravel implements Serializable{
    int aluguel;
    
    Companhia(String nome, int preco, int hipoteca, int aluguel) {
        super(nome,preco,hipoteca);
        this.aluguel = aluguel;
    }

    /**
     *
     * @return Valor do aluguel do espaço
     */
    public int getAluguel() {
        return aluguel;
    }
    
    @Override
    public String toString(){
    	return this.getNome();
    }
    
}
