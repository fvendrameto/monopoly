
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author guimontemovo
 */
public abstract class Espaco {
    private String nome;
    LinkedList<Jogador> jogadores;
        
    Espaco(String nome) {
        this.nome = nome;
        jogadores = new LinkedList<>();
    }

    public void addJogador(Jogador jogador){
        jogadores.add(jogador);
    }

    public void removeJogador(Jogador jogador){
        jogadores.remove(jogador);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }  
    
    public boolean compravel(){
    	if(this instanceof Compravel)
    		return true;
    	return false;
    }
     
    public static ArrayList<Espaco> initEspacos(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		int tipo;
		ArrayList<Espaco> espacos = new ArrayList<Espaco>();
		
		while(scanner.hasNextInt()){
			tipo = scanner.nextInt();
			scanner.nextLine();
			if(tipo == 1){ //propriedade
				String nome = scanner.nextLine();
				int preco = scanner.nextInt();
				int hipoteca = scanner.nextInt();
				int[] tabela_precos = new int[6];
				for(int i=0;i<6;i++){
					tabela_precos[i] = scanner.nextInt();
				}
				int cor = scanner.nextInt();
				Propriedade p = new Propriedade(nome,preco,hipoteca,tabela_precos,cor);
				espacos.add(p);
			}else if(tipo == 2){ //companhia
				String nome = scanner.nextLine();
				int preco = scanner.nextInt();
				int hipoteca = scanner.nextInt();
				int aluguel = scanner.nextInt();
				Companhia c = new Companhia(nome,preco,hipoteca,aluguel);
				espacos.add(c);
			}else{ //jogavel
				String nome = scanner.nextLine();
				int acao = scanner.nextInt(); 
				Jogavel j = new Jogavel(nome,acao);
				espacos.add(j);
			}
		}
		
		scanner.close();
		
		return espacos;
	}
}
