package repositorio.sala;

import negocio.base.Sala;
import iterator.IteratorSalaArray;
public interface RepositorioSala  {
	
		Sala buscar(String codigo) throws SalaNaoAchada;
		
		void inserir(Sala sala) throws SalaExistente;
		
		void remover(String codigo) throws SalaNaoAchada;
		
		void atualizar(Sala sala,String codigo) throws SalaNaoAchada;
		
		IteratorSalaArray getIterator();
		
		
			
}

