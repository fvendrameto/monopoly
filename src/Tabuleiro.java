import java.util.ArrayList;

public class Tabuleiro {
	private ArrayList<Espaco> espacos;
	private ArrayList<Jogador> jogadores;
	private ArrayList<Carta> cartas;
	private int[] ordem;
	private boolean[] temDinheiro;
	private int indJogador;
	
	public Tabuleiro(ArrayList<Jogador> j, ArrayList<Espaco> e, ArrayList<Carta> c){
		this.jogadores = j;
		this.espacos = e;
		this.cartas = c;
		
		this.ordem = new int[this.jogadores.size()];
		this.temDinheiro = new boolean[this.jogadores.size()];
		for(int i=0; i<this.jogadores.size(); i++) {
			this.ordem[i] = -1;
			this.temDinheiro[i] = true;
		}
	}
	
	private void proximoIndJogador() {
		do {
			this.indJogador = ((this.indJogador+1) % this.jogadores.size());
		} while(!this.temDinheiro[this.indJogador]);
	}
	
	public void setOrdem(){
		for(int i=0;i<jogadores.size();i++)
			ordem[i] = i;
	}
	
	public Jogador getJogadorAtual() {
		proximoIndJogador();
		return jogadores.get(this.ordem[this.indJogador]);
	}
	
	public boolean jogoContinua() {
		int cont = 0;
		
		for(int i=0; i<jogadores.size(); i++)
			if(this.temDinheiro[i])
				cont++;
		
		if(cont >= 2)
			return true;
		return false;
	}
	
	public Espaco getEspacoPosicao(int posicao) {
		return this.espacos.get(posicao);
	}
	
	public void setFalencia(int pos) {
		this.temDinheiro[pos] = false;
	}
	
	public ArrayList<Jogador> getJogadores (){
		return jogadores;
	}
	
}
