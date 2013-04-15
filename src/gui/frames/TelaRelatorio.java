package gui.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import repositorio.relatorio.TextoRelatorio;

import negocio.base.Relatorio;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TelaRelatorio extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;
	private Iterator iteratorTexto;

	
	/**
	 * Create the frame.
	 */
	public TelaRelatorio(Relatorio relatorio) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.iteratorTexto = relatorio.getTexto().iterator();
		setBounds(100, 100, 563, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton btnSalvarTexto = new JButton("Salvar arquivo texto");
		btnSalvarTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botaoSalvarAction();
			}

		});
		btnSalvarTexto
				.setIcon(new ImageIcon(
						TelaRelatorio.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		toolBar.add(btnSalvarTexto);
		
		JButton btnmais = new JButton(">>Mais");
		btnmais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMaisAction();
			}

		});
		toolBar.add(btnmais);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textPane = new JTextPane();
		textPane.setEditable(false);
		if(this.iteratorTexto.hasNext())
		textPane.setText(((TextoRelatorio)iteratorTexto.next()).getTexto());
		scrollPane.setViewportView(textPane);
	}

	private void botaoSalvarAction() {
		String nomeArquivo = abreChoser();
		escreveArquivo(nomeArquivo);
	}

	private String abreChoser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return ".txt";
			}

			@Override
			public boolean accept(File f) {

				return f.getName().endsWith(".txt") || f.isDirectory();
			}
		});

		String texto = "";

		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File files = chooser.getSelectedFile();
			if (files != null) {
				texto = files.getAbsolutePath();
				if (!texto.endsWith(".txt"))
					texto += ".txt";
			}

		}
		return texto;
	}

	private void escreveArquivo(String nomeArquivo){
		File file = new File(nomeArquivo);
		FileWriter fwArquivo;
		BufferedWriter bwArquivo;
		StringTokenizer st = new StringTokenizer(this.textPane.getText(),"\n");
		
		try {
			fwArquivo = new FileWriter(file);
			bwArquivo = new BufferedWriter(fwArquivo);
			while(st.hasMoreElements()){
				bwArquivo.write(st.nextToken());
				bwArquivo.newLine();
			}
			
			bwArquivo.close();
			fwArquivo.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	

	private void btnMaisAction() {
		if(this.iteratorTexto.hasNext())
			textPane.setText(textPane.getText()+"\n\n"+((TextoRelatorio)iteratorTexto.next()).getTexto());
		else JOptionPane.showMessageDialog(this, "Fim do relatório");
	}
}
