package repositorio.sala;

import negocio.base.Sala;
import iterator.IteratorSala;
import iterator.IteratorSalaArray;

public class RepositorioSalaArray implements RepositorioSala{
	private int indice;
	private Sala[] salas;
	
	public RepositorioSalaArray(){
		this.indice = 0;
		this.salas = new Sala[50];
	}


	public Sala buscar(String id) throws SalaNaoEncontradaException {
		Sala retorno = null;
		
		retorno = this.salas[this.buscarIndice(id)];			
		 
		return retorno;
	}


	public void inserir(Sala sala) {
		
		if(this.indice == this.salas.length){
			Sala[] apoio = new Sala[this.salas.length*2];
			for(int i = 0; i<this.indice; i++){
				apoio[i] = this.salas[i];
			}
			this.salas = apoio;
		}
		
		this.salas[indice] = sala;
		this.indice++;
	}


	public void remover(String codigo) throws SalaNaoEncontradaException {
		int inicio = buscarIndice(codigo);
		
			this.salas[inicio] = this.salas[this.indice-1];
			this.salas[this.indice-1] = null;
			this.indice--;
		
	}

	public void atualizar(Sala sala) throws SalaNaoEncontradaException {
		this.salas[buscarIndice(sala.getCodigo())] = sala;
	}
	
	private int buscarIndice(String codigo) throws SalaNaoEncontradaException{
		int retorno = -1;
		boolean continuar=true;
		
		
		for(int i = 0; i < this.indice && continuar; i++){
			if (salas[i].getCodigo().equals(codigo)){
				retorno = i;
				continuar = false;
			}
		}
		if (retorno ==-1) throw new SalaNaoEncontradaException();
		return retorno;
	}


	public IteratorSala getIterator() {
		return new IteratorSalaArray(this.salas);
	}


	@Override
	public boolean temSala(String codigo) {
		boolean retorno = true;
		
		try {
			buscarIndice(codigo);
		} catch (SalaNaoEncontradaException e) {
			retorno = false;
		}
		
		return retorno;
	}

}
