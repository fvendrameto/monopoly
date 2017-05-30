/**
 *
 * @author guimontemovo
 */
public class Jogavel extends Espaco {
    Jogavel(String nome, int posicao) {
        super(nome, posicao);
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
}
