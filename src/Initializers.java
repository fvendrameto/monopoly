import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Initializers {
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
				int preco_casa = scanner.nextInt();
				int cor = scanner.nextInt();
				Propriedade.setCorPropriedade(cor);
				Propriedade p = new Propriedade(nome,preco,hipoteca,tabela_precos,preco_casa,cor);
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
				String descricao = scanner.nextLine();
				int acao = scanner.nextInt(); 
				int quantia = scanner.nextInt();
				int posicao = scanner.nextInt();
				Jogavel j = new Jogavel(nome,descricao,acao,quantia,posicao);
				espacos.add(j);
			}
		}
		
		scanner.close();
		
		return espacos;
	}
    
    public static ArrayList<Carta> initCartas(String filename) throws FileNotFoundException{
    	File file = new File(filename);
    	Scanner scanner = new Scanner(file);
    	ArrayList<Carta> cartas = new ArrayList<Carta>();
    	
    	while(scanner.hasNextInt()){
    		int sorte = scanner.nextInt();
    		scanner.nextLine();
    		String descricao = scanner.nextLine();
    		int acao = scanner.nextInt();
    		int quantia = scanner.nextInt();
    		int posicao = scanner.nextInt();
    		Carta c = new Carta(sorte == 1,descricao,acao,quantia,posicao);
    		cartas.add(c);
    	}
    	
    	scanner.close();
    	return cartas;
    }
    
    
    
    
    
}
