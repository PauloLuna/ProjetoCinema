package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JButton;

import fachada.Fachada;
import gui.frames.TelaVendas;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class TesteGUI extends JFrame {

	private JPanel contentPane;
	private Fachada fachada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				} 
				try {
					TesteGUI frame = new TesteGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TesteGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 170, 128);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			this.fachada = new Fachada();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e1.getMessage());
		}

		JButton btnTeste = new JButton("Administra\u00E7\u00E3o");
		btnTeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestePanels administra = new TestePanels(fachada);
				administra.setVisible(true);
			}
		});
		btnTeste.setBounds(10, 11, 134, 23);
		contentPane.add(btnTeste);

		JButton btnTeste_1 = new JButton("Vendas");
		btnTeste_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaVendas vendas = new TelaVendas(fachada);
				vendas.setVisible(true);
			}
		});
		btnTeste_1.setBounds(10, 45, 134, 23);
		contentPane.add(btnTeste_1);


	}

}
