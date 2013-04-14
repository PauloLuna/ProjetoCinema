package gui.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.SpinnerNumberModel;

import java.awt.Color;
import javax.swing.JScrollPane;

import negocio.base.Filme;



import fachada.Fachada;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TelaAtualizarFilme extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nomeTf;
	private JTextField categoriaTf;
	private JSpinner horasSp;
	private JSpinner minutoSp;
	private JLabel lblMin;
	private JSpinner segundosSp;
	private JComboBox classificacaoCb;
	private JEditorPane descricaoEp;
	private Fachada fachada;


	/**
	 * Create the dialog.
	 */
	public TelaAtualizarFilme(Filme filme, Fachada fachada) {
		setModal(true);
				
		this.fachada = fachada;
		setTitle("Atualizar filme");
		setBounds(100, 100, 361, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			nomeTf = new JTextField();
			nomeTf.setEditable(false);
			nomeTf.setBounds(106, 35, 216, 20);
			contentPanel.add(nomeTf);
			nomeTf.setColumns(10);
		}
		
		JLabel lblNome = new JLabel("Nome :");
		lblNome.setBounds(22, 38, 46, 14);
		contentPanel.add(lblNome);
		
		JLabel lblNewLabel = new JLabel("Dura\u00E7\u00E3o:");
		lblNewLabel.setBounds(22, 63, 74, 14);
		contentPanel.add(lblNewLabel);
		
		SpinnerNumberModel modelHs = new SpinnerNumberModel(0,0,23,1);
		
		horasSp = new JSpinner();
		horasSp.setModel(modelHs);
		horasSp.setBounds(106, 60, 46, 20);
		contentPanel.add(horasSp);
		
		JLabel lblHs = new JLabel("hs.");
		lblHs.setBounds(156, 66, 25, 14);
		contentPanel.add(lblHs);
		

		SpinnerNumberModel modelMin = new SpinnerNumberModel(0,0,59,1);
		
		minutoSp = new JSpinner();
		minutoSp.setModel(modelMin);
		minutoSp.setBounds(176, 60, 46, 20);
		contentPanel.add(minutoSp);
		
		lblMin = new JLabel("min.");
		lblMin.setBounds(226, 66, 25, 14);
		contentPanel.add(lblMin);
		
		SpinnerNumberModel modelSeg = new SpinnerNumberModel(0,0,59,1);
		segundosSp = new JSpinner();
		segundosSp.setModel(modelSeg);
		segundosSp.setBounds(251, 60, 46, 20);
		contentPanel.add(segundosSp);
		
		JLabel lblSeg = new JLabel("seg.");
		lblSeg.setBounds(301, 66, 34, 14);
		contentPanel.add(lblSeg);
		
		JLabel lblCategoria = new JLabel("Classifica\u00E7\u00E3o:");
		lblCategoria.setBounds(22, 88, 86, 14);
		contentPanel.add(lblCategoria);
		
		classificacaoCb = new JComboBox();
		classificacaoCb.setModel(new DefaultComboBoxModel(new String[] {"Livre", "+10", "+12", "+14", "+16", "+18"}));
		classificacaoCb.setBounds(106, 85, 65, 20);
		contentPanel.add(classificacaoCb);
		
		JLabel lblCategoria_1 = new JLabel("Categoria:");
		lblCategoria_1.setBounds(22, 113, 74, 14);
		contentPanel.add(lblCategoria_1);
		
		categoriaTf = new JTextField();
		categoriaTf.setBounds(106, 110, 116, 20);
		contentPanel.add(categoriaTf);
		categoriaTf.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(22, 138, 74, 14);
		contentPanel.add(lblDescrio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(106, 138, 216, 79);
		contentPanel.add(scrollPane);
		
		descricaoEp = new JEditorPane();
		scrollPane.setViewportView(descricaoEp);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Atualizar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						okButtonAction();
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
						cancelButtonAction();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}	
		
		nomeTf.setText(filme.getNome());
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(filme.getDuracao());
		horasSp.setValue(new Integer(gc.get(Calendar.HOUR_OF_DAY)));
		minutoSp.setValue(new Integer(gc.get(Calendar.MINUTE)));
		segundosSp.setValue(new Integer(gc.get(Calendar.SECOND)));
		classificacaoCb.setSelectedItem(filme.getClassificacao());
		categoriaTf.setText(filme.getCategoria());
		descricaoEp.setText(filme.getDescricao());
		
	}
	
	private void okButtonAction(){
		try {
			fachada.getCadFilme().atualizarFilme(nomeTf.getText(), ((Integer)horasSp.getValue()).intValue(),
					((Integer)minutoSp.getValue()).intValue(), ((Integer)segundosSp.getValue()).intValue(),
					(String)classificacaoCb.getItemAt(classificacaoCb.getSelectedIndex()),
					categoriaTf.getText(), descricaoEp.getText());
						
			JOptionPane.showMessageDialog(this, "Filme atualizado com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		this.dispose();
	}
	
	private void cancelButtonAction(){
		this.dispose();
	}
}
