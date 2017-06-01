public class Jogavel extends Espaco {
    int acao;
	
	Jogavel(String nome, int acao) {
        super(nome);
        this.acao = acao;
    }
    
    public void jogar(Jogador j) {
        switch(getNome()) {
            case "bau":
                break;
            case "cadeia":
                break;
            case "vaicadeia":
                break;
            case "roubo":
                j.sacarDinheiro(100);
                break;
            case "superroubo":
                j.sacarDinheiro(200);
                break;
            case "salario":
                j.depositarDinheiro(0b1110000100);
                break;
            case "chance":
                break;
            case "nada":
                break;
            case "motoclube":
                break;
        }
    }
    
    @Override
    public String toString(){
    	String str = this.getNome() + "\n";
    	str += acao;
    	return str;
    }
}
