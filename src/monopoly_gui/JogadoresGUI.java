package monopoly_gui;

import monopoly_elements.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 * Classe responsavel pela exibição dos bens de cada jogador na GUI
 */
public class JogadoresGUI extends JPanel {
	private JTree tree;
	private JLabel titulo;
	private ImageIcon icon_jogador;
	private ImageIcon icon_compravel;
	JScrollPane scroll;


	private void loadIcons(){
		icon_jogador = new ImageIcon("icon_jogador.png");
		icon_compravel = new ImageIcon("icon_compravel.png");
	}


	public JogadoresGUI(ArrayList<Jogador> jogadores){
		setLayout(new GridBagLayout());

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Jogadores");
		DefaultTreeModel model = new DefaultTreeModel(root);

		for(Jogador j : jogadores){
			DefaultMutableTreeNode new_node = new DefaultMutableTreeNode(j.getNome());
			root.add(new_node);
		}

		tree = new JTree(model);
		tree.putClientProperty("JTree.lineStyle", "None");
		JLabel titulo = new JLabel();


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.weightx = 1;

		gbc.gridy = 0;
		gbc.weighty = 0.2;
		titulo.setText("Jogadores");
		add(titulo,gbc);

		scroll = new JScrollPane(tree);

		gbc.gridy = 1;
		gbc.weighty = 0.8;
		gbc.fill = GridBagConstraints.BOTH;
		add(scroll,gbc);
	}

	/**
	 * Adiciona um compravel a um jogador na GUI
	 * @param jogador Nome do jogador que deve ter compravel adicionada
		 * @param nova_propriedade Nome do compravel
	 */
	public void addCompravelJogador(String jogador, String nova_propriedade){
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        Enumeration children = root.children();
        while(children.hasMoreElements()){
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
        	if(node.toString().equals(jogador)){
        		node.add(new DefaultMutableTreeNode(nova_propriedade + "(0)"));
        	}
        }

        model.reload(root);
	}

	/**
	 * Remove um compravel a um jogador na GUI
	 * @param jogador Nome do jogador que deve ter compravel removido
	 * @param propriedade Nome do compravel
	 */
	public void removeCompravelJogador(String jogador, String propriedade){
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        Enumeration children = root.children();
        while(children.hasMoreElements()){
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
        	if(node.toString().equals(jogador)){
        		Enumeration propriedades = node.children();
        		while(propriedades.hasMoreElements()){
        			DefaultMutableTreeNode it = (DefaultMutableTreeNode)  propriedades.nextElement();
        			if(it.toString().contains(propriedade)){
        				node.remove(it);
        				break;
        			}
        		}
        		break;
        	}
        }

        model.reload(root);
	}

	/**
	 * Altera o numero de casas de um compravel na GUI
	 * @param jogador Jogador em questão
	 * @param propriedade Compravel que deve ser modificada
	 * @param ncasas O novo numero que deve ser atualizado
	 */
	public void alterarPropriedadeJogador(String jogador, String propriedade, int ncasas){
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();


        Enumeration children = root.children();
        while(children.hasMoreElements()){
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
        	if(node.toString().equals(jogador)){
        		Enumeration propriedades = node.children();
        		while(propriedades.hasMoreElements()){
        			DefaultMutableTreeNode it = (DefaultMutableTreeNode)  propriedades.nextElement();
        			if(it.toString().contains(propriedade)){ //encontrou propriedade buscada
        				node.remove(it);
        				node.add(new DefaultMutableTreeNode(propriedade + "(" + ncasas + ")"));
        				break;
        			}
        		}
        		break;
        	}
        }

        model.reload(root);
	}

}
