import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	private Socket sock;
	private String nome;
	private static boolean noJogo = true;

	public Cliente(Socket sock) throws UnknownHostException, IOException {
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
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Socket cliente = new Socket("127.0.0.1", 12345);
		cliente.setKeepAlive(true);
		Scanner teclado = new Scanner(System.in);
		ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
		ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
		Tabuleiro tabuleiro;
		
		while(noJogo) {
			try {
				int codigoOp = entrada.readInt();
				System.out.println(codigoOp);
				
				if(codigoOp == Server.OP.ENVIAR_STR.codOp()) {
					System.out.println(entrada.readUTF());
				}
				
				if(codigoOp == Server.OP.ENVIAR_E_RECEBER_STR.codOp()) {
					System.out.println(entrada.readUTF());
					
					String t = teclado.nextLine();
					saida.writeUTF(t);
					saida.flush();
				}
				
				if(codigoOp == Server.OP.ENVIAR_GUI.codOp()) {
					String codigo = entrada.readUTF();
					//System.out.println("codigo " + codigo);
					String mensagem = entrada.readUTF();
					//System.out.println("msg" + mensagem);
					
//					if(codigo.equals("00")) {
//						//atualizar posicao do jogador no tabuleiro e saldo (pq pode ter passado no inicio)
//					} else if(codigo.equals("01")) {
//						//jogador pagou aluguel, atualizar saldo de quem pagou e de quem recebeu
//					} else if(codigo.equals("02")) {
//						//jogador comprou propriedade, atualizar saldo e suas propriedades
//					} else if(codigo.equals("03")) {
//						//jogador hipotecou propriedade, atualizar saldo e suas propriedades
//					} else if(codigo.equals("04")) {
//						//jogador construiu uma casa, atualizar saldo e casas na propriedade
//					} else if(codigo.equals("05")) {
//						//jogador demoliu uma casa, atualizar saldo e casas na propriedade
//					} else {
//						//System.out.println();
//					}
				}
				
				if(codigoOp == Server.OP.ENVIAR_OBJ.codOp()) {
					Object obj = entrada.readObject();
					
					if(obj instanceof Tabuleiro) {
						tabuleiro = (Tabuleiro) obj;
					}
					
					if(obj instanceof Compravel) {
						//do other thing
					}
				}
				
				if(codigoOp == Server.OP.ENVIAR_E_RECEBER_OBJ.codOp()) {
					//Arqui é um arraylist de alguma coisa
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			
		}

		teclado.close();
		cliente.close();
	}
}
