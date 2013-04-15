package repositorio.sala;

import negocio.base.Cadeira;
import negocio.base.Sala;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ManipuladorSalaExcel {

	
	public static void escreveSala(Row row, Sala sala) {
		//Processo de serialização de uma sala

		int indice = 0;
		Cell cell = row.createCell(indice);	
		indice++;
		cell.setCellValue(sala.getCodigo());
		cell = row.createCell(indice);
		indice++;
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(sala.getNumFilas());
		cell = row.createCell(indice);
		indice++;
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(sala.getNumColunas());

		Cadeira[][] cadeiras = sala.getCadeiras();

		cell = row.createCell(indice);
		indice++;
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(cadeiras.length);
		//Insere a dimensão do array de arrays

		for(int i = 0; i< cadeiras.length; i++){
			//Insere a dimensão de cada array de cadeira

			cell = row.createCell(indice);
			indice++;
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(cadeiras[i].length);			

			//A cadeira é representada por duas letras t(true) ou f(false) para indicar seu estado
			for(int j = 0; j< cadeiras[i].length; j++){
				String cadeira = "";
				if(cadeiras[i][j].getCadeiraComprada()) cadeira+="t";
				else cadeira+="f";
				if(cadeiras[i][j].getCadeiraQuebrada()) cadeira+="t";
				else cadeira+="f";

				cell = row.createCell(indice);
				indice++;
				cell.setCellValue(cadeira);
			}
		}

	}

	public static Sala leSala(Row row) {
		//Processo de deserialização de uma sala
		int indice = 0;
		Cell cell = row.getCell(indice);
		indice++;
		String codigo = cell.getStringCellValue();
		cell = row.getCell(indice);
		indice++;
		int numFilas = (int)cell.getNumericCellValue();
		cell = row.getCell(indice);
		indice++;
		int numColunas = (int)cell.getNumericCellValue();

		Sala sala = new Sala(codigo, numFilas, numColunas);

		cell = row.getCell(indice);
		indice++;
		int maximoI = (int)cell.getNumericCellValue();	
		//Lê a dimensão do array de array

		Cadeira[][] cadeiras = new Cadeira[maximoI][];

		for(int i = 0; i< maximoI; i++){

			cell = row.getCell(indice);
			indice++;
			int maximoJ = (int)cell.getNumericCellValue();	

			cadeiras[i] = new Cadeira[maximoJ];
			//Lê a dimensão de cada array

			for(int j = 0; j< maximoJ; j++){
				cell = row.getCell(indice);
				indice++;
				String cadeira = cell.getStringCellValue();
				cadeiras[i][j] = new Cadeira();
				//A cadeira é representada por duas letras t(true) ou f(false) para indicar seu estado
				if(cadeira.charAt(0)=='t') cadeiras[i][j].setCadeiraComprada(true);
				else cadeiras[i][j].setCadeiraComprada(false);
				if(cadeira.charAt(1)=='t') cadeiras[i][j].setCadeiraQuebrada(true);
				else cadeiras[i][j].setCadeiraQuebrada(false);
			}
		}

		sala.setCadeiras(cadeiras);

		return sala;
	}
}
