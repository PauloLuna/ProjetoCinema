package gui.frames;

import fachada.Fachada;
import iterator.IteratorSessao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;




import repositorio.relatorio.*;
import repositorio.sessao.SessaoNaoEncontradaException;
import repositorio.sessao.TipoDeObjetoNaoSuportado;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

import negocio.base.Filme;
import negocio.base.Sessao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TelaVendas extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Fachada fachada;
	private DefaultTableModel modeloTabela;


	/**
	 * Create the frame.
	 */
	public TelaVendas(Fachada fachada) {
		setTitle("Vendas");
		this.fachada = fachada;
		setBounds(100, 100, 722, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 686, 260);

		table = new JTable();
		preencheTabela();
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false); 
		table.setAutoCreateRowSorter(true);

		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(641, 281, 55, 23);
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAbrirAction();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnAbrir);
		contentPane.add(scrollPane);


	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
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

			IteratorSessao itr = null;
			try {
				itr = fachada.getCadSessao().getIteratorSessao();
			} catch (TipoDeObjetoNaoSuportado e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			TableColumn codigoColumn = table.getColumnModel().getColumn(0);  
			codigoColumn.setMinWidth(0);  
			codigoColumn.setMaxWidth(0);  
	}


	private void btnAbrirAction() {
		String id = (String) table.getValueAt(table.getSelectedRow(), 0);
		try {
			Sessao sessao = fachada.getCadSessao().buscarSessao(id);
			TelaVendaSessao tela = new TelaVendaSessao(sessao);
			tela.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
}
