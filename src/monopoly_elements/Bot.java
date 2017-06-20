package monopoly_elements;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import monopoly_elements.Companhia;
import monopoly_elements.Compravel;
import monopoly_elements.Dado;
import monopoly_elements.Jogador;
import monopoly_elements.Propriedade;
import monopoly_gui.MainGUI;

/**
 * Classe do Bot. Herda da classe jogador e possui os métodos de escolha das ações do Bot
 *
 */
public class Bot extends Jogador{

	public Bot(String nome) {
		super(nome);
	}
	
	/**
	 * Sempre que possível, o bot comprará uma propriedade ou companhia
	 * @return valor afirmativo para a compra.
	 */
	public static int OpcaoComprarPropriedade () {
		return 0;
	}
	
	/**
	 * Método que escolhe um comprável para o bot hipotecar segundo às seguintes prioridades:
	 * 1-Propriedades que o bot não tem todas de uma cor;
	 * 2-Companhias;
	 * 3-Propriedades que o bot tem todas de uma cor.
	 * @param compraveis lista com as propriedades e compamhias que o bot possui
	 * @return o comprável selecionado para hipotecar.
	 */
	public Compravel EscolherOpcaoHipoteca (ArrayList<Compravel> compraveis) {
		ArrayList<Compravel> companhias = new ArrayList<Compravel>();
		ArrayList<Compravel> propSemTodosDaCor = new ArrayList<Compravel>();
		ArrayList<Compravel> propTemTodosDaCor = new ArrayList<Compravel>();
		
		for (Compravel c : compraveis){
			if (c instanceof Companhia)
				companhias.add(c);
			else if (c.propriedade() && !this.temTodosCor(((Propriedade) c).getCor()))
				propSemTodosDaCor.add(c);
			else if(c.propriedade() && this.temTodosCor(((Propriedade) c).getCor()) && ((Propriedade) c).getNumeroCasas() == 0)
				propTemTodosDaCor.add(c);
		}
		if (propSemTodosDaCor.size()>0){
			Dado dado = new Dado(propSemTodosDaCor.size());
			dado.rolar();
			return propSemTodosDaCor.get(dado.getValor());
		}
		else if (companhias.size()>0){
			Dado dado = new Dado(companhias.size());
			dado.rolar();
			return companhias.get(dado.getValor());
		}
		else if (propTemTodosDaCor.size() > 0){
			Dado dado = new Dado(propTemTodosDaCor.size());
			dado.rolar();
			return propTemTodosDaCor.get(dado.getValor());
		}
		return null;
			
	}
	
	/**
	 * Método que escolhe aleatoriamente uma propriedade para construir casa.
	 * @param propriedades Propriedades que o bot possui todas de uma cor.
	 * @return A propriedade escolhida para contruir casa
	 */
	public Compravel EscolherOpcaoComprarCasa (ArrayList<Propriedade> propriedades){
		Dado dado = new Dado (propriedades.size());
		dado.rolar();
		return propriedades.get(dado.getValor());
	}
	
	/**
	 * Método que escolhe para vender uma casa da propriedade que mais tem casas.
	 * @param propriedades Lista de propriedades com casas que o bot possui.
	 * @return A propriedade cuja casa será vendida.
	 */
	public Compravel EscolherOpcaoVenderCasa (ArrayList<Propriedade> propriedades){
		
		Collections.sort(propriedades, new Comparator<Propriedade>() {
	        @Override
	        public int compare(Propriedade p2, Propriedade p1)
	        {
	            return  (p1.getNumeroCasas() > p2.getNumeroCasas()) ? -1 : (p1.getNumeroCasas() < p2.getNumeroCasas()) ? 1 : 0;
	        }
	    });
		return propriedades.get(0);
	}
	
	/**
	 * Método que escolhe uma opção de jogo para o bot. Se tiver saldo abaixo de 100, o bot irá hipotecar ou vendar casa.
	 * Se tiver saldo acima de 300, o bot irá construir casa.
	 * @return Um inteiro que representa a opção selecionada.
	 */
	public int EscolherOpcaoJogo (){
		ArrayList<Compravel> propComCasa = new ArrayList<Compravel>();
		ArrayList<Compravel> companhias = new ArrayList<Compravel>();
		ArrayList<Compravel> propSemCasa = new ArrayList<Compravel>();
		ArrayList<Compravel> propTemTodosDaCor = new ArrayList<Compravel>();
		
		for (Compravel c : this.getCompraveis()){
			if (c.propriedade() && ((Propriedade) c).getNumeroCasas()>0)
				propComCasa.add(c);
			else if (c instanceof Companhia)
				companhias.add(c);
			else
				propSemCasa.add(c);
			
			if (c.propriedade() && this.temTodosCor(((Propriedade) c).getCor()))
				propTemTodosDaCor.add(c);
		}
		
		if (this.getSaldo() < 100){
			
			if (propSemCasa.size()>0 || companhias.size()>0)
				return 0;
			else if (propComCasa.size()>0)
				return 2;
		}
		else if (this.getSaldo() > 300){
			if(propTemTodosDaCor.size()>0)
				return 1;
		}
		
		return 3;
	}
}
