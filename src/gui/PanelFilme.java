package gui;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PanelFilme extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelFilme() {
		setLayout(new CardLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(this.getWidth()-70);
		add(splitPane, "name_424319822332266");
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(10, 112, 75, 23);
		panel.add(btnRemover);
		
		JButton btnInserir = new JButton("Inserir");
		btnInserir.setBounds(10, 44, 75, 23);
		panel.add(btnInserir);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(10, 78, 75, 23);
		panel.add(btnAtualizar);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		DefaultTableModel  modeloTabela = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nome", "In\u00EDcio", "Dura\u00E7\u00E3o", "Classifica\u00E7\u00E3o", "Categoria", "Descri\u00E7\u00E3o"
				}){
			
			public Class getColumnClass(int columnIndex) {
				return String.class;
			}
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return false;
			}};


			table.setModel(modeloTabela);
			table.setShowHorizontalLines(false);
			table.setShowVerticalLines(false);
			scrollPane.setViewportView(table);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false); 
			table.setAutoCreateRowSorter(true);

	}

}
