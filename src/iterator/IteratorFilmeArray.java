package iterator;

import basicas.Filme;

public class IteratorFilmeArray implements IteratorFilme {

	private Filme[] filmes;
	private int indice;
	
	public IteratorFilmeArray(Filme[] filmes) {
		this.filmes = filmes;
		this.indice = 0;
	}
	
	public boolean hasNext() {
		boolean retorno;
		retorno = (this.indice<filmes.length&&this.filmes[indice]!=null);
		return retorno;
	}

	public Filme next() {
		Filme retorno = this.filmes[this.indice];
		this.indice++;
		
		return retorno;
	}

}
