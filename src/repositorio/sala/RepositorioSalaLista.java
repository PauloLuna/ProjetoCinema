package repositorio.sala;

import negocio.base.Sala;
import iterator.IteratorSala;
import iterator.IteratorSalaLista;

public class RepositorioSalaLista implements RepositorioSala {

	private Sala sala;
	private RepositorioSalaLista proximo;

	public RepositorioSalaLista(){
		//Cada novo elemento tem seus valores como nulos
		this.sala = null;
		this.proximo = null;
	}
	
	public Sala buscar(String codigo) throws SalaNaoEncontradaException {
		Sala retorno = null;
		
		
		if(this.sala==null){//Checa fim da lista se chegar lá retorna a excessão
			throw new SalaNaoEncontradaException();
		} else if(this.sala.getCodigo().equals(codigo)){//Checa se o Sala é o procurado se sim o retorna
			retorno = this.sala;
		} else {
			retorno = this.proximo.buscar(codigo);
		}
		
		return retorno;
	}

	public void inserir(Sala sala){
		if(this.sala==null){//Checa fim de lista se achar insere os dados
			this.sala = sala;
			this.proximo = new RepositorioSalaLista();
		} else {//Se não manda o proximo elemento tentar inserir
			this.proximo.inserir(sala);
		}
	}

	public void remover(String codigo) throws SalaNaoEncontradaException {
		
		if(this.sala==null){//Checa fim de lista se chagar retorna a excessão
			throw new SalaNaoEncontradaException();
		} else if(this.sala.getCodigo().equals(codigo)){//Se for a Sala procurada a remove coloando nela os valores da próxima
			this.sala = this.proximo.sala;
			this.proximo = this.proximo.proximo;
		} else {//em ultimo caso manda o proximo checar a remoção
			this.proximo.remover(codigo);
		}
		
	}

	public void atualizar(Sala sala) throws SalaNaoEncontradaException {
		//Substitui o Sala por um outro com o mesmo codigo, os dados podem ser diferentes
		if(this.sala==null){
			throw new SalaNaoEncontradaException();
		} else if(this.sala.getCodigo().equals(sala.getCodigo())){
			this.sala = sala;
		} else {
			this.proximo.atualizar(sala);
		}
		
	}
	
	public IteratorSala getIterator(){
		return new IteratorSalaLista(this.sala, this.proximo);
	}

	
	public boolean temSala(String codigo) {
		boolean retorno = true;
		try{
			buscar(codigo);
		}catch(SalaNaoEncontradaException e){
			retorno = false;
		}
		
		return retorno;
	}

}
