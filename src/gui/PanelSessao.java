package gui;

import iterator.IteratorFilme;
import iterator.IteratorSessao;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import basicas.Filme;
import basicas.Sessao;

import repositorio.FilmeNaoEncontradoException;


import fachada.Fachada;

public class PanelSessao extends JPanel {
	private JTable table;
	private DefaultTableModel  modeloTabela;
	private Fachada fachada;

	/**
	 * Create the panel.
	 */
	public PanelSessao(Fachada fachada) {
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
		
		preencheTabela();
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false); 
		table.setAutoCreateRowSorter(true);
		TableColumn codigoColumn = table.getColumnModel().getColumn(0);  
		codigoColumn.setMinWidth(0);  
		codigoColumn.setMaxWidth(0);  



	}
	
	
	public void btnInserirAciton(){

		TelaCadastroSessao cadastra = new TelaCadastroSessao();
		cadastra.setVisible(true);
	}

	public void btnAtualizarAction(){

		/*int linha = table.getSelectedRow();
		String nomeFilme = (String)table.getValueAt(linha, 0);
		Filme filme;
		try {
			filme = fachada.buscarFilme(nomeFilme);
		} catch (FilmeNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());			
		}*/


	}

	public void btnRemoverAction(){
		int linha = table.getSelectedRow();
		String idSessao = (String)table.getValueAt(linha, 0);
		try {
			fachada.getCadSessao().removerSessao(idSessao);
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
						"id","Titulo", "Inicio", "Fim"
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

			IteratorSessao itr = fachada.getCadSessao().getIteratorSessao();
			SimpleDateFormat df;
			df = new SimpleDateFormat("HH:mm:ss");
			while(itr.hasNext()){
				Sessao sessao = itr.next();
				String id = sessao.getId();
				String filme = sessao.getTitulo();
				String inicio = df.format(sessao.getHoraInicio());
				String fim = df.format(sessao.getHoraFim());
				modeloTabela.addRow(new Object[]{id,filme,inicio,fim});
			}

			table.setModel(modeloTabela);
	}

}
