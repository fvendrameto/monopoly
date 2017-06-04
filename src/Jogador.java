import java.util.ArrayList;

public class Jogador{
	private static final int SALDO_INICIAL = 500;
	private static final int TAMANHO_TABULEIRO = 10;
	private String nome;
	private int saldo;
	private int posicao_tabuleiro;
	private boolean preso;
	private ArrayList<Compravel> compraveis;
	private ArrayList<Carta> cartas;

	public Jogador(String nome){
		this.nome = nome;
		this.saldo = SALDO_INICIAL;
		this.posicao_tabuleiro = 0;
		this.preso = false;
		this.compraveis = new ArrayList<Compravel>();
		this.cartas = new ArrayList<Carta>();
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
	
	public boolean andarPosicaoTabuleiro(int pos){
		this.posicao_tabuleiro += pos;
		if(this.posicao_tabuleiro >= TAMANHO_TABULEIRO){
			this.posicao_tabuleiro %= TAMANHO_TABULEIRO;
			return true;
		}
		return false;
	}
	
	public void adicionarCompravel(Compravel compravel){
		if(!this.compraveis.contains(compravel))
			this.compraveis.add(compravel);
	}
	
	public void removerCompravel(Compravel compravel){
		this.compraveis.remove(compravel);
	}
	
	public void depositarDinheiro(int quantia){
		this.saldo += quantia;
	}
	
	public void sacarDinheiro(int quantia){
		this.saldo -= quantia;
	}
	
	public boolean temTodosCor(int cor){
		int total = Propriedade.getTotalCor(cor);
		int count = 0;
		for(Compravel c : this.compraveis)
			if(c.propriedade())
				if(((Propriedade) c).getCor() == cor) count++;
		return count == total;
	}
	
	public ArrayList<Compravel> getCompraveis(){
		return this.compraveis;
	}
	
	@Override
	public String toString(){
		return nome + " (" + saldo + ")";
	}

}