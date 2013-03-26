package negocio;

import java.util.*;

public class Sessao {
	private Filme filme;
	private String sala;
	private Date horaInicio;
	private Date horaFim;
	private String id;

	public Sessao(String id,Filme filme, String sala, Date horaInicio) {
		this.setFilme(filme);
		this.setHoraInicio(horaInicio);
		this.setSala(sala);
		this.setId(id);
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar duracao = new GregorianCalendar();
		duracao.setTime(filme.getDuracao());
		gc.setTime(horaInicio);
		gc.add(Calendar.HOUR, duracao.get(Calendar.HOUR_OF_DAY));
		gc.add(Calendar.MINUTE, duracao.get(Calendar.MINUTE));
		gc.add(Calendar.SECOND, duracao.get(Calendar.SECOND));
		this.horaFim = gc.getTime();
	}

	public String getSala() {
		return this.sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public Date getHoraFim() {
		return this.horaFim;
	}

	public boolean checaConflito(Sessao sessao) {
		boolean retorno=true;
		if(this.sala.equals(sessao.getSala())){
			if(sessao.horaFim.compareTo(this.horaInicio)==-1){
				retorno = false;
			} else if(sessao.horaInicio.compareTo(this.horaFim)==1){
				retorno = false;
			}
		}
		return retorno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
