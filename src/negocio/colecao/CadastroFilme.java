package negocio.colecao;

import iterator.IteratorFilme;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import negocio.base.Filme;


import repositorio.filme.FilmeJaExistenteException;
import repositorio.filme.FilmeNaoEncontradoException;
import repositorio.filme.RepositorioFilme;

public class CadastroFilme {

	private RepositorioFilme repFilme;

	public CadastroFilme(RepositorioFilme repFilme) throws IOException{
		this.repFilme = repFilme;
	}

	public void cadastrarFilme(String nome, int hs, int min, int seg, String classificacao, String categoria, String descricao) throws FileNotFoundException, IOException, FilmeJaExistenteException{
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR_OF_DAY, hs);
		gc.set(Calendar.MINUTE, min);
		gc.set(Calendar.SECOND, seg);
		Filme filme = new Filme(nome, gc.getTime(), categoria, classificacao, descricao);

		if(this.repFilme.temFilme(nome))throw new FilmeJaExistenteException();
		this.repFilme.inserir(filme);
	}

	public void atualizarFilme(String nome, int hs, int min, int seg, String classificacao, String categoria, String descricao) throws FileNotFoundException, IOException, FilmeNaoEncontradoException{

		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR_OF_DAY, hs);
		gc.set(Calendar.MINUTE, min);
		gc.set(Calendar.SECOND, seg);
		Filme filme = new Filme(nome, gc.getTime(), categoria, classificacao, descricao);
		this.repFilme.atualizar(filme);
	}

	public void removerFilme(String nomeFilme) throws FileNotFoundException, FilmeNaoEncontradoException, IOException{
		this.repFilme.remover(nomeFilme);
	}

	public Filme buscarFilme(String nomeFilme) throws FilmeNaoEncontradoException {
		return this.repFilme.buscar(nomeFilme);
	}

	public IteratorFilme getIteratorFilme() {
		return this.repFilme.getIterator();
	}

}
