package gui;

import iterator.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import repositorio.relatorio.TextoRelatorio;

import negocio.base.*;

import fachada.Fachada;

public class Programa {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		Fachada fachada = null;
		try {
			fachada  = new Fachada();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Filme filme1 = new Filme("Filme 1", df.parse("01:20:10"), "Drama", "+10", "Isso é o teste padrão");
		
		try {
			fachada.getCadFilme().cadastrarFilme("Filme 1", 1, 20, 10, "Drama", "+10", "Isso é o teste padrão");
			fachada.getCadSala().criarSala("Sala 1", 10, 10);
			Sala sala = new Sala("Sala 1", 10, 10);
			Sessao sessao = new SessaoPublicaFilme("1", filme1, sala, df.parse("13:10:20"));
			fachada.getCadSessao().inserirSessao(sessao);
			
			
			fachada.getCadFilme().cadastrarFilme("Filme 2", 1, 20, 10, "Drama", "+10", "Isso é o teste padrão");
			fachada.getCadSala().criarSala("Sala 2", 10, 10);
			
			IteratorFilme itr = fachada.getCadFilme().getIteratorFilme();
			while(itr.hasNext()){
				Filme filme = itr.next();
				System.out.println("Filme: "+filme.getNome()+" Duração: "+df.format(filme.getDuracao())+ filme.getCategoria() + filme.getClassificacao()+ filme.getDescricao());
			}
			IteratorSessao itr2 = fachada.getCadSessao().getIteratorSessao();
			
			while(itr2.hasNext()){
				sessao = itr2.next();
				System.out.println("Titulo: "+sessao.getTitulo()+" Início: "+df.format(sessao.getHoraInicio())+" Fim: " +df.format(sessao.getHoraFim()));
			}
			
			fachada.getCadSessao().removerSessao("0");
			IteratorRelatorio itrRelatorio= fachada.getControleRelatorios().getIteratorRelatorio();
			
			while(itrRelatorio.hasNext()){
				Iterator itrTexto = itrRelatorio.next().getTexto().iterator();
				while(itrTexto.hasNext()){
					Object texto = itrTexto.next();
					System.out.println(((TextoRelatorio)texto).getTexto());
				}
			}
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}

}
