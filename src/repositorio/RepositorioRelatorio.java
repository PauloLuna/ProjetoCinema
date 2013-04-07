package repositorio;

import java.io.Serializable;

import iterator.IteratorRelatorio;
import basicas.Relatorio;


public class RepositorioRelatorio implements Serializable{

	private Relatorio relatorio;
	private RepositorioRelatorio proximo;

	public RepositorioRelatorio(){
		//Cada novo elemento tem seus valores como nulos
		this.relatorio = null;
		this.proximo = null;

	}

	public Relatorio buscar(String nome) throws RelatorioNaoEncontradoException {
		Relatorio retorno = null;


		if(this.relatorio==null){//Checa fim da lista se chegar lá retorna a excessão
			throw new RelatorioNaoEncontradoException();
		} else if(this.relatorio.getNomeSala().equals(nome)){//Checa se o relatorio é o procurado se sim o retorna
			retorno = this.relatorio;
		} else {
			retorno = this.proximo.buscar(nome);
		}

		return retorno;
	}

	public void inserir(Relatorio relatorio){
		if(this.relatorio==null){//Checa fim de lista se achar insere os dados
			this.relatorio = relatorio;
			this.proximo = new RepositorioRelatorio();
		} else {//Se não manda o proximo elemento tentar inserir
			this.proximo.inserir(relatorio);
		}		
	}

	public void remover(String nome) throws RelatorioNaoEncontradoException{

		if(this.relatorio==null){//Checa fim de lista se chagra retorna a excessão
			throw new RelatorioNaoEncontradoException();
		} else if(this.relatorio.getNomeSala().equals(nome)){//Se for o relatorio procurado o remove coloando nele os valores do próximo
			this.relatorio = this.proximo.relatorio;
			this.proximo = this.proximo.proximo;
		} else {//em ultimo caso manda o proximo checar a remoção
			this.proximo.remover(nome);
		}
		
		

	}

	public void atualizar(Relatorio relatorio) throws RelatorioNaoEncontradoException {
		//Substitui o relatorio por um outro com o mesmo nome, os dados podem ser diferentes
		if(this.relatorio==null){
			throw new RelatorioNaoEncontradoException();
		} else if(this.relatorio.getNomeSala().equals(relatorio.getNomeSala())){
			this.relatorio = relatorio;
		} else {
			this.proximo.atualizar(relatorio);
		}
		

	}

	public IteratorRelatorio getIterator(){
		return new IteratorRelatorio(this.relatorio, this.proximo);
	}


	public boolean temRelatorio(String sala) {
		boolean retorno = true;
		try{
			buscar(sala);
		}catch(RelatorioNaoEncontradoException e){
			retorno = false;
		}

		return retorno;
	}

	
}