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

	public ControleRelatorios() throws ClassNotFoundException{
		try {
			//Tenta deserializar todo repositório
			deserializa();
		} catch (IOException e) {
			//Caso tenha problemas com o arquivo cria um novo repositorio
			this.repRelatorio = new RepositorioRelatorio();
		}
	}

	public boolean temRelatorio(String nomeSala){
		boolean retorno = true;
		try {
			buscarRelatorio(nomeSala);
		} catch (RelatorioNaoEncontradoException e) {
			retorno = false;
		}

		return retorno;
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
		Relatorio relatorio;

		try {
			//procura um relatorio da sala para adcionar os dados
			repRelatorio.buscar(nomeSala).getTexto().inserir(texto);
		} catch (RelatorioNaoEncontradoException e) {
			//Caso não encontre cria um relatorio
			relatorio = new Relatorio(nomeSala, new Date());
			relatorio.getTexto().inserir(texto);
			repRelatorio.inserir(relatorio);
		}

		serializar();
	}

	public Relatorio buscarRelatorio(String nomeSala) throws RelatorioNaoEncontradoException{
		return repRelatorio.buscar(nomeSala);
	}

	public void removeRelatorio(String nomeSala) throws RelatorioNaoEncontradoException{
		repRelatorio.remover(nomeSala);
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
		//Serializa todo repositorio onde todas as classes implementam serializable
		FileOutputStream fosRelatorio = null;
		ObjectOutputStream oosRelatorio = null; 

		fosRelatorio = new FileOutputStream("relatorios.ip",false); // vai apagar o arquivo
		oosRelatorio = new ObjectOutputStream(fosRelatorio); 

		oosRelatorio.writeObject(repRelatorio);

		fosRelatorio.close();
		oosRelatorio.close();

	}
}
