package repositorio.sala;

import iterator.IteratorSalaArray;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import negocio.base.*;

public class RepositorioSalaExcel implements RepositorioSala {
	private String nomeArquivo;
	private Sheet folha;
	private Workbook wb;
	
	private int indiceFinal;
	
	public RepositorioSalaExcel(String nomeArquivo, String nomeFolha,String nomeFolhaCadeiras) throws IOException{
		this.nomeArquivo = nomeArquivo;
		this.indiceFinal = 0;
		
		// Checando se o Arquivo existe, se n�o cria um novo
		try{
			FileInputStream arquivo = new FileInputStream(nomeArquivo);
			
			wb = new HSSFWorkbook();
			//Pegando as duas planilhas a serem usadas
			this.folha = wb.getSheet(nomeFolha);
			
			arquivo.close();
		}
		catch(FileNotFoundException erro){
			wb = new HSSFWorkbook();
			//Criando as duas planilhas a serem usadas
			this.folha = wb.createSheet(nomeFolha);
		}
		
	}// Fim do contrutor
	
	
	// Métodos da INTERFACE
	public Sala buscar(String codigo) throws SalaNaoEncontradaException {
		int indice;
		int i = 0;
		Sala sala;

		// Buscando e devolvendo o objeto se ele existir
		sala = this.instanciandoSalaObjeto(codigo);
		
		return sala;
	}

	//Métodos da INTERFACE
	public void inserir(Sala sala) throws SalaConflitanteException, IOException {
		//Inserindo o objeto
		this.instanciandoSalaExcel(sala);
	}


	public void remover(String codigo) throws SalaNaoEncontradaException, IOException {
		{
			int indice;
			
			//Buscando row do objeto
			indice = this.getLinhaSala(codigo);
		
			Row row = this.folha.getRow(indice);
			Row rowFinal = this.folha.getRow(indiceFinal - 1);
			
			// Passando o codigo do ultimo para o primeiro
			Cell cell =  row.getCell(0);
			Cell cellFinal = rowFinal.getCell(0);
			
			cell.setCellValue(cellFinal.getStringCellValue());
			
			//Excluíndo as cell dos ultimo objeto
			cellFinal.setCellFormula(null);

			// Passando numFilas
			cell =  row.getCell(1);
			cellFinal = rowFinal.getCell(1);
			
			cell.setCellValue(cellFinal.getStringCellValue());
			
			//Excluíndo as cell dos ultimo objeto
			cellFinal.setCellFormula(null);
			
			// Passando numColunas
		
			cell =  row.getCell(2);
			cellFinal = rowFinal.getCell(2);
			
			cell.setCellValue(cellFinal.getStringCellValue());
			
			//Excluíndo as cell dos ultimo objeto
			cellFinal.setCellFormula(null);
			
			FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
			wb.write(arquivo);
			arquivo.close();
		}
	}

	
	public void atualizar(Sala sala, String codigo) throws SalaNaoEncontradaException, IOException {
		int indice;
					
		// Primeiro removemos o objeto
		this.remover(codigo);
			
		// Atualizando o objeto no Excel
		this.instanciandoSalaExcel(sala);

	}	
	// Retornar qual linha est� o objeto - M�todo privado da classe
	private int getLinhaSala(String codigo) throws SalaNaoEncontradaException{
		// O que indentifica a sala � seu codigo
		int indice = 0;
		boolean procurando = true;		
		int i = 0;
		
		while (procurando){
			Row row = this.folha.getRow(i);
			
			if (row.getCell(i) == null){
				// Se a linha � nula est�o acabou a procura, objeto n�o encontrado
				SalaNaoEncontradaException erro;
				erro = new SalaNaoEncontradaException();
				throw erro;
			}
			else if (row.getCell(0).getStringCellValue().equals(codigo)) {
				indice = i;
				procurando = false;
			}
	
			// Adicionando valores 
			i ++;
		}

		return indice;
	} // Fim do m�todo getLinhaSala
	
	public IteratorSalaArray getIterator() throws SalaNaoEncontradaException {
		IteratorSalaArray iterator;
		Sala[] salas = null;
		int indice = indiceFinal - 1;
		
		salas = new Sala[indiceFinal];
		
		while (indice >= 0) {
			if (indiceFinal == 0){
				iterator = new IteratorSalaArray(salas);
			}
			else {
			salas[indice] = lerObjetoSala(indiceFinal);
			}
		} // Fim do While
	
		iterator = new IteratorSalaArray(salas);
		return iterator;
	}
	// Obtem uma linha do excel(das planilhas folha e folhaCadeira) e instacia um objeto sala e o devove
	private Sala instanciandoSalaObjeto(String codigo) throws SalaNaoEncontradaException{
		Sala sala;
		
		int numFilas;
		int numColunas;
		int indice;
		
		//Buscando objeto
		indice = this.getLinhaSala(codigo);
		
		//Pegando dados do excel
		Row row = this.folha.getRow(indice);
		
		Cell cell = row.getCell(1);
		
		numFilas = Integer.parseInt(cell.getStringCellValue());
				
		cell = row.getCell(2);

		numColunas = Integer.parseInt(cell.getStringCellValue());
	
		// Construindo o objeto sala
		sala = new Sala(codigo,numFilas, numColunas);
		
		return sala;
	}
	
	private Sala lerObjetoSala(int indice) throws SalaNaoEncontradaException{
		Sala sala;
		
		int numFilas;
		int numColunas;
		String codigo;
		
		Cell cell;
		Row row;
		
		//Pegando dados do excel		
		row = this.folha.getRow(indice);
		
		// Pegando codigo
		cell = row.getCell(0);
		codigo = cell.getStringCellValue();		
		
		cell = row.getCell(1);
		
		numFilas = Integer.parseInt(cell.getStringCellValue());
				
		cell = row.getCell(2);

		numColunas = Integer.parseInt(cell.getStringCellValue());
	
		// Construindo o objeto sala
		sala = new Sala(codigo,numFilas, numColunas);
		
		return sala;
	}
		//Obtem um objeto Sala e escreve na linha do objeto
	private void instanciandoSalaExcel(Sala sala) throws IOException{
		Row row;
		Cell cell;
		
		row = this.folha.getRow(this.indiceFinal);
		
		cell = row.getCell(0);
		cell.setCellValue(sala.getCodigo());
		
		cell = row.getCell(1);
		cell.setCellValue(sala.getNumFilas());
		
		cell = row.getCell(2);
		cell.setCellValue(sala.getNumColunas());
		
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();
		
		this.indiceFinal++;
	}


	@Override
	public void atualizar(Sala sala) throws SalaNaoEncontradaException,
			FileNotFoundException, IOException {
		remover(sala.getCodigo());
		try {
			inserir(sala);
		} catch (SalaConflitanteException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean temSala(String nome) {
		boolean retorno = true;
		try {
			buscar(nome);
		} catch (SalaNaoEncontradaException e) {
			retorno = false;
		}
		return retorno;
	}

} // Fim da classe Repositorio Excel
