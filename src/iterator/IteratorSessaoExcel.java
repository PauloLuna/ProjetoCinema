package iterator;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import repositorio.sala.ManipuladorSalaExcel;
import repositorio.sessao.TipoDeObjetoNaoSuportado;

import negocio.base.Cadeira;
import negocio.base.Filme;
import negocio.base.Sala;
import negocio.base.Sessao;
import negocio.base.SessaoFechadaFilme;
import negocio.base.SessaoFechadaPropria;
import negocio.base.SessaoPublicaFilme;
import negocio.base.SessaoPublicaPropria;

public class IteratorSessaoExcel implements IteratorSessao {

	
	private Sheet folhaSessao, folhaSala;
	private int indice;
	private final int TIPO = 0;
	private final String SESSAO_PUBLICA_FILME = "SPF", SESSAO_FECHADA_FILME = "SFF", SESSAO_PUBLICA_PROPRIA = "SPP", SESSAO_FECHADA_PROPRIA = "SFP";
	
	
	public IteratorSessaoExcel(Sheet folhaSessao,Sheet folhaSala) {
		this.folhaSessao = folhaSessao;
		this.folhaSala = folhaSala;
		this.indice = folhaSessao.getFirstRowNum();//Começa o índice com a primeira linha válida
	}
	@Override
	public boolean hasNext() {
		Row row = this.folhaSessao.getRow(indice);
		return row!=null;
	}

	@Override
	public Sessao next() throws TipoDeObjetoNaoSuportado{
		Row row  = this.folhaSessao.getRow(indice);
		int fim = this.folhaSessao.getLastRowNum();
		Sessao sessao = leSessao(row);	
		
		do{//Procur o próximo índice válido
			this.indice++;
		}while(this.folhaSessao.getRow(indice)==null && this.indice<=fim);
				
		return sessao;
	}
	//Métodos de leitura vindos do repositório
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

}
