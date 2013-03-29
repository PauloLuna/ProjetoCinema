package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TesteGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
		
		JButton btnTeste = new JButton("Administra\u00E7\u00E3o");
		btnTeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadastroFilme tela = new TelaCadastroFilme();
				tela.setVisible(true);
			}
		});
		btnTeste.setBounds(10, 11, 134, 23);
		contentPane.add(btnTeste);
		
		JButton btnTeste_1 = new JButton("Vendas");
		btnTeste_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vendas vendas = new Vendas();
				vendas.setVisible(true);
			}
		});
		btnTeste_1.setBounds(10, 45, 134, 23);
		contentPane.add(btnTeste_1);
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
	}
}
