package repositorio.sessao;

import negocio.base.Sessao;
import iterator.IteratorSessao;
import iterator.IteratorSessaoLista;

public class RepositorioSessaoLista implements RepositorioSessao {

	private Sessao sessao;
	private RepositorioSessaoLista proximo;

	public RepositorioSessaoLista(){
		//Cada novo elemento tem seus valores como nulos
		this.sessao = null;
		this.proximo = null;
	}
	
	public Sessao buscar(String id) throws SessaoNaoEncontradaException {
		Sessao retorno = null;
		
		
		if(this.sessao==null){//Checa fim da lista se chegar lá retorna a excessão
			throw new SessaoNaoEncontradaException();
		} else if(this.sessao.getId().equals(id)){//Checa se o Sessao é o procurado se sim o retorna
			retorno = this.sessao;
		} else {
			retorno = this.proximo.buscar(id);
		}
		
		return retorno;
	}

	public void inserir(Sessao sessao) throws SessaoConflitanteException {
		if(this.sessao==null){//Checa fim de lista se achar insere os dados
			this.sessao = sessao;
			this.proximo = new RepositorioSessaoLista();
		} else {//Se não manda o proximo elemento tentar inserir
			if(this.sessao.checaConflito(sessao)) throw new SessaoConflitanteException();
			this.proximo.inserir(sessao);
		}
	}

	public void remover(String id) throws SessaoNaoEncontradaException {
		
		if(this.sessao==null){//Checa fim de lista se chagar retorna a excessão
			throw new SessaoNaoEncontradaException();
		} else if(this.sessao.getId().equals(id)){//Se for a Sessao procurada a remove coloando nela os valores da próxima
			this.sessao = this.proximo.sessao;
			this.proximo = this.proximo.proximo;
		} else {//em ultimo caso manda o proximo checar a remoção
			this.proximo.remover(id);
		}
		
	}

	public void atualizar(Sessao sessao) throws SessaoNaoEncontradaException {
		//Substitui o Sessao por um outro com o mesmo id, os dados podem ser diferentes
		if(this.sessao==null){
			throw new SessaoNaoEncontradaException();
		} else if(this.sessao.getId().equals(sessao.getId())){
			this.sessao = sessao;
		} else {
			this.proximo.atualizar(sessao);
		}
		
	}
	
	public IteratorSessao getIterator(){
		return new IteratorSessaoLista(this.sessao, this.proximo);
	}

	@Override
	public boolean temSessao(String id) {
		boolean retorno = true;
		try{
			buscar(id);
		}catch(SessaoNaoEncontradaException e){
			retorno = false;
		}
		
		return retorno;
	}

}
