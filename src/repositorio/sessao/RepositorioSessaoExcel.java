package repositorio.sessao;

import iterator.IteratorSalaExcel;
import iterator.IteratorSessao;
import iterator.IteratorSessaoExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import repositorio.filme.FilmeNaoEncontradoException;
import repositorio.sala.ManipuladorSalaExcel;

import negocio.base.Cadeira;
import negocio.base.Filme;
import negocio.base.Sala;
import negocio.base.Sessao;
import negocio.base.SessaoFechadaFilme;
import negocio.base.SessaoFechadaPropria;
import negocio.base.SessaoPublicaFilme;
import negocio.base.SessaoPublicaPropria;

public class RepositorioSessaoExcel implements RepositorioSessao {


	private Sheet folhaSessao, folhaSala;//Variável que contém a planilha sendo usada
	private Workbook wb;//Variável que contém o arquivo do exel virtualmente
	private String nomeArquivo;
	private final int TIPO = 0;
	private final String SESSAO_PUBLICA_FILME = "SPF", SESSAO_FECHADA_FILME = "SFF", SESSAO_PUBLICA_PROPRIA = "SPP", SESSAO_FECHADA_PROPRIA = "SFP";

	public RepositorioSessaoExcel(String nomeArquivo, String nomeFolhaSessao, String nomeFolhaSala) throws IOException{
		this.nomeArquivo = nomeArquivo;
		try{//Tenta abrir os dados do arquivo
			FileInputStream arquivo = new FileInputStream(nomeArquivo);
			wb = new HSSFWorkbook(arquivo);
			folhaSessao = wb.getSheet(nomeFolhaSessao);
			folhaSala = wb.getSheet(nomeFolhaSala);
			
			arquivo.close();
		}catch(FileNotFoundException e){//Se o arquivo não for encontrado cria os dados de um novo arquivo
			wb = new HSSFWorkbook();
			folhaSessao = wb.createSheet(nomeFolhaSessao);
			folhaSala = wb.createSheet(nomeFolhaSala);
		}

	}

	public Sessao buscar(String id) throws SessaoNaoEncontradaException,
	TipoDeObjetoNaoSuportado {

		int idSessao = Integer.parseInt(id);
		Row row = folhaSessao.getRow(idSessao);
		if (row == null) throw new SessaoNaoEncontradaException();
		return leSessao(row);
	}

	public void inserir(Sessao sessao) throws FileNotFoundException,
	IOException, TipoDeObjetoNaoSuportado {

		int indice = 0;
		while(this.folhaSessao.getRow(indice)!=null) indice++;//Obtém o primeiro índice de linha livre para não sobrecarregar a planilha
		Row row = folhaSessao.createRow(indice);// cria uma nova linha
		sessao.setId(""+row.getRowNum());
		guardarSessao(sessao, row);
		gravar();

	}

	public void remover(String id) throws SessaoNaoEncontradaException,
	FileNotFoundException, IOException, TipoDeObjetoNaoSuportado {
		int idSessao = Integer.parseInt(id);
		Row row = folhaSessao.getRow(idSessao);//Obtém a linha
		folhaSessao.removeRow(row);//Exclui a linha
		Row rowSala = folhaSala.getRow(idSessao);//Faz o mesmo na folha das salas
		folhaSala.removeRow(rowSala);
		gravar();

	}

	public void atualizar(Sessao sessao) throws SessaoNaoEncontradaException,
	FileNotFoundException, IOException, TipoDeObjetoNaoSuportado,
	SessaoConflitanteException {
		remover(sessao.getId());//Remove a versão antiga
		inserir(sessao);//Insere a nova
		gravar();
	}

	public boolean temSessao(String id) {
		int  idsessao = Integer.parseInt(id);

		return folhaSessao.getRow(idsessao)!=null;
	}

	public IteratorSessao getIterator() throws TipoDeObjetoNaoSuportado {
		return  new IteratorSessaoExcel(this.folhaSessao, this.folhaSala);	
	}

	private void guardarSessao(Sessao sessao, Row row) throws TipoDeObjetoNaoSuportado {
		//Processo de serialização de uma sessão
		int indiceCells = 1;
		if(sessao instanceof SessaoPublicaFilme  || sessao instanceof SessaoFechadaFilme){
			if(sessao instanceof SessaoPublicaFilme) row.createCell(TIPO).setCellValue(SESSAO_PUBLICA_FILME);
			else row.createCell(TIPO).setCellValue(SESSAO_FECHADA_FILME);
			indiceCells = escreveFilme(row,indiceCells, ((SessaoPublicaFilme) sessao).getFilme());
			
			Row rowSala = folhaSala.createRow(row.getRowNum());
			ManipuladorSalaExcel.escreveSala(rowSala,sessao.getSala());

			Cell cell = row.createCell(indiceCells);
			indiceCells++;
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(sessao.getHoraInicio().getTime());

			cell = row.createCell(indiceCells);
			indiceCells++;
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(sessao.getHoraFim().getTime());

		} else if(sessao instanceof SessaoPublicaPropria || sessao instanceof SessaoFechadaPropria){
			if(sessao instanceof SessaoPublicaFilme) row.createCell(TIPO).setCellValue(SESSAO_PUBLICA_PROPRIA);
			else row.createCell(TIPO).setCellValue(SESSAO_FECHADA_PROPRIA);

			Cell cell = row.createCell(indiceCells);
			indiceCells++;
			cell.setCellValue(sessao.getTitulo());

			Row rowSala = folhaSala.getRow(row.getRowNum());
			ManipuladorSalaExcel.escreveSala(rowSala,sessao.getSala());

			cell = row.createCell(indiceCells);
			indiceCells++;
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(sessao.getHoraInicio().getTime());

			cell = row.createCell(indiceCells);
			indiceCells++;
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(sessao.getHoraFim().getTime());

		} else throw new TipoDeObjetoNaoSuportado();

	}


	private int escreveFilme(Row row, int indice, Filme filme) {

		Cell cell = row.createCell(indice);	
		indice++;
		cell.setCellValue(filme.getNome());
		cell = row.createCell(indice);
		indice++;
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);//Declara esta célula como numérica para receber a data
		cell.setCellValue(filme.getDuracao().getTime());//Retorna a data como um long		
		cell = row.createCell(indice);
		indice++;
		cell.setCellValue(filme.getCategoria());
		cell = row.createCell(indice);
		indice++;
		cell.setCellValue(filme.getClassificacao());
		cell = row.createCell(indice);
		indice++;
		cell.setCellValue(filme.getDescricao());

		return indice;

	}

	private Sessao leSessao(Row row) throws TipoDeObjetoNaoSuportado{
		Sessao retorno = null;

		String tipo = row.getCell(TIPO).getStringCellValue();
		int indice = 1;

		if(tipo.equals(SESSAO_PUBLICA_FILME)){
			Filme filme = leFilme(row, indice);
			indice+=5;
			Row rowSala = folhaSala.getRow(row.getRowNum());
			Sala sala  = ManipuladorSalaExcel.leSala(rowSala);

			Cell cell = row.getCell(indice);
			indice++;
			Date horaInicio = new Date((long)cell.getNumericCellValue());

			retorno = new SessaoPublicaFilme(""+row.getRowNum(), filme, sala, horaInicio);
		} else if(tipo.equals(SESSAO_FECHADA_FILME)){
			Filme filme = leFilme(row, indice);
			indice+=5;
			
			Row rowSala = folhaSala.getRow(row.getRowNum());
			Sala sala  = ManipuladorSalaExcel.leSala(rowSala);

			Cell cell = row.getCell(indice);
			indice++;
			Date horaInicio = new Date((long)cell.getNumericCellValue());

			retorno = new SessaoFechadaFilme(""+row.getRowNum(), filme, sala, horaInicio);
		} else if(tipo.equals(SESSAO_PUBLICA_PROPRIA)){

			Cell cell = row.getCell(indice);
			indice++;
			String titulo = cell.getStringCellValue();

			Row rowSala = folhaSala.getRow(row.getRowNum());
			Sala sala  = ManipuladorSalaExcel.leSala(rowSala);

			cell = row.getCell(indice);
			indice++;
			Date horaInicio = new Date((long)cell.getNumericCellValue());

			cell = row.getCell(indice);
			indice++;
			Date horaFim = new Date((long)cell.getNumericCellValue());

			retorno = new SessaoPublicaPropria(""+row.getRowNum(), sala, horaInicio, null, titulo);
			retorno.setHoraFim(horaFim);

		} else if(tipo.equals(SESSAO_FECHADA_PROPRIA)){

			Cell cell = row.getCell(indice);
			indice++;
			String titulo = cell.getStringCellValue();

			Row rowSala = folhaSala.getRow(row.getRowNum());
			Sala sala  = ManipuladorSalaExcel.leSala(rowSala);

			cell = row.getCell(indice);
			indice++;
			Date horaInicio = new Date((long)cell.getNumericCellValue());

			cell = row.getCell(indice);
			indice++;
			Date horaFim = new Date((long)cell.getNumericCellValue());

			retorno = new SessaoFechadaPropria(""+row.getRowNum(), sala, horaInicio, null, titulo);
			retorno.setHoraFim(horaFim);

		} else throw new TipoDeObjetoNaoSuportado();

		return retorno;
	}

	private Filme leFilme(Row row, int indice) {


		//Copia os valores das celulas para variáveis
		String nome2 = row.getCell(indice).getStringCellValue();
		indice++;
		long duracao1 = (long)row.getCell(indice).getNumericCellValue();
		indice++;
		Date duracao=new Date(duracao1);//Converte o valor da duração em long para um objeto tipo Date
		String categoria = row.getCell(indice).getStringCellValue();
		indice++;
		String classificacao = row.getCell(indice).getStringCellValue();
		indice++;
		String descricao = row.getCell(indice).getStringCellValue();
		indice++;

		Filme filme = new Filme(nome2, duracao, categoria, classificacao, descricao);
		//Instância um filme com os dados para ser retornado

		return filme;
	}


	public void gravar() throws IOException{
		//Finaliza as gravações no arquivo
		FileOutputStream arquivo = new FileOutputStream(this.nomeArquivo);
		wb.write(arquivo);
		arquivo.close();
	}
}
