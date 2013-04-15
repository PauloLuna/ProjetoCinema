package repositorio.sala;

import iterator.IteratorFilmeExel;
import iterator.IteratorSala;
import iterator.IteratorSalaExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import repositorio.filme.FilmeNaoEncontradoException;

import negocio.base.Cadeira;
import negocio.base.Sala;

public class RepositorioSalaExcel implements RepositorioSala {

	private Sheet folha;//Vari�vel que cont�m a planilha sendo usada
	private Workbook wb;//Vari�vel que cont�m o arquivo do exel virtualmente
	private String nomeArquivo;

	public RepositorioSalaExcel(String nomeArquivo, String nomeFolha) throws IOException{
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

	@Override
	public Sala buscar(String nome) throws SalaNaoEncontradaException {
		int indice = buscarIndice(nome);//Procura o �ndice do filme na planilha
		Row row = this.folha.getRow(indice);//Copia a linha
		Sala sala =	ManipuladorSalaExcel.leSala(row);
		return sala;
	}

	@Override
	public void inserir(Sala sala) throws FileNotFoundException, IOException {
		int indice = 0;
		while(this.folha.getRow(indice)!=null) indice++;//Obt�m o primeiro �ndice de linha livre para n�o sobrecarregar a planilha
		Row row = folha.createRow(indice);// cria uma nova linha
		ManipuladorSalaExcel.escreveSala(row, sala);
		gravar();

	}

	@Override
	public void remover(String nome) throws SalaNaoEncontradaException,
	FileNotFoundException, IOException {
		int indice = buscarIndice(nome);//obt�m o �ndice do filme

		this.folha.removeRow(this.folha.getRow(indice));//Remove a linha da planilha deixando como nula	

		gravar();
	}

	@Override
	public void atualizar(Sala sala) throws SalaNaoEncontradaException,
	FileNotFoundException, IOException {
		remover(sala.getCodigo());//Remove a vers�o antiga
		inserir(sala);//Insere a nova
		gravar();

	}

	@Override
	public boolean temSala(String nome) {
		boolean retorno = true;

		try {
			buscarIndice(nome);
		} catch (SalaNaoEncontradaException e) {
			retorno = false;
		}

		return retorno;
	}

	@Override
	public IteratorSala getIterator() throws SalaNaoEncontradaException {
		return  new IteratorSalaExcel(this.folha);	
	}

	private int buscarIndice(String codigo) throws SalaNaoEncontradaException{
		int retorno;

		int apoio = this.folha.getLastRowNum()+2;//Obt�m a �ndice ap�s a ultima linha
		boolean continua = true;//Checa at� a primeira remo��o
		int i;
		for(i = 0; i< apoio && continua; i++){
			Row row = this.folha.getRow(i);
			if(row!=null&&row.getCell(0).getStringCellValue().equals(codigo)){
				//Se a linha n�o for nula checa a celula de nome, se corresponder finalliza a busca
				continua = false;
			}
		}
		if(i==apoio) i = 0;//Checa se n�o foi encontrado
		retorno = i-1;//Calcula o �ndice
		if(retorno == -1)throw new SalaNaoEncontradaException();//Se n�o foi encontrado ou n�o existem dados retorna uma excess�o
		return retorno;
	}

	private void gravar() throws IOException{
		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();
	}

}
