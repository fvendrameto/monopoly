import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PopUp_Compra_companhia extends JFrame {

	private JPanel contentPane;
	/**
	 * Main para testar somente. Pode-se retirar depois.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Companhia c = new Companhia ("Companhia de Taxi Aéreo", 500, 250, 50);
				try {
					PopUp_Compra_companhia frame = new PopUp_Compra_companhia(c);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public PopUp_Compra_companhia(Companhia companhia) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 352);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lbNome2 = new JLabel(companhia.getNome().toUpperCase());
		lbNome2.setHorizontalAlignment(SwingConstants.CENTER);
		lbNome2.setHorizontalTextPosition(SwingConstants.LEADING);
		lbNome2.setBounds(135, 79, 188, 50);
		
		contentPane.add(lbNome2);
		
		JLabel lbNome = new JLabel(companhia.getNome().toUpperCase());
		lbNome.setBounds(249, 8, 229, 21);
		contentPane.add(lbNome);
		
		JLabel lb1 = new JLabel("Voc\u00EA caiu na companhia");
		lb1.setBounds(103, 11, 160, 14);
		contentPane.add(lb1);
		
		JLabel lb3 = new JLabel("Deseja compr\u00E1-la?");
		lb3.setBounds(173, 36, 119, 14);
		contentPane.add(lb3);
		
		JButton btnSim = new JButton("SIM");
		btnSim.setBounds(135, 279, 89, 23);
		contentPane.add(btnSim);
		
		JButton btnNao = new JButton("N\u00C3O");
		btnNao.setBounds(234, 279, 89, 23);
		contentPane.add(btnNao);
		
		JLabel lblTaxaACobrar = new JLabel("Taxa a cobrar:");
		lblTaxaACobrar.setBounds(194, 140, 101, 14);
		contentPane.add(lblTaxaACobrar);
		
		JLabel lblNewLabel = new JLabel("Pontos dos dados");
		lblNewLabel.setBounds(162, 165, 130, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblMultiplicadosPor = new JLabel("multiplicados por");
		lblMultiplicadosPor.setBounds(162, 179, 101, 14);
		contentPane.add(lblMultiplicadosPor);
		
		JLabel lbValorTaxa = new JLabel(""+companhia.getAluguel());
		lbValorTaxa.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorTaxa.setBounds(253, 179, 55, 14);
		contentPane.add(lbValorTaxa);
		
		JLabel lblH = new JLabel("Hipoteca");
		lblH.setBounds(162, 213, 74, 14);
		contentPane.add(lblH);
		
		JLabel lbValorHipoteca = new JLabel(""+companhia.getValorHipoteca());
		lbValorHipoteca.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorHipoteca.setHorizontalTextPosition(SwingConstants.LEADING);
		lbValorHipoteca.setBounds(253, 213, 56, 14);
		contentPane.add(lbValorHipoteca);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(SystemColor.menu);
		editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		editorPane.setBounds(135, 61, 188, 194);
		
		
		contentPane.add(editorPane);
		
	}
	

}
