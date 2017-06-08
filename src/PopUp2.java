import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;

public class PopUp2 extends JFrame {

	private JPanel contentPane;
	private Compravel compravel;
	private JTable table;
	private JTable table_1;

	
	public PopUp2(Compravel compravel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 352);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		this.compravel = compravel;
		
		JLabel lbNome = new JLabel(compravel.getNome());
		lbNome.setBounds(273, 11, 136, 14);
		contentPane.add(lbNome);
		
		JLabel lb1 = new JLabel("Voc\u00EA caiu na propriedade");
		lb1.setBounds(127, 11, 136, 14);
		contentPane.add(lb1);
		
		JLabel lb3 = new JLabel("Deseja compr\u00E1-la?");
		lb3.setBounds(188, 39, 101, 14);
		contentPane.add(lb3);
		
		JButton btnSim = new JButton("SIM");
		btnSim.setBounds(135, 279, 89, 23);
		contentPane.add(btnSim);
		
		JButton btnNao = new JButton("N\u00C3O");
		btnNao.setBounds(234, 279, 89, 23);
		contentPane.add(btnNao);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(SystemColor.menu);
		editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		editorPane.setBounds(135, 67, 188, 188);
		contentPane.add(editorPane);
		
		JLabel label = new JLabel("New label");
		editorPane.add(label);
		label.setBounds(10, 20, 46, 14);
		
		
		
		
		
	}
}
