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

	private Sheet folha;//Variável que contém a planilha sendo usada
	private Workbook wb;//Variável que contém o arquivo do exel virtualmente
	private String nomeArquivo;

	public RepositorioSalaExcel(String nomeArquivo, String nomeFolha) throws IOException{
		this.nomeArquivo = nomeArquivo;
		try{//Tenta abrir os dados do arquivo
			FileInputStream arquivo = new FileInputStream(nomeArquivo);
			wb = new HSSFWorkbook(arquivo);
			folha = wb.getSheet(nomeFolha);
			arquivo.close();
		}catch(FileNotFoundException e){//Se o arquivo não for encontrado cria os dados de um novo arquivo
			wb = new HSSFWorkbook();
			folha = wb.createSheet(nomeFolha);
		}

	}

	@Override
	public Sala buscar(String nome) throws SalaNaoEncontradaException {
		int indice = buscarIndice(nome);//Procura o índice do filme na planilha
		Row row = this.folha.getRow(indice);//Copia a linha
		Sala sala =	ManipuladorSalaExcel.leSala(row);
		return sala;
	}

	@Override
	public void inserir(Sala sala) throws FileNotFoundException, IOException {
		int indice = 0;
		while(this.folha.getRow(indice)!=null) indice++;//Obtém o primeiro índice de linha livre para não sobrecarregar a planilha
		Row row = folha.createRow(indice);// cria uma nova linha
		ManipuladorSalaExcel.escreveSala(row, sala);
		gravar();

	}

	@Override
	public void remover(String nome) throws SalaNaoEncontradaException,
	FileNotFoundException, IOException {
		int indice = buscarIndice(nome);//obtém o índice do filme

		this.folha.removeRow(this.folha.getRow(indice));//Remove a linha da planilha deixando como nula	

		gravar();
	}

	@Override
	public void atualizar(Sala sala) throws SalaNaoEncontradaException,
	FileNotFoundException, IOException {
		remover(sala.getCodigo());//Remove a versão antiga
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

		int apoio = this.folha.getLastRowNum()+2;//Obtém a índice após a ultima linha
		boolean continua = true;//Checa até a primeira remoção
		int i;
		for(i = 0; i< apoio && continua; i++){
			Row row = this.folha.getRow(i);
			if(row!=null&&row.getCell(0).getStringCellValue().equals(codigo)){
				//Se a linha não for nula checa a celula de nome, se corresponder finalliza a busca
				continua = false;
			}
		}
		if(i==apoio) i = 0;//Checa se não foi encontrado
		retorno = i-1;//Calcula o índice
		if(retorno == -1)throw new SalaNaoEncontradaException();//Se não foi encontrado ou não existem dados retorna uma excessão
		return retorno;
	}

	private void gravar() throws IOException{
		//Finaliza as gravações no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();
	}

}
