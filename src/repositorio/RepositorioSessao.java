package repositorio;

import iterator.IteratorSessao;

import java.io.FileNotFoundException;
import java.io.IOException;

import basicas.Sessao;


public interface RepositorioSessao {

	public Sessao buscar(String nome) throws SessaoNaoEncontradaException;
	public void inserir(Sessao sessao) throws FileNotFoundException, IOException,SessaoConflitanteException;
	public void remover(String nome)throws SessaoNaoEncontradaException, FileNotFoundException, IOException;
	public void atualizar(Sessao Sessao)throws SessaoNaoEncontradaException, FileNotFoundException, IOException;
	public boolean temSessao(String nome);
	public IteratorSessao getIterator();
	
}
