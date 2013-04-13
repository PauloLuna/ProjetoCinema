package iterator;

import repositorio.sala.RepositorioSalaLista;
import negocio.base.Sala;
import negocio.base.Sala;
import negocio.base.Sessao;

public class IteratorSalaLista implements IteratorSala {
	
	
	private Sala sala;
	private RepositorioSalaLista proximo;
	
	public IteratorSalaLista(Sala Sala, RepositorioSalaLista proximo){
		
		this.sala = Sala;
		this.proximo = proximo;
	}

	public boolean hasNext() {
		return(this.sala != null);
	}

	public Sala next() {
		Sala retorno = this.sala;
		IteratorSalaLista itr = (IteratorSalaLista)this.proximo.getIterator();
		this.sala = itr.sala;
		this.proximo = itr.proximo;
		return retorno;
	}

}
