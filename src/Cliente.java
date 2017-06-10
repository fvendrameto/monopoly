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
				String s = entrada.readUTF();

				if(Integer.parseInt(s) == 0) {
					System.out.println(entrada.readUTF());
				}
				
				if(Integer.parseInt(s) == 1) {
					System.out.println(entrada.readUTF());
					
					String t = teclado.nextLine();
					saida.writeUTF(t);
					saida.flush();
				}
				
				if(Integer.parseInt(s) == 2) {
					tabuleiro = (Tabuleiro) entrada.readObject();
				}

			} catch (Exception e) {
				System.out.println("aqui "+e.getMessage());
				return;
			}
			
		}

		teclado.close();
		cliente.close();
	}
}