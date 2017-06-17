import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * Responsavel pelo log de jogo exibido na GUI
 */
public class LogGUI extends JPanel{
	private JTextArea area_texto;
	private JLabel titulo;

	
	public LogGUI(){
		area_texto = new JTextArea();
		titulo = new JLabel();
	
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.gridy = 0;
		gbc.weighty = 0.2;
		titulo.setText("Log de jogo");
		add(titulo,gbc);

		area_texto.setBackground(Color.WHITE);
		area_texto.setEditable(false);
		JScrollPane scroll = new JScrollPane(area_texto);
		
		gbc.gridy = 1;
		gbc.weighty = 0.8;
		gbc.fill = GridBagConstraints.BOTH;
		add(scroll,gbc);
	}

	/**
	 * @param str String que será inserido no log de jogo
	 */
	public void addTexto(String str){
		area_texto.append(str);
	}
	
}
