package gui.frames;


import imagem.Imagens;

import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.Image;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

import negocio.base.Sala;

import fachada.Fachada;




public class TelaConfigSala extends JDialog {

	private JPanel contentPanel = new JPanel();
	private JToggleButton[][] cadeiras;
	private JPanel panel;
	private Sala sala;



	/**
	 * Create the dialog.
	 */
	public TelaConfigSala(Sala sala) {
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
					gbc_btnNewButton.gridy = i*2;

					cadeiras[i][j] = new JToggleButton("");
					cadeiras[i][j].setSelectedIcon(new ImageIcon(loadImage("/imagem/cadeiraVendidapqn.png")));
					cadeiras[i][j].setIcon(new ImageIcon(loadImage("/imagem/cadeiraNvendidapqn.png")));
					cadeiras[i][j].setSelected(sala.getCadeiras()[i][j].getCadeiraQuebrada());
					panel.add(cadeiras[i][j],gbc_btnNewButton);
					
					JLabel lblNewLabel = new JLabel("Cadeira: "+(i*5+j));
					
					
					GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
					gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
					gbc_lblNewLabel.gridx = j;
					gbc_lblNewLabel.gridy = i*2+1;
					panel.add(lblNewLabel, gbc_lblNewLabel);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private Image loadImage(String imageName) {    
		   try {  
		      return ImageIO.read(getClass().getResource(imageName));  
		   } catch (IOException e) {  
		      throw new RuntimeException("Unable to load image " + imageName, e);  
		   }  
		}  

}
