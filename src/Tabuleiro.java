import java.util.ArrayList;

public class Tabuleiro {
	private ArrayList<Carta> cartas;
	private ArrayList<Espaco> espacos;
	private ArrayList<Jogador> jogadores;
	private Dados dados = new Dados(2);
	private int[] ordem;
	private int jogadorAtual;
	private int indJogador;
	
	public Tabuleiro(ArrayList<Jogadores> j, ArrayList<Carta> c, ArrayList<Espaco> e) {
		this.jogadores = j;
		this.cartas = c;
		this.espacos = e;
		
		this.ordem = new int[this.jogadores.size()];
		this.temDinheiro = new boolean[this.jogadores.size()];
		for(int i=0; i<this.jogadores.size(); i++) {
			this.ordem[i] = -1;
			this.temDinheiro[i] = true;
		}
		
		this.turno = 0;
	}
	
	public void sortearJogadores() {
		int[] somaJogador = new int[this.jogadores.size()];
		int i=0;
		
		for(Jogador j : this.jogadores) {
			this.dados.rolar();
			somaJogador[i++] = this.dados.getSomaValores();
		}
		
		for(i=0; i<this.jogadores.size(); i++) {
			ordem[i] = indiceValorMaximo(somaJogador);
		}
	}
	
	private int indiceValorMaximo(int[] vals) {
		int max = Integer.MIN_VALUE, indMax = -1, i;
		
		for(i=0; i<vals.length; i++) {
			if(vals[i] > max) {
				max = vals[i];
				indMax = i;
			}
		}
		
		vals[indMax] = -1;
		
		return indMax;
	}
	
	public int getJogadorAtual() {
		return this.ordem[this.indJogador];
	}
	
	public boolean jogoContinua() { 
		for(Jogador j : this.jogadores)
			if(j.getSaldo() > 0)
				return true;
		return false;
	}
	
	public void proximoJogador() {
		if((this.indJogador + 1) >= this.jogadores.size())
			this.indJogador = 0;
		else
			this.indJogador++;
	}
	
	public Espaco getEspacoPosicao(int posicao) {
		return this.espacos.get(posicao);
	}
	
	
}
