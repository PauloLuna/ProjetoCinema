import iterator.IteratorFilme;

import java.io.*;
import java.util.*;

import repositorio.*;
import negocio.*;


public class Teste {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		RepositorioFilme rep= new RepositorioFilmeExel("teste.xls","Filmes");

		//RepositorioFilme rep= new RepositorioFilmeArray();
		GregorianCalendar gc = new GregorianCalendar();

		Filme filme1 = new Filme("teste1", new Date(0, 0, 0, 1, 30, 20), "Drama", "Livre", "é bom véi");
		Filme filme2 = new Filme("teste2", new Date(0, 0, 0, 1, 30, 20), "Drama", "Livre", "é bom véi");
		Date teste = new Date(0, 0, 0, 1, 30, 20);
		Filme filme3 = new Filme("teste3", teste, "Drama", "Livre", "é bom véi");
		gc.setTime(teste);
		gc.add(Calendar.MINUTE, 16);
		Filme filme = new Filme("teste4", gc.getTime(), "Drama", "Livre", "é bom véi");

		rep.inserir(filme1);
		rep.inserir(filme2);
		rep.inserir(filme3);
		rep.inserir(filme);
		
		Sessao sessao = new Sessao("1", filme, "sala", teste);
		
		

		try {
			System.out.println(rep.buscar("teste3").getDuracao());
		} catch (FilmeNaoEncontradoException e) {

			System.out.println(e.getMessage());
		}

		try {
			rep.remover("teste1");
			rep.remover("teste3");
			rep.remover("teste");
		} catch (FilmeNaoEncontradoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		try {
			System.out.println(rep.buscar("teste4").getDuracao());
		} catch (FilmeNaoEncontradoException e) {

			System.out.println(e.getMessage());
		}
		filme1.setNome("Ultimo inserido");
		rep.inserir(filme1);

		IteratorFilme itr = rep.getIterator();

		while(itr.hasNext()){
			Filme film = itr.next();

			System.out.println("Nome: "+film.getNome()+" Duração: "+film.getDuracao()+
					"Descrição: "+film.getDescricao());
		}
		

		RepositorioSessao repSess = new RepositorioSessaoExel("teste.xls", "Sessoes");
		
		
		try {
			repSess.inserir(sessao);
			System.out.println("inserido");
			repSess.inserir(sessao);
			System.out.println("não devia aparecer");
		} catch (SessaoConflitanteException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	}
}
