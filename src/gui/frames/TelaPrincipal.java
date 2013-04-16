package gui.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import fachada.Fachada;
import fachada.FalhaNaConfiguracaoException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.Toolkit;


public class TelaPrincipal extends JFrame {

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
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/imagem/pipoca.png")));
		setTitle("MeuCine");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 170, 128);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			try {
				try {
					this.fachada = new Fachada();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(this, "Erro interno");
				}
			} catch (FalhaNaConfiguracaoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e1.getMessage());
		}

		JButton btnTeste = new JButton("Administra\u00E7\u00E3o");
		btnTeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaAdministracao administra = new TelaAdministracao(fachada);
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
