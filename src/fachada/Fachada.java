package fachada;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import repositorio.*;

import colecaoNegocios.*;

public class Fachada {

	private CadastroFilme cadFilme;
	private CadastroSessao cadSessao;
	private ControleRelatorios controleRelatorios;

	public Fachada() throws IOException{
		File dir = new File("repositorios");
		dir.mkdirs();
		
		String tipo = leConfiguracao();
		iniciaControle(tipo);
	}

	public CadastroFilme getCadFilme() {
		return this.cadFilme;
	}

	public CadastroSessao getCadSessao() {
		return this.cadSessao;
	}

	private String leConfiguracao(){
		
		File file = new File("repositorios/config.txt");
		String tipo = "";
		try {
			Scanner scn = new Scanner(file);
			scn.nextLine();
			if(scn.hasNextLine()){
				tipo = scn.nextLine();
			}
			
		} catch (FileNotFoundException e1) {

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

	private void iniciaControle(String tipo) throws IOException{
		RepositorioFilme repFilme;
		RepositorioSessao repSessao;
		RepositorioSala repSala;
		
		if(tipo.equalsIgnoreCase("TAD")){
			
			repFilme = new RepositorioFilmeLista();
			repSessao = new RepositorioSessaoLista();
			repSala = new RepositorioSalaLista();
			
		}else if(tipo.equalsIgnoreCase("Arquivo")){
			
			repFilme = new RepositorioFilmeExel("repositorios/filmes.xls", "Filmes");
			repSessao = new RepositorioSessaoExel("repositorios/sessoes.xls", "Sessoes");
			//repSala = new RepositorioSalaExel("repositorios/salas.xls", "Salas");
			
		}else if(tipo.equalsIgnoreCase("Array")){
			
			repFilme = new RepositorioFilmeArray();
			repSessao = new RepositorioSessaoArray();
			repSala = new RepositorioSalaArray();
			
		}else{
			throw new RuntimeException("Erro ainda vou criar a excessão mas deu merda");
		}
		
		RepositorioRelatorio repRelatorio = new RepositorioRelatorio();
		cadFilme = new CadastroFilme(repFilme);
		cadSessao = new CadastroSessao(repSessao);
		setControleRelatorios(new ControleRelatorios(repRelatorio));
	}

	public ControleRelatorios getControleRelatorios() {
		return controleRelatorios;
	}

	public void setControleRelatorios(ControleRelatorios controleRelatorios) {
		this.controleRelatorios = controleRelatorios;
	}
}
