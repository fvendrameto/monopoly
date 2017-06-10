import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PopUp_Compra_Propriedade extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private boolean comprado;//Retorna "true" caso o cliente clique em SIM, "false" caso contrário.
	/**
	 * Main para testar somente. Pode-se retirar depois.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int[]tabela={10, 20, 30, 40, 50, 60};
				Propriedade p = new Propriedade("Av. Brigadeiro Faria Lima", 1000, 500, tabela, 250, 2);
				try {
					PopUp_Compra_Propriedade frame = new PopUp_Compra_Propriedade(p);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PopUp_Compra_Propriedade(Propriedade propriedade) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 352);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		int[] tabela = propriedade.getTabela_Precos();
		
		JLabel lbNome2 = new JLabel(propriedade.getNome().toUpperCase());
		lbNome2.setHorizontalTextPosition(SwingConstants.LEADING);
		lbNome2.setHorizontalAlignment(SwingConstants.CENTER);
		lbNome2.setBounds(140, 67, 172, 45);
		
		contentPane.add(lbNome2);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		editorPane_1.setBounds(147, 67, 165, 45);
		editorPane_1.setBackground(getCor(propriedade.getCor()));
		contentPane.add(editorPane_1);
		
		JLabel lblNewLabel = new JLabel(""+tabela[0]);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setName("lbValorAluguel");
		lblNewLabel.setBounds(261, 114, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblAluguel = new JLabel("ALUGUEL");
		lblAluguel.setBounds(205, 114, 58, 14);
		contentPane.add(lblAluguel);
		
		JLabel lbNome = new JLabel(propriedade.getNome().toUpperCase());
		lbNome.setBounds(253, 8, 225, 21);
		contentPane.add(lbNome);
		
		JLabel lb1 = new JLabel("Voc\u00EA caiu na propriedade");
		lb1.setBounds(103, 11, 160, 14);
		contentPane.add(lb1);
		
		JLabel lb3 = new JLabel("Deseja compr\u00E1-la?");
		lb3.setBounds(173, 36, 119, 14);
		contentPane.add(lb3);
		
		JButton btnSim = new JButton("SIM");
		btnSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comprado = true;
			}
		});
		btnSim.setBounds(135, 279, 89, 23);
		contentPane.add(btnSim);
		
		JButton btnNao = new JButton("N\u00C3O");
		btnNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comprado = false;
			}
		});
		btnNao.setBounds(234, 279, 89, 23);
		contentPane.add(btnNao);
		
		btnSim.setActionCommand("SIM");
		btnNao.setActionCommand("NAO");
		
		JLabel lblNewLabel_1 = new JLabel("C/ 1 CASA");
		lblNewLabel_1.setName("lbCasa1");
		lblNewLabel_1.setBounds(148, 127, 76, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblCCasas = new JLabel("C/ 2 CASAS");
		lblCCasas.setName("lbCasa2");
		lblCCasas.setBounds(148, 139, 76, 14);
		contentPane.add(lblCCasas);
		
		JLabel lblCCasas_1 = new JLabel("C/ 3 CASAS");
		lblCCasas_1.setName("lbCasa3");
		lblCCasas_1.setBounds(148, 152, 76, 14);
		contentPane.add(lblCCasas_1);
		
		JLabel lblCCasas_2 = new JLabel("C/ 4 CASAS");
		lblCCasas_2.setName("lbCasa4");
		lblCCasas_2.setBounds(148, 164, 76, 14);
		contentPane.add(lblCCasas_2);
		
		JLabel lblCHotel = new JLabel("C/ HOTEL");
		lblCHotel.setName("lbHotel");
		lblCHotel.setBounds(148, 177, 76, 14);
		contentPane.add(lblCHotel);
		
		JLabel lblHipoteca = new JLabel("HIPOTECA");
		lblHipoteca.setBounds(148, 227, 76, 14);
		contentPane.add(lblHipoteca);
		
		JLabel lblCadaCasa = new JLabel("CADA CASA");
		lblCadaCasa.setBounds(148, 202, 76, 14);
		contentPane.add(lblCadaCasa);
		
		JLabel lblCadaHotel = new JLabel("CADA HOTEL");
		lblCadaHotel.setBounds(148, 214, 76, 14);
		contentPane.add(lblCadaHotel);
		
		JLabel lbValor1Casa = new JLabel(""+tabela[1]);
		lbValor1Casa.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValor1Casa.setBounds(261, 127, 46, 14);
		contentPane.add(lbValor1Casa);
		
		JLabel lbValor2Casas = new JLabel(""+tabela[2]);
		lbValor2Casas.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValor2Casas.setName("lb1Casa");
		lbValor2Casas.setBounds(261, 139, 46, 14);
		contentPane.add(lbValor2Casas);
		
		JLabel lbValor3Casas = new JLabel(""+tabela[3]);
		lbValor3Casas.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValor3Casas.setBounds(261, 152, 46, 14);
		contentPane.add(lbValor3Casas);
		
		JLabel lbValor4Casas = new JLabel(""+tabela[4]);
		lbValor4Casas.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValor4Casas.setBounds(261, 164, 46, 14);
		contentPane.add(lbValor4Casas);
		
		JLabel lbValorHotel = new JLabel(""+tabela[5]);
		lbValorHotel.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorHotel.setBounds(261, 177, 46, 14);
		contentPane.add(lbValorHotel);
		
		JLabel lbValorCadaCasa = new JLabel(""+propriedade.getPrecoCasa());
		lbValorCadaCasa.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorCadaCasa.setBounds(261, 202, 46, 14);
		contentPane.add(lbValorCadaCasa);
		
		JLabel lbValorCadaHotel = new JLabel(""+propriedade.getPrecoCasa());
		lbValorCadaHotel.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorCadaHotel.setBounds(261, 214, 46, 14);
		contentPane.add(lbValorCadaHotel);
		
		JLabel lbValorHipoteca = new JLabel(""+propriedade.getValorHipoteca());
		lbValorHipoteca.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorHipoteca.setBounds(261, 227, 46, 14);
		contentPane.add(lbValorHipoteca);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBackground(SystemColor.menu);
		editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		editorPane.setBounds(135, 61, 188, 194);
		
		
		contentPane.add(editorPane);
	}
	
	private Color getCor (int cor){
		switch (cor){
			case 1:
				return new Color(245, 102, 255);//Lilas
			case 2:
				return new Color(130,207,255);//Azul claro
			case 3:
				return new Color(170,59,255);//Roxo
			case 4:
				return new Color(255,160,59);//Laranja
			case 5:
				return new Color(235,35,39);//Vermelho
			case 6:
				return new Color(255,245,51);//Amarelo
			case 7:
				return new Color(60,191,48);//Verde
			case 8:
				return new Color(81,106,232);//Azul escuro
		}
		return null;
		
	}
}
