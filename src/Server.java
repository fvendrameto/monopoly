import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server extends Thread {
	private Cliente cliente;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	
	private static String pergunta; 
	private static String resposta;
	private static int op;
	private static Tabuleiro tabuleiroStatic;

	
	public Server(Cliente c) {
		this.cliente = c;
		
		try {
			this.saida = new ObjectOutputStream(cliente.getSocket().getOutputStream());
			this.entrada = new ObjectInputStream(cliente.getSocket().getInputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void send(String s) {		
		try {
			this.saida.writeUTF("0");
			this.saida.flush();
			this.saida.writeUTF(s);
			this.saida.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public String sendAndReceive(String s) {
		String response = null;
		
		try {
			this.saida.writeUTF("1");
			this.saida.flush();
			this.saida.writeUTF(s);
			this.saida.flush();
			response = entrada.readUTF();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
	
	public void sendObject(Object o) {
		try {
			this.saida.writeUTF("2");
			this.saida.flush();
			this.saida.writeObject(o);
			this.saida.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void run() {
		if(op == 0) {
			send(pergunta);
		}
		
		if(op == 1) {
			resposta = sendAndReceive(pergunta);
		}
		
		if(op == 2) {
			sendObject((Object) tabuleiroStatic);
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		ArrayList<Espaco> espacos =  Initializers.initEspacos("espacos.dat");
		ArrayList<Carta> cartas = Initializers.initCartas("cartas.dat");
		ArrayList<Compravel> compraveis;
		HashMap<Integer,Compravel> map_compraveis;
		Tabuleiro tabuleiro;
		Jogador jogador;
		Espaco espacoAtual;
		ServerSocket servidor = new ServerSocket(12345);
		Scanner teclado = new Scanner(System.in);
		int indJogadorAtual = -1;
		int nJogadores;
		int jogadoresConectados = 0;
		int dados[];
		int resultadoDados;
		
		System.out.println("Digite o número de jogadores: ");
		nJogadores = teclado.nextInt();
		dados = new int[nJogadores];
		
		while(jogadoresConectados < nJogadores) {
			Cliente cliente = new Cliente(servidor.accept());
			Server s = new Server(cliente);
			threads.add(new Thread(s));
			jogadoresConectados++;
		}
		
		System.out.println("Todos conectados");
		
		for(int i=0; i<nJogadores; i++) {
			op = 1;
			pergunta = "Digite seu nome: ";
			threads.get(i).run();
			jogadores.add(new Jogador(resposta));
		}
		
		tabuleiro = new Tabuleiro(jogadores, espacos, cartas);
		
		for(int i=0; i<nJogadores; i++) {
			op = 1;
			pergunta = "Definindo a ordem dos jogadores. Aperte ENTER para rolar os dados.";
			threads.get(i).run();
			dados[i] = Dados.rolar(2, null);
			
			op = 0;
			pergunta = jogadores.get(i) + " rolou " + dados[i];
			threads.get(i).run();
		}
		
		tabuleiro.setOrdem(dados);
		
		while(tabuleiro.jogoContinua()) {
			jogador = tabuleiro.getJogadorAtual();
			indJogadorAtual = tabuleiro.getIndJogador();
			op = 0;
			pergunta = "É a vez de " + jogador;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			op = 1;
			pergunta = "Aperte ENTER para rolar os dados";
			threads.get(indJogadorAtual).run();
			resultadoDados = Dados.rolar(2, null);
			
			op = 0;
			pergunta = jogador + " rolou " + resultadoDados;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro()).removeJogador(jogador);
			boolean passouInicio = jogador.andarPosicaoTabuleiro(resultadoDados);
			if(passouInicio) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
			espacoAtual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
			espacoAtual.addJogador(jogador);
			
			pergunta = "Você parou em " + espacoAtual;
			threads.get(indJogadorAtual).run();
			
			op = 2;
			tabuleiroStatic = tabuleiro;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			if(espacoAtual.compravel()) {
				Compravel espacoCompravel = (Compravel) espacoAtual;
				if(espacoCompravel.temDono()) {
					op = 0;
					pergunta = "Esse lugar tem dono, você vai pagar aluguel";
					threads.get(indJogadorAtual).run();
					
					int aluguel = espacoCompravel.getAluguel();
					jogador.sacarDinheiro(aluguel);
					espacoCompravel.getDono().depositarDinheiro(aluguel);
					
					pergunta = jogador + " pagou aluguel em " + espacoCompravel + " para " + espacoCompravel.getDono();
					for(int i=0; i<nJogadores; i++) threads.get(i).run();
				} else { //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= espacoCompravel.getPreco()) {
						op = 1;
						pergunta = "Deseja comprar a localidade? 1 - Sim | 2 - Nao";
						threads.get(indJogadorAtual).run();

						if(resposta.equals("1")) {
							System.out.println("aqui");
							Banco.comprarCompravel(espacoCompravel, jogador);
						}
						
						op = 0;
						pergunta = jogador + " comprou " + espacoCompravel;
						for(int i=0; i<nJogadores; i++) threads.get(i).run();
					}
				}
			} else {
				Acao.realizarAcao(tabuleiro,jogador,(Jogavel)espacoAtual);
			}
			
			int cmd = 1;
			while(cmd != 0) {
				op = 1;
				pergunta = "Opçoes de jogo: 1 - Hipoteca | 2 - Construir casa | 3 - Vender Casa | 0- Sair";
				threads.get(indJogadorAtual).run();
				cmd = Integer.parseInt(resposta);
				if(cmd == 1) {
					int i = 0;
					compraveis = jogador.getCompraveis();
					map_compraveis = new HashMap<Integer,Compravel>();
					
					pergunta = "Você escolheu hipoteca. Essas são suas propriedades: ";
					threads.get(indJogadorAtual).run();
					
					op = 0;
					for(Compravel c : compraveis) {
						map_compraveis.put(i, c);
						pergunta = i++ +  " - " + c.getNome() + " " + c.getPreco();
						threads.get(indJogadorAtual).run();
					}
					
					op = 1;
					pergunta =  "Qual deseja hipotecar?";
					threads.get(indJogadorAtual).run();
					int n = Integer.parseInt(resposta);
					Compravel p_hipoteca = map_compraveis.get(n);
					Banco.hipotecaCompravel(p_hipoteca, jogador);
					
					op = 0;
					pergunta = jogador + " hipotecou " + p_hipoteca;
					for(int j=0; j<nJogadores; j++) threads.get(j).run();
				}
				
				if(cmd == 2){
					int i = 0;
					compraveis = jogador.getCompraveis();
					map_compraveis = new HashMap<Integer,Compravel>();
					
					op = 0;
					for(Compravel p : jogador.getCompraveis()) {
						if(p instanceof Propriedade){
							map_compraveis.put(i, p);
							pergunta = (i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa());
							threads.get(indJogadorAtual).run();
						}
					}
					
					op = 1;
					pergunta = "Aonde deseja construir uma casa?";
					threads.get(indJogadorAtual).run();
					int n = Integer.parseInt(resposta);
					Propriedade p_escolhida = (Propriedade) map_compraveis.get(n);
					
					if(jogador.getSaldo() >= p_escolhida.getPreco() && jogador.temTodosCor(p_escolhida.getCor())) {
						Banco.comprarCasa(p_escolhida, jogador);
						op = 0;
						pergunta = "Casa comprada";
						threads.get(indJogadorAtual).run();
						
						pergunta = jogador + " construiu um casa em " + p_escolhida;
						for(int j=0; j<nJogadores; j++) threads.get(j).run();
					} else {
						System.out.println("Casa não pode ser comprada");
					}
				}
				
				if(cmd == 3){
					int i = 0;
					compraveis = jogador.getCompraveis();
					map_compraveis = new HashMap<Integer,Compravel>();
					
					op = 0;
					for(Compravel p : jogador.getCompraveis()){
						if(p instanceof Propriedade){
							map_compraveis.put(i, p);
							pergunta = i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa();
							threads.get(indJogadorAtual).run();
						}
					}
					
					op = 1;
					pergunta = "De qual propriedade você deseja remover uma casa?";
					threads.get(indJogadorAtual).run();
					int n = Integer.parseInt(resposta);
					Propriedade p_escolhida = (Propriedade) map_compraveis.get(n);
					
					op = 0;
					if(p_escolhida.getNumeroCasas() > 0){
						Banco.comprarCasa(p_escolhida, jogador);
						
						pergunta = "Casa vendida";
						threads.get(indJogadorAtual).run();
						
						pergunta = jogador + " vendeu uma casa em " + p_escolhida;
						for(int j=0; j<nJogadores; j++) threads.get(j).run();
					}else{
						pergunta = "Não pode vender casa";
						threads.get(indJogadorAtual).run();
					}
				}
			}
		}
		
		servidor.close();
		teclado.close();
		for(int i=0; i<nJogadores; i++)
			threads.get(i).join();
	}

}
