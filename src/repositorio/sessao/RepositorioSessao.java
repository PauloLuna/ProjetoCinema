package repositorio.sessao;

import iterator.IteratorSessao;

import java.io.FileNotFoundException;
import java.io.IOException;


import negocio.base.Sessao;



public interface RepositorioSessao {

	public Sessao buscar(String nome) throws SessaoNaoEncontradaException, TipoDeObjetoNaoSuportado;
	public void inserir(Sessao sessao) throws FileNotFoundException, IOException,SessaoConflitanteException, TipoDeObjetoNaoSuportado;
	public void remover(String nome)throws SessaoNaoEncontradaException, FileNotFoundException, IOException, TipoDeObjetoNaoSuportado;
	public void atualizar(Sessao Sessao)throws SessaoNaoEncontradaException, FileNotFoundException, IOException, TipoDeObjetoNaoSuportado, SessaoConflitanteException;
	public boolean temSessao(String nome);
	public IteratorSessao getIterator() throws TipoDeObjetoNaoSuportado;
	
}
