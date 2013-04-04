package repositorio;

import iterator.IteratorFilme;

import java.io.FileNotFoundException;
import java.io.IOException;

import basicas.Filme;



public interface RepositorioFilme {

	public Filme buscar(String nome) throws FilmeNaoEncontradoException;
	public void inserir(Filme filme) throws FileNotFoundException, IOException;
	public void remover(String nome)throws FilmeNaoEncontradoException, FileNotFoundException, IOException;
	public void atualizar(Filme filme)throws FilmeNaoEncontradoException, FileNotFoundException, IOException;
	public boolean temFilme(String nome);
	public IteratorFilme getIterator();
	
}
