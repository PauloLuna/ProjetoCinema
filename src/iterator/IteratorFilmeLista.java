package iterator;

import negocio.base.Filme;
import repositorio.filme.RepositorioFilmeLista;

public class IteratorFilmeLista implements IteratorFilme {

	private Filme filme;
	private RepositorioFilmeLista proximo;
	
	public IteratorFilmeLista(Filme filme, RepositorioFilmeLista proximo){
		
		this.filme = filme;
		this.proximo = proximo;
	}
	
	public boolean hasNext() {
		return(this.filme != null);
	}

	public Filme next() {
		Filme retorno = this.filme;
		//Obtém os dados do iterator do próximo elemento da lista
		IteratorFilmeLista itr = (IteratorFilmeLista)this.proximo.getIterator();
		this.filme = itr.filme;
		this.proximo = itr.proximo;
		return retorno;
	}

}
