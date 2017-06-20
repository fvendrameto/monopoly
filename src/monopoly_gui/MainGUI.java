package monopoly_gui;

import monopoly_elements.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;

/**
 * Suporta todos os componentes da GUI criados separamente o os exibi para o jogador
 */
public class MainGUI extends JFrame {
	private TabuleiroGUI tabuleiroGui;
	private JogadoresGUI jogadoresGui;
	private LogGUI logGui;
	private BensGUI bensGui;
	
	
	public MainGUI(Tabuleiro tabuleiro){
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

	/**
	 * Exibe uma caixa de dialogo com a descrição de uma carta retirada pelo jogador
	 * @param descricao Descrição da carta retirada pelo jogador
	 */
	public static void mostrarRetirouCarta(String descricao){
		JOptionPane.showMessageDialog(null,descricao);
	}

	/**
	 * Exibe caixa de dialogo pedindo nome
	 * @return Nome digitado pelo jogador
	 */
	public static String mostrarDigiteNome(){
		return JOptionPane.showInputDialog(null,"Digite seu nome:","Bem vindo",JOptionPane.DEFAULT_OPTION);
	}

	/**
	 * Exibe caixa de dialogo informando o ganhador da partida
	 * @param ganhador Nome do ganhador que será anunciano
	 */
	public static void anunciarGanhador(String ganhador){
		String str = "Fim da partida! Parabéns " + ganhador + "!";
		JOptionPane.showMessageDialog(null,str);
	}

	/**
	 * Exibe uma janela com uma mensagem de erro
	 * @param msg Mensagem que deve ser exibida
	 */
	public static void mostrarMensagemErro(String msg){
		JOptionPane.showMessageDialog(null,msg,"Erro",JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Mostra janela de dialogo pedindo para digitar IP
	 * @return IP inserido pelo usuario
	 */
	public static String mostrarDigiteIp(){
		String ip = "";
		boolean run = true;
		while(run){
			ip = JOptionPane.showInputDialog(null,"Digite o IP:","Iniciando...",JOptionPane.DEFAULT_OPTION);
			if(ip == null) return null;
			if(ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) run = false;
			else mostrarMensagemErro("IP inválido");
		}
		return ip;
	}

	/**
	 * Mostra janela de dialogo pedindo o numero de jogadores
	 * @return Numero de jogadores digitados
	 */
	public static int mostrarDigiteNumeroJogadores(){
		String num = "";
		boolean run = true;
		while(run){
			num = JOptionPane.showInputDialog(null,"Digite numero de jogadores:(Min = 1 | Máx = 6)","Iniciando...",JOptionPane.DEFAULT_OPTION);
			if(num == null) return 0;
			if(num.matches("[0-9]+") && Integer.parseInt(num) >= 1 && Integer.parseInt(num) <= 6) run = false;
			else mostrarMensagemErro("Número inválido");
		}
		return Integer.parseInt(num);
	}

	/**
	 * Exibe janela de dialogo pedindo o numero de bots
	 * @param numeroMinimo Número minimo que pode ser inserido
	 * @param numeroMaximo Número máximo que pode ser inserido
	 * @return
	 */
	public static int mostrarDigiteNumeroBots(int numeroMinimo,int numeroMaximo){
		String num = "";
		String texto;

		if(numeroMinimo > 0) texto = "Digite o numero de bots(Min = "+numeroMinimo+" | Máx = "+numeroMaximo+")";
		else texto = "Digite o numero de bots(Min = 0 | Máx = "+numeroMaximo+")";

		boolean run = true;
		while(run){
			num = JOptionPane.showInputDialog(null,texto,"Iniciando...",JOptionPane.DEFAULT_OPTION);
			if(num == null) return 0;
			if(num.matches("[0-9]+") && Integer.parseInt(num) >= numeroMinimo && Integer.parseInt(num) <= numeroMaximo) run = false;
			else mostrarMensagemErro("Número inválido");
		}
		return Integer.parseInt(num);
	}

	/**
	 * Mostra janela de dialogo pedindo para usuário inserir porta
	 * @return Porta inserida pelo usuario
	 */
	public static int mostrarDigitePorta(){
		String porta = "";
		boolean run = true;
		while(run){
			porta = JOptionPane.showInputDialog(null,"Digite a porta:","Iniciando...",JOptionPane.DEFAULT_OPTION);
			if(porta == null) return -1;
			if(porta.matches("[0-9]+")) run = false;
			else mostrarMensagemErro("Porta inválida");
		}
		return Integer.parseInt(porta);
	}

	/**
	 * Exibe caixa de dialogo dando a opção de compra pro jogador
	 * @param c Compravel que será exibido
	 * @return Opção do usuario (0 = Sim | 1 = Não)
	 */
	public int mostarOpcaoComprar(Compravel c){
		Object[] acoes = {"Sim","Nao"};
		ImageIcon carta = new ImageIcon(c.getEndImg());
		//ImageIcon carta = null;
		String texto = "";
		//texto += "Nome: " + c.getNome() + "\n";
		//texto += "Preço: " + c.getPreco() + "\n";
		//texto += "Hipoteca:" + c.getValorHipoteca();
		
		return JOptionPane.showOptionDialog(null,texto,"Deseja comprar?",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION,carta,acoes,acoes[0]);
	}

	/**
	 * Mostra a janela avisando para rolar os dados
	 */
	public void mostrarRolarDados(){
		JOptionPane.showMessageDialog(null, "Sua vez, clique em OK para rolar os dados");
	}
	
	/**
	 * Mostra a janela avisando para rolar os dados
	 */
	public void mostrarFalencia(){
		JOptionPane.showMessageDialog(null, "Você não tem dinheiro para pagar e foi à falência!");
	}
	
	/**
	 * Mostra aviso referente ao espaço que o jogador se encontra
	 * @param string String que sera exibida
	 */
	public void mostrarAvisoEspaco(String string){
		JOptionPane.showMessageDialog(null, string);
	}
	
	/**
	 * Exibe as opções de jogo para o jogador
	 * @return Inteiro equivalente a posição escolhida
	 */
	public int mostrarOpcoesJogo(){
		Object[] acoes = {"Hipotecar","Comprar Casa","Vender Casa","Encerrar Rodada","Sair do Jogo"};

		String titulo = "Escolha a opção do jogo";
		String nome_janela = "";
		int ret =  JOptionPane.showOptionDialog(null,titulo,nome_janela,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,acoes,acoes[0]);
		if(ret == 4){ //pediu para sair do jogo
			Object[] opcoes = {"Sim","Nao"};
			int conf = JOptionPane.showConfirmDialog(null,"Deseja mesmo sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
			if(conf == 0) return 4; //jogador realmente quer sair
			else return -1;
		}
		return ret;
	}
	
	
	
	/**
	 * Exibe um ArrayList de compraveis para serem selecionados
	 * @param compraveis Objetos da classe Compravel que serão exibidos para escolha
	 * @param titulo Titulo da janela de seleção
	 * @return Objeto da classe compravel escolhido
	 */
	public Compravel mostrarEscolherCompravel(ArrayList<Compravel> compraveis, String titulo){
		if(compraveis.size() > 0) {
			String pergunta = "Escolha a propriedade";
			HashMap<String, Compravel> map_compraveis = new HashMap<String, Compravel>();

			String[] nomes = new String[compraveis.size()];
			int i = 0;
			for (Compravel c : compraveis) {
				nomes[i++] = c.getNome();
				map_compraveis.put(c.getNome(), c);
			}

			String compravel = (String) JOptionPane.showInputDialog(null, pergunta, titulo, JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);


			return map_compraveis.get(compravel);
		}else{
			JOptionPane.showMessageDialog(null, "Você não possui propriedades para isso");
			return null;
		}
	}
	
	
	/**
	 * Escreve no log de jogo
	 * @param string String
	 */
	public void addTextoLog(String string){
		logGui.addTexto(string);
	}


	/**
	 * Adiciona compravel a jogador no componente que exibe todos os jogadores
	 * @param jogador Nome do jogador que tera compravel adicionado
	 * @param compravel Nome do compravel que será adicionado
	 */
	public void addCompravelOutro(String jogador,String compravel){
		jogadoresGui.addCompravelJogador(jogador, compravel);
	}

	/**
	 * Remove compravel de jogador no componente que exibe todos os jogadores
	 * @param jogador Nome do jogador que tera compravel removido
	 * @param compravel Nome do compravel que será adicionado
	 */
	public void removerCompravelOutro(String jogador,String compravel){
		jogadoresGui.removeCompravelJogador(jogador, compravel);
	}

	/**
	 * Atualiza numero de casas de propriedade de jogador no componente que exibe todos os jogadores
	 * @param jogador Nome do jogador que tera numero de casas alterado
	 * @param propriedade Nome da propriedade que será atualizada
	 * @param ncasas Novo numero de casas da propriedade
	 */
	public void alteraCasaPropriedadeOutro(String jogador, String propriedade, int ncasas){
		jogadoresGui.alterarPropriedadeJogador(jogador, propriedade, ncasas);
	}

	/**
	 * Adiciona compravel aos bens do jogador
	 * @param compravel compravel que será adicionado
	 */
	public void addCompravelJogador(Compravel compravel){
		bensGui.addCompravel(compravel);
	}


	/**
	 * Remove compravel dos bens do jogador
	 * @param compravel Compravel que será removido
	 */
	public void removeCompravelJogador(Compravel compravel){
		bensGui.removeCompravel(compravel.getNome());
	}


	/**
	 * Altera numero de casas de propriedade nos bens do jogador
	 * @param propriedade Nome do compravel que será adicionado
	 */
	public void alteraCasaPropriedadeJogador(String propriedade,int nCasas, int novoAluguel) {
		bensGui.alteraCompravel(propriedade, nCasas + "", 3);
		bensGui.alteraCompravel(propriedade,novoAluguel + "",2);
	}


	/**
	 * Reposiciona um peão no tabuleiro
	 * @param peao Indice do jogador que sera reposicionado
	 * @param posicao Nova posição do peão
	 */
	public void posicionaPeao(int peao, int posicao){
		tabuleiroGui.putPeao(peao, posicao);
	}

	/**
	 * Altera o saldo exibido na GUI
	 * @param novo_saldo Novo valor de saldo
	 */
	public void alterarSaldo(int novo_saldo){
		bensGui.setDinheiro(novo_saldo);
	}

	public void setFalencia(String jogador, int indice){
		jogadoresGui.setFalenciaJogador(jogador);
		tabuleiroGui.removePeao(indice);
	}
}
