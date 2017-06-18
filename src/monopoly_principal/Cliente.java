package monopoly_principal;

import monopoly_gui.*;
import monopoly_elements.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente{
	private Socket sock;
	private String nome;
	private static boolean noJogo = true;
	private static MainGUI mainGui;

	private enum OP {
		RECEBER_STR(0), RECEBER_E_ENVIAR_STR(1), RECEBER_GUI(2), RECEBER_OBJ(3), RECEBER_E_ENVIAR_OBJ(4);

		private int val;
		OP(int n) {
			this.val = n;
		}

		public int codOp() {
			return this.val;
		}
	}

	public Cliente(Socket sock) throws IOException {
		this.sock = sock;
	}

	public void setNome(String n) {
		this.nome = n;
	}

	public String getNome() {
		return this.nome;
	}

	public Socket getSocket() {
		return this.sock;
	}

	public static void setNoJogo(boolean b) {
		noJogo = b;
	}

	private static void enviarEReceberStr(ObjectInputStream entrada, ObjectOutputStream saida) {
		try {
			String t = "";
			System.out.println("aqui2");
			String cmd = entrada.readUTF();
			System.out.println(cmd);

			if (cmd.equals("Digite seu nome: ")) {
				t = MainGUI.mostrarDigiteNome(); //aqui ainda é usado o metodo estatico porque o obj ainda nao foi estanciado
			} else if (cmd.contains("Aperte ENTER para rolar os dados")) {
				mainGui.mostrarRolarDados();
			}

			saida.writeUTF(t);
			saida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void receberGUI(ObjectInputStream entrada, int saldo) {
		try {

			String codigo = entrada.readUTF();
			String[] mensagem = entrada.readUTF().split("#");


			if(codigo.equals("00")) { //atualizar posicao do jogador no tabuleiro e saldo (pq pode ter passado no inicio)
				int jogador = Integer.parseInt(mensagem[0]);
				int posicao = Integer.parseInt(mensagem[1]);
				int novo_saldo = Integer.parseInt(mensagem[2]);

				mainGui.posicionaPeao(jogador, posicao);
				if(saldo != novo_saldo && novo_saldo != -1){ //novo salvo == -1 significa que esse jogador não deve atualizar o saldo
					saldo = novo_saldo;
					mainGui.alterarSaldo(saldo);
				}
			} else if(codigo.equals("01")) { //pagar aluguel
				int valor = Integer.parseInt(mensagem[0]);
				String dono = mensagem[1];
				String lugar = mensagem[2];

				saldo -= valor;
				mainGui.alterarSaldo(saldo);

				mainGui.mostrarAvisoEspaco("Você pagou " + valor + " para " + dono + " porque parou em " + lugar);

			} else if(codigo.equals("02")) { //add compravel no tree jogadores
				String jogador = mensagem[0];
				String lugar = mensagem[1];
				mainGui.addCompravelOutro(jogador, lugar);
			} else if(codigo.equals("03")) { //jogador hipotecou propriedade, atualizar saldo e suas propriedades
				String jogador = mensagem[0];
				String compravel = mensagem[1];
				mainGui.removerCompravelOutro(jogador, compravel);
			} else if(codigo.equals("04")) { //casa foi adicionada
				String jogador = mensagem[0];
				String propriedade = mensagem[1];
				int nCasas = Integer.parseInt(mensagem[2]);
				mainGui.alteraCasaPropriedadeOutro(jogador, propriedade, nCasas);
			}else if(codigo.equals("05")){ //acão que altera o saldo
				int novo_saldo = Integer.parseInt(mensagem[0]);
				String str = mensagem[1];

				if(saldo != novo_saldo){
					saldo = novo_saldo;
					mainGui.alterarSaldo(saldo);
				}

				mainGui.mostrarAvisoEspaco(str);
			}else if(codigo.equals("06")){ //acão que altera o saldo
				int novo_saldo = Integer.parseInt(mensagem[0]);
				String str = mensagem[1];

				if(saldo != novo_saldo){
					saldo = novo_saldo;
					mainGui.alterarSaldo(saldo);
				}

				mainGui.mostrarAvisoEspaco(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void receberEEnviarObj(ObjectInputStream entrada, ObjectOutputStream saida, int saldo) {
		try {

			String codigo = entrada.readUTF();

			if(codigo.equals("00")){ //opcão de compra
				Compravel compravel = (Compravel) entrada.readObject();
				int op = mainGui.mostarOpcaoComprar(compravel);
				if(op == 0){ //quer comprar
					mainGui.addCompravelJogador(compravel);
					saldo -= compravel.getPreco();
					mainGui.alterarSaldo(saldo);
				}
				saida.writeObject(op);
				saida.flush();
			}else if(codigo.equals("01")){ //mostrar opçoes de jogo
				entrada.readObject();
				int op = mainGui.mostrarOpcoesJogo();
				saida.writeObject(op);
				saida.flush();
			}else if(codigo.equals("02")){ //opção de hipoteca
				ArrayList<Compravel> compraveis = (ArrayList<Compravel>) entrada.readObject();
				Compravel escolhido = mainGui.mostrarEscolherCompravel(compraveis, "Hipoteca");

				if(escolhido != null){
					mainGui.removeCompravelJogador(escolhido);
					saldo += escolhido.getValorHipoteca();
					mainGui.alterarSaldo(saldo);
				}

				saida.writeObject(escolhido);
				saida.flush();
			}else if(codigo.equals("03")){ //opção de comprar casa
				ArrayList<Compravel> compraveis = (ArrayList<Compravel>) entrada.readObject();
				Propriedade escolhido = (Propriedade) mainGui.mostrarEscolherCompravel(compraveis, "Comprar casa");

				if(escolhido != null){
					mainGui.alteraCasaPropriedadeJogador(escolhido.getNome(),escolhido.getNumeroCasas() + 1);
					saldo -= escolhido.getPrecoCasa();
					mainGui.alterarSaldo(saldo);
				}

				saida.writeObject(escolhido);
			}else if(codigo.equals("04")){ //opção de vender casa
				ArrayList<Compravel> compraveis = (ArrayList<Compravel>) entrada.readObject();
				Propriedade escolhido = (Propriedade) mainGui.mostrarEscolherCompravel(compraveis, "Vender casa");

				if(escolhido != null){
					mainGui.alteraCasaPropriedadeJogador(escolhido.getNome(),escolhido.getNumeroCasas() - 1);
					saldo += (escolhido.getPrecoCasa() / 2);
					mainGui.alterarSaldo(saldo);
				}

				saida.writeObject(escolhido);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Socket cliente = new Socket("127.0.0.1", 12345);
		cliente.setKeepAlive(true);
		Scanner teclado = new Scanner(System.in);
		ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
		ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
		Tabuleiro tabuleiro;
		int saldo = 1500;

		while(noJogo) {
			try {
				int codigoOp = entrada.readInt();
				System.out.println(codigoOp);

				if(codigoOp == OP.RECEBER_STR.codOp()) { //MENSAGENS QUE VÃO PARA O LOG
					String str = entrada.readUTF();
					mainGui.addTextoLog(str + "\n");
				}

				if(codigoOp == OP.RECEBER_E_ENVIAR_STR.codOp()) {
					enviarEReceberStr(entrada, saida);
				}

				if(codigoOp == OP.RECEBER_GUI.codOp()) {
					receberGUI(entrada, saldo);
				}

				if(codigoOp == OP.RECEBER_OBJ.codOp()) {
					Object obj = entrada.readObject();

					if(obj instanceof Tabuleiro) {
						tabuleiro = (Tabuleiro) obj;
						mainGui = new MainGUI(tabuleiro);
						mainGui.setVisible(true);
					}
				}

				if(codigoOp == OP.RECEBER_E_ENVIAR_OBJ.codOp()) {
					receberEEnviarObj(entrada, saida, saldo);
				}

			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
				return;
			}

		}

		teclado.close();
		cliente.close();
	}
}
