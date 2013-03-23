package negocio;

import java.util.Date;

public class Filme {

	private String nome;
	private Date duracao;
	private String categoria;
	private String classificacao;
	private String descricao;
	
	public Filme(String nome, Date duracao, String categoria, String classificacao, String descricao){
		this.setNome(nome);
		this.setDuracao(duracao);
		this.setCategoria(categoria);
		this.setClassificacao(classificacao);
		this.setDescricao(descricao);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}

	public Date getDuracao() {
		return duracao;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}
