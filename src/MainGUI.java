import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainGUI extends JFrame {
	private TabuleiroGUI tabuleiroGui;
	private JogadoresGUI jogadoresGui;
	private LogGUI logGui;
	private BensGUI bensGui;
	
	
	public MainGUI(Tabuleiro tabuleiro){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setSize(1100,690);
		this.setResizable(true);
		
		JPanel esquerda = new JPanel();
		JPanel direita = new JPanel();
		esquerda.setLayout(null);
		direita.setLayout(null);
		direita.setBackground(new Color(205,230,208));
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weighty = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		
		gbc.weightx = 0.66;
		gbc.gridx = 0;
		this.add(esquerda,gbc);
		
		
		gbc.weightx = 0.33;
		gbc.gridx = 1;
		this.add(direita,gbc);			
		      
	       
        this.tabuleiroGui = new TabuleiroGUI("images/board.jpg", 690, 690,tabuleiro.getNumeroJogadores());
        for(int i=0;i<tabuleiro.getNumeroJogadores();i++)
        	this.tabuleiroGui.putPeao(i,0);
        esquerda.add(tabuleiroGui);
        esquerda.setBackground(new Color(205,230,208));
        
        direita.setLayout(null);
        
        this.logGui = new LogGUI();
        this.logGui.setSize(345, 220);
        logGui.setBackground(new Color(205,230,208));
        
        this.jogadoresGui = new JogadoresGUI(tabuleiro.getJogadores());
        this.jogadoresGui.setSize(345,220);
        this.jogadoresGui.setLocation(0,230);
        jogadoresGui.setBackground(new Color(205,230,208));
        
        this.bensGui = new BensGUI();
        this.bensGui.setSize(345,220);
        this.bensGui.setLocation(0,460);
        this.bensGui.setBackground(new Color(205,230,208));
        
        direita.add(logGui);
        direita.add(jogadoresGui);
        direita.add(bensGui);
	
	}
	
	public static String mostrarDigiteNome(){
		return JOptionPane.showInputDialog(null,"Digite seu nome:","Bem vindo",JOptionPane.DEFAULT_OPTION);
	}
	
	public int mostarOpcaoComprar(Compravel c){
		Object[] acoes = {"Sim","Nao"};
		//ImageIcon carta = new ImageIcon("images/carta-teste.png");
		ImageIcon carta = null;
		String texto = "";
		texto += "Nome: " + c.getNome() + "\n";
		texto += "Preço: " + c.getPreco() + "\n";
		texto += "Hipoteca:" + c.getValorHipoteca();
		
		return JOptionPane.showOptionDialog(null,texto,"Deseja comprar?",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION,carta,acoes,acoes[0]);
	}
	
	/**
	 * Mostra a janela avisando para rolar os dados
	 */
	public void mostrarRolarDados(){
		JOptionPane.showMessageDialog(null, "Sua vez, clique em OK para rolar os dados");
	}
	
	public void mostrarPagouAluguel(String string){
		JOptionPane.showMessageDialog(null, string);
	}
	
	
	
	/**
	 * Exibe as opções de jogo para o jogador
	 * @return Inteiro equivalente a posição escolhida
	 */
	public int mostrarOpcoesJogo(){
		Object[] acoes = {"Hipotecar","Comprar Casa","Vender Casa","Encerrar Rodada"};
		String titulo = "Escolha a opção do jogo";
		String nome_janela = "";
		return JOptionPane.showOptionDialog(null,titulo,nome_janela,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,acoes,acoes[0]);
	}
	
	
	
	/**
	 * Exibe um ArrayList de compraveis para serem selecionados
	 * @param compraveis Objetos da classe Compravel que serão exibidos para escolha
	 * @param titulo Titulo da janela de seleção
	 * @return Objeto da classe compravel escolhido
	 */
	public Compravel escolherCompravel(ArrayList<Compravel> compraveis, String titulo){
		String pergunta = "Escolha a propriedade";
		HashMap<String,Compravel> map_compraveis = new HashMap<String,Compravel>();	
		
		String[] nomes = new String[compraveis.size()];
		int i=0;
		for(Compravel c : compraveis){
			nomes[i++] = c.getNome();
			map_compraveis.put(c.getNome(),c);
		}
			
		
		String compravel = (String) JOptionPane.showInputDialog(null,pergunta,titulo,JOptionPane.QUESTION_MESSAGE,null,nomes,nomes[0]);
		
		
		return map_compraveis.get(compravel);
	}
	
	
	/**
	 * Escreve no log de jogo
	 * @param string String
	 */
	public void addTextoLog(String string){
		logGui.addTexto(string);
	}
	
	
	
	public void addCompravelOutro(String jogador,String propriedade){
		jogadoresGui.addCompravelJogador(jogador, propriedade);
	}
		
	
	
	public void removeCompravelOutro(String jogador,String propriedade){
		jogadoresGui.removeCompravelJogador(jogador, propriedade);
	}
	
	
	
	public void alteraCasaPropriedadeOutro(String jogador, String propriedade, int ncasas){
		jogadoresGui.alterarPropriedadeJogador(jogador, propriedade, ncasas);
	}
	
	
	
	public void addCompravelJogador(Compravel compravel){
		bensGui.addCompravel(compravel);
	}
	
	
	
	public void removeCompravelJogador(Compravel compravel){
		bensGui.removeCompravel(compravel.getNome());
	}
	
	
	
	public void alteraCasaPropriedadeJogador(Propriedade propriedade){
		bensGui.alteraCompravel(propriedade.getNome(),"" + propriedade.getNumeroCasas(),3);
	}
	
	
	
	public void posicionaPeao(int peao, int posicao){
		tabuleiroGui.putPeao(peao, posicao);
	}
	
	public void alterarSaldo(int novo_saldo){
		bensGui.setDinheiro(novo_saldo);
	}
	
}
