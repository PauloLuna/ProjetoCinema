package fachada;

import java.io.File;
import java.io.IOException;

import colecaoNegocios.*;

public class Fachada {
	
	private CadastroFilme cadFilme;
	private CadastroSessao cadSessao;
	
	public Fachada() throws IOException{
		File dir = new File("repositorios");
		dir.mkdirs();
		this.cadFilme = new CadastroFilme();
		this.cadSessao = new CadastroSessao();
	}

	public CadastroFilme getCadFilme() {
		return this.cadFilme;
	}

	public CadastroSessao getCadSessao() {
		return this.cadSessao;
	}
	
}
