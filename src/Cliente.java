import java.io.IOException;
import java.io.PrintStream;
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
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket cliente = new Socket("127.0.0.1", 12345);
		cliente.setKeepAlive(true);
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		Scanner teclado = new Scanner(System.in);
		Scanner server = new Scanner(cliente.getInputStream());

		while(noJogo) {
			String s = server.nextLine();
			System.out.println(server.nextLine());

			if(Integer.parseInt(s) == 0) {
				String t = teclado.nextLine();
				saida.println(t);
			}
			
			while(!server.hasNext());
		}
		
		teclado.close();
		server.close();
		cliente.close();
	}
}