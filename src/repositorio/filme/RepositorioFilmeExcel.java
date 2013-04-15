package repositorio.filme;

import iterator.IteratorFilme;
import iterator.IteratorFilmeExel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import negocio.base.Filme;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;



public class RepositorioFilmeExcel implements RepositorioFilme{

	private Sheet folha;//Vari�vel que cont�m a planilha sendo usada
	private Workbook wb;//Vari�vel que cont�m o arquivo do exel virtualmente
	private String nomeArquivo;
	
	public RepositorioFilmeExcel(String nomeArquivo, String nomeFolha) throws IOException{
		this.nomeArquivo = nomeArquivo;
		try{//Tenta abrir os dados do arquivo
			FileInputStream arquivo = new FileInputStream(nomeArquivo);
			wb = new HSSFWorkbook(arquivo);
			folha = wb.getSheet(nomeFolha);
			arquivo.close();
		}catch(FileNotFoundException e){//Se o arquivo n�o for encontrado cria os dados de um novo arquivo
			wb = new HSSFWorkbook();
			folha = wb.createSheet(nomeFolha);
		}
		
	}

	public Filme buscar(String nome) throws FilmeNaoEncontradoException {
		Filme retorno = null;
		
		int indice = buscarIndice(nome);//Procura o �ndice do filme na planilha
		Row row = this.folha.getRow(indice);//Copia a linha

		//Copia os valores das celulas para vari�veis
		String nome2 = row.getCell(0).getStringCellValue();
		long duracao1 = (long)row.getCell(1).getNumericCellValue();
		Date duracao=new Date(duracao1);//Converte o valor da dura��o em long para um objeto tipo Date
		String categoria = row.getCell(2).getStringCellValue();
		String classificacao = row.getCell(3).getStringCellValue();
		String descricao = row.getCell(4).getStringCellValue();

		retorno = new Filme(nome2, duracao, categoria, classificacao, descricao);
		//Inst�ncia um filme com os dados para ser retornado


		return retorno;
	}

	public void inserir(Filme filme) throws FileNotFoundException, IOException {
		int indice = 0;
		while(this.folha.getRow(indice)!=null) indice++;//Obt�m o primeiro �ndice de linha livre para n�o sobrecarregar a planilha
		Row row = folha.createRow(indice);// cria uma nova linha
		//Copia os dados do objeto para a linha
		Cell cell = row.createCell(0);		
		cell.setCellValue(filme.getNome());
		cell = row.createCell(1);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);//Declara esta c�lula como num�rica para receber a data
		cell.setCellValue(filme.getDuracao().getTime());//Retorna a data como um long		
		cell = row.createCell(2);		
		cell.setCellValue(filme.getCategoria());
		cell = row.createCell(3);
		cell.setCellValue(filme.getClassificacao());
		cell = row.createCell(4);
		cell.setCellValue(filme.getDescricao());
				

		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();

	}

	public void remover(String nome) throws FilmeNaoEncontradoException, FileNotFoundException,IOException {
		int indice = buscarIndice(nome);//obt�m o �ndice do filme

		this.folha.removeRow(this.folha.getRow(indice));//Remove a linha da planilha deixando como nula	


		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();

	}

	public void atualizar(Filme filme) throws FilmeNaoEncontradoException, FileNotFoundException,IOException {
		
		
		int indice = buscarIndice(filme.getNome());
		
		Row row = this.folha.getRow(indice);//Obtem a linha com o objeto
		//Copia os dados do objeto para a linha
		Cell cell = row.getCell(0);
		cell.setCellValue(filme.getNome());
		cell = row.getCell(1);
		cell.setCellValue(filme.getDuracao().getTime());//Insere a data como um long		
		cell = row.getCell(2);		
		cell.setCellValue(filme.getCategoria());
		cell = row.getCell(3);
		cell.setCellValue(filme.getClassificacao());
		cell = row.getCell(4);
		cell.setCellValue(filme.getDescricao());
				
		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();


	}
	
	private int buscarIndice(String nome) throws FilmeNaoEncontradoException{
		int retorno;
		
		int apoio = this.folha.getLastRowNum()+2;//Obt�m a �ndice ap�s a ultima linha
		boolean continua = true;//Checa at� a primeira remo��o
		int i;
		for(i = 0; i< apoio && continua; i++){
			Row row = this.folha.getRow(i);
			if(row!=null&&row.getCell(0).getStringCellValue().equals(nome)){
				//Se a linha n�o for nula checa a celula de nome, se corresponder finalliza a busca
				continua = false;
			}
		}
		if(i==apoio) i = 0;//Checa se n�o foi encontrado
		retorno = i-1;//Calcula o �ndice
		if(retorno == -1)throw new FilmeNaoEncontradoException();//Se n�o foi encontrado ou n�o existem dados retorna uma excess�o
		return retorno;
	}

	public IteratorFilme getIterator() {
		return  new IteratorFilmeExel(this.folha);		
	}

	
	public boolean temFilme(String nome) {
		boolean retorno = true;
		
		try {
			buscarIndice(nome);
		} catch (FilmeNaoEncontradoException e) {
			retorno = false;
		}
		
		return retorno;
	}

}
