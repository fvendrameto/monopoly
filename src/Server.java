import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import oracle.jbo.jbotester.server.ServerThread;

public class Server extends Thread {

	public void run() {
		System.out.println("Thread do Servidor inicializada");
		
		
	}
	
	public static void main(String[] args) throws IOException {
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		ArrayList<Espaco> espacos = new ArrayList<Espaco>();
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		ArrayList<Compravel> compraveis;
		HashMap<Integer,Compravel> map_compraveis;
		ServerSocket servidor = new ServerSocket(12345);
		Jogador jogador;
		Tabuleiro tabuleiro;
		Espaco espaco_atual;
		int resultado_dados;
		int[] dados = null;
	
		espacos = Initializers.initEspacos("espacos.dat");
		cartas = Initializers.initCartas("cartas.dat");
		System.out.println(espacos.size());
		
		System.out.println("Digite o numero de jogadores: ");
		int numero_jogadores = EntradaTeclado.leInt();
		
		for(int i=0;i<numero_jogadores;i++) {
			System.out.println("Digite o nome do jogador: ");
			String nome = EntradaTeclado.leString();
			jogadores.add(new Jogador(nome));
		}
		
		tabuleiro = new Tabuleiro(jogadores,espacos,cartas);
		
		tabuleiro.setOrdem();
		
		while(tabuleiro.jogoContinua()) {
			Socket cliente = servidor.accept();
			Thread t = new ServerThread(cliente);
			t.start();
		}
	}

}
