package monopoly_elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe responsavel por inicializar os componentes de jogo a partir de arquivos formatados
 */
public class Initializers {
	/**
	 * Inicializa os espaços do jogo
	 * @param filename Nome do arquivo com os dados
	 * @return Espaços do tabuleiro devidamente inicializados
	 * @throws FileNotFoundException
	 */
    public static ArrayList<Espaco> initEspacos(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		int tipo;
		ArrayList<Espaco> espacos = new ArrayList<Espaco>();

		int contP = 0;
		int contC = 0;
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
				String endImg = "images/carta" + contP++ + ".png";
				Propriedade p = new Propriedade(nome,preco,hipoteca,tabela_precos,preco_casa,cor,endImg);
				espacos.add(p);
			}else if(tipo == 2){ //companhia
				String nome = scanner.nextLine();
				int preco = scanner.nextInt();
				int hipoteca = scanner.nextInt();
				int aluguel = scanner.nextInt();
				String endImg = "images/companhia" + contC++ + ".png";
				Companhia c = new Companhia(nome,preco,hipoteca,aluguel,endImg);
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

	/**
	 * Inicializa as cartas do jogo
	 * @param filename Nome do arquivo com os dados
	 * @return Cartas do tabuleiro devidamente inicializados
	 * @throws FileNotFoundException
	 */
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
