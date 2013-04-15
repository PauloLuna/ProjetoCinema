package iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import repositorio.sala.ManipuladorSalaExcel;

import negocio.base.Cadeira;
import negocio.base.Sala;

public class IteratorSalaExcel implements IteratorSala {

	private Sheet folha;
	private int indice;
	
	public IteratorSalaExcel(Sheet folha) {
		this.folha = folha;
		this.indice = folha.getFirstRowNum();//Começa o índice com a primeira linha válida
	}
	@Override
	public boolean hasNext() {
		Row row = this.folha.getRow(indice);
		return row!=null;
	}

	@Override
	public Sala next() {
		Row row  = this.folha.getRow(indice);
		int fim = this.folha.getLastRowNum();
		Sala sala =	ManipuladorSalaExcel.leSala(row);
		
		do{//Procur o próximo índice válido
			this.indice++;
		}while(this.folha.getRow(indice)==null && this.indice<=fim);
				
		return sala;
	}

}
