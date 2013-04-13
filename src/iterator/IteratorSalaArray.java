package iterator;

import negocio.base.Sala;

public class IteratorSalaArray implements IteratorSala {

	private Sala[] salas;
	private int indice;
	
	public IteratorSalaArray(Sala[] salas) {
		this.salas = salas;
		this.indice = 0;
	}
	
	public boolean hasNext() {
		boolean retorno;
		retorno = (this.indice<salas.length&&this.salas[indice]!=null);
		return retorno;
	}

	public Sala next() {
		Sala retorno = this.salas[this.indice];
		this.indice++;
		
		return retorno;
	}

}
