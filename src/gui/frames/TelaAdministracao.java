package gui.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fachada.Fachada;
import gui.panels.PanelAdministracao;

import repositorio.filme.RepositorioFilme;
import repositorio.filme.RepositorioFilmeExcel;

import java.awt.CardLayout;

public class TelaAdministracao extends JFrame {

	private JPanel  contentPane;
	private RepositorioFilme repositorioFilme;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public TelaAdministracao(Fachada fachada) {
		setTitle("Administra\u00E7\u00E3o");
		
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
