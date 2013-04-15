package gui.panels;

import fachada.Fachada;
import gui.frames.TelaAtualizarFilme;
import gui.frames.TelaCadastroFilme;
import iterator.IteratorFilme;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.CardLayout;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabExpander;

import negocio.base.Filme;
import negocio.base.Sessao;



import repositorio.filme.FilmeNaoEncontradoException;
import repositorio.filme.RepositorioFilme;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PanelFilme extends JPanel {
	private JTable table;
	private DefaultTableModel  modeloTabela;
	private Fachada fachada;

	/**
	 * Create the panel.
	 */
	public PanelFilme(Fachada fachada) {

		this.fachada = fachada;

		setLayout(new CardLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(620);
		add(splitPane, "name_424319822332266");

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRemoverAction();
			}
		});
		btnRemover.setBounds(10, 112, 95, 23);
		panel.add(btnRemover);

		JButton btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInserirAciton();
			}
		});
		btnInserir.setBounds(10, 44, 95, 23);
		panel.add(btnInserir);

		JButton btnAtualizar = new JButton("Modificar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAtualizarAction();
			}
		});
		btnAtualizar.setBounds(10, 78, 95, 23);
		panel.add(btnAtualizar);

		JButton btnAtualizarLista = new JButton("Atualizar");
		btnAtualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				preencheTabela();
			}
		});
		btnAtualizarLista.setBounds(10, 11, 95, 23);
		panel.add(btnAtualizarLista);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		table = new JTable();


		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false); 
		table.setAutoCreateRowSorter(true);


		preencheTabela();

	}

	public void btnInserirAciton(){

		TelaCadastroFilme cadastra = new TelaCadastroFilme(this.fachada);
		cadastra.setVisible(true);
	}

	public void btnAtualizarAction(){

		int linha = table.getSelectedRow();
		String nomeFilme = (String)table.getValueAt(linha, 0);
		Filme filme;
		try {
			filme = fachada.getCadFilme().buscarFilme(nomeFilme);
			TelaAtualizarFilme atualiza = new TelaAtualizarFilme(filme, this.fachada);
			atualiza.setVisible(true);
		} catch (FilmeNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());			
		}
	}

	public void btnRemoverAction(){
		int linha = table.getSelectedRow();
		String nomeFilme = (String)table.getValueAt(linha, 0);
		try {
			fachada.getCadFilme().removerFilme(nomeFilme);
			JOptionPane.showMessageDialog(this, "Filme excluido com sucesso!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e.getMessage());
		} 
	}

	private void preencheTabela(){

		modeloTabela = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nome", "Dura\u00E7\u00E3o", "Classifica\u00E7\u00E3o", "Categoria", "Descri\u00E7\u00E3o"
				}){

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return false;
			}};

			IteratorFilme itr = fachada.getCadFilme().getIteratorFilme();
			SimpleDateFormat df;
			df = new SimpleDateFormat("HH:mm:ss");
			while(itr.hasNext()){
				Filme filme = itr.next();
				String nome = filme.getNome();
				String duracao = df.format(filme.getDuracao());
				String categoria = filme.getCategoria();
				String classifica = filme.getClassificacao();
				String descricao = filme.getDescricao();
				modeloTabela.addRow(new Object[]{nome,duracao,classifica,categoria,descricao});
			}

			table.setModel(modeloTabela);
	}
}
