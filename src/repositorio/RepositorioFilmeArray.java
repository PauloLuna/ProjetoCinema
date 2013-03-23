package repositorio;

import iterator.IteratorFilme;
import iterator.IteratorFilmeArray;
import negocio.Filme;

public class RepositorioFilmeArray implements RepositorioFilme{
	private int indice;
	private Filme[] filmes;
	
	public RepositorioFilmeArray(){
		this.indice = 0;
		this.filmes = new Filme[50];
	}


	public Filme buscar(String nome) throws FilmeNaoEncontradoException {
		Filme retorno = null;
		
		retorno = this.filmes[this.buscarIndice(nome)];			
		 
		return retorno;
	}


	public void inserir(Filme filme) {
		if(this.indice == this.filmes.length){
			Filme[] apoio = new Filme[this.filmes.length*2];
			for(int i = 0; i<this.indice; i++){
				apoio[i] = this.filmes[i];
			}
			this.filmes = apoio;
		}
		this.filmes[indice] = filme;
		this.indice++;
	}


	public void remover(String nome) throws FilmeNaoEncontradoException {
		int inicio = buscarIndice(nome);
		
			this.filmes[inicio] = this.filmes[indice-1];
		
	}

	public void atualizar(Filme filme) throws FilmeNaoEncontradoException {
		this.filmes[buscarIndice(filme.getNome())] = filme;
	}
	
	private int buscarIndice(String nome) throws FilmeNaoEncontradoException{
		int retorno = -1;
		boolean continuar=true;
		
		
		for(int i = 0; i < this.indice && continuar; i++){
			if (filmes[i].getNome().equals(nome)){
				retorno = i;
				continuar = false;
			}
		}
		if (retorno ==-1) throw new FilmeNaoEncontradoException();
		return retorno;
	}


	public IteratorFilme getIterator() {
		return new IteratorFilmeArray(this.filmes);
	}

}
