import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import negocio.Sessao;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;


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
				panel.setLayout(new GridLayout(4, 10, 3, 3));
			}
		}
		{
			this.cadeiras = new JToggleButton[20];
			for(int i = 0; i<20; i++){
				cadeiras[i] = new JToggleButton("");
				cadeiras[i].setSelectedIcon(new ImageIcon("C:\\Users\\Paulo\\Desktop\\pastaEclipse\\projetoCinema\\src\\cadeiraVendidapqn.png"));
				cadeiras[i].setIcon(new ImageIcon("C:\\Users\\Paulo\\Desktop\\pastaEclipse\\projetoCinema\\src\\cadeiraNvendidapqn.png"));
				panel.add(cadeiras[i]);
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

}
