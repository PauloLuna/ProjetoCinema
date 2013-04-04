package gui;

import iterator.IteratorSessao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.AbstractListModel;
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
import javax.swing.table.TableColumnModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;




import repositorio.*;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

import basicas.Filme;
import basicas.Sessao;


public class Vendas extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private RepositorioSessao rep;
	private IteratorSessao itr;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vendas frame = new Vendas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vendas() {
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 346);
		

		textPane = new JTextPane();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnRemove = new JMenu("Remove");
		mnFile.add(mnRemove);
		
		JCheckBoxMenuItem chckbxmntmChec = new JCheckBoxMenuItem("Chec");
		mnRemove.add(chckbxmntmChec);
		
		JMenuItem mntmLasco = new JMenuItem("Lasco");
		mnRemove.add(mntmLasco);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		DefaultTableModel  modeloTabela = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Filme","Sala", "In\u00EDcio", "Dura\u00E7\u00E3o", "Classifica\u00E7\u00E3o","Categoria", "Descri\u00E7\u00E3o"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		table = new JTable(modeloTabela);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false); 
		table.setAutoCreateRowSorter(true);
		
		JButton btnVender = new JButton("Vender");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(2)
					.addComponent(btnVender, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(321, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
					.addGap(4))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnVender)
					.addGap(7))
		);
		scrollPane_1.setViewportView(textPane);
		File file = new File("teste.txt");
		try {
			Scanner scn = new Scanner(file);
			while (scn.hasNextLine()){
				textPane.setText(textPane.getText()+'\n'+scn.nextLine());
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		contentPane.setLayout(gl_contentPane);
		try {
			rep = new RepositorioSessaoExel("teste.xls", "Sessoes");
			itr = rep.getIterator();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleDateFormat df;
		df = new SimpleDateFormat("HH:mm:ss");
	
		while(itr.hasNext()){
			Sessao sessao = itr.next();
			String id = sessao.getId();
			String nome = sessao.getFilme().getNome();
			String sala = sessao.getSala();
			String duracao = df.format(sessao.getFilme().getDuracao());
			String inicio = df.format(sessao.getHoraInicio());
			String categoria = sessao.getFilme().getCategoria();
			String classifica = sessao.getFilme().getClassificacao();
			String descricao = sessao.getFilme().getDescricao();
			modeloTabela.addRow(new Object[]{nome,sala, inicio,duracao,classifica,categoria,descricao});
		}
		
		
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
}
