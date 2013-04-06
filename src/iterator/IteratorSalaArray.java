package iterator;

import basicas.Sala;

public class IteratorSalaArray implements IteratorSala{

	private Sala[] sala;
	private int indice;

	public IteratorSalaArray (Sala[] salas) {
		this.sala = salas;
		this.indice = 0;
	}

	public boolean hasnext() {
		boolean hasnext;
		
		if (this.sala[indice + 1] == null){
			hasnext = false;
		}
		else{
			hasnext = true;
		}
				
		return hasnext;
	}

	
	public Sala next() {
		Sala sala = this.sala[this.indice];
		this.indice++;
		
		return sala;
	}

} // Fim da classe Iterator
