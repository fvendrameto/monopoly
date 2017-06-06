import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server extends Thread {
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Espaco> espacos = new ArrayList<Espaco>();
	private ArrayList<Carta> cartas = new ArrayList<Carta>();
	private ArrayList<Compravel> compraveis;
	private HashMap<Integer,Compravel> map_compraveis;
	private Socket cliente;
	private Jogador jogador;
	private Tabuleiro tabuleiro;
	private Espaco espaco_atual;
	private int resultado_dados;
	private int[] dados = null;
	private int id;
	
	private static int numero_jogadores;

	
	public Server() throws FileNotFoundException {
		this.espacos = Initializers.initEspacos("espacos.dat");
		this.cartas = Initializers.initCartas("cartas.dat");
	}
	
	public Server(Socket c) {
		this.cliente = c;
	}
	
	public static void setNroJogadores(int n) {
		numero_jogadores = n;
	}
	
	public void run() {
		PrintStream saida = null;
		Scanner entrada = null;
		
		System.out.println("Nova conexão com: " + cliente.getInetAddress().getHostAddress());
		
		try {
			saida = new PrintStream(this.cliente.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			entrada = new Scanner(this.cliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		saida.println("Digite seu nome: ");
		String nome = entrada.nextLine();
		
		this.jogadores.add(new Jogador(nome));
		this.tabuleiro = new Tabuleiro(this.jogadores, this.espacos, this.cartas);
		
		while(tabuleiro.jogoContinua()) {
			jogador = tabuleiro.getJogadorAtual();
			saida.println("É a vez de: " + jogador);
			if(this.cliente.getNome() == jogador.getNome()) {
				saida.println("Role os dados");
				entrada.nextLine();
			}
			
			try {
				resultado_dados = Dados.rolar(2,dados);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			saida.println(jogador + "rolou " + resultado_dados);
			
			boolean ret = jogador.andarPosicaoTabuleiro(resultado_dados);
			if(ret) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
			espaco_atual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
			espaco_atual.addJogador(jogador);
			saida.println(jogador + " parou em " + espaco_atual);
			
			if(espaco_atual.compravel()) {
				Compravel e_atual = (Compravel) espaco_atual;
				if(e_atual.temDono()){
					if(this.cliente.getNome() == jogador.getNome()) {
						saida.println("Esse lugar tem dono, você vai pagar aluguel");
						int aluguel = e_atual.getAluguel();
						jogador.sacarDinheiro(aluguel);
						e_atual.getDono().depositarDinheiro(aluguel);
					}
					saida.println(jogador + " pagou aluguel por " + e_atual + " para " + e_atual.getDono());
				} else { //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= e_atual.getPreco()) {
						if(this.cliente.getNome() == jogador.getNome()) {
							saida.println("Deseja comprar a localidade? 1 - Sim | 2 - Nao");
							int op = Integer.parseInt(entrada.nextLine());
						}
						if(op == 1) {
							Banco.comprarCompravel(e_atual, jogador);
							saida.println(jogador + " comprou " + e_atual);
						}							
					}
				}
			} else {
				Acao.realizarAcao(tabuleiro,jogador,(Jogavel)espaco_atual);
			}
			
			
			int cmd = -1;
			while(cmd != 0) {
				if(this.cliente.getNome() == jogador.getNome()) {
					saida.println("Opçoes de jogo: 1 - Hipoteca | 2 - Construir casa | 3 - Vender Casa | 4 - Oferta de compra | 0- Sair");
					cmd = Integer.parseInt(entrada.nextLine());
				}
				if(cmd == 1) {
					Compravel p_hipoteca;
					if(this.cliente.getNome() == jogador.getNome()) {
						int i = 0;
						compraveis = jogador.getCompraveis();
						map_compraveis = new HashMap<Integer,Compravel>();
						for(Compravel c : compraveis) {
							map_compraveis.put(i, c);
							saida.println(i++ +  " - " + c.getNome() + " " + c.getPreco());
						}
						saida.println("\nDigite a propriedade desejada: ");
						int n = Integer.parseInt(entrada.nextLine());
						p_hipoteca = map_compraveis.get(n);
						Banco.hipotecaCompravel(p_hipoteca, jogador);
					}
					saida.println(jogador + " hipotecou a propriedade " + p_hipoteca);
				}else if(cmd == 2) {
					boolean construiu = false;
					Propriedade p_escolhida = null;
					if(this.cliente.getNome() == jogador.getNome()) {
						int i = 0;
						compraveis = jogador.getCompraveis();
						map_compraveis = new HashMap<Integer,Compravel>();
						for(Compravel p : jogador.getCompraveis()){
							if(p instanceof Propriedade){
								map_compraveis.put(i, p);
								saida.println(i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa());
							}
						}
						saida.println("\nDigite a desejada: ");
						int n = Integer.parseInt(entrada.nextLine());
						p_escolhida = (Propriedade) map_compraveis.get(n);
						if(jogador.getSaldo() >= p_escolhida.getPreco() && jogador.temTodosCor(p_escolhida.getCor())){
							Banco.comprarCasa(p_escolhida, jogador);
							saida.println("Casa comprada");
							construiu = true;
						}else{
							saida.println("Casa não pode ser comprada");
						}
					}
					if(construiu)
						saida.println(jogador + " construiu uma casa em " + p_escolhida);
				}if(cmd == 3){
					boolean vendeu = false;
					Propriedade p_escolhida;
					if(this.cliente.getNome() == jogador.getNome()) {
						int i = 0;
						compraveis = jogador.getCompraveis();
						map_compraveis = new HashMap<Integer,Compravel>();
						for(Compravel p : jogador.getCompraveis()){
							if(p instanceof Propriedade){
								map_compraveis.put(i, p);
								saida.println(i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa());
							}
						}
						saida.println("\nDigite a propriedade desejada: ");
						int n = Integer.parseInt(entrada.nextLine());
						p_escolhida = (Propriedade) map_compraveis.get(n);
						if(p_escolhida.getNumeroCasas() > 0){
							Banco.comprarCasa(p_escolhida, jogador);
							saida.println("Casa vendida");
							vendeu = true;
						}else{
							saida.println("Não pode vender casa");
						}
					}
					if(vendeu) {
						saida.println(jogador + " vendeu uma casa em " + p_escolhida);
					}
				}if(cmd == 4){
					//depois
				}	
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket servidor = new ServerSocket(12345);
	
		System.out.println("Digite o numero de jogadores: ");
		int n = EntradaTeclado.leInt();
		setNroJogadores(n);
		
		while(true) {
			Socket c = servidor.accept();
			Thread t = new Thread(new Server(c));
			t.start();
		}
	}

}
