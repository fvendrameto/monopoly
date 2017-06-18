package monopoly_principal;

import monopoly_gui.*;
import monopoly_elements.*;
import jdk.nashorn.internal.scripts.JO;
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

	private enum OP {
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
			this.saida.reset();
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
			this.saida.reset();
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
			this.saida.reset();
			this.saida.writeInt(OP.ENVIAR_OBJ.codOp());
			this.saida.flush();
			this.saida.writeObject(o);
			this.saida.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private Object enviarEReceberObj(String cod, Object o) {
		try {
			this.saida.reset();
			this.saida.writeInt(OP.ENVIAR_E_RECEBER_OBJ.codOp());
			this.saida.flush();
			this.saida.writeUTF(cod);
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
			objetoRetorno = enviarEReceberObj(codigo, objetoEnvio);
		}
	}
	
	private static void pagarAluguel(Compravel espacoCompravel, Jogador jogador, ArrayList<Jogador> jogadores, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual) {
		int aluguel = espacoCompravel.getAluguel();
		jogador.sacarDinheiro(aluguel);
		espacoCompravel.getDono().depositarDinheiro(aluguel);
		
		op = OP.ENVIAR_STR.codOp();
		mensagem = jogador.getNome() + " pagou aluguel em " + espacoCompravel.getNome() + " para " + espacoCompravel.getDono();
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//atualizar saldo jogador que pagou
		op = OP.ENVIAR_GUI.codOp();
		codigo = "01";
		mensagem = espacoCompravel.getAluguel() + "#" + espacoCompravel.getDono() + "#" + espacoCompravel.getNome();
		threads.get(indJogadorAtual).run();
		
		//atualizar saldo jogador que recebeu
		codigo = "02";
		mensagem = espacoCompravel.getAluguel() + "#" + jogador.getNome() + "#" + espacoCompravel.getNome();
		threads.get(jogadores.indexOf(espacoCompravel.getDono())).run();
		
	}
	
	private static void comprarPropriedade(Compravel espacoCompravel, Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual) {
		op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
		codigo = "00";
		objetoEnvio = espacoCompravel;
		threads.get(indJogadorAtual).run();
		int ret = (int) objetoRetorno;
		
		if(ret == 0) { //vai comprar
			Banco.comprarCompravel(espacoCompravel, jogador);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem = jogador.getNome() + " comprou " + espacoCompravel.getNome();
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			op = OP.ENVIAR_GUI.codOp();
			codigo = "02";
			mensagem = jogador.getNome() + "#" + espacoCompravel.getNome();
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
		}
	}
	
	private static void moverJogador(Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual, int posicao) {
		//avisa no log
		op = OP.ENVIAR_STR.codOp();
		mensagem = jogador.getNome() + " teve posição alterada...";
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//muda a posição nos tabuleiros
		op = OP.ENVIAR_GUI.codOp();
		codigo = "00";
		mensagem = indJogadorAtual + "#" + posicao + "#" + jogador.getSaldo();
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
	}
	
	private static void pagarOuReceber(Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual, int acao, int quantia) {
		//avisa no log
		op = OP.ENVIAR_STR.codOp();
		if(acao == 1) mensagem = jogador.getNome() + " pagou ao banco";
		else mensagem = jogador.getNome() + " recebeu do banco";
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//atualiza gui do jogador
		op = OP.ENVIAR_GUI.codOp();
		codigo = "05";
		if(acao == 1) mensagem = jogador.getSaldo() + "Você pagou " + quantia ;
		else mensagem = jogador.getSaldo() + "#" + "Você recebeu " + quantia ;
		threads.get(indJogadorAtual).run();
	}
	
	private static void moverEPagar(Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual, int posicao, int quantia, String nome) {
		//avisa no log
		op = OP.ENVIAR_STR.codOp();
		mensagem = jogador.getNome() + " pagou ao banco e mudou de posição";
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//muda a posição nos tabuleiros
		op = OP.ENVIAR_GUI.codOp();
		codigo = "00";
		mensagem = indJogadorAtual + "#" + posicao + "#" + jogador.getSaldo();
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//atualiza saldo gui do jogador e avisa
		op = OP.ENVIAR_GUI.codOp();
		codigo = "05";
		mensagem = jogador.getSaldo() + "#" + "Você pagou " + quantia + "e foi para " + nome;
		threads.get(indJogadorAtual).run();
		
	}
	
	private static void pagarJogadores(Jogador jogador, ArrayList<Jogador> jogadores, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual, int quantia) {
		//avisa no log
		op = OP.ENVIAR_STR.codOp();
		mensagem = jogador.getNome() + " pagou " + quantia + " para todos os jogadores";
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//atualiza saldo gui do jogador e avisaJogador jogador, ArrayList<Jogador> jogadores, ArrayList<Thread> threads, int nJogadores
		op = OP.ENVIAR_GUI.codOp();
		codigo = "05";
		for(int i=0;i<nJogadores;i++){
			mensagem = jogadores.get(i).getSaldo() + "#";
			if(i != indJogadorAtual) mensagem +="Você recebeu " + quantia + "de" + jogador.getNome();
			else mensagem += "Você pagou " + quantia + " para todos os jogadores";
			threads.get(i).run();
		}
	}
	
	private static void receberJogadores(Jogador jogador, ArrayList<Jogador> jogadores, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual, int quantia) {
		//avisa no log
		op = OP.ENVIAR_STR.codOp();
		mensagem = jogador.getNome() + " recebeu " + quantia + " de todos os jogadores";
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		//atualiza saldo gui do jogador e avisa
		op = OP.ENVIAR_GUI.codOp();
		codigo = "05";
		for(int i=0;i<nJogadores;i++){
			mensagem = jogadores.get(i).getSaldo() + "#";
			if(i != indJogadorAtual) mensagem +="Você pagou " + quantia + " para " + jogador.getNome();
			else mensagem += "Você recebeu " + quantia + " de todos os jogadores";
			threads.get(i).run();
		}
	}
	
	private static void hipotecar(Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual) {
		ArrayList<Compravel> compraveis = jogador.getCompraveis();
		
		if(compraveis.size() == 0) return;
		
		op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
		objetoEnvio = compraveis;
		codigo = "02";
		threads.get(indJogadorAtual).run();
		Compravel propHipoteca = (Compravel) objetoRetorno;
		
		if(propHipoteca != null){ //caso
			Banco.hipotecaCompravel(propHipoteca,jogador);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem = jogador + " hipotecou " + propHipoteca;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			op = OP.ENVIAR_GUI.codOp();
			codigo = "03";
			mensagem = jogador + "#" + propHipoteca;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
		}
	}
	
	private static void construirCasa(Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual) {
		System.out.println("Opção de comprar casa escolhida...");
		ArrayList<Compravel> propriedades = new ArrayList<>();
		
		op = OP.ENVIAR_STR.codOp();
		for(Compravel p : jogador.getCompraveis()) {
			if (p.propriedade() && jogador.temTodosCor(((Propriedade) p).getCor()) && jogador.getSaldo() >= ((Propriedade) p).getPrecoCasa() && ((Propriedade) p).getNumeroCasas() < 5)
				propriedades.add(p);
		}
		
		if(propriedades.size() == 0) return;
		
		op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
		objetoEnvio = propriedades;
		threads.get(indJogadorAtual).run();
		Propriedade propEscolhida = (Propriedade) objetoRetorno;
		
		if(propEscolhida != null){
			Banco.comprarCasa(propEscolhida, jogador);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem = jogador + " construiu um casa em " + propEscolhida;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			op = OP.ENVIAR_GUI.codOp();
			codigo = "04";
			mensagem = jogador + "#" + propEscolhida + "#" +  propEscolhida.getNumeroCasas();
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
		}
		
	}
	
	private static void venderCasa(Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual) {
		ArrayList<Compravel> propriedades = new ArrayList<>();
		
		op = OP.ENVIAR_STR.codOp();
		for(Compravel p : jogador.getCompraveis()){
			if(p instanceof Propriedade && ((Propriedade)p).temCasa()) {
				propriedades.add(p);
			}
		}
		
		if(propriedades.size() == 0) return;
		
		op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
		objetoEnvio = propriedades;
		threads.get(indJogadorAtual).run();
		Propriedade propEscolhida = (Propriedade) objetoRetorno;
		
		if(objetoRetorno != null){
			Banco.venderCasa(propEscolhida, jogador);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem  = jogador + " vendeu uma casa em " + propEscolhida;
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
			
			op = OP.ENVIAR_GUI.codOp();
			codigo = "04";
			mensagem = jogador + "#" + propEscolhida + "#" +  propEscolhida.getNumeroCasas();
			for(int i=0; i<nJogadores; i++) threads.get(i).run();
		}
	}
	
	private static Espaco comecarRodada(Tabuleiro tabuleiro, Jogador jogador, ArrayList<Thread> threads, int nJogadores, int indJogadorAtual) {
		op = OP.ENVIAR_STR.codOp();
		mensagem = "É a vez de " + jogador;
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		op = OP.ENVIAR_E_RECEBER_STR.codOp();
		pergunta = "Aperte ENTER para rolar os dados";
		threads.get(indJogadorAtual).run();
		int resultadoDados = Dados.rolar(2, null);
		
		op = OP.ENVIAR_STR.codOp();
		mensagem = jogador + " rolou " + resultadoDados;
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		System.out.println(jogador.getPosicaoTabuleiro() + "");
		tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro()).removeJogador(jogador);
		boolean passouInicio = jogador.andarPosicaoTabuleiro(resultadoDados);
		if(passouInicio) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
		Espaco espacoAtual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
		espacoAtual.addJogador(jogador);


		op = OP.ENVIAR_GUI.codOp();
		codigo = "00";
		mensagem = indJogadorAtual + "#" + jogador.getPosicaoTabuleiro() + "#" + jogador.getSaldo();
		threads.get(indJogadorAtual).run();
		mensagem = indJogadorAtual + "#" + jogador.getPosicaoTabuleiro() + "#" + "-1";
		for(int i=0; i<nJogadores; i++) threads.get(i).run();
		
		return espacoAtual;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<Jogador> jogadores = new ArrayList<>();
		ArrayList<Espaco> espacos =  Initializers.initEspacos("espacos.dat");
		ArrayList<Carta> cartas = Initializers.initCartas("cartas.dat");
		
		Tabuleiro tabuleiro;
		Jogador jogador;
		Espaco espacoAtual;
		ServerSocket servidor = new ServerSocket(12345);
		Scanner teclado = new Scanner(System.in);
		int indJogadorAtual;
		int nJogadores;
		int jogadoresConectados = 0;
		
		System.out.println("Digite o número de jogadores: ");
		nJogadores = teclado.nextInt();
		
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
		

		while(tabuleiro.jogoContinua()) {
			jogador = tabuleiro.getJogadorAtual();
			indJogadorAtual = tabuleiro.getIndJogador();
			espacoAtual = comecarRodada(tabuleiro, jogador, threads, nJogadores, indJogadorAtual);

			if(espacoAtual.compravel()) {
				Compravel espacoCompravel = (Compravel) espacoAtual;
				if(espacoCompravel.temDono() && espacoCompravel.getDono() != jogador) { //SE TEM DONO PAGA O ALUGUEL
					pagarAluguel(espacoCompravel, jogador, jogadores, threads, nJogadores, indJogadorAtual);
				} else if(!espacoCompravel.temDono()){ //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= espacoCompravel.getPreco()) {
						comprarPropriedade(espacoCompravel, jogador, threads, nJogadores, indJogadorAtual);
					}
				}
			} else {
				Jogavel espacoJogavel = (Jogavel) espacoAtual;
				espacoJogavel.getAcao().realizarAcao(tabuleiro,jogador);
				int acao = espacoJogavel.getAcao().getCodigo();
				int posicao = espacoJogavel.getAcao().getPosicao();
				int quantia = espacoJogavel.getAcao().getQuantia();
				switch(acao){
					case 0: //mudar posiçao
						moverJogador(jogador, threads, nJogadores, indJogadorAtual, posicao);
						break;
					case 1: //pagar ao banco
					case 2: //receber do banco
						pagarOuReceber(jogador, threads, nJogadores, indJogadorAtual, quantia, acao);
						break;
					case 3: //mudar posição e pagar
						moverEPagar(jogador, threads, nJogadores, indJogadorAtual, posicao, quantia,tabuleiro.getEspacoPosicao(posicao).getNome());
						break;
					case 4: //pagar aos jogadores
						pagarJogadores(jogador, jogadores, threads, nJogadores, indJogadorAtual, quantia);
						break;
					case 5: //receber dos jogadores
						receberJogadores(jogador, jogadores, threads, nJogadores, indJogadorAtual, quantia);
						break;
				}
			}
			
			int cmd = 1;
			while(cmd != 3) {
				op = OP.ENVIAR_E_RECEBER_OBJ.codOp();
				objetoEnvio = "";
				codigo = "01";
				threads.get(indJogadorAtual).run();
				cmd = (int) objetoRetorno;
				
				
				// hipotecar uma propriedade
				if(cmd == 0) {
					hipotecar(jogador, threads, nJogadores, indJogadorAtual);
				}
				
				// construir casa
				if(cmd == 1){
					construirCasa(jogador, threads, nJogadores, indJogadorAtual);
				}

				// vender casa
				if(cmd == 2){
					venderCasa(jogador, threads, nJogadores, indJogadorAtual);
				}
			}
		}

		servidor.close();
		teclado.close();
		for(int i=0; i<nJogadores; i++)
				threads.get(i).join();
	}

}
