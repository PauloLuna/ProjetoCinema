package negocio.base;

import java.io.Serializable;
import java.util.Date;

public class Relatorio implements Serializable{
	private String nomeSala;
	private Date dataCriacao;
	private String texto;
	
	public Relatorio(String nomeSala, Date dataCriacao, String texto){
		this.setNomeSala(nomeSala);
		this.setDataCriacao(dataCriacao);
		this.setTexto(texto);
	}
	
	
	public String getNomeSala() {
		return nomeSala;
	}
	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
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
	
	public void addTexto(String texto){
		this.texto += "\n\n"+texto;
	}
	
	

}
