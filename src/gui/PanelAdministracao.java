package gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fachada.Fachada;

import java.awt.CardLayout;
import repositorio.*;

public class PanelAdministracao extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelAdministracao(Fachada fachada) {
		setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "name_427372473414371");
		
		PanelFilme panelFilme = new PanelFilme(fachada);
		tabbedPane.addTab("Filmes", null, panelFilme, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Salas", null, panel_1, null);
		
		PanelSessao panelSessao = new PanelSessao(fachada);
		tabbedPane.addTab("Sessões", null, panelSessao, null);
		
		JPanel panelRelatorio = new PanelRelatorio();
		tabbedPane.addTab("Relat\u00F3rios", null, panelRelatorio, null);
		
		
		
		

	}

}
