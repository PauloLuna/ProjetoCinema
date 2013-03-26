package repositorio;

import iterator.IteratorFilme;
import iterator.IteratorFilmeLista;
import negocio.Filme;

public class RepositorioFilmeLista implements RepositorioFilme {

	private Filme filme;
	private RepositorioFilmeLista proximo;

	public RepositorioFilmeLista(){
		//Cada novo elemento tem seus valores como nulos
		this.filme = null;
		this.proximo = null;
	}
	
	public Filme buscar(String nome) throws FilmeNaoEncontradoException {
		Filme retorno = null;
		
		
		if(this.filme==null){//Checa fim da lista se chegar lá retorna a excessão
			throw new FilmeNaoEncontradoException();
		} else if(this.filme.getNome().equals(nome)){//Checa se o filme é o procurado se sim o retorna
			retorno = this.filme;
		} else {
			retorno = this.proximo.buscar(nome);
		}
		
		return retorno;
	}

	public void inserir(Filme filme) {
		if(this.filme==null){//Checa fim de lista se achar insere os dados
			this.filme = filme;
			this.proximo = new RepositorioFilmeLista();
		} else {//Se não manda o proximo elemento tentar inserir
			this.proximo.inserir(filme);
		}
	}

	public void remover(String nome) throws FilmeNaoEncontradoException {
		
		if(this.filme==null){//Checa fim de lista se chagra retorna a excessão
			throw new FilmeNaoEncontradoException();
		} else if(this.filme.getNome().equals(nome)){//Se for o filme procurado o remove coloando nele os valores do próximo
			this.filme = this.proximo.filme;
			this.proximo = this.proximo.proximo;
		} else {//em ultimo caso manda o proximo checar a remoção
			this.proximo.remover(nome);
		}
		
	}

	public void atualizar(Filme filme) throws FilmeNaoEncontradoException {
		//Substitui o filme por um outro com o mesmo nome, os dados podem ser diferentes
		if(this.filme==null){
			throw new FilmeNaoEncontradoException();
		} else if(this.filme.getNome().equals(filme.getNome())){
			this.filme = filme;
		} else {
			this.proximo.atualizar(filme);
		}
		
	}
	
	public IteratorFilme getIterator(){
		return new IteratorFilmeLista(this.filme, this.proximo);
	}

	@Override
	public boolean temFilme(String nome) {
		boolean retorno = true;
		try{
			buscar(nome);
		}catch(FilmeNaoEncontradoException e){
			retorno = false;
		}
		
		return retorno;
	}

}
