package gui.frames;

import iterator.*;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import org.apache.poi.ss.util.DateFormatConverter;

import repositorio.filme.FilmeNaoEncontradoException;

import negocio.base.*;

import fachada.Fachada;

public class TelaCadastroSessao extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JRadioButton rdbtnSessaoFilmeFechada, rdbtnSessaoParticularAberta, rdbtnSessaoParticularFechada, rdbtnSessaoFilme;
	private JComboBox filme, sala, faixaHorario;
	private JSpinner duracaoHs, duracaoMin, duracaoSeg, ajusteHs, ajusteMin, ajusteSeg;
	private Fachada fachada;


	/**
	 * Create the dialog.
	 */
	public TelaCadastroSessao(Fachada fachada) {
		setModal(true);
		this.fachada = fachada;
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

		filme = new JComboBox();
		filme.setBounds(116, 65, 172, 20);
		contentPanel.add(filme);

		JLabel lblSala = new JLabel("Sala:");
		lblSala.setBounds(345, 40, 46, 14);
		contentPanel.add(lblSala);

		sala = new JComboBox();
		sala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mudaSala();
			}
		});
		sala.setBounds(421, 34, 122, 20);
		contentPanel.add(sala);

		JLabel lblDurao = new JLabel("Dura\u00E7\u00E3o:");
		lblDurao.setBounds(32, 102, 57, 14);
		contentPanel.add(lblDurao);

		SpinnerNumberModel modelDHs = new SpinnerNumberModel(0,0,23,1);
		duracaoHs = new JSpinner(modelDHs);
		duracaoHs.setBounds(116, 96, 36, 20);
		contentPanel.add(duracaoHs);

		SpinnerNumberModel modelDMin = new SpinnerNumberModel(0,0,59,1);
		duracaoMin = new JSpinner(modelDMin);
		duracaoMin.setBounds(181, 96, 36, 20);
		contentPanel.add(duracaoMin);

		SpinnerNumberModel modelDSeg = new SpinnerNumberModel(0,0,59,1);
		duracaoSeg = new JSpinner(modelDSeg);
		duracaoSeg.setBounds(252, 96, 36, 20);
		contentPanel.add(duracaoSeg);

		JLabel lblHs = new JLabel("hs.");
		lblHs.setBounds(162, 102, 46, 14);
		contentPanel.add(lblHs);

		JLabel lblMin = new JLabel("min.");
		lblMin.setBounds(227, 102, 46, 14);
		contentPanel.add(lblMin);

		JLabel lblSeg = new JLabel("seg.");
		lblSeg.setBounds(299, 102, 46, 14);
		contentPanel.add(lblSeg);

		faixaHorario = new JComboBox();
		faixaHorario.setBounds(227, 140, 316, 20);
		contentPanel.add(faixaHorario);

		JLabel lblFaixasDeHorrio = new JLabel("Faixas de hor\u00E1rio de in\u00EDcio:");
		lblFaixasDeHorrio.setBounds(32, 143, 176, 14);
		contentPanel.add(lblFaixasDeHorrio);

		JLabel lblAjusteDoHorrio = new JLabel("Ajuste do hor\u00E1rio:");
		lblAjusteDoHorrio.setBounds(32, 174, 106, 14);
		contentPanel.add(lblAjusteDoHorrio);

		SpinnerNumberModel modelHs = new SpinnerNumberModel(0,0,23,1);
		ajusteHs = new JSpinner(modelHs);
		ajusteHs.setBounds(227, 171, 36, 20);
		contentPanel.add(ajusteHs);

		JLabel label = new JLabel("hs.");
		label.setBounds(273, 177, 46, 14);
		contentPanel.add(label);

		SpinnerNumberModel modelMin = new SpinnerNumberModel(0,0,59,1);
		ajusteMin = new JSpinner(modelMin);
		ajusteMin.setBounds(292, 171, 36, 20);
		contentPanel.add(ajusteMin);

		JLabel label_1 = new JLabel("min.");
		label_1.setBounds(338, 177, 46, 14);
		contentPanel.add(label_1);

		SpinnerNumberModel modelSeg = new SpinnerNumberModel(0,0,59,1);
		ajusteSeg = new JSpinner(modelSeg);
		ajusteSeg.setBounds(363, 171, 36, 20);
		contentPanel.add(ajusteSeg);

		JLabel label_2 = new JLabel("seg.");
		label_2.setBounds(410, 177, 46, 14);
		contentPanel.add(label_2);

		CheckBoxHandler handler = new CheckBoxHandler();
		ButtonGroup grupo = new ButtonGroup();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						criarSessaoAction();
					}
				});
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


			{
				rdbtnSessaoFilme = new JRadioButton("Sess\u00E3o filme");
				rdbtnSessaoFilme.addItemListener(handler);
				grupo.add(rdbtnSessaoFilme);
				toolBar.add(rdbtnSessaoFilme);
			}
			{
				rdbtnSessaoFilmeFechada = new JRadioButton("Sess\u00E3o filme fechada");
				rdbtnSessaoFilmeFechada.addItemListener(handler);
				grupo.add(rdbtnSessaoFilmeFechada);
				toolBar.add(rdbtnSessaoFilmeFechada);
			}
			{
				rdbtnSessaoParticularAberta = new JRadioButton("Sess\u00E3o particular aberta");
				rdbtnSessaoParticularAberta.addItemListener(handler);
				grupo.add(rdbtnSessaoParticularAberta);
				toolBar.add(rdbtnSessaoParticularAberta);
			}
			{
				rdbtnSessaoParticularFechada = new JRadioButton("Sess\u00E3o particular fechada");
				rdbtnSessaoParticularFechada.addItemListener(handler);
				grupo.add(rdbtnSessaoParticularFechada);
				toolBar.add(rdbtnSessaoParticularFechada);
			}
		}

		preencheListas();
		rdbtnSessaoFilme.setSelected(true);
		textField.setEnabled(false);
		filme.setEnabled(true);
		duracaoHs.setEnabled(false);
		duracaoMin.setEnabled(false);
		duracaoSeg.setEnabled(false);
	}


	private void preencheListas(){
		IteratorFilme itrFilme = fachada.getCadFilme().getIteratorFilme();
		while(itrFilme.hasNext()){
			filme.addItem(itrFilme.next().getNome());
		}
		IteratorSala itrSala = fachada.getCadSala().getIteratorSala();
		while(itrSala.hasNext()){
			sala.addItem(itrSala.next().getCodigo());
		}
	}


	private void mudaSala() {
		this.faixaHorario.removeAllItems();
		IteratorSessao itr = fachada.getCadSessao().getIteratorSessao();

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		while(itr.hasNext()){
			Sessao sessao = itr.next();
			System.out.println(sessao.getSala().getCodigo()+"="+sala.getSelectedItem());
			if(sessao.getSala().getCodigo().equals((String)sala.getSelectedItem())){
				this.faixaHorario.addItem(df.format(sessao.getHoraInicio())+" - "+df.format(sessao.getHoraFim()));
			}
		}
	}



	private void criarSessaoAction() {

		GregorianCalendar gc = new GregorianCalendar();

		gc.add(Calendar.HOUR_OF_DAY, ((Integer)duracaoHs.getValue()).intValue());
		gc.add(Calendar.MINUTE, ((Integer)duracaoMin.getValue()).intValue());
		gc.add(Calendar.SECOND, ((Integer)duracaoSeg.getValue()).intValue());
		Date duracao = gc.getTime();

		GregorianCalendar gc2 = new GregorianCalendar();

		gc2.add(Calendar.HOUR_OF_DAY, ((Integer)ajusteHs.getValue()).intValue());
		gc2.add(Calendar.MINUTE, ((Integer)ajusteMin.getValue()).intValue());
		gc2.add(Calendar.SECOND, ((Integer)ajusteSeg.getValue()).intValue());
		Date inicio = gc.getTime();


		if(rdbtnSessaoFilme.isSelected()){
			try {
				Filme filme = fachada.getCadFilme().buscarFilme((String)this.filme.getSelectedItem());
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem());
				Sessao sessao = new SessaoPublicaFilme("", filme, sala, inicio);
				fachada.getCadSessao().inserirSessao(sessao);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		} else if(rdbtnSessaoFilmeFechada.isSelected()){

			try {
				Filme filme = fachada.getCadFilme().buscarFilme((String)this.filme.getSelectedItem());
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem());
				Sessao sessao = new SessaoFechadaFilme("", filme, sala, inicio);
				fachada.getCadSessao().inserirSessao(sessao);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}

		}else if(rdbtnSessaoParticularAberta.isSelected()){

			try {
				String titulo = this.textField.getText();
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem());
				Sessao sessao = new SessaoPublicaPropria("", sala, inicio, duracao,titulo);
				fachada.getCadSessao().inserirSessao(sessao);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}

		}else if(rdbtnSessaoParticularFechada.isSelected()){

			try {
				String titulo = this.textField.getText();
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem());
				Sessao sessao = new SessaoFechadaPropria("", sala, inicio, duracao,titulo);
				fachada.getCadSessao().inserirSessao(sessao);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}

		}
	}


	private class CheckBoxHandler implements ItemListener{


		public void itemStateChanged(ItemEvent event){


			if(event.getSource() == rdbtnSessaoFilme || event.getSource() == rdbtnSessaoFilmeFechada){
				textField.setEnabled(false);
				filme.setEnabled(true);
				duracaoHs.setEnabled(false);
				duracaoMin.setEnabled(false);
				duracaoSeg.setEnabled(false);

			} else if(event.getSource() == rdbtnSessaoParticularAberta|| event.getSource() == rdbtnSessaoParticularFechada){
				textField.setEnabled(true);
				filme.setEnabled(false);
				duracaoHs.setEnabled(true);
				duracaoMin.setEnabled(true);
				duracaoSeg.setEnabled(true);
			}

		}
	}
}
