package gui;

import fachada.Fachada;
import iterator.IteratorFilme;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import java.text.SimpleDateFormat;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import basicas.Filme;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelSala extends JPanel {
	private JTable table;
	private DefaultTableModel modeloTabela;
	private Fachada fachada;

	/**
	 * Create the panel.
	 */
	public PanelSala(Fachada fachada) {
		this.fachada = fachada;
		
		setLayout(new CardLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		add(splitPane, "name_491025024262547");
		splitPane.setDividerLocation(620);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		table = new JTable();
		
		preencheTabela();
		
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cadastrarAction();
			}			
		});
		btnCadastrar.setBounds(10, 44, 89, 23);
		panel.add(btnCadastrar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(10, 78, 89, 23);
		panel.add(btnModificar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(10, 112, 89, 23);
		panel.add(btnRemover);

	}


	private void preencheTabela(){

		modeloTabela = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						 "C\u00F3digo", "Cadeiras","Cadeiras quebradas"
				}
				) {
			
			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

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
	
	private void cadastrarAction() {
		
	}
}
