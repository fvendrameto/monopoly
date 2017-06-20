package monopoly_elements;

import java.util.Random;
import java.util.Calendar;

/**
 * Classe que realiza função de um dado
 */
public class Dado {
	private int valor;
	private int nLados;
	
	public Dado() {
		this.nLados = 6;
	}
	
	public Dado(int nLados) {
		this.nLados = nLados;
	}

	private void setValor(int val) {
		this.valor = val;
	}

	/**
	 * @return Valor do ultimo lançamento do dado
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * Rola o dado, gerando um inteiro no intervalo [1,6]
	 */
	public void rolar() {
		Random rand = new Random();
		rand.setSeed(Calendar.getInstance().getTimeInMillis());
		
		this.setValor(rand.nextInt(this.nLados) + 1);
	}

}
