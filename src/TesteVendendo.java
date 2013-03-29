import java.awt.BorderLayout;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;




public class TesteVendendo extends JDialog {

	private JPanel contentPanel = new JPanel();
	private JToggleButton[] cadeiras;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TesteVendendo dialog = new TesteVendendo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TesteVendendo() {
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
			this.cadeiras = new JToggleButton[30];
			for(int i = 0; i<5; i++){
				for(int j =0; j<5; j++){
					GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
					gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
					gbc_btnNewButton.gridx = j;
					gbc_btnNewButton.gridy = i*2;

					cadeiras[i*5+j] = new JToggleButton("");
					cadeiras[i*5+j].setSelectedIcon(new ImageIcon(loadImage("imagem/cadeiraVendidapqn.png")));
					cadeiras[i*5+j].setIcon(new ImageIcon(loadImage("imagem/cadeiraNvendidapqn.png")));
					panel.add(cadeiras[i*5+j],gbc_btnNewButton);
					
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
