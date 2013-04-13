package gui.panels;

import fachada.Fachada;
import gui.frames.TelaCadastroSala;
import gui.frames.TelaConfigSala;
import iterator.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import java.awt.CardLayout;
import java.text.SimpleDateFormat;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;

import repositorio.sala.SalaNaoEncontradaException;

import negocio.base.Filme;
import negocio.base.Sala;

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
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false); 
		table.setAutoCreateRowSorter(true);
		
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
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnModificarAction();
			}
		});
		btnModificar.setBounds(10, 78, 89, 23);
		panel.add(btnModificar);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(10, 112, 89, 23);
		panel.add(btnRemover);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				preencheTabela();
			}
		});
		btnAtualizar.setBounds(10, 11, 89, 23);
		panel.add(btnAtualizar);

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

			IteratorSala itr = fachada.getCadSala().getIteratorSala();
			
			while(itr.hasNext()){
				Sala sala = itr.next();
				String codigo = sala.getCodigo();
				String numCadeiras = ""+(sala.getNumFilas()*sala.getNumColunas());
				String numCadeirasQuebradas = "";
				modeloTabela.addRow(new Object[]{codigo, numCadeiras, numCadeirasQuebradas});
			}

			table.setModel(modeloTabela);
	}
	
	private void cadastrarAction() {
		TelaCadastroSala tela = new TelaCadastroSala(fachada);
		tela.setVisible(true);
	}
	
	private void btnModificarAction() {
		String codigo = (String)modeloTabela.getValueAt(table.getSelectedRow(), 0);
		Sala sala;
		try {
			sala = fachada.getCadSala().procurarSala(codigo);
			TelaConfigSala tela = new TelaConfigSala(sala);
			tela.setVisible(true);
		} catch (SalaNaoEncontradaException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
}
