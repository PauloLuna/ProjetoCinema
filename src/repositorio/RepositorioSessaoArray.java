package repositorio;

import basicas.Sessao;
import iterator.IteratorSessao;
import iterator.IteratorSessaoArray;

public class RepositorioSessaoArray implements RepositorioSessao{
	private int indice;
	private Sessao[] sessoes;
	
	public RepositorioSessaoArray(){
		this.indice = 0;
		this.sessoes = new Sessao[50];
	}


	public Sessao buscar(String id) throws SessaoNaoEncontradaException {
		Sessao retorno = null;
		
		retorno = this.sessoes[this.buscarIndice(id)];			
		 
		return retorno;
	}


	public void inserir(Sessao sessao) throws SessaoConflitanteException {
		
		if(this.indice == this.sessoes.length){
			Sessao[] apoio = new Sessao[this.sessoes.length*2];
			for(int i = 0; i<this.indice; i++){
				apoio[i] = this.sessoes[i];
			}
			this.sessoes = apoio;
		}
		for(int i = 0; i< this.indice; i++){
			if(this.sessoes[i].checaConflito(sessao)){
				throw new SessaoConflitanteException();
			}
		}
		this.sessoes[indice] = sessao;
		this.indice++;
	}


	public void remover(String id) throws SessaoNaoEncontradaException {
		int inicio = buscarIndice(id);
		
			this.sessoes[inicio] = this.sessoes[this.indice-1];
			this.sessoes[this.indice-1] = null;
			this.indice--;
		
	}

	public void atualizar(Sessao sessao) throws SessaoNaoEncontradaException {
		this.sessoes[buscarIndice(sessao.getId())] = sessao;
	}
	
	private int buscarIndice(String id) throws SessaoNaoEncontradaException{
		int retorno = -1;
		boolean continuar=true;
		
		
		for(int i = 0; i < this.indice && continuar; i++){
			if (sessoes[i].getId().equals(id)){
				retorno = i;
				continuar = false;
			}
		}
		if (retorno ==-1) throw new SessaoNaoEncontradaException();
		return retorno;
	}


	public IteratorSessao getIterator() {
		return new IteratorSessaoArray(this.sessoes);
	}


	@Override
	public boolean temSessao(String id) {
		boolean retorno = true;
		
		try {
			buscarIndice(id);
		} catch (SessaoNaoEncontradaException e) {
			retorno = false;
		}
		
		return retorno;
	}

}
