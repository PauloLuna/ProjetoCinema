package negocio.colecao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import negocio.base.Relatorio;


import iterator.IteratorRelatorio;
import repositorio.relatorio.RelatorioNaoEncontradoException;
import repositorio.relatorio.RepositorioRelatorio;

public class ControleRelatorios {

	private RepositorioRelatorio repRelatorio;
	
	public ControleRelatorios(){
		try {
			deserializa();
		} catch (ClassNotFoundException e) {
			System.out.println("Merda na classe");
		} catch (IOException e) {
			this.repRelatorio = new RepositorioRelatorio();
			System.out.println("novo");
		}
	}

	public RepositorioRelatorio getRepRelatorio() {
		return repRelatorio;
	}

	public void setRepRelatorio(RepositorioRelatorio repRelatorio) {
		this.repRelatorio = repRelatorio;
	}
	
	public IteratorRelatorio getIteratorRelatorio(){
		return this.repRelatorio.getIterator();
	}
	
	public void inserirRelatorio(String nomeSala, String texto) throws IOException{
		Relatorio relatorio = new Relatorio(nomeSala, new Date(), texto);
		repRelatorio.inserir(relatorio);
		serializar();
	}
	
	public Relatorio buscarRelatorio(String nomeSala) throws RelatorioNaoEncontradoException{
		return repRelatorio.buscar(nomeSala);
	}
	
	private void deserializa() throws IOException, ClassNotFoundException{
		FileInputStream fisRelatorio = null;
		ObjectInputStream oisRelatorio = null;

		fisRelatorio = new FileInputStream("relatorios.ip");
		oisRelatorio = new ObjectInputStream(fisRelatorio);

		repRelatorio = (RepositorioRelatorio) oisRelatorio.readObject();

		oisRelatorio.close();
		fisRelatorio.close();

	}
	
	public void serializar() throws IOException{
		FileOutputStream fosRelatorio = null;
		ObjectOutputStream oosRelatorio = null; 

		fosRelatorio = new FileOutputStream("relatorios.ip",false); // vai apagar o arquivo
		oosRelatorio = new ObjectOutputStream(fosRelatorio); 

		oosRelatorio.writeObject(repRelatorio);

		fosRelatorio.close();
		oosRelatorio.close();

	}
}
