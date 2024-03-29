package monopoly_elements;

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
		indJogador = (indJogador + 1) % getNumeroJogadores();
		while(!temDinheiro[this.indJogador] || jogadores.get(indJogador).naPrisao()){
			indJogador = (indJogador + 1) % getNumeroJogadores();
		}
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
		int players = 0;

		for(int i=0; i<jogadores.size(); i++)
			if(this.temDinheiro[i])
				cont++;

		for(int i=0;i<jogadores.size();i++)
			if(this.temDinheiro[i] && !(jogadores.get(i) instanceof Bot))
				players++;

		return cont >= 2 && players > 0;
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
	 * @param jogador Jogador que foi à falência
	 */
	public void setFalencia(Jogador jogador) {
		this.temDinheiro[jogadores.indexOf(jogador)] = false;
	}

	/**
	 * @return Todos os jogadores da partida
	 */
	public ArrayList<Jogador> getJogadores (){
		return jogadores;
	}


	/**
	 * Ao fim do jogo a função retorna quem venceu
	 * @return Jogador que ganhou a partida
	 */
	public int getIndiceGanhador(){
		int ganhador = -1;
		int players = 0;

		for(int i=0;i<jogadores.size();i++)
			if(this.temDinheiro[i] && !(jogadores.get(i) instanceof Bot))
				players++;

		if(players <= 0) return -1; //se jogo terminou porque não tinham mais jogadores(humanos) com dinheiro;

		for(int i=0;i<jogadores.size();i++)
			if(temDinheiro[i])
				ganhador = i;

		return ganhador;
	}
	
	public void removerDonoCompravel(Compravel compravel) {
		ArrayList<Compravel> compraveis = new ArrayList<>();
		for(Espaco e : this.espacos)
			if(e instanceof Compravel)
				compraveis.add((Compravel) e);
		
		for(int i=0; i < compraveis.size(); i++) {
			Compravel c = compraveis.get(i);
			if(compravel.getDono() != null && c.getDono() != null && c.getDono().getNome().equals(compravel.getDono().getNome()) && c.getNome().equals(compravel.getNome())) {
				compraveis.get(i).setDono(null);
			}
		}
	}
	
}
