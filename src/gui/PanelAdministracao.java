package gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;

public class PanelAdministracao extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelAdministracao() {
		setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "name_427372473414371");
		
		PanelFilme panel = new PanelFilme();
		tabbedPane.addTab("Filmes", null, panel, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Salas", null, panel_2, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Sessões", null, panel_1, null);
		
		

	}

}
