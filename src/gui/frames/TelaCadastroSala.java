package gui.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import repositorio.sala.SalaConflitanteException;

import fachada.Fachada;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroSala extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	private JSpinner fileirasSp;
	private JSpinner cadPfileiraSp;
	private Fachada fachada;


	/**
	 * Create the dialog.
	 */
	public TelaCadastroSala(Fachada fachada) {
		setModal(true);
		this.fachada = fachada;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Cadastrar sala");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(202, 48, 132, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JLabel lblCdigoDaSala = new JLabel("C\u00F3digo da sala:");
			lblCdigoDaSala.setBounds(10, 54, 95, 14);
			contentPanel.add(lblCdigoDaSala);
		}
		{
			JLabel lblNmeroDeFileiras = new JLabel("N\u00FAmero de fileiras:");
			lblNmeroDeFileiras.setBounds(10, 79, 117, 14);
			contentPanel.add(lblNmeroDeFileiras);
		}
		{
			JLabel lblNmeroDeCadeiras = new JLabel("N\u00FAmero de cadeiras por fileira:");
			lblNmeroDeCadeiras.setBounds(10, 104, 194, 14);
			contentPanel.add(lblNmeroDeCadeiras);
		}
		
		SpinnerModel filModel = new SpinnerNumberModel(0, 0, 99, 1);
		this.fileirasSp = new JSpinner(filModel);
		this.fileirasSp.setBounds(202, 73, 40, 20);
		contentPanel.add(this.fileirasSp);
		
		SpinnerModel cadModel = new SpinnerNumberModel(0, 0, 99, 1);
		this.cadPfileiraSp = new JSpinner(cadModel);
		this.cadPfileiraSp.setBounds(202, 98, 40, 20);
		contentPanel.add(this.cadPfileiraSp);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Cadastrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						botaoCadastrarAction();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						btnCancelAction();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void botaoCadastrarAction(){
		String codigo = this.textField.getText();
		int numFilas = ((Integer)this.fileirasSp.getValue()).intValue();
		int numColunas = ((Integer)this.cadPfileiraSp.getValue()).intValue();
		
		try {
			fachada.getCadSala().criarSala(codigo, numFilas, numColunas);
			JOptionPane.showMessageDialog(this, "Sala cadastrada com sucesso!");
			this.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		this.dispose();
	}
	
	private void btnCancelAction() {
		this.dispose();		
	}
}
