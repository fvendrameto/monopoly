import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread {
	private Cliente cliente;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	
	private static Object objetoEnvio;
	private static Object objetoRetorno;
	private static String pergunta; 
	private static String resposta;
	private static String mensagem;
	private static String codigo;
	private static int op;
//	private static Tabuleiro tabuleiroStatic;

	public enum OP {
		ENVIAR_STR(0), ENVIAR_E_RECEBER_STR(1), ENVIAR_GUI(2), ENVIAR_OBJ(3), ENVIAR_E_RECEBER_OBJ(4);
		
		private int val;
		OP(int n) {
			this.val = n;
		}
		
		public int codOp() {
			return this.val;
		}
	}
	
	private Server(Cliente c) {
		this.cliente = c;
		
		try {
			this.saida = new ObjectOutputStream(cliente.getSocket().getOutputStream());
			this.entrada = new ObjectInputStream(cliente.getSocket().getInputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void enviarStr(String s) {
		try {
			this.saida.writeInt(OP.ENVIAR_STR.codOp());
			this.saida.flush();
			this.saida.writeUTF(s);
			this.saida.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	private String enviarEReceberStr(String s) {
		try {
			this.saida.writeInt(OP.ENVIAR_E_RECEBER_STR.codOp());
			this.saida.flush();
			this.saida.writeUTF(s);
			this.saida.flush();
			return entrada.readUTF();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private void enviarGUI(String codigo, String s) {
		try{
			this.saida.writeInt(OP.ENVIAR_GUI.codOp());
			this.saida.flush();
			this.saida.writeUTF(codigo);
			this.saida.flush();
			this.saida.writeUTF(s);
			this.saida.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void enviarObj(Object o) {
		try {
			this.saida.writeInt(OP.ENVIAR_OBJ.codOp());
			this.saida.flush();
			this.saida.writeObject(o);
			this.saida.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private Object enviarEReceberObj(Object o) {
		try {
			this.saida.writeInt(OP.ENVIAR_E_RECEBER_OBJ.codOp());
			this.saida.flush();
			this.saida.writeObject(o);
			this.saida.flush();
			return entrada.readObject();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void run() {
		if(op == 0) {
			enviarStr(mensagem);
		}
		
		if(op == 1) {
			resposta = enviarEReceberStr(pergunta);
		}
		
		if(op == 2) {
			enviarGUI(codigo, mensagem);
		}
		
		if(op == 3) {
			enviarObj(objetoEnvio);
		}
		
		if(op == 4) {
			objetoRetorno = enviarEReceberObj(objetoEnvio);
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<Jogador> jogadores = new ArrayList<>();
		ArrayList<Espaco> espacos =  Initializers.initEspacos("espacos.dat");
		ArrayList<Carta> cartas = Initializers.initCartas("cartas.dat");
		ArrayList<Compravel> compraveis;
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
			op = OP.ENVIAR_E_RECEBER_STR.codOp();
			pergunta = "Digite seu nome: ";
			threads.get(i).run();
			jogadores.add(new Jogador(resposta));
			threads.get(i).setName(resposta);
		}
		
		tabuleiro = new Tabuleiro(jogadores, espacos, cartas);
		op = OP.ENVIAR_OBJ.codOp();
		objetoEnvio = tabuleiro;
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		for(int i=0; i<nJogadores; i++) {
			op = OP.ENVIAR_E_RECEBER_STR.codOp();
			pergunta = "Definindo a ordem dos jogadores. Aperte ENTER para rolar os dados.";
			threads.get(i).run();
			dados[i] = Dados.rolar(2, null);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem = jogadores.get(i) + " rolou " + dados[i];
			threads.get(i).run();
		}
		
		//tabuleiro.setOrdem(dados);
		
		while(tabuleiro.jogoContinua()) {
			jogador = tabuleiro.getJogadorAtual();
			indJogadorAtual = tabuleiro.getIndJogador();
			op = OP.ENVIAR_STR.codOp();
			mensagem = "É a vez de " + jogador;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			op = OP.ENVIAR_E_RECEBER_STR.codOp();
			pergunta = "Aperte ENTER para rolar os dados";
			threads.get(indJogadorAtual).run();
			resultadoDados = Dados.rolar(2, null);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem = jogador + " rolou " + resultadoDados;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro()).removeJogador(jogador);
			boolean passouInicio = jogador.andarPosicaoTabuleiro(resultadoDados);
			if(passouInicio) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
			espacoAtual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
			espacoAtual.addJogador(jogador);
			
			mensagem = "Você parou em " + espacoAtual;
			threads.get(indJogadorAtual).run();
			
			op = OP.ENVIAR_GUI.codOp();
			codigo = "00";
			mensagem = "" + jogador + "#" + jogador.getPosicaoTabuleiro() + "#" + jogador.getSaldo();
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			if(espacoAtual.compravel()) {
				Compravel espacoCompravel = (Compravel) espacoAtual;
				if(espacoCompravel.temDono()) {
					op = OP.ENVIAR_STR.codOp();
					mensagem = "Esse lugar tem dono, você vai pagar aluguel";
					threads.get(indJogadorAtual).run();
					
					int aluguel = espacoCompravel.getAluguel();
					jogador.sacarDinheiro(aluguel);
					espacoCompravel.getDono().depositarDinheiro(aluguel);
					
					mensagem = jogador + " pagou aluguel em " + espacoCompravel + " para " + espacoCompravel.getDono();
					for(int i=0; i<nJogadores; i++) threads.get(i).run();
					
					//atualizar saldo jogador que pagou
					op = OP.ENVIAR_GUI.codOp();
					codigo = "01";
					mensagem = jogador.getSaldo() + "";
					threads.get(indJogadorAtual).run();
					
					//atualizar saldo jogador que recebeu
					mensagem = espacoCompravel.getDono().getSaldo() + "";
					threads.get(jogadores.indexOf(espacoCompravel.getDono())).run();
				} else { //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= espacoCompravel.getPreco()) {
						op = OP.ENVIAR_E_RECEBER_STR.codOp();
						pergunta = "Deseja comprar a localidade? 1 - Sim | 2 - Nao";
						threads.get(indJogadorAtual).run();
						
						if(resposta.equals("1")) {
							Banco.comprarCompravel(espacoCompravel, jogador);
							
							op = OP.ENVIAR_OBJ.codOp();
							objetoEnvio = espacoCompravel;
							threads.get(indJogadorAtual).run();
							
							op = OP.ENVIAR_STR.codOp();
							mensagem = jogador + " comprou " + espacoCompravel;
							for(int i=0; i<nJogadores; i++) threads.get(i).run();
							
							op = OP.ENVIAR_GUI.codOp();
							codigo = "02";
							mensagem = jogador + "#" + espacoCompravel;
							for(int i=0; i<nJogadores; i++) threads.get(i).run();							
						}
					}
				}
			} else {
				Acao.realizarAcao(tabuleiro,jogador,(Jogavel)espacoAtual);
			}
			
			int cmd = 1;
			while(cmd != 0) {
				op = OP.ENVIAR_E_RECEBER_STR.codOp();
				pergunta = "Opçoes de jogo: 1 - Hipoteca | 2 - Construir casa | 3 - Vender Casa | 0- Sair";
				threads.get(indJogadorAtual).run();
				cmd = Integer.parseInt(resposta);
				
				// hipotecar uma propriedade
				if(cmd == 1) {
					int i = 0;
					compraveis = jogador.getCompraveis();
					
					op = OP.ENVIAR_STR.codOp();
					mensagem = "Você escolheu hipoteca. Essas são suas propriedades: ";
					threads.get(indJogadorAtual).run();
					
					for(Compravel c : compraveis) {
						pergunta = i++ +  " - " + c.getNome() + "#" + c.getPreco();
						threads.get(indJogadorAtual).run();
					}
					
					op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
					objetoEnvio =  compraveis;
					threads.get(indJogadorAtual).run();
					Compravel propHipoteca = (Compravel) objetoRetorno;

					Banco.hipotecaCompravel(propHipoteca, jogador);

					op = OP.ENVIAR_OBJ.codOp();
					objetoEnvio = propHipoteca;
					threads.get(indJogadorAtual).run();
					
					op = OP.ENVIAR_STR.codOp();
					mensagem = jogador + " hipotecou " + propHipoteca;
					for(int j=0; j<nJogadores; j++) threads.get(j).run();

					op = OP.ENVIAR_GUI.codOp();
					codigo = "03";
					mensagem = jogador + "#" + propHipoteca;
					for(int j=0; j<nJogadores; j++) threads.get(j).run();
				}
				
				// construir casa
				if(cmd == 2){
					int i = 0;
					compraveis = jogador.getCompraveis();
					ArrayList<Propriedade> propriedades = new ArrayList<>();
					
					op = OP.ENVIAR_STR.codOp();
					for(Compravel p : jogador.getCompraveis()) {
						if(p instanceof Propriedade && jogador.temTodosCor(((Propriedade) p).getCor())){
							propriedades.add((Propriedade) p);
							mensagem = (i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa());
							threads.get(indJogadorAtual).run();
						}
					}
					
					op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
					objetoEnvio = propriedades;
					threads.get(indJogadorAtual).run();
					Propriedade propEscolhida = (Propriedade) objetoRetorno;
					
					if(jogador.getSaldo() >= propEscolhida.getPrecoCasa() && jogador.temTodosCor(propEscolhida.getCor())) {
						Banco.comprarCasa(propEscolhida, jogador);
						op = OP.ENVIAR_STR.codOp();
						mensagem = "Casa comprada";
						threads.get(indJogadorAtual).run();
						
						mensagem = jogador + " construiu um casa em " + propEscolhida;
						for(int j=0; j<nJogadores; j++) threads.get(j).run();
						
						op = OP.ENVIAR_GUI.codOp();
						codigo = "04";
						mensagem = jogador + "#" + propEscolhida;
						
					} else {
						op = OP.ENVIAR_STR.codOp();
						mensagem = ("Casa não pode ser comprada");
						threads.get(indJogadorAtual).run();
					}
				}
				
				// vender casa
				if(cmd == 3){
					int i = 0;
					compraveis = jogador.getCompraveis();
					ArrayList<Propriedade> propriedades = new ArrayList<>();
					
					op = OP.ENVIAR_STR.codOp();
					for(Compravel p : jogador.getCompraveis()){
						if(p instanceof Propriedade && ((Propriedade)p).temCasa()) {
							propriedades.add((Propriedade)p);
							mensagem = i++ +  " - " + p.getNome() + " :" + ((Propriedade)p).getPrecoCasa();
							threads.get(indJogadorAtual).run();
						}
					}

					op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
					objetoEnvio = propriedades;
					threads.get(indJogadorAtual).run();
					Propriedade propEscolhida = (Propriedade) objetoRetorno;
					
					if(propEscolhida.getNumeroCasas() > 0){
						Banco.comprarCasa(propEscolhida, jogador);
						
						op = OP.ENVIAR_STR.codOp();
						mensagem = "Casa vendida";
						threads.get(indJogadorAtual).run();
						
						mensagem  = jogador + " vendeu uma casa em " + propEscolhida;
						for(int j=0; j<nJogadores; j++) threads.get(j).run();
						
						op = OP.ENVIAR_GUI.codOp();
						codigo = "05";
						mensagem = jogador + "#" + propEscolhida;
						threads.get(indJogadorAtual).run();
					} else {
						op = OP.ENVIAR_STR.codOp();
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
