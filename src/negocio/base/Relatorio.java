package negocio.base;

import java.io.Serializable;
import java.util.Date;

import repositorio.relatorio.RepositorioTextosRelatorios;

public class Relatorio implements Serializable{
	//Cada relatório responde a uma sala
	private String nomeSala;
	private Date dataModificacao;
	private RepositorioTextosRelatorios texto;
	
	public Relatorio(String nomeSala, Date dataModificacao){
		this.setNomeSala(nomeSala);
		this.setDataModificacao(dataModificacao);
		RepositorioTextosRelatorios texto = new RepositorioTextosRelatorios();
		this.setTexto(texto);
	}
	
	
	public String getNomeSala() {
		return nomeSala;
	}
	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
	}
	public Date getDataModificacao() {
		return dataModificacao;
	}
	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	public RepositorioTextosRelatorios getTexto() {
		return texto;
	}
	public void setTexto(RepositorioTextosRelatorios texto) {
		this.texto = texto;
	}

}
