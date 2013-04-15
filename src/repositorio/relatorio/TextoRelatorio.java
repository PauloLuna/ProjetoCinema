package repositorio.relatorio;

import java.io.Serializable;
import java.util.Date;

import negocio.base.Relatorio;

public class TextoRelatorio implements  Comparable, Serializable {

	private Date dataCriacao;
	private String texto;
	
	public TextoRelatorio(String texto){
		this.setTexto(texto);
		this.setDataCriacao(new Date());
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public int compareTo(Object relatorio) {
		return ((TextoRelatorio)relatorio).getDataCriacao().compareTo(this.dataCriacao);
	}
	
}
