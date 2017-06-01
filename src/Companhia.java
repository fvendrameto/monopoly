public class Companhia extends Compravel {
    int aluguel;
    
    Companhia(String nome, int preco, int hipoteca, int aluguel) {
        super(nome,preco,hipoteca);
    }
    
    public int getAluguel(int res_dados) {
        return aluguel * res_dados;
    }
    
}
