package gui.frames;


import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.Image;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.StringTokenizer;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

import repositorio.sala.SalaNaoEncontradaException;

import negocio.base.Sala;

import fachada.Fachada;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class TelaConfigSala extends JDialog {

	private JPanel contentPanel = new JPanel();
	private JToggleButton[][] cadeiras;
	private JPanel panel;
	private Sala sala;
	private Fachada fachada;



	/**
	 * Create the dialog.
	 */
	public TelaConfigSala(Sala sala, Fachada fachada) {
		this.fachada = fachada;
		setTitle("Marcar cadeiras quebradas");
		setModal(true);
		this.sala = sala;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "name_256313778718141");
			{
				panel = new JPanel();
				scrollPane.setViewportView(panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
				gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);

			}
		}
		{
			int filas = sala.getNumFilas();
			int colunas = sala.getNumColunas();
			this.cadeiras = new JToggleButton[filas][colunas];
			for(int i = 0; i<filas; i++){
				for(int j =0; j<colunas; j++){
					GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
					gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
					gbc_btnNewButton.gridx = j;
					gbc_btnNewButton.gridy = i;

					cadeiras[i][j] = new JToggleButton(i+"x"+j);
					cadeiras[i][j].setSelectedIcon(new ImageIcon(loadImage("/imagem/cadeiraVendidapqn.png")));
					cadeiras[i][j].setIcon(new ImageIcon(loadImage("/imagem/cadeiraNvendidapqn.png")));
					cadeiras[i][j].setSelected(sala.getCadeiras()[i][j].getCadeiraQuebrada());
					cadeiras[i][j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							mudaSala(((JToggleButton)e.getSource()).getText());
						}
					});
					panel.add(cadeiras[i][j],gbc_btnNewButton);

				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnOkAction();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private Image loadImage(String imageName) {    
		Image retorno = null;
		try {  
			retorno = ImageIO.read(getClass().getResource(imageName));  
		} catch (IOException e) {  
			JOptionPane.showMessageDialog(this, "Erro interno.");
		}  

		return retorno;
	}  


	private void mudaSala(String text) {
		int confirma =  JOptionPane.showConfirmDialog(this, "Confirmar mudança no estado da cadeira?");
				
		StringTokenizer st = new StringTokenizer(text, "x");
		String str = st.nextToken();
		int i = Integer.parseInt(str);
		int j = Integer.parseInt(st.nextToken());
		
		switch(confirma){
		case 0:
			boolean estado =  sala.getCadeiras()[i][j].getCadeiraQuebrada();
			sala.getCadeiras()[i][j].setCadeiraQuebrada(!estado);
			break;
		case 1:
		case 2:
			this.cadeiras[i][j].setSelected(sala.getCadeiras()[i][j].getCadeiraQuebrada());
			break;		
		}
		
		try {
			this.fachada.getCadSala().atualizaSala(sala);
		} catch (SalaNaoEncontradaException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro interno!");
		}
		
	}


	private void btnOkAction() {
		
		this.dispose();
	}
}
