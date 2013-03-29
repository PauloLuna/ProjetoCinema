package fachada;

import gui.TelaAtualizarFilme;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import negocio.Filme;

import repositorio.*;

public class FachadaFilme {
	
	private RepositorioFilme repFilme;
	
	public FachadaFilme() throws IOException{
		repFilme = new RepositorioFilmeExel("teste.xls", "Filmes");
	}
	
	public void cadastrarFilme(String nome, int hs, int min, int seg, String classificacao, String categoria, String descricao) throws FileNotFoundException, IOException, FilmeJaExistenteException{
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR_OF_DAY, hs);
		gc.set(Calendar.MINUTE, min);
		gc.set(Calendar.SECOND, seg);
		Filme filme = new Filme(nome, gc.getTime(), categoria, classificacao, descricao);
		
		if(repFilme.temFilme(nome))throw new FilmeJaExistenteException();
		repFilme.inserir(filme);
	}
	
	public void atualizarFilme(String nome, int hs, int min, int seg, String classificacao, String categoria, String descricao) throws FileNotFoundException, IOException, FilmeNaoEncontradoException{
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR_OF_DAY, hs);
		gc.set(Calendar.MINUTE, min);
		gc.set(Calendar.SECOND, seg);
		Filme filme = new Filme(nome, gc.getTime(), categoria, classificacao, descricao);
		repFilme.atualizar(filme);
	}

}
