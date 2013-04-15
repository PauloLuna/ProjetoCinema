package repositorio.sessao;

import iterator.IteratorSessao;
import iterator.IteratorSessaoArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.cellwalk.CellWalkContext;
import org.apache.poi.hssf.usermodel.*;

import repositorio.sessao.*;

import negocio.base.*;

public class RepositorioSessaoExcel implements RepositorioSessao {

	private Sheet folha;// Variï¿½vel que contï¿½m a planilha sendo usada
	private Workbook wb;// Variï¿½vel que contï¿½m o arquivo do exel virtualmente
	private String idArquivo;
	private int numLinhas;
	
	public RepositorioSessaoExcel(String idArquivo, String idFolha)
			throws IOException {
		this.idArquivo = idArquivo;
		this.numLinhas = 0;
		try {// Tenta abrir os dados do arquivo
			FileInputStream	arquivo = new FileInputStream(idArquivo);
			this.wb = new HSSFWorkbook(arquivo);
			this.folha = wb.getSheet(idFolha);
			arquivo.close();
		} catch (FileNotFoundException e) {// Se o arquivo nï¿½o for Encontrada
											// cria os dados de um novo arquivo
			this.wb = new HSSFWorkbook();
			this.folha = wb.createSheet(idFolha);
		}

	} // Fim do contrutor
		
	
	public Sessao buscar(String nome) throws SessaoNaoEncontradaException, TipoDeObjetoNaoSuportado {
		int linha ;
		Sessao sessao;
		
		linha = this.getLinha(nome);
		sessao = this.ler(linha);
		
		return sessao;
	} // Fim do método busca
	
	public void inserir(Sessao sessao) throws SessaoConflitanteException, IOException, TipoDeObjetoNaoSuportado{
		// Primeiro checamos se objeto já existe
		try{
			this.getLinha(sessao.getId());             // Irá gerar um erro se o objeto não existir
			// Lançando  se o objeto já existe ERRO
			SessaoConflitanteException erro;
			erro = new SessaoConflitanteException();
			throw erro;
		}
		catch (SessaoNaoEncontradaException erro){
			// Se o objeto não for achado, então...
			this.escrever(sessao, this.numLinhas);
		}
		
		this.numLinhas++;
	} // Fim do método inserir
	
	public void remover(String nome) throws SessaoNaoEncontradaException,
			FileNotFoundException, IOException, TipoDeObjetoNaoSuportado {
	
		int linha,indice;
		boolean processando = true,apagando = true;
		String tipo = "";
		Row row;
		Row rowFinal;
		Cell cell;
		Cell cellFinal;
		
		// Lança erro se a linha que contém o objeto não for encontrada
		linha = this.getLinha(nome);
		indice = 0;

		// Inicializando variáveis row
		row = this.folha.getRow(linha);
		rowFinal = this.folha.getRow(this.numLinhas -1);
		
		
		// Apagando a linha do objeto no excel
		while (apagando){
			String cellValue = null;
			long cellValueLong = -1L;
			
			cell = row.getCell(indice);

			// "Pular o problema de pegar um texto númerico"
			if (indice == 2 || indice == 3){
				cellValue = "";
				cellValueLong = 1;
			}
			
			// Checando primeiro se já chegamos na ultima cell
			if (cellValue == null && cellValueLong == -1L){
				apagando = false;
			}
			else {
				// Se não chegamos na última cell apagamos a atual
				cell.setCellFormula(null);
			}
			
			indice++;
		} // Fim do while
		
		
		// Escrevendo objeto
		if (this.numLinhas == 0){
			System.out.println("Checando se numLinhas é 0");
			// O processo anterior resolveu o problema
			;
		}
		else {
			this.escrever(this.ler(this.numLinhas - 1),linha);	
			
			// Apagando última linha
			apagando = true;
			indice = 0;
			
			while (apagando){
				String cellValue = null;
				long cellValueLong = -1L;
				
				cellFinal = rowFinal.getCell(indice);

				// "Pular o problema de pegar um texto númerico"
				if (indice == 2 || indice == 3){
					cellValue = "";
					cellValueLong = 1;
				}
				
				// Checando primeiro se já chegamos na ultima cell
				if (cellFinal == null && cellValueLong == -1L){
					apagando = false;
				}
				else {
					// Se não chegamos na última cell apagamos a atual
					cellFinal.setCellFormula(null);
				}
				indice++;
			} // Fim do while
			
		}
		// Salvar alterações
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();
		
		this.numLinhas--;
	}
	
	public void atualizar(Sessao sessao) throws SessaoNaoEncontradaException,
			FileNotFoundException, IOException, TipoDeObjetoNaoSuportado, SessaoConflitanteException {
		int linha;
		
		// Checando para ver se o objeto está no Excel
		this.buscar(sessao.getId());
		// Removemos o objeto no excel atual
		this.remover(sessao.getId());
		// E escrevemos o novo
		this.inserir(sessao);

		// Salvar alterações
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();
	}
	// FAZER
	public boolean temSessao(String nome) {
		int linha;
		boolean retorno;
		try{
			linha = this.getLinha(nome);
			retorno = true;
		}
		catch (SessaoNaoEncontradaException erro){
			retorno = false;
		}
		return retorno;
	}
	
	public IteratorSessao getIterator() throws TipoDeObjetoNaoSuportado {
		/*
		 * Retorna um array com todos os objetos do excel
		 */
		
		IteratorSessao iterator;
		Sessao[] sessoes;
		
		if (this.numLinhas == 0){
			sessoes = new Sessao[0];
		}
		else{
			sessoes = new Sessao[this.numLinhas];			
		}
		
		sessoes = new Sessao[this.numLinhas];
		for (int i = 0; i < this.numLinhas; i++){
			sessoes[i] = this.ler(i);
		}
		
		// Passando o array para o iterator
		iterator = new IteratorSessaoArray(sessoes);
		
		return iterator;
	}
	
	// Método getIndice
	public int getLinha(String nome) throws SessaoNaoEncontradaException{
		/*
		 * Retorna o valor int, onde está o objeto procurado!
		 * Se nenhum objeto for achado lança SessaoNaoEncontraException
		 */
		int linha = 0,contagem;
		boolean procurando = true;
		Row row;
		Cell cell;
		
		
		contagem = this.numLinhas - 1;
				
		while (contagem >= 0 && procurando){
			String cellValue = "";
			row = this.folha.getRow(contagem);
			
			try{
				cell = row.getCell(1); // Posição numero = ID
				cellValue = cell.getStringCellValue();
			}
			catch (NullPointerException erro){
				contagem = -10;
			}
			
			// Checando se o objeto foi achado
			// Se o objeto não foi achado, lança um erro!
			if (cellValue.equals(nome)){
				linha = contagem;
				procurando = false;
			}
			
			contagem--;
		}
		
		
		// Se a variável não tiver sido inicializada, lança um erro
		if (procurando){
			SessaoNaoEncontradaException erro;
			erro = new SessaoNaoEncontradaException();
			throw erro;
		}
		
		return linha;
	}
		
	// Métodos privados do objeto
	public Cadeira lerObjetoCadeira(String cellValue) {
		/*
		 * Recebe o valor de string "Serializado" de algum objeto cadeira e o
		 * retorna como objeto Cadeira
		 */

		Cadeira cadeira;
		cadeira = new Cadeira();

		// Primeira parte da String corresponde se a cadeira estÃ¡ quebrada
		if (cellValue.substring(0, 1).equals("t")) {
			cadeira.setCadeiraQuebrada(true);;
		} else {
			cadeira.setCadeiraQuebrada(false);
		}

		// Segunda parte da String corresponde se a cadeira foi vendida
		if (cellValue.substring(1, 2).equals("t")) {
			cadeira.setCadeiraComprada(true);
		} else {
			cadeira.setCadeiraComprada(false);
		}

		return cadeira;
	}

	public String escreverObjetoCadeira(Cadeira cadeira) {
		/*
		 * Retorna o texto Serializado de um objeto cadeira
		 */
		
		String cellValue = "";

		if (cadeira.getCadeiraQuebrada()) {
			cellValue += "t";
		} else {
			cellValue += "f";
		}

		if (cadeira.getCadeiraComprada()) {
			cellValue += "t";
		} else {
			cellValue += "f";
		}

		return cellValue;
	} // Fim do mÃ©todo escreverObjetoCadeira

	private Sala lerObjetoSala(int linha, int indice) {
		/*
		 * Vai no row especificado por (int linha) e usa o indice,
		 * Para saber onde começa o objeto sala no ROW
		 */
		
		Sala sala;
		String codigo;
		boolean trabalhando = true;
		int numFilas = 0, numColunas = 0, numArray = -1, numArrayColuna = 0;
		Cadeira[][] cadeiras;
		Cadeira[] fila;
		Row row = this.folha.getRow(linha);
		
		// Coletando os dados no excel
		Cell cell;

		cell = row.getCell(indice);
		codigo = cell.getStringCellValue();
		indice++;

		cell = row.getCell(indice);
		numFilas = (int) cell.getNumericCellValue();
		indice++;

		cell = row.getCell(indice);
		numColunas = (int) cell.getNumericCellValue();
		
		// Criando o objeto sala
		sala = new Sala(codigo, numFilas, numColunas);

		// Criando o objeto cadeiras[][]
		cadeiras = new Cadeira[numFilas][numColunas];
		fila = new Cadeira[numColunas];

		do {
			indice++;
			cell = row.getCell(indice);

			// Checando se chegamos ao fim
			try{
				if (cell.getStringCellValue() == null);
			}
			catch (NullPointerException erro){
				trabalhando = false;
			}
			
			if (!trabalhando) {
				cadeiras[numArray] = fila;
			} else if (cell.getStringCellValue().equals("array") && numArray == -1) { // Isso ocorre quando chegarmos no
										// primeiro "array" do Excel
				numArray++;
			} else if (cell.getStringCellValue().equals("array")) { // Isso
																	// ocorre
																	// quando
																	// chegarmos
																	// ao um
																	// novo
																	// array no
																	// Excel
				// Se chegar ao final passamos o array
				cadeiras[numArray] = fila;
				numArray++;
				// Reinicializando as variaveis fila e numArrayColuna
				fila = null;
				fila = new Cadeira[numFilas];
				numArrayColuna = 0;

			} else {
				fila[numArrayColuna] = this.lerObjetoCadeira(cell.getStringCellValue());
				numArrayColuna++;
			} // Fim do if-else

		} while (trabalhando);


		// Setando o objeto Cadeiras[][] no objeto sala
		sala.setCadeiras(cadeiras);

		return sala;
	}

	private void escreverObjetoSala(Sala sala, int linha, int indice) throws IOException {
		/*
		 * Escrever um objeto Sala na planilha apartir do indice e linha passados como paramentros 
		 */
		
		Cell cell;
		int conta = indice;
		Row row = this.folha.getRow(linha);
		
		// Copiando atributos de sala para o excel
		cell = row.createCell(conta);
		cell.setCellValue(sala.getCodigo());
		conta++;
		
		cell = row.createCell(conta);
		cell.setCellValue(sala.getNumFilas());
		conta++;
		
		cell = row.createCell(conta);
		cell.setCellValue(sala.getNumColunas());
		conta++;
		
		// Serializando o array de cadeiras no Excel
		for (int i = 0; i < sala.getNumFilas(); i++) {
			cell = row.createCell(conta);
			cell.setCellValue("array");
			conta++;
			
			// Agora jogando o array no Excel
			for (int j = 0; j < sala.getNumColunas(); j++) {
				cell = row.createCell(conta);
				cell.setCellValue(this.escreverObjetoCadeira(sala.getCadeiras()[i][j]));
				conta++;
			} // Fim do FOR J
		} // Fim do FOR I

		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();
		
	} // Fim do mÃ©todo  escreverObjetoExcel

	private Filme lerObjetoFilme(int linha, int indice){
		/*
		 * Ler um objeto filme no excel, que é equivalente a 5 celulas no excel
		 */
		Cell cell;
		Filme filme;
		String nome;
		long duracao;
		String categoria;
		String classificacao;
		String descricao;
		
		Row row = this.folha.getRow(linha);
		
		// Copiando tudo para as vÃ¡riaveis
		cell = row.getCell(indice);
		nome = cell.getStringCellValue();
		indice++;
		
		cell = row.getCell(indice);
		duracao = (long)cell.getNumericCellValue();
		indice++;
		
		cell = row.getCell(indice);
		categoria = cell.getStringCellValue();
		indice++;
		
		cell = row.getCell(indice);
		classificacao = cell.getStringCellValue();
		indice++;
		
		cell = row.getCell(indice);
		descricao = cell.getStringCellValue();
		indice++;
		
		// Inicializando objeto HORA
		Date date;
		date = new Date(duracao);
		
		// Inicializando Filme
		filme = new Filme(nome,date,categoria,classificacao, descricao);
		
		return filme;
	}
	
	private void escreverObjetoFilme(Filme filme, int linha, int coluna) throws IOException{
		/*
		 * Escrevi um objeto filme no excel, que é equivalente a 5 celulas no excel
		 * Vai no row e na celula especificada pelos parametros passados ao método
		 */
		Cell cell;
		
		// TEMPORARIO
		Row row = this.folha.getRow(linha);
		
		// Escrevendo dados no Excel
		cell = row.createCell(coluna);
		cell.setCellValue(filme.getNome());
		coluna++;
		
		
		cell = row.createCell(coluna);
		cell.setCellValue(filme.getDuracao().getTime());
		coluna++;
		
		cell = row.createCell(coluna);
		cell.setCellValue(filme.getCategoria());
		coluna++;
		
		cell = row.createCell(coluna);
		cell.setCellValue(filme.getClassificacao());
		coluna++;
		
		cell = row.createCell(coluna);
		cell.setCellValue(filme.getDescricao());
		coluna++;
		
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();
		
	} // Fim da classe escreverObjetoFilme
	
	// Método completo para ler e escrever todo tipo de SESSAO
	public void escrever(Sessao sessao, int linha) throws IOException, TipoDeObjetoNaoSuportado{
		/*
		 *  Escreve um objeto sessao(Qualquer TIPO) em um row(linha)
		 *  Obs: É necessario que a linha inteira seja NULA
		 */
		Cell cell;
		int indice = 0;
		
		Row row = this.folha.createRow(linha);
		
		if (sessao instanceof SessaoFechadaFilme){
			// Escrevendo nome da classe
			cell = row.createCell(indice);
			cell.setCellValue("sessaoFechadaFilme");
			indice++;
		
			// Escrevendo ID
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getId());
			indice++;
		
			// Escrevendo horaInicio
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraInicio());
			indice++;
			
			// Escrevendo horaFim
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraFim());
			indice++;
			
			//Escrevendo objeto FILME
			this.escreverObjetoFilme(((SessaoFechadaFilme) sessao).getFilme(), linha, indice);
			indice += 5; // NÃºmero de atributos da classe filme;
			
			
			// Escrevendo o objeto SALA
			this.escreverObjetoSala(sessao.getSala(), linha, indice);
			
		}
		else if (sessao instanceof SessaoPublicaFilme){
			// Copiando nome da classe
			cell = row.createCell(indice);
			cell.setCellValue("sessaoPublicaFilme");
			indice++;
			
			// Escrevendo ID
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getId());
			indice++;
		
			// Escrevendo horaInicio
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraInicio());
			indice++;
			
			// Escrevendo horaFim
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraFim());
			indice++;
			
			//Escrevendo objeto FILME
			this.escreverObjetoFilme(((SessaoPublicaFilme) sessao).getFilme(), linha, indice);
			indice += 5; // NÃºmero de atributos da classe filme;
			
			
			// Escrevendo o objeto SALA
			this.escreverObjetoSala(sessao.getSala(), linha, indice);
			
		}
		else if (sessao instanceof SessaoFechadaPropria){
			// Copiando nome da classe
			cell = row.createCell(indice);
			cell.setCellValue("sessaoFechadaPropria");
			indice++;
			
			// Escrevendo ID
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getId());
			indice++;
			
			// Escrevendo horaInicio
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraInicio());
			indice++;
			
			// Escrevendo horaFim
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraFim());
			indice++;
			
			// Escrevendo Titulo
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getTitulo());
			indice++;
			
			// Escrevendo objeto sala
			cell = row.createCell(indice);
			this.escreverObjetoSala(sessao.getSala(), linha, indice);
		}

		else if (sessao instanceof SessaoPublicaPropria){
			// Copiando nome da classe
			cell = row.createCell(indice);
			cell.setCellValue("sessaoPublicaPropria");
			indice++;
			
			// Escrevendo ID
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getId());
			indice++;
			
			// Escrevendo horaInicio
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraInicio());
			indice++;
			
			// Escrevendo horaFim
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getHoraFim());
			indice++;
			
			// Escrevendo Titulo
			cell = row.createCell(indice);
			cell.setCellValue(sessao.getTitulo());
			indice++;
			
			// Escrevendo objeto sala
			cell = row.createCell(indice);
			this.escreverObjetoSala(sessao.getSala(), linha, indice);
		}
		else {
			// Lançando erro se não reconhecer o objeto a ser escrito
			TipoDeObjetoNaoSuportado erro;
			erro = new TipoDeObjetoNaoSuportado();
			throw erro;
		}
		
		// Escrever Objeto
		FileOutputStream arquivo = new FileOutputStream(this.idArquivo);
		wb.write(arquivo);
		arquivo.close();
	} 
	
	public Sessao ler(int linha) throws TipoDeObjetoNaoSuportado{
		/*
		 *  Ler um objeto sessao(Qualquer TIPO) em um row(linha)
		 *  Obs: É necessario que a linha esteja já preenchida por
		 *  algum objeto de Sessao
		 */
		Cell cell;
		int indice = 0;
		Row row = this.folha.getRow(linha);
		Sessao sessao = null;
		
		// Variaveis que representam atributos de sessao
		String classe;
		String id;
		String titulo;
		Date horaInicio;
		Date horaFim;
		Filme filme;
		Sala sala;
		
		// Recebendo valor da classe
		cell = row.getCell(indice);
		classe = cell.getStringCellValue();	
		indice++;
		
		// Recebendo valor do ID
		cell = row.getCell(indice);
		id = cell.getStringCellValue();
		indice++;
				
		// Agora começa a parte qe difere entre as classes
		if ( classe.equals("sessaoFechadaFilme")){
			// Pegando valor Hora Inicio
			cell = row.getCell(indice);
			horaInicio = cell.getDateCellValue();
			//new Date((long) cell.getNumericCellValue());
			indice++;
			indice++; // Pulando HoraFim, por que essa classe não utilizar direto esse atributo
			
			// Pegando o objeto filme
			filme = this.lerObjetoFilme(linha, indice);
			indice += 5;
			
			// Pegando o objeto SALA
			sala = this.lerObjetoSala(linha,indice);
			
			
			// Inicializando a variável sessão
			sessao = new SessaoFechadaFilme(id, filme, sala, horaInicio);	
		}
		else if ( classe.equals("sessaoPublicaFilme")){
			// Pegando valor Hora Inicio
			cell = row.getCell(indice);
			horaInicio= cell.getDateCellValue();
				//new Date((long) cell.getNumericCellValue());
			indice++;
			indice++; // Pulando HoraFim, por que essa classe não utilizar direto esse atributo
			
			// Pegando o objeto filme
			filme = this.lerObjetoFilme(linha, indice);
			indice += 5;
			
			// Pegando o objeto SALA
			sala = this.lerObjetoSala(linha,indice);
			
			// Inicializando a variável sessão
			sessao = new SessaoPublicaFilme(id, filme, sala, horaInicio);

		}
		else if ( classe.equals("sessaoFechadaPropria")){
			// Pegando valor Hora Inicio
			cell = row.getCell(indice);
			horaInicio= cell.getDateCellValue();
			//new Date((long) cell.getNumericCellValue());
			indice++;
			
			// Pegando valor Hora Fim - Valor da duracao...
			cell = row.getCell(indice);
			horaFim = cell.getDateCellValue();
			//new Date((long) cell.getNumericCellValue());
			indice++; 
			
			// Pegando titulo
			cell = row.getCell(indice);
			titulo = cell.getStringCellValue();
			indice++;
			
			// Pegando objeto sala
			sala = lerObjetoSala(linha, indice);
			
			// Inicializando objeto SessaoFecadaPropria
			sessao = new SessaoFechadaPropria(id, sala, horaInicio, horaFim, titulo);
			
		}
		else if ( classe.equals("sessaoPublicaPropria")){
			// Pegando valor Hora Inicio
			cell = row.getCell(indice);
			horaInicio = cell.getDateCellValue();
			//new Date((long) cell.getNumericCellValue());
			indice++;
			
			// Pegando valor Hora Fim - Valor da duracao...
			cell = row.getCell(indice);
			horaFim = cell.getDateCellValue();		
			//new Date((long) cell.getNumericCellValue());
			indice++; 
			
			// Pegando titulo
			cell = row.getCell(indice);
			titulo = cell.getStringCellValue();
			indice++;
			
			// Pegando objeto sala
			sala = lerObjetoSala(linha, indice);
			
			// Inicializando objeto SessaoFecadaPropria
			sessao = new SessaoPublicaPropria(id, sala, horaInicio, horaFim, titulo);
		}
		else{
			// Lançando erro se não reconhecer o objeto a ser lido
			TipoDeObjetoNaoSuportado erro;
			erro = new TipoDeObjetoNaoSuportado();
			throw erro;
		}		

		
		return sessao;
	}
	
} // Fim da classe RepositorioSessaoExcel  