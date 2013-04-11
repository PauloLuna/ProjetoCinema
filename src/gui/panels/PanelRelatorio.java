package gui.panels;

import fachada.Fachada;
import gui.frames.TelaRelatorio;
import iterator.IteratorFilme;
import iterator.IteratorRelatorio;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import java.awt.CardLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;

import negocio.base.Filme;
import negocio.base.Relatorio;

import repositorio.relatorio.RelatorioNaoEncontradoException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelRelatorio extends JPanel {
	private JTable table;
	private DefaultTableModel modeloTabela;
	private Fachada fachada;

	/**
	 * Create the panel.
	 */
	public PanelRelatorio(Fachada fachada) {
		this.fachada = fachada;
		
		try {
			fachada.getControleRelatorios().inserirRelatorio("teste", "askkfnczdxjcmn \nadlvnmfcxzm,cmnxnsdkjmxz\njsdmnvclamz\nxnak\ndfdngmvlsdxcnmdaslkxfcnmfdcxm\nkdfkf");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro interno "+e.getMessage());
		}
		
		setLayout(new CardLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(620);
		add(splitPane, "name_448841281678032");
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(null);
		
		JButton btnLer = new JButton("Ler");
		btnLer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botaoLerAction();
			}
		});
		btnLer.setBounds(10, 51, 89, 23);
		panel_1.add(btnLer);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(10, 85, 89, 23);
		panel_1.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		
		preencheTabela();
		
		scrollPane.setViewportView(table);

		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false); 
		table.setAutoCreateRowSorter(true);

	}
	
	
private void preencheTabela(){
		
		modeloTabela = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					 "Sala", "Data de gera\u00E7\u00E3o"
				}
			) {
				
				public Class getColumnClass(int columnIndex) {
					Class retorno = String.class;
					if (columnIndex == 1) retorno = Date.class;
					return retorno;
				}
				
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
		IteratorRelatorio itr = fachada.getControleRelatorios().getIteratorRelatorio();
//		SimpleDateFormat df;
//		df = new SimpleDateFormat("HH:mm:ss");
		while(itr.hasNext()){
			Relatorio rel = itr.next();
			String sala = rel.getNomeSala();
			Date dataGeracao = rel.getDataCriacao();
			modeloTabela.addRow(new Object[]{sala,dataGeracao});
		}
		
		table.setModel(modeloTabela);
		
	}

	public void botaoLerAction(){
		String nomeSala = (String)table.getValueAt(table.getSelectedRow(),0);
		Relatorio rel;
		try {
			rel = fachada.getControleRelatorios().buscarRelatorio(nomeSala);
			TelaRelatorio tela = new TelaRelatorio(rel);
			tela.setTitle(nomeSala+" Criado em: "+rel.getDataCriacao());
			tela.setVisible(true);
		} catch (RelatorioNaoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
