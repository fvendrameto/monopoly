package monopoly_elements;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe responsavel por representar o jogador e seus pertences
 */
public class Jogador implements Serializable {

	private static final long serialVersionUID = -6980492824693699650L;
	private static final int SALDO_INICIAL = 1500;
	private static final int TAMANHO_TABULEIRO = 40;
	private String nome;
	private int saldo;
	private int posicao_tabuleiro;
	private boolean preso;
	private ArrayList<Compravel> compraveis;

	public Jogador(String nome){
		this.nome = nome;
		this.saldo = SALDO_INICIAL;
		this.posicao_tabuleiro = 0;
		this.preso = false;
		this.compraveis = new ArrayList<Compravel>();
	}

	/**
	 * @return Nome do jogador
	 */
	public String getNome(){
		return this.nome;
	}

	/**
	 * @return Saldo do jogador
	 */
	public int getSaldo(){
		return this.saldo;
	}

	/**
	 * @return Boolean indicando se jogador está preso
	 */
	public boolean naPrisao(){
		return this.preso == true;
	}

	/**
	 * @return Posição do tabuleiro que o jogador se encontra
	 */
	public int getPosicaoTabuleiro(){
		return this.posicao_tabuleiro;
	}

	/**
	 * @param pos Posição do tabuleiro que o jogador deve estar
	 */
	public void setPosicaoTabuleiro(int pos){
		this.posicao_tabuleiro = pos;
	}

	/**
	 * Adianta a posição do jogador no tabuleiro
	 * @param nCasas Quanto o jogador deve avançar
	 * @return Boolean indicando se jogador deu a volta no tabuleiro
	 */
	public boolean andarPosicaoTabuleiro(int nCasas){
		this.posicao_tabuleiro += nCasas;
		if(this.posicao_tabuleiro >= TAMANHO_TABULEIRO){
			this.posicao_tabuleiro %= TAMANHO_TABULEIRO;
			return true;
		}
		return false;
	}

	/**
	 * @param compravel Compravel que deve ser adicionado a lista do jogador
	 */
	public void adicionarCompravel(Compravel compravel){
		if(!this.compraveis.contains(compravel))
			this.compraveis.add(compravel);
	}

	/**
	 * @param c_remover Compravel que dev ser removido da lista do jogador
	 */
	public void removerCompravel(Compravel c_remover){
		for(int i=0;i<compraveis.size();i++){
			if(compraveis.get(i).getNome().equals(c_remover.getNome())){
				compraveis.remove(i);
				break;
			}
		}
	}

	/**
	 * @param quantia Quantia que deve ser adicionada na conta do jogador
	 */
	public void depositarDinheiro(int quantia){
		this.saldo += quantia;
	}

	/**
	 * @param quantia Quantia que deve serremovida da conta do jogador
	 */
	public void sacarDinheiro(int quantia){
		this.saldo -= quantia;
	}

	/**
	 * Retorna se o jogador tem todas as propriedades de uma determinada cor
	 * @param cor Cor que será verificada
	 * @return Boolean indicando se tem todos da cor
	 */
	public boolean temTodosCor(int cor){
		int total = Propriedade.getTotalCor(cor);
		int count = 0;
		for(Compravel c : this.compraveis)
			if(c.propriedade())
				if(((Propriedade) c).getCor() == cor) count++;
		return count == total;
	}

	/**
	 * @return Todas as compraveis que pertencem ao jogador
	 */
	public ArrayList<Compravel> getCompraveis(){
		return this.compraveis;
	}
	
	@Override
	public String toString(){
		return nome;
	}

}