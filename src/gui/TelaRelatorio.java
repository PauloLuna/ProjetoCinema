package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import basicas.Relatorio;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class TelaRelatorio extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;

	
	/**
	 * Create the frame.
	 */
	public TelaRelatorio(Relatorio relatorio) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(relatorio.getTexto());
		scrollPane.setViewportView(textPane);
	}

	private void botaoSalvarAction() {
		abreChoser();
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

}
