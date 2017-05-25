import java.util.ArrayList;

public class Jogador{
	private static final int SALDO_INICIAL = 500;
	private String nome;
	private int saldo;
	private int posicao_tabuleiro;
	private boolean preso;
	private ArrayList<Propriedade> propriedades;
	private ArrayList<Carta> cartas;

	public Jogador(String nome){
		this.nome = nome;
		this.saldo = SALDO_INICIAL;
		this.posicao_tabuleiro = 0;
		this.preso = false;
		this.propriedades = new ArrayList<Propriedade>();
		this.cartas = new ArrayList<Cartas>();
	}

	public String getNome(){
		return this.nome;
	}

	public int getSaldo(){
		return this.saldo;
	}	

	public boolean naPrisao(){
		return this.preso == true;
	}

	public int getPosicaoTabuleiro(){
		return this.posicao_tabuleiro;
	}
	
	public void setPosicaoTabuleiro(int pos){
		this.posicao_tabuleiro = pos;
	}
	
	public void adicionarPropriedade(Propriedade propriedade){
		this.propriedades.add(propriedade);
	}
	
	public void removerPropriedade(Propriedade propriedade){
		this.propriedades.remove(propriedade);
	}
	
	public void depositarDinheiro(int quantia){
		this.saldo += quantia;
	}
	
	public int sacarDinheiro(int quantia){
		this.saldo -= quantia;
	}

}