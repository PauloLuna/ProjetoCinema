package iterator;

import java.util.Date;

import negocio.base.Filme;
import negocio.base.Sessao;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;



public class IteratorSessaoExel implements IteratorSessao {

	private Sheet folha;
	private int indice;
	
	
	public IteratorSessaoExel(Sheet folha) {
		this.folha = folha;
		this.indice = folha.getFirstRowNum();//Começa o índice com a primeira linha válida
	}
	public boolean hasNext() {
		Row row = this.folha.getRow(indice);
		return row!=null;
	}

	public Sessao next() {
		Row row  = this.folha.getRow(indice);
		int fim = this.folha.getLastRowNum();
		//Obtém os valores da linha do arquivo para o objeto
		String id2 = row.getCell(0).getStringCellValue();
		Date horaInicio = new Date((long)row.getCell(1).getNumericCellValue());
		String filmeNome = row.getCell(2).getStringCellValue();
		long filmeDuracao1 = (long)row.getCell(3).getNumericCellValue();
		Date filmeDuracao=new Date(filmeDuracao1);//Converte o valor da duração em long para um objeto tipo Date
		String filmeCategoria = row.getCell(4).getStringCellValue();
		String filmeClassificacao = row.getCell(5).getStringCellValue();
		String filmeDescricao = row.getCell(6).getStringCellValue();
		Filme filme = new Filme(filmeNome, filmeDuracao, filmeCategoria, filmeClassificacao, filmeDescricao);

		Sessao retorno = new Sessao(id2, filme, "sala", horaInicio);//Instância o objeto
		do{//Procur o próximo índice válido
			this.indice++;
		}while(this.folha.getRow(indice)==null && this.indice<=fim);
				
		return retorno;
	}

}
