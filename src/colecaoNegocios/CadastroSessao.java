package colecaoNegocios;

import iterator.IteratorSessao;

import java.io.FileNotFoundException;
import java.io.IOException;

import repositorio.*;

public class CadastroSessao {
	
	private RepositorioSessao repSessao;
	
	public CadastroSessao(RepositorioSessao repSessao) throws IOException{
		this.repSessao = repSessao;
	}
	
	public IteratorSessao getIteratorSessao() {
		return this.repSessao.getIterator();
	}

	public void removerSessao(String idSessao) throws FileNotFoundException, SessaoNaoEncontradaException, IOException {
		this.repSessao.remover(idSessao);
		
	}	

}
