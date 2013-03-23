package iterator;

import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import negocio.Filme;

public class IteratorFilmeExel implements IteratorFilme {

	private Sheet folha;
	private int indice;
	
	
	public IteratorFilmeExel(Sheet folha) {
		this.folha = folha;
		this.indice = folha.getFirstRowNum();//Come�a o �ndice com a primeira linha v�lida
	}
	public boolean hasNext() {
		Row row = this.folha.getRow(indice);
		return row!=null;
	}

	public Filme next() {
		Row row  = this.folha.getRow(indice);
		int fim = this.folha.getLastRowNum();
		//Obt�m os valores da linha do arquivo para o objeto
		String nome = row.getCell(0).getStringCellValue();
		long duracao1 = (long)row.getCell(1).getNumericCellValue();
		Date duracao=new Date(duracao1);//Converte o valor da dura��o em long para um objeto tipo Date
		String categoria = row.getCell(2).getStringCellValue();
		String classificacao = row.getCell(3).getStringCellValue();
		String descricao = row.getCell(4).getStringCellValue();

		Filme retorno = new Filme(nome, duracao, categoria, classificacao, descricao);//Inst�ncia o objeto
		do{//Procur o pr�ximo �ndice v�lido
			this.indice++;
		}while(this.folha.getRow(indice)==null && this.indice<=fim);
				
		return retorno;
	}

}
