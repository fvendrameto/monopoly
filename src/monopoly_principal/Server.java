package monopoly_principal;

import monopoly_elements.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe do servidor, gerencia os clientes e roda o jogo
 */
public class Server {
	private Cliente cliente;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	
	/**
	 * Enum para definição das operações de comunicação servidor-cliente, torna o código mais legível
	 */
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
	
	/**
	 * Construtor do Servidor, recebe e atribui o cliente conectado e também atribui
	 * as Streams de entrada e saída do servidor com esse cliente.
	 * @param cliente Objeto cliente, que contém o socket de conexão com o servidor
	 */
	private Server(Cliente cliente) {
		this.cliente = cliente;
		
		try {
			this.saida = new ObjectOutputStream(cliente.getSocket().getOutputStream());
			this.entrada = new ObjectInputStream(cliente.getSocket().getInputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método para envio de uma string para o cliente
	 * @param string String a ser enviada
	 */
	private void enviarStr(String string) {
		try {
			this.saida.reset();
			this.saida.writeInt(OP.ENVIAR_STR.codOp());
			this.saida.flush();
			this.saida.writeUTF(string);
			this.saida.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	/**
	 * Método para enviar uma string para o cliente e receber outra string como resposta.
	 * @param string String a ser enviada
	 * @return Uma string, enviada pelo cliente como resposta, ou null caso ocorra uma execeção
	 */
	private String enviarEReceberStr(String string) {
		try {
			this.saida.writeInt(OP.ENVIAR_E_RECEBER_STR.codOp());
			this.saida.flush();
			this.saida.writeUTF(string);
			this.saida.flush();
			return entrada.readUTF();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Método para envio de mensagens para atualização da interface gráfica
 	 * @param codigo String que diz ao cliente o que será alterado
	 * @param mensagem Valor atualizado dos parâmetros que serão alterados no cliente
	 */
	private void enviarGUI(String codigo, String mensagem) {
		try{
			this.saida.reset();
			this.saida.writeInt(OP.ENVIAR_GUI.codOp());
			this.saida.flush();
			this.saida.writeUTF(codigo);
			this.saida.flush();
			this.saida.writeUTF(mensagem);
			this.saida.flush();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método para envio de objetos para o cliente
	 * @param o Objeto genérico
	 */
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
	
	/**
	 * Método para enviar um objeto para o cliente e receber outro como resposta
	 * @param codigo String que diz ao cliente o que ele está recebendo
	 * @param o Objeto que será enviado ao cliente
	 * @return Um objeto, que foi enviado pelo cliente como resposta
	 */
	private Object enviarEReceberObj(String codigo, Object o) {
		try {
			this.saida.reset();
			this.saida.writeInt(OP.ENVIAR_E_RECEBER_OBJ.codOp());
			this.saida.flush();
			this.saida.writeUTF(codigo);
			this.saida.flush();
			this.saida.writeObject(o);
			this.saida.flush();
			return entrada.readObject();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Método estático, usado na main, para pagar aluguel por parar na propriedade de outro jogador
	 * @param espacoCompravel Espaço em que o jogador atual parou
	 * @param jogador Jogador atual
	 * @param jogadores Lista com todos os jogadores que estão no jogo
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param dados Valor dos dados, usado espacoCompravel seja uma companhia
	 */
	private static void pagarAluguel(Compravel espacoCompravel, Jogador jogador, ArrayList<Jogador> jogadores, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int dados) {
		int aluguel = espacoCompravel.getAluguel();
		if(espacoCompravel instanceof Companhia) aluguel *= dados;
		jogador.sacarDinheiro(aluguel);
		espacoCompravel.getDono().depositarDinheiro(aluguel);
		
		String mensagem = jogador.getNome() + " pagou aluguel em " + espacoCompravel.getNome() + " para " + espacoCompravel.getDono();
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		//atualizar saldo jogador que pagou
		mensagem = espacoCompravel.getAluguel() + "#" + espacoCompravel.getDono() + "#" + espacoCompravel.getNome();
		clientes.get(indJogadorAtual).enviarGUI("01", mensagem);
		
		//atualizar saldo jogador que recebeu
		mensagem = espacoCompravel.getAluguel() + "#" + jogador.getNome() + "#" + espacoCompravel.getNome();
		clientes.get(jogadores.indexOf(espacoCompravel.getDono())).enviarGUI("02", mensagem);
	}
	
	/**
	 * Método estático, usado na main, para o jogador atual comprar o espaço em que ele parou
	 * @param espacoCompravel Espaco em que o jogador parou
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 */
	private static void comprarPropriedade(Compravel espacoCompravel, Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual) {
		int ret  = (int) clientes.get(indJogadorAtual).enviarEReceberObj("00", espacoCompravel);
		
		if(ret == 0) { //vai comprar
			Banco.comprarCompravel(espacoCompravel, jogador);
			
			String mensagem = jogador.getNome() + " comprou " + espacoCompravel.getNome();
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
			
			mensagem = jogador.getNome() + "#" + espacoCompravel.getNome();
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("02", mensagem);
		}
	}
	
	/**
	 * Método estático para mover o jogador no tabuleiro, caso ele tenha parado em um espaço que
	 * executa essa ação
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param posicao Posição do tabuleiro para a qual o jogador deve ir
	 */
	private static void moverJogador(Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int posicao) {
		//avisa no log
		String mensagem = jogador.getNome() + " teve posição alterada...";
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		//muda a posição nos tabuleiros
		mensagem = indJogadorAtual + "#" + posicao + "#" + jogador.getSaldo();
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("00", mensagem);
	}
	
	/**
	 * Método estático que faz o jogador atual pagar ou receber um valor, se cair em um espaço
	 * que executa alguma dessas ações
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param acao Código númerico da ação a ser executada
	 * @param quantia Valor a ser recebido ou pago
	 */
	private static void pagarOuReceber(Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int acao, int quantia) {
		//avisa no log
		String mensagem;
		if(acao == 1) mensagem = jogador.getNome() + " pagou ao banco";
		else mensagem = jogador.getNome() + " recebeu do banco";
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		//atualiza gui do jogador
		if(acao == 1) mensagem = jogador.getSaldo() + "Você pagou " + quantia ;
		else mensagem = jogador.getSaldo() + "#" + "Você recebeu " + quantia ;
		clientes.get(indJogadorAtual).enviarGUI("05", mensagem);
	}
	
	/**
	 * Método estático usado para mover o jogador atual e fazer ele receber um valor,  @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param posicao Posição do tabuleiro para a qual o jogador deve ir
	 * @param quantia Valor a ser recebido ou pago
	 * @param nome Nome do espaço ao qual o jogador foi movido
	 */
	private static void moverEPagar(Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int posicao, int quantia, String nome) {
		//avisa no log
		String mensagem = jogador.getNome() + " pagou ao banco e mudou de posição";
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		//muda a posição nos tabuleiros
		mensagem = indJogadorAtual + "#" + posicao + "#" + jogador.getSaldo();
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("00", mensagem);
		
		//atualiza saldo gui do jogador e avisa
		mensagem = jogador.getSaldo() + "#" + "Você pagou " + quantia + "e foi para " + nome;
		clientes.get(indJogadorAtual).enviarGUI("05", mensagem);
		
	}
	
	/**
	 * Método estático que faz o jogador atual pagar um valor a todos os outros jogadores, caso ele
	 * caia em espaço que executa essa ação
	 * @param jogador Jogador atual
	 * @param jogadores Lista com todos os jogadores que estão no jogo
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param quantia Valor a ser pago para os outros jogadores
	 */
	private static void pagarJogadores(Jogador jogador, ArrayList<Jogador> jogadores, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int quantia) {
		//avisa no log
		String mensagem = jogador.getNome() + " pagou " + quantia + " para todos os jogadores";
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		//atualiza saldo gui do jogador e avisaJogador jogador, ArrayList<Jogador> jogadores, ArrayList<Server> clientes, int nJogadores
		for(int i=0;i<nJogadores;i++){
			mensagem = jogadores.get(i).getSaldo() + "#";
			if(i != indJogadorAtual) mensagem +="Você recebeu " + quantia + "de" + jogador.getNome();
			else mensagem += "Você pagou " + quantia + " para todos os jogadores";
			clientes.get(i).enviarGUI("05", mensagem);
		}
	}
	
	/**
	 * Método estático que faz o jogador atual receber um valor de todos os outros jogadores, caso ele
	 * caia em espaço que executa essa ação
	 * @param jogador Jogador atual
	 * @param jogadores Lista com todos os jogadores que estão no jogo
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param quantia Valor a ser pago recebido de cada um dos outros jogadores
	 */
	private static void receberJogadores(Jogador jogador, ArrayList<Jogador> jogadores, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int quantia) {
		//avisa no log
		String mensagem = jogador.getNome() + " recebeu " + quantia + " de todos os jogadores";
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		//atualiza saldo gui do jogador e avisa
		for(int i=0;i<nJogadores;i++){
			mensagem = jogadores.get(i).getSaldo() + "#";
			if(i != indJogadorAtual) mensagem +="Você pagou " + quantia + " para " + jogador.getNome();
			else mensagem += "Você recebeu " + quantia + " de todos os jogadores";
			clientes.get(i).enviarGUI("05", mensagem);
		}
	}
	
	/**
	 * Método estático para o jogador atual hipotecar uma de suas propriedades
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 */
	private static void hipotecar(Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual) {
		ArrayList<Compravel> compraveis = jogador.getCompraveis();
		
		if(compraveis.size() == 0) return;
		
		Compravel propHipoteca = (Compravel) clientes.get(indJogadorAtual).enviarEReceberObj("02", compraveis);
		
		if(propHipoteca != null){ //caso
			Banco.hipotecaCompravel(propHipoteca,jogador);
			
			String mensagem = jogador + " hipotecou " + propHipoteca;
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
			
			mensagem = jogador + "#" + propHipoteca;
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("03", mensagem);
		}
	}
	
	/**
	 * Método estático para o jogador atual construir uma casa em uma de suas propriedades
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 */
	private static void construirCasa(Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual) {
		ArrayList<Compravel> propriedades = new ArrayList<>();
		
		for(Compravel p : jogador.getCompraveis()) {
			if (p.propriedade() && jogador.temTodosCor(((Propriedade) p).getCor()) && jogador.getSaldo() >= ((Propriedade) p).getPrecoCasa() && ((Propriedade) p).getNumeroCasas() < 5)
				propriedades.add(p);
		}
		
		if(propriedades.size() == 0) return;
		
		Propriedade propEscolhida = (Propriedade) clientes.get(indJogadorAtual).enviarEReceberObj("03", propriedades);
		
		if(propEscolhida != null){
			Banco.comprarCasa(propEscolhida, jogador);
			
			String mensagem = jogador + " construiu um casa em " + propEscolhida;
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
			
			mensagem = jogador + "#" + propEscolhida + "#" +  propEscolhida.getNumeroCasas();
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("04", mensagem);
		}
		
	}
	
	/**
	 * Método estático para o jogador atual vender uma casa de uma de suas propriedades
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 */
	private static void venderCasa(Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual) {
		ArrayList<Compravel> propriedades = new ArrayList<>();
		
		for(Compravel p : jogador.getCompraveis()){
			if(p instanceof Propriedade && ((Propriedade)p).temCasa()) {
				propriedades.add(p);
			}
		}
		
		if(propriedades.size() == 0) return;
		
		Propriedade propEscolhida = (Propriedade) clientes.get(indJogadorAtual).enviarEReceberObj("04", propriedades);
		
		if(propEscolhida != null){
			Banco.venderCasa(propEscolhida, jogador);
			
			String mensagem  = jogador + " vendeu uma casa em " + propEscolhida;
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
			
			mensagem = jogador + "#" + propEscolhida + "#" +  propEscolhida.getNumeroCasas();
			for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("04", mensagem);
		}
	}
	
	/**
	 * Método estático que começa uma rodada, fazendo a rolagem dos dados e movendo o jogador no
	 * tabuleiro de acordo com a rolagem, retornando para a main o espaço em que ele parou
	 * @param tabuleiro Tabuleiro de jogo
	 * @param jogador Jogador atual
	 * @param clientes Lista com os clientes conectados ao servidor
	 * @param nJogadores Número de jogadores que estão no jogo
	 * @param indJogadorAtual Índice do jogador atual na lista de jogadores
	 * @param resultadoDados Valor que foi rolado dos dados, para que possa ser usado posteriormente na main
	 * @return O espaço em que o jogador atual parou
	 */
	private static Espaco comecarRodada(Tabuleiro tabuleiro, Jogador jogador, ArrayList<Server> clientes, int nJogadores, int indJogadorAtual, int resultadoDados) {
		String mensagem = "É a vez de " + jogador;
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		mensagem = "Aperte ENTER para rolar os dados";
		clientes.get(indJogadorAtual).enviarEReceberStr(mensagem);
		resultadoDados = Dados.rolar(2, null);
		
		mensagem = jogador + " rolou " + resultadoDados;
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarStr(mensagem);
		
		tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro()).removeJogador(jogador);
		boolean passouInicio = jogador.andarPosicaoTabuleiro(resultadoDados);
		if(passouInicio) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
		Espaco espacoAtual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
		espacoAtual.addJogador(jogador);
		
		mensagem = indJogadorAtual + "#" + jogador.getPosicaoTabuleiro() + "#" + jogador.getSaldo();
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarGUI("00", mensagem);
		
		return espacoAtual;
	}
	
	/**
	 * Método principal, que inicializa o tabuleiro, abre as conexões dos clientes e roda o jogo
	 * @param args
	 * @throws IOException Caso a abertura de algum arquivo lance uma exceceção
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<Server> clientes = new ArrayList<>();
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
		int resultadoDados = 0;
		
		System.out.println("Digite o número de jogadores: ");
		nJogadores = teclado.nextInt();
		
		while(jogadoresConectados < nJogadores) {
			Cliente cliente = new Cliente(servidor.accept());
			Server s = new Server(cliente);
			clientes.add(s);
			jogadoresConectados++;
		}
		
		System.out.println("Todos conectados");
		
		for(int i=0; i<nJogadores; i++) {
			String nome = clientes.get(i).enviarEReceberStr("Digite seu nome: ");
			jogadores.add(new Jogador(nome));
		}
		
		tabuleiro = new Tabuleiro(jogadores, espacos, cartas);
		for(int i=0; i<nJogadores; i++) clientes.get(i).enviarObj(tabuleiro);

		/*for(int i=0; i<nJogadores; i++) {
			op = OP.ENVIAR_E_RECEBER_STR.codOp();
			pergunta = "Definindo a ordem dos jogadores. Aperte ENTER para rolar os dados.";
			clientes.get(i).run();
			dados[i] = Dados.rolar(2, null);
			
			op = OP.ENVIAR_STR.codOp();
			mensagem = jogadores.get(i) + " rolou " + dados[i];
			clientes.get(i).run();
		}*/
		
		//tabuleiro.setOrdem(dados);
		
		while(tabuleiro.jogoContinua()) {
			jogador = tabuleiro.getJogadorAtual();
			indJogadorAtual = tabuleiro.getIndJogador();
			espacoAtual = comecarRodada(tabuleiro, jogador, clientes, nJogadores, indJogadorAtual, resultadoDados);

			if(espacoAtual.compravel()) {
				Compravel espacoCompravel = (Compravel) espacoAtual;
				if(espacoCompravel.temDono() && espacoCompravel.getDono() != jogador) { //SE TEM DONO PAGA O ALUGUEL
					pagarAluguel(espacoCompravel, jogador, jogadores, clientes, nJogadores, indJogadorAtual, resultadoDados);
				} else if(!espacoCompravel.temDono()){ //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= espacoCompravel.getPreco()) {
						comprarPropriedade(espacoCompravel, jogador, clientes, nJogadores, indJogadorAtual);
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
						moverJogador(jogador, clientes, nJogadores, indJogadorAtual, posicao);
						break;
					case 1: //pagar ao banco
					case 2: //receber do banco
						pagarOuReceber(jogador, clientes, nJogadores, indJogadorAtual, quantia, acao);
						break;
					case 3: //mudar posição e pagar
						moverEPagar(jogador, clientes, nJogadores, indJogadorAtual, posicao, quantia,tabuleiro.getEspacoPosicao(posicao).getNome());
						break;
					case 4: //pagar aos jogadores
						pagarJogadores(jogador, jogadores, clientes, nJogadores, indJogadorAtual, quantia);
						break;
					case 5: //receber dos jogadores
						receberJogadores(jogador, jogadores, clientes, nJogadores, indJogadorAtual, quantia);
						break;
				}
			}
			
			int cmd = 1;
			while(cmd != 3) {
				cmd = (int) clientes.get(indJogadorAtual).enviarEReceberObj("01", "");
				
				// hipotecar uma propriedade
				if(cmd == 0) {
					hipotecar(jogador, clientes, nJogadores, indJogadorAtual);
				}
				
				// construir casa
				if(cmd == 1){
					construirCasa(jogador, clientes, nJogadores, indJogadorAtual);
				}

				// vender casa
				if(cmd == 2){
					venderCasa(jogador, clientes, nJogadores, indJogadorAtual);
				}
			}
		}

		servidor.close();
		teclado.close();
	}

}
