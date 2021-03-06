package negocio.colecao;

import java.io.FileNotFoundException;
import java.io.IOException;

import iterator.IteratorSala;
import negocio.base.Sala;
import repositorio.sala.RepositorioSala;
import repositorio.sala.SalaConflitanteException;
import repositorio.sala.SalaNaoEncontradaException;

public class CadastroSala {
	
	private RepositorioSala repSala;
	
	public CadastroSala(RepositorioSala repSala) {
		this.setRepSala(repSala);
	}

	public RepositorioSala getRepSala() {
		return repSala;
	}

	public void setRepSala(RepositorioSala repSala) {
		this.repSala = repSala;
	}
	
	public void criarSala(String codigo, int numFilas, int numColunas) throws SalaConflitanteException, FileNotFoundException, IOException{
		Sala sala = new Sala(codigo, numFilas, numColunas);
		//Checa a existencia da sala para se preciso levantar o erro
		if(this.repSala.temSala(codigo)) throw new SalaConflitanteException();
		this.repSala.inserir(sala);
	}
	
	public void removerSala(String codigo) throws SalaNaoEncontradaException, FileNotFoundException, IOException{
		this.repSala.remover(codigo);
	}
	
	public IteratorSala getIteratorSala() throws SalaNaoEncontradaException{
		return this.repSala.getIterator();
	}
	
	public Sala procurarSala(String codigo) throws SalaNaoEncontradaException{
		Sala retorno;
		retorno = repSala.buscar(codigo);
		return retorno;
	}
	
	public void atualizaSala(Sala sala) throws SalaNaoEncontradaException, IOException{
		this.repSala.atualizar(sala);
	}

}
