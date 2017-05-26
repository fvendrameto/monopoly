
public class Dados {
	private Dado[] dados;
	private int[] valores;
	

	public Dados(int nDados) {
		this.dados = new Dado[nDados];
		this.valores = new int[nDados];
	}
	
	public int[] rolar() throws InterruptedException {
		for(int i=0; i<dados.length; i++) {
			dados[i].rolar();
			valores[i] = dados[i].getValor();
			
			Thread.sleep(1);
		}
		
		return valores;
	}
	
	public int getSomaValores() {
		int soma = 0;
		
		for(int i=0; i<dados.length; i++)
			soma += dados[i].getValor();
		
		return soma;
	}
	
	public int[] getValores(){
		return valores;
	}
	
}
