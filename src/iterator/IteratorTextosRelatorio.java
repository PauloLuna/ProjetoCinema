package iterator;

import java.io.Serializable;
import java.util.Iterator;

import repositorio.relatorio.RepositorioTextosRelatorios;
import repositorio.relatorio.TextoRelatorio;

public class IteratorTextosRelatorio implements Iterator, Serializable {
	
	private TextoRelatorio relatorio;
	private RepositorioTextosRelatorios proximo;

	public IteratorTextosRelatorio(TextoRelatorio relatorio,
			RepositorioTextosRelatorios proximo) {
		this.relatorio = relatorio;
		this.proximo = proximo;
	}

	public boolean hasNext() {
		return(this.relatorio != null);
	}

	public Object next() {
		TextoRelatorio retorno = this.relatorio;
		//Obt�m os dados do iterator do pr�ximo elemento da lista
		IteratorTextosRelatorio itr = (IteratorTextosRelatorio)this.proximo.iterator();
		this.relatorio = itr.relatorio;
		this.proximo = itr.proximo;
		return retorno;		
	}

	public void remove() {
		//N�o � poss�vel a remo��o
	}

}
