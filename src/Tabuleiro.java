import java.io.Serializable;
import java.util.ArrayList;

public class Tabuleiro implements Serializable {

	private static final long serialVersionUID = -9061517518999832305L;
	private ArrayList<Espaco> espacos;
	private ArrayList<Jogador> jogadores;
	private ArrayList<Carta> cartas;
	private int[] ordem;
	private boolean[] temDinheiro;
	private int indJogador = -1;
	
	public Tabuleiro(ArrayList<Jogador> j, ArrayList<Espaco> e, ArrayList<Carta> c){
		this.jogadores = j;
		this.espacos = e;
		this.cartas = c;
		
		this.ordem = new int[this.jogadores.size()];
		this.temDinheiro = new boolean[this.jogadores.size()];
		
		for(int i=0; i<this.jogadores.size(); i++) {
			this.ordem[i] = i;
			this.temDinheiro[i] = true;
		}
	}
	
	public int getIndJogador() {
		return this.indJogador;
	}
	
	private void proximoIndJogador() {
		do {
			this.indJogador++;
			if(this.indJogador >= this.jogadores.size())
				this.indJogador = 0;
		} while(!this.temDinheiro[this.indJogador]);
	}
	
	public void setOrdem(int[] dados) {
		int j, max, idx = 0;
		for(int i=0;i<jogadores.size();i++) {
			max = Integer.MIN_VALUE;
			for(j=0; j<jogadores.size(); j++) {
				if(dados[j] > max) {
					max = dados[j];
					idx = j;
				}
			}
			ordem[i] = idx;
			dados[idx] = -1;
		}
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

		return cont >= 2;
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
