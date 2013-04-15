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
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.RowSorter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import org.apache.poi.ss.util.DateFormatConverter;

import repositorio.filme.FilmeNaoEncontradoException;
import repositorio.sala.SalaNaoEncontradaException;
import repositorio.sessao.TipoDeObjetoNaoSuportado;

import negocio.base.*;

import fachada.Fachada;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelaCadastroSessao extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JRadioButton rdbtnSessaoFilmeFechada, rdbtnSessaoParticularAberta, rdbtnSessaoParticularFechada, rdbtnSessaoFilme;
	private JComboBox filme, sala;
	private JSpinner duracaoHs, duracaoMin, duracaoSeg, ajusteHs, ajusteMin, ajusteSeg;
	private Fachada fachada;
	private JTable table;
	private DefaultTableModel modelo;


	/**
	 * Create the dialog.
	 */
	public TelaCadastroSessao(Fachada fachada) {
		setTitle("Cadastrar sess\u00E3o");
		setModal(true);
		this.fachada = fachada;
		setBounds(100, 100, 678, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(126, 34, 172, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(32, 37, 46, 14);
		contentPanel.add(lblTitulo);

		filme = new JComboBox();
		filme.setBounds(126, 65, 172, 20);
		contentPanel.add(filme);

		JLabel lblSala = new JLabel("Sala:");
		lblSala.setBounds(345, 37, 46, 14);
		contentPanel.add(lblSala);

		JLabel lblDurao = new JLabel("Dura\u00E7\u00E3o:");
		lblDurao.setBounds(32, 102, 57, 14);
		contentPanel.add(lblDurao);

		SpinnerNumberModel modelDHs = new SpinnerNumberModel(0,0,23,1);
		duracaoHs = new JSpinner(modelDHs);
		duracaoHs.setBounds(126, 96, 36, 20);
		contentPanel.add(duracaoHs);

		SpinnerNumberModel modelDMin = new SpinnerNumberModel(0,0,59,1);
		duracaoMin = new JSpinner(modelDMin);
		duracaoMin.setBounds(191, 96, 36, 20);
		contentPanel.add(duracaoMin);

		SpinnerNumberModel modelDSeg = new SpinnerNumberModel(0,0,59,1);
		duracaoSeg = new JSpinner(modelDSeg);
		duracaoSeg.setBounds(262, 96, 36, 20);
		contentPanel.add(duracaoSeg);

		JLabel lblHs = new JLabel("hs.");
		lblHs.setBounds(172, 102, 46, 14);
		contentPanel.add(lblHs);

		JLabel lblMin = new JLabel("min.");
		lblMin.setBounds(237, 102, 46, 14);
		contentPanel.add(lblMin);

		JLabel lblSeg = new JLabel("seg.");
		lblSeg.setBounds(309, 102, 46, 14);
		contentPanel.add(lblSeg);

		JLabel lblAjusteDoHorrio = new JLabel("Ajuste do hor\u00E1rio:");
		lblAjusteDoHorrio.setBounds(32, 133, 106, 14);
		contentPanel.add(lblAjusteDoHorrio);

		SpinnerNumberModel modelHs = new SpinnerNumberModel(0,0,23,1);
		ajusteHs = new JSpinner(modelHs);
		ajusteHs.setBounds(126, 127, 36, 20);
		contentPanel.add(ajusteHs);

		JLabel label = new JLabel("hs.");
		label.setBounds(172, 133, 46, 14);
		contentPanel.add(label);

		SpinnerNumberModel modelMin = new SpinnerNumberModel(0,0,59,1);
		ajusteMin = new JSpinner(modelMin);
		ajusteMin.setBounds(191, 127, 36, 20);
		contentPanel.add(ajusteMin);

		JLabel label_1 = new JLabel("min.");
		label_1.setBounds(237, 133, 46, 14);
		contentPanel.add(label_1);

		SpinnerNumberModel modelSeg = new SpinnerNumberModel(0,0,59,1);
		ajusteSeg = new JSpinner(modelSeg);
		ajusteSeg.setBounds(262, 127, 36, 20);
		contentPanel.add(ajusteSeg);

		JLabel label_2 = new JLabel("seg.");
		label_2.setBounds(309, 133, 46, 14);
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						btnCancelAction();
					}
				});
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

		rdbtnSessaoFilme.setSelected(true);
		textField.setEnabled(false);
		filme.setEnabled(true);
		duracaoHs.setEnabled(false);
		duracaoMin.setEnabled(false);
		duracaoSeg.setEnabled(false);

		JLabel lblHorriosOcupados = new JLabel("Hor\u00E1rios ocupados:");
		lblHorriosOcupados.setBounds(345, 68, 130, 14);
		contentPanel.add(lblHorriosOcupados);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(345, 84, 256, 116);
		contentPanel.add(scrollPane);

		table = new JTable();
		modelo = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Hora in\u00EDcio", "Hora t\u00E9rmino"
				})
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class getColumnClass(int columnIndex) {
				return String.class;
			}	
		};

		table.setModel(modelo);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		scrollPane.setViewportView(table);

		sala = new JComboBox();
		sala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mudaSala();
			}
		});
		sala.setBounds(401, 34, 200, 20);
		contentPanel.add(sala);

		preencheListas();
	}


	private void preencheListas(){
		IteratorFilme itrFilme = fachada.getCadFilme().getIteratorFilme();
		while(itrFilme.hasNext()){
			filme.addItem(itrFilme.next().getNome());
		}
		IteratorSala itrSala = null;
		try {
			itrSala = fachada.getCadSala().getIteratorSala();
		} catch (SalaNaoEncontradaException e) {
			JOptionPane.showMessageDialog(this, "Erro interno!");
		}
		while(itrSala.hasNext()){
			sala.addItem(itrSala.next().getCodigo());
		}
	}


	private void mudaSala() {

		IteratorSessao itr = null;
		try {
			itr = fachada.getCadSessao().getIteratorSessao();
		} catch (TipoDeObjetoNaoSuportado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		while(itr.hasNext()){
			Sessao sessao = itr.next();
			if(sessao.getSala().getCodigo().equals((String)sala.getSelectedItem())){
				modelo.addRow(new Object[]{df.format(sessao.getHoraInicio()),df.format(sessao.getHoraFim())});
				table.getRowSorter().toggleSortOrder(0);
			}
		}
	}



	private void criarSessaoAction() {

		//Lendo os Spinners e transformando-os em horarios
		GregorianCalendar gc = new GregorianCalendar(2000,0,1);

		gc.set(Calendar.HOUR_OF_DAY, ((Integer)duracaoHs.getValue()).intValue());
		gc.set(Calendar.MINUTE, ((Integer)duracaoMin.getValue()).intValue());
		gc.set(Calendar.SECOND, ((Integer)duracaoSeg.getValue()).intValue());
		Date duracao = gc.getTime();

		GregorianCalendar gc2 = new GregorianCalendar(2000,0,1);

		gc2.set(Calendar.HOUR_OF_DAY, ((Integer)ajusteHs.getValue()).intValue());
		gc2.set(Calendar.MINUTE, ((Integer)ajusteMin.getValue()).intValue());
		gc2.set(Calendar.SECOND, ((Integer)ajusteSeg.getValue()).intValue());
		Date inicio = gc2.getTime();



		try {
			//Selecionando o tipo de sessão
			if(rdbtnSessaoFilme.isSelected()){

				Filme filme = fachada.getCadFilme().buscarFilme((String)this.filme.getSelectedItem());
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem()).getCopiaObjeto();

				Sessao sessao = new SessaoPublicaFilme("", filme, sala, inicio);
				fachada.getCadSessao().inserirSessao(sessao);

			} else if(rdbtnSessaoFilmeFechada.isSelected()){

				Filme filme = fachada.getCadFilme().buscarFilme((String)this.filme.getSelectedItem());
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem()).getCopiaObjeto();
				Sessao sessao = new SessaoFechadaFilme("", filme, sala, inicio);
				fachada.getCadSessao().inserirSessao(sessao);


			}else if(rdbtnSessaoParticularAberta.isSelected()){

				String titulo = this.textField.getText();System.out.println(titulo);
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem()).getCopiaObjeto();
				Sessao sessao = new SessaoPublicaPropria("", sala, inicio, duracao,titulo);
				fachada.getCadSessao().inserirSessao(sessao);


			}else if(rdbtnSessaoParticularFechada.isSelected()){

				String titulo = this.textField.getText();
				Sala sala = fachada.getCadSala().procurarSala((String)this.sala.getSelectedItem()).getCopiaObjeto();
				Sessao sessao = new SessaoFechadaPropria("", sala, inicio, duracao,titulo);
				fachada.getCadSessao().inserirSessao(sessao);

			}
			JOptionPane.showMessageDialog(this, "Sessão criada com sucesso!");
			this.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}


	private class CheckBoxHandler implements ItemListener{


		public void itemStateChanged(ItemEvent event){
			//Evento de mudança de estado do checkboxai


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


	private void btnCancelAction() {
		this.dispose();
	}
}
