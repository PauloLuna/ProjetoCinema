package gui.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;

public class TelaCadastroSessao extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Create the dialog.
	 */
	public TelaCadastroSessao() {
		setBounds(100, 100, 678, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(116, 34, 172, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(32, 40, 46, 14);
		contentPanel.add(lblTitulo);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(116, 65, 172, 20);
		contentPanel.add(comboBox);
		
		JLabel lblSala = new JLabel("Sala:");
		lblSala.setBounds(345, 40, 46, 14);
		contentPanel.add(lblSala);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(421, 34, 122, 20);
		contentPanel.add(comboBox_1);
		
		JLabel lblDurao = new JLabel("Dura\u00E7\u00E3o:");
		lblDurao.setBounds(32, 102, 57, 14);
		contentPanel.add(lblDurao);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(116, 96, 36, 20);
		contentPanel.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(181, 96, 36, 20);
		contentPanel.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(252, 96, 36, 20);
		contentPanel.add(spinner_2);
		
		JLabel lblHs = new JLabel("hs.");
		lblHs.setBounds(162, 102, 46, 14);
		contentPanel.add(lblHs);
		
		JLabel lblMin = new JLabel("min.");
		lblMin.setBounds(227, 102, 46, 14);
		contentPanel.add(lblMin);
		
		JLabel lblSeg = new JLabel("seg.");
		lblSeg.setBounds(299, 102, 46, 14);
		contentPanel.add(lblSeg);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(227, 140, 316, 20);
		contentPanel.add(comboBox_2);
		
		JLabel lblFaixasDeHorrio = new JLabel("Faixas de hor\u00E1rio de in\u00EDcio:");
		lblFaixasDeHorrio.setBounds(32, 143, 176, 14);
		contentPanel.add(lblFaixasDeHorrio);
		
		JLabel lblAjusteDoHorrio = new JLabel("Ajuste do hor\u00E1rio:");
		lblAjusteDoHorrio.setBounds(32, 174, 106, 14);
		contentPanel.add(lblAjusteDoHorrio);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(227, 171, 36, 20);
		contentPanel.add(spinner_3);
		
		JLabel label = new JLabel("hs.");
		label.setBounds(273, 177, 46, 14);
		contentPanel.add(label);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setBounds(292, 171, 36, 20);
		contentPanel.add(spinner_4);
		
		JLabel label_1 = new JLabel("min.");
		label_1.setBounds(338, 177, 46, 14);
		contentPanel.add(label_1);
		
		JSpinner spinner_5 = new JSpinner();
		spinner_5.setBounds(363, 171, 36, 20);
		contentPanel.add(spinner_5);
		
		JLabel label_2 = new JLabel("seg.");
		label_2.setBounds(410, 177, 46, 14);
		contentPanel.add(label_2);
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
		{
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			toolBar.setRollover(true);
			getContentPane().add(toolBar, BorderLayout.NORTH);
			{
				JLabel lblTipo = new JLabel("Tipo:  ");
				toolBar.add(lblTipo);
			}
			ButtonGroup agrupar = new ButtonGroup();
			
//			CheckBoxHandler handler = new CheckBoxHandler();
			{
				final JRadioButton rdbtnSessoComum = new JRadioButton("Sess\u00E3o filme");
				rdbtnSessoComum.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if(rdbtnSessoComum.isSelected())
						sessaoComum();
					}
				});
				toolBar.add(rdbtnSessoComum);
				agrupar.add(rdbtnSessoComum);
			}
			{
				JRadioButton rdbtnSessoEscola = new JRadioButton("Sess\u00E3o filme fechada");
				toolBar.add(rdbtnSessoEscola);
				agrupar.add(rdbtnSessoEscola);
			}
			{
				JRadioButton rdbtnSessoEspecial = new JRadioButton("Sess\u00E3o particular aberta");
				toolBar.add(rdbtnSessoEspecial);
				agrupar.add(rdbtnSessoEspecial);
			}
			{
				JRadioButton rdbtnSessoExclusiva = new JRadioButton("Sess\u00E3o particular fechada");
				toolBar.add(rdbtnSessoExclusiva);
				agrupar.add(rdbtnSessoExclusiva);
			}
		}
	}
	
	private void sessaoComum() {
		
		System.out.println("chegouaqui");
		
	}
}
