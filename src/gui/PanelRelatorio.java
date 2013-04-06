package gui;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class PanelRelatorio extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelRelatorio() {
		setLayout(new CardLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(620);
		add(splitPane, "name_448841281678032");
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(null);
		
		JButton btnLer = new JButton("Ler");
		btnLer.setBounds(10, 51, 89, 23);
		panel_1.add(btnLer);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(10, 85, 89, 23);
		panel_1.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				 "Sala", "Data de gera\u00E7\u00E3o"
			}
		) {
			
			public Class getColumnClass(int columnIndex) {
				return String.class;
			}
		});
		scrollPane.setViewportView(table);

	}
}
