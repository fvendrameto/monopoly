package monopoly_elements;

/**
 * Classe que representa os dados do jogo
 */
public class Dados {

	public Dados() {

	}

	/**
	 * Realiza o sorteio dos dados
	 * @param nDados Numero de dados que devem ser rolados
	 * @param vals Array que contera os valores sorteados em cada dado
	 * @return Soma dos valores dos dados lançados
	 * @throws InterruptedException
	 */
	public static int rolar(int nDados, int[] vals) {
		Dado[] dados = new Dado[nDados];
		int[] valores = new int[nDados];
		
		for(int i=0; i<nDados; i++) {
			dados[i] = new Dado();
			dados[i].rolar();
			valores[i] = dados[i].getValor();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
