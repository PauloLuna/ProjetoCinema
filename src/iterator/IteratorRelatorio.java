package iterator;

import negocio.base.Relatorio;
import repositorio.relatorio.RepositorioRelatorio;

public class IteratorRelatorio{

	private Relatorio relatorio;
	private RepositorioRelatorio proximo;
	
	public IteratorRelatorio(Relatorio relatorio, RepositorioRelatorio proximo){
		
		this.relatorio = relatorio;
		this.proximo = proximo;
	}
	
	public boolean hasNext() {
		return(this.relatorio != null);
	}

	public Relatorio next() {
		Relatorio retorno = this.relatorio;
		//Obt�m os dados do iterator do pr�ximo elemento da lista
		IteratorRelatorio itr = (IteratorRelatorio)this.proximo.getIterator();
		this.relatorio = itr.relatorio;
		this.proximo = itr.proximo;
		return retorno;
	}

}
