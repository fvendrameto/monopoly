import java.util.Random;
import java.util.Calendar;

public class Dado {
	private int valor;
	private int nLados;
	
	public Dado() {
		this.nLados = 6;
	}
	
	public void setValor(int val) {
		this.valor = val;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void rolar() {
		Random rand = new Random();
		rand.setSeed(Calendar.getInstance().getTimeInMillis());
		
		this.setValor(rand.nextInt(this.nLados) + 1);
	}

}
