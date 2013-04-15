package repositorio.relatorio;

import iterator.IteratorTextosRelatorio;

import java.io.Serializable;
import java.util.Iterator;


public class RepositorioTextosRelatorios implements Iterable, Serializable{

	private TextoRelatorio relatorio;
	private RepositorioTextosRelatorios proximo;

	public RepositorioTextosRelatorios(){
		//Cada novo elemento tem seus valores como nulos
		this.relatorio = null;
		this.proximo = null;

	}

	public void inserir(String relatorio){
		if(this.relatorio==null){//Checa fim de lista se achar insere os dados
			this.relatorio = new TextoRelatorio(relatorio);
			this.proximo = new RepositorioTextosRelatorios();
		} else if(this.relatorio.compareTo(this.relatorio)==-1){
			RepositorioTextosRelatorios rep = new RepositorioTextosRelatorios();
			rep.relatorio.setTexto(relatorio);
			rep.proximo = this.proximo;
			this.proximo = rep;
		} else {//Se não manda o proximo elemento tentar inserir
			this.proximo.inserir(relatorio);
		}		
	}
	
	public Iterator iterator() {
		return new IteratorTextosRelatorio(this.relatorio, this.proximo);
	}

}
