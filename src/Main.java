import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static Tabuleiro tabuleiro; 
	public static int numero_jogadores;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		ArrayList<Espaco> espacos = new ArrayList<Espaco>();
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		ArrayList<Compravel> compraveis;
		HashMap<Integer,Compravel> map_compraveis;
		Jogador jogador;
		Espaco espaco_atual;
		int resultado_dados;
		int[] dados = null;
	
		espacos = Initializers.initEspacos("espacos.dat");
		cartas = Initializers.initCartas("cartas.dat");
		System.out.println(espacos.size());
		
		System.out.println("Digite o numero de jogadores: ");
		numero_jogadores = EntradaTeclado.leInt();
		
		for(int i=0;i<numero_jogadores;i++){
			System.out.println("Digite o nome do jogador: ");
			String nome = EntradaTeclado.leString();
			jogadores.add(new Jogador(nome));
		}
		
		tabuleiro = new Tabuleiro(jogadores,espacos,cartas);
		
		tabuleiro.setOrdem();
		while(tabuleiro.jogoContinua()){
			jogador = tabuleiro.getJogadorAtual();
			System.out.println(jogador+ " é sua vez");
			System.out.println("Aperte enter para jogar os dados");
			EntradaTeclado.leString();
			resultado_dados = Dados.rolar(2,dados);
			System.out.println("Deu " + resultado_dados);
			
			tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro()).removeJogador(jogador);
			boolean ret = jogador.andarPosicaoTabuleiro(resultado_dados);
			if(ret) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
			espaco_atual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
			espaco_atual.addJogador(jogador);
			
			System.out.println("Voce parou no: \n" + espaco_atual);
			//OPCÃO QUANDO PARA NO ESPAÇO
			if(espaco_atual.compravel()){
				Compravel e_atual = (Compravel) espaco_atual;
				if(e_atual.temDono()){
					System.out.println("Esse lugar tem dono, você vai pagar aluguel");
					int aluguel = e_atual.getAluguel();
					jogador.sacarDinheiro(aluguel);
					e_atual.getDono().depositarDinheiro(aluguel);
				}else{ //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= e_atual.getPreco()){
						System.out.println("Deseja comprar a localidade? 1 - Sim | 2 - Nao");
						int op = EntradaTeclado.leInt();
						if(op == 1){
							Banco.comprarCompravel(e_atual, jogador);
						}							
					}
				}
			}else{
				Acao.realizarAcao(tabuleiro,jogador,(Jogavel)espaco_atual);
			}
			
			
			int cmd = 1;
			while(cmd != 0){
				System.out.println("Opçoes de jogo: 1 - Hipoteca | 2 - Construir casa | 3 - Vender Casa | 4 - Oferta de compra | 0- Sair");
				if(cmd == 1){
					int i = 0;
					compraveis = jogador.getCompraveis();
					map_compraveis = new HashMap<Integer,Compravel>();
					for(Compravel c : compraveis){
						map_compraveis.put(i, c);
						System.out.println(i++ +  " - " + c.getNome() + " " + c.getPreco());
					}
					System.out.println("\nDigite a desejada: ");
					int n = EntradaTeclado.leInt();
					Compravel p_hipoteca = map_compraveis.get(n);
					Banco.hipotecaCompravel(p_hipoteca, jogador);
				}else if(cmd == 2){
					int i = 0;
					compraveis = jogador.getCompraveis();
					map_compraveis = new HashMap<Integer,Compravel>();
					for(Compravel p : jogador.getCompraveis()){
						if(p instanceof Propriedade){
							map_compraveis.put(i, p);
							System.out.println(i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa());
						}
					}
					System.out.println("\nDigite a desejada: ");
					int n = EntradaTeclado.leInt();
					Propriedade p_escolhida = (Propriedade) map_compraveis.get(n);
					if(jogador.getSaldo() >= p_escolhida.getPreco() && jogador.temTodosCor(p_escolhida.getCor())){
						Banco.comprarCasa(p_escolhida, jogador);
						System.out.println("Casa comprada");
					}else{
						System.out.println("Casa não pode ser comprada");
					}
				}if(cmd == 3){
					int i = 0;
					compraveis = jogador.getCompraveis();
					map_compraveis = new HashMap<Integer,Compravel>();
					for(Compravel p : jogador.getCompraveis()){
						if(p instanceof Propriedade){
							map_compraveis.put(i, p);
							System.out.println(i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa());
						}
					}
					System.out.println("\nDigite a desejada: ");
					int n = EntradaTeclado.leInt();
					Propriedade p_escolhida = (Propriedade) map_compraveis.get(n);
					if(p_escolhida.getNumeroCasas() > 0){
						Banco.comprarCasa(p_escolhida, jogador);
						System.out.println("Casa vendida");
					}else{
						System.out.println("Não pode vender casa");
					}
				}if(cmd == 4){
					//depois
				}
				
				
				
			}
			
		}
	
	}
}	
