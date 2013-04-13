package repositorio.sala;

import iterator.IteratorSala;

import java.io.FileNotFoundException;
import java.io.IOException;

import negocio.base.Sala;



public interface RepositorioSala {

	public Sala buscar(String nome) throws SalaNaoEncontradaException;
	public void inserir(Sala sessao) throws FileNotFoundException, IOException,SalaConflitanteException;
	public void remover(String nome)throws SalaNaoEncontradaException, FileNotFoundException, IOException;
	public void atualizar(Sala Sala)throws SalaNaoEncontradaException, FileNotFoundException, IOException;
	public boolean temSala(String nome);
	public IteratorSala getIterator();
	
}
