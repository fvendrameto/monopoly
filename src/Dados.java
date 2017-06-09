
public class Dados {

	public Dados() {

	}
	
	public static int rolar(int nDados, int[] vals) throws InterruptedException {
		Dado[] dados = new Dado[nDados];
		int[] valores = new int[nDados];
		
		for(int i=0; i<nDados; i++) {
			dados[i] = new Dado();
			dados[i].rolar();
			valores[i] = dados[i].getValor();
			
			Thread.sleep(1);
		}
		
		if(vals != null)
			vals = valores;
		
		return getSomaValores(dados);
	}
	
	private static int getSomaValores(Dado[] dados) {
		int soma = 0;
		
		for(int i=0; i<dados.length; i++)
			soma += dados[i].getValor();
		
		return soma;
	}
	
}
