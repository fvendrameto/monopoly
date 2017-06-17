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

	/**
	 * @return Numero de jogadores na partida
	 */
	public int getNumeroJogadores() {
		return jogadores.size();
	}

	/**
	 * Avança para o proximo jogador
	 */
	private void proximoIndJogador() {
		do {
			this.indJogador++;
			if(this.indJogador >= this.jogadores.size())
				this.indJogador = 0;
		} while(!this.temDinheiro[this.indJogador]);
	}

	/**
	 * @return Indice do jogador atual
	 */
	public int getIndJogador(){
		return this.indJogador;
	}

	/**
	 * @return Jogador atual
	 */
	public Jogador getJogadorAtual() {
		proximoIndJogador();
		return jogadores.get(this.ordem[this.indJogador]);
	}

	/**
	 * @return Boolean indicando se ainda a jogadores(em condições) o suficiente para o jogo continuar
	 */
	public boolean jogoContinua() {
		int cont = 0;
		
		for(int i=0; i<jogadores.size(); i++)
			if(this.temDinheiro[i])
				cont++;

		return cont >= 2;
	}

	/**
	 * Retorna espaço que se encontra em uma determinada posição do tabuleiro
	 * @param posicao Posição solicitada
	 * @return Espaço da posição solicitada
	 */
	public Espaco getEspacoPosicao(int posicao) {
		return this.espacos.get(posicao);
	}

	/**
	 * Marca o jogador como falido, isto é, GAME OVER
	 * @param pos Indice do jogador
	 */
	public void setFalencia(int pos) {
		this.temDinheiro[pos] = false;
	}

	/**
	 * @return Todos os jogadores da partida
	 */
	public ArrayList<Jogador> getJogadores (){
		return jogadores;
	}


}
