import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
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
	
	public static Socket conectar(InetAddress host, int port) throws UnknownHostException, IOException {
		Socket s = new Socket(host, port);
		System.out.println("Cliente conectado");
		return s;
	}
	
	public static void setNoJogo(boolean b) { 
		noJogo = b;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
//		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(buffer));
		
		Socket cliente = new Socket("127.0.0.1", 12345);
		
		DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
		Scanner teclado = new Scanner(System.in);
		Scanner server = new Scanner(cliente.getInputStream());

		while(noJogo) {
			String s = server.nextLine();
			System.out.println(server.nextLine());

			if(s == "0") {				
				saida.writeUTF(teclado.nextLine());
			}		
		}
		
		teclado.close();
		server.close();
		cliente.close();
	}
}