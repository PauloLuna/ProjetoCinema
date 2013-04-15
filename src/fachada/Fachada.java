package fachada;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import negocio.colecao.*;

import repositorio.filme.RepositorioFilme;
import repositorio.filme.RepositorioFilmeArray;
import repositorio.filme.RepositorioFilmeExcel;
import repositorio.filme.RepositorioFilmeLista;
import repositorio.relatorio.*;
import repositorio.sala.*;
import repositorio.sessao.RepositorioSessao;
import repositorio.sessao.RepositorioSessaoArray;
import repositorio.sessao.RepositorioSessaoExcel;
import repositorio.sessao.RepositorioSessaoLista;


public class Fachada {

	//Objetos de controle dos dados
	private CadastroFilme cadFilme;
	private CadastroSessao cadSessao;
	private ControleRelatorios controleRelatorios;
	private CadastroSala cadSala;

	public Fachada() throws IOException, FalhaNaConfiguracaoException, ClassNotFoundException{
		File dir = new File("meuCine/repositorios");//Diretório para os arquivos do programa
		dir.mkdirs();

		String tipo = leConfiguracao();
		iniciaControle(tipo);
	}

	private String leConfiguracao(){

		File file = new File("meuCine/repositorios/config.txt");//Arquivo de configuração
		String tipo = "";
		try {
			Scanner scn = new Scanner(file);
			scn.nextLine();
			if(scn.hasNextLine()){
				tipo = scn.nextLine();
			}

		} catch (FileNotFoundException e1) {
			//Caso o Arquivo não exista cria um arquivo padrão

			FileWriter fw;
			BufferedWriter bw;
			try {
				fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				bw.write("//Arquivo de configuração dos repositórios. Opções possíveis: TAD (para estruturas de dados), Arquivo, Array");
				bw.newLine();
				bw.write("Arquivo");
				tipo = "Arquivo";
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return tipo;
	}

	private void iniciaControle(String tipo) throws IOException, FalhaNaConfiguracaoException, ClassNotFoundException{
		RepositorioFilme repFilme;
		RepositorioSessao repSessao;
		RepositorioSala repSala = null;
		//Seleção e inicialização dos diferentes tipos de repositório

		if(tipo.equalsIgnoreCase("TAD")){

			repFilme = new RepositorioFilmeLista();
			repSessao = new RepositorioSessaoLista();
			repSala = new RepositorioSalaLista();

		}else if(tipo.equalsIgnoreCase("Arquivo")){

			repFilme = new RepositorioFilmeExcel("meuCine/repositorios/filmes.xls", "Filmes");
			repSessao = new RepositorioSessaoExcel("meuCine/repositorios/sessoes.xls", "Sessoes", "Salas");
			repSala = new RepositorioSalaExcel("meuCine/repositorios/salas.xls", "Salas");

		}else if(tipo.equalsIgnoreCase("Array")){

			repFilme = new RepositorioFilmeArray();
			repSessao = new RepositorioSessaoArray();
			repSala = new RepositorioSalaArray();

		}else{
			throw new FalhaNaConfiguracaoException();
		}

		setCadFilme(new CadastroFilme(repFilme));
		setControleRelatorios(new ControleRelatorios());
		setCadSessao(new CadastroSessao(repSessao, controleRelatorios));
		setCadSala(new CadastroSala(repSala));
	}

	public ControleRelatorios getControleRelatorios() {
		return controleRelatorios;
	}

	public void setControleRelatorios(ControleRelatorios controleRelatorios) {
		this.controleRelatorios = controleRelatorios;
	}

	public CadastroSala getCadSala() {
		return cadSala;
	}

	public void setCadSala(CadastroSala cadSala) {
		this.cadSala = cadSala;
	}

	public CadastroFilme getCadFilme() {
		return cadFilme;
	}

	public void setCadFilme(CadastroFilme cadFilme) {
		this.cadFilme = cadFilme;
	}

	public CadastroSessao getCadSessao() {
		return cadSessao;
	}

	public void setCadSessao(CadastroSessao cadSessao) {
		this.cadSessao = cadSessao;
	}
}
