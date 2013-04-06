package colecaoNegocios;

import iterator.IteratorRelatorio;
import repositorio.RepositorioRelatorio;

public class ControleRelatorios {

	private RepositorioRelatorio repRelatorio;
	
	public ControleRelatorios(RepositorioRelatorio repRelatorio){
		this.setRepRelatorio(repRelatorio);
	}

	public RepositorioRelatorio getRepRelatorio() {
		return repRelatorio;
	}

	public void setRepRelatorio(RepositorioRelatorio repRelatorio) {
		this.repRelatorio = repRelatorio;
	}
	
	public IteratorRelatorio getIteratorRelatorio(){
		return this.repRelatorio.getIterator();
	}
}
