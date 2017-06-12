import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class BensGUI extends JPanel{	
	private JTable compraveis;
	private JLabel titulo;
	
	public void setDinheiro(int valor){
		this.titulo.setText("Seus bens(R$" + valor + ")");
	}
	
	public void addCompravel(Compravel compravel){
		DefaultTableModel model = (DefaultTableModel) compraveis.getModel();
		Object[] nova_linha = new Object[4];
		
		nova_linha[0] = compravel.getNome();
		nova_linha[1] = compravel.getValorHipoteca();
		nova_linha[2] = "30";//compravel.getAluguel();
		if(compravel.propriedade()){
			int casas = ((Propriedade) compravel).getNumeroCasas();
			nova_linha[3] = "" + casas;
		}else{
			nova_linha[3] = "-";
		}
		
		model.addRow(nova_linha);
	}
	
	public void removeCompravel(String nome_compravel){
		DefaultTableModel model = (DefaultTableModel) compraveis.getModel();
		for(int i=0;i<model.getRowCount();i++){
			if(model.getValueAt(i,0).equals(nome_compravel)){
				model.removeRow(i);
				break;
			}
		}
	}
	
	public void alteraCompravel(String nome_compravel, String novo_valor,int campo){
		DefaultTableModel model = (DefaultTableModel) compraveis.getModel();
		for(int i=0;i<model.getRowCount();i++){
			if(model.getValueAt(i,0).equals(nome_compravel)){
				model.setValueAt(novo_valor, i, campo);
				break;
			}
		}
	}
	
	public BensGUI(){
		String[] colunas = {"Nome","Hipoteca","Aluguel","Nº Casas"};
		DefaultTableModel model = new DefaultTableModel(0,colunas.length){
			 @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		model.setColumnIdentifiers(colunas);
		
		titulo = new JLabel();
	
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.weightx = 1;
		
		gbc.gridy = 0;
		gbc.weighty = 0.2;
		titulo.setText("Seus bens($1500)");
		add(titulo,gbc);
		
		compraveis = new JTable(model);
		//compraveis.getColumnModel().getColumn(0).setPreferredWidth(26);
		//compraveis.getColumnModel().getColumn(1).setPreferredWidth(26);
		//compraveis.getColumnModel().getColumn(2).setPreferredWidth(26);
		compraveis.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(compraveis);

		gbc.gridy = 1;
		gbc.weighty = 0.8;
		gbc.fill = GridBagConstraints.BOTH;
		add(scroll,gbc);
	}
		
	
}