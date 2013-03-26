package iterator;

import negocio.Sessao;

public class IteratorSessaoArray implements IteratorSessao {

	private Sessao[] Sessoes;
	private int indice;
	
	public IteratorSessaoArray(Sessao[] Sessoes) {
		this.Sessoes = Sessoes;
		this.indice = 0;
	}
	
	public boolean hasNext() {
		boolean retorno;
		retorno = (this.indice<Sessoes.length&&this.Sessoes[indice]!=null);
		return retorno;
	}

	public Sessao next() {
		Sessao retorno = this.Sessoes[this.indice];
		this.indice++;
		
		return retorno;
	}

}
