package repositorio;

import iterator.IteratorSessao;
import iterator.IteratorSessaoExel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;

import negocio.Filme;
import negocio.Sessao;

public class RepositorioSessaoExel implements RepositorioSessao{

	private Sheet folha;//Vari�vel que cont�m a planilha sendo usada
	private Workbook wb;//Vari�vel que cont�m o arquivo do exel virtualmente
	private String idArquivo;
	
	public RepositorioSessaoExel(String idArquivo, String idFolha) throws IOException{
		this.idArquivo = idArquivo;
		try{//Tenta abrir os dados do arquivo
			FileInputStream arquivo = new FileInputStream(idArquivo);
			wb = new HSSFWorkbook(arquivo);
			folha = wb.getSheet(idFolha);
			if(folha==null)folha = wb.createSheet(idFolha);
			arquivo.close();
		}catch(FileNotFoundException e){//Se o arquivo n�o for Encontrada cria os dados de um novo arquivo
			wb = new HSSFWorkbook();
			folha = wb.createSheet(idFolha);
		}
		
	}

	public Sessao buscar(String id) throws SessaoNaoEncontradaException {
		Sessao retorno = null;
		
		int indice = buscarIndice(id);//Procura o �ndice do Sessao na planilha
		Row row = this.folha.getRow(indice);//Copia a linha

		//Copia os valores das celulas para vari�veis
		String id2 = row.getCell(0).getStringCellValue();
		Date horaInicio = new Date((long)row.getCell(1).getNumericCellValue());
		String filmeNome = row.getCell(2).getStringCellValue();
		long filmeDuracao1 = (long)row.getCell(3).getNumericCellValue();
		Date filmeDuracao=new Date(filmeDuracao1);//Converte o valor da dura��o em long para um objeto tipo Date
		String filmeCategoria = row.getCell(4).getStringCellValue();
		String filmeClassificacao = row.getCell(5).getStringCellValue();
		String filmeDescricao = row.getCell(6).getStringCellValue();
		Filme filme = new Filme(filmeNome, filmeDuracao, filmeCategoria, filmeClassificacao, filmeDescricao);

		retorno = new Sessao(id2, filme, "sala", horaInicio);
		//Inst�ncia um Sessao com os dados para ser retornado


		return retorno;
	}

	public void inserir(Sessao sessao) throws FileNotFoundException, IOException, SessaoConflitanteException {
		int indice = 0;
		while(this.folha.getRow(indice)!=null) indice++;//Obt�m o primeiro �ndice de linha livre para n�o sobrecarregar a planilha
		
		IteratorSessao itr = new IteratorSessaoExel(folha);
		
		while(itr.hasNext()){
			Sessao sessaoTeste = itr.next();
			if(sessaoTeste.checaConflito(sessao)) throw new SessaoConflitanteException();
		}
		
		Row row = folha.createRow(indice);// cria uma nova linha
		//Copia os dados do objeto para a linha
		
		
		Cell cell = row.createCell(0);		
		cell.setCellValue(sessao.getId());
		cell = row.createCell(1);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);//Declara esta c�lula como num�rica para receber a data
		cell.setCellValue(sessao.getHoraInicio().getTime());//Retorna a data como um long		
		cell = row.createCell(2);
		cell.setCellValue(sessao.getFilme().getNome());
		cell = row.createCell(3);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(sessao.getFilme().getDuracao().getTime());
		cell = row.createCell(4);
		cell.setCellValue(sessao.getFilme().getCategoria());
		cell = row.createCell(5);
		cell.setCellValue(sessao.getFilme().getClassificacao());
		cell = row.createCell(6);
		cell.setCellValue(sessao.getFilme().getDescricao());
				

		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();

	}

	public void remover(String id) throws SessaoNaoEncontradaException, FileNotFoundException,IOException {
		int indice = buscarIndice(id);//obt�m o �ndice do Sessao

		this.folha.removeRow(this.folha.getRow(indice));//Remove a linha da planilha deixando como nula	


		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();

	}

	public void atualizar(Sessao sessao) throws SessaoNaoEncontradaException, FileNotFoundException,IOException {
		
		
		int indice = buscarIndice(sessao.getId());
		
		Row row = this.folha.getRow(indice);//Obtem a linha com o objeto
		//Copia os dados do objeto para a linha
		Cell cell = row.createCell(0);		
		cell.setCellValue(sessao.getId());
		cell = row.getCell(1);
		cell.setCellValue(sessao.getHoraInicio().getTime());//Retorna a data como um long		
		cell = row.getCell(2);
		cell.setCellValue(sessao.getFilme().getNome());
		cell = row.getCell(3);
		cell.setCellValue(sessao.getFilme().getDuracao().getTime());
		cell = row.getCell(4);
		cell.setCellValue(sessao.getFilme().getCategoria());
		cell = row.getCell(5);
		cell.setCellValue(sessao.getFilme().getClassificacao());
		cell = row.getCell(6);
		cell.setCellValue(sessao.getFilme().getDescricao());
				
		//Finaliza as grava��es no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();


	}
	
	private int buscarIndice(String id) throws SessaoNaoEncontradaException{
		int retorno;
		
		int apoio = this.folha.getLastRowNum()+1;//Obt�m a �ndice ap�s a ultima linha
		boolean continua = true;//Checa at� a primeira remo��o
		int i;
		for(i = 0; i< apoio && continua; i++){
			Row row = this.folha.getRow(i);
			if(row!=null&&row.getCell(0).getStringCellValue().equals(id)){
				//Se a linha n�o for nula checa a celula de id, se corresponder finalliza a busca
				continua = false;
			}
		}
		if(i==apoio) i = 0;//Checa se n�o foi Encontrada
		retorno = i-1;//Calcula o �ndice
		if(retorno == -1)throw new SessaoNaoEncontradaException();//Se n�o foi Encontrada ou n�o existem dados retorna uma excess�o
		return retorno;
	}

	public IteratorSessao getIterator() {
		return  new IteratorSessaoExel(this.folha);		
	}

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
