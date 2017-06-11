import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by fernando on 11/06/17.
 */
public class MainTeste {
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Jogador> j = new ArrayList<>();
		ArrayList<Espaco> e = Initializers.initEspacos("espacos.dat");
		
		j.add(new Jogador("Gustavo"));
		j.add(new Jogador("Leonardo"));
		
		Tabuleiro t = new Tabuleiro(j,e,null);
		MainGUI m = new MainGUI(t);
		m.setVisible(true);
		
		m.posicionaPeao(0,0);
		
		m.mostrarRolarDados();
	
		
		ArrayList<Compravel> compraveis = new ArrayList<>();
		compraveis.add(new Companhia("Propriedade 1",100,100,20));
		compraveis.add(new Companhia("Propriedade 2",100,100,20));
		compraveis.add(new Companhia("Propriedade 3",100,100,20));
		
		m.addCompravelJogador(compraveis.get(0));
		m.addCompravelJogador(compraveis.get(1));
		
		int ret = 1;
		while(ret != -1) {
			ret = m.mostrarOpcoesJogo();
			if (ret == 0) {
				Compravel c = m.escolherCompravel(compraveis, "Hipoteca");
				if(c != null) System.out.println(c.getNome());
			}
		}
		
	}
}
