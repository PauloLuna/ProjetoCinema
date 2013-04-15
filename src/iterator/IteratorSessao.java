package iterator;

import repositorio.sessao.TipoDeObjetoNaoSuportado;
import negocio.base.Sessao;

public interface IteratorSessao {
	
	boolean hasNext();
	Sessao next() throws TipoDeObjetoNaoSuportado;

}
