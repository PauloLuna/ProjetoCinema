package iterator;

import negocio.base.Sessao;
import repositorio.sessao.RepositorioSessaoLista;

public class IteratorSessaoLista implements IteratorSessao {

	private Sessao Sessao;
	private RepositorioSessaoLista proximo;
	
	public IteratorSessaoLista(Sessao Sessao, RepositorioSessaoLista proximo){
		
		this.Sessao = Sessao;
		this.proximo = proximo;
	}
	
	public boolean hasNext() {
		return(this.Sessao != null);
	}

	public Sessao next() {
		Sessao retorno = this.Sessao;
		//Obt�m os dados do iterator do pr�ximo elemento da lista
		IteratorSessaoLista itr = (IteratorSessaoLista)this.proximo.getIterator();
		this.Sessao = itr.Sessao;
		this.proximo = itr.proximo;
		return retorno;
	}

}
