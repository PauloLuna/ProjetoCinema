package iterator;

import negocio.base.Sessao;

public interface IteratorSessao {
	
	boolean hasNext();
	Sessao next();

}
