package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fachada.Fachada;

import repositorio.RepositorioFilme;
import repositorio.RepositorioFilmeExel;
import java.awt.CardLayout;

public class TestePanels extends JFrame {

	private JPanel  contentPane;
	private RepositorioFilme repositorioFilme;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public TestePanels(Fachada fachada) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 778, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		PanelAdministracao panel = new PanelAdministracao(fachada);
		contentPane.add(panel, "name_596650876178447");
	}

}
