package negocio.base;

import java.util.*;

public abstract class Sessao {
	private Sala sala;
	private Date horaInicio;
	private Date horaFim;
	private String id;

	public Sessao(String id, Sala sala, Date horaInicio, Date horaFim) {
		this.setHoraInicio(horaInicio);
		this.setSala(sala);
		this.setId(id);
		
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
	
	public Date getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Sala getSala() {
		return this.sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Date getHoraFim() {
		return this.horaFim;
	}
	
	public void setHoraFim(Date horaFim){
		this.horaFim = horaFim;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public abstract String getTitulo();
	
	public abstract String geraRelatorio();
	
	public int[] contaCadeiras(){
		int[] retorno = new int[2];
		retorno[0] = 0;
		retorno[1] = 0;
		Cadeira[][] cad = this.getSala().getCadeiras();
		for(int i = 0; i < cad.length; i++){
			for(int j = 0; j < cad[i].length; j++){
				if(cad[i][j].getCadeiraComprada()){
					retorno[0]++;
				}
				if(cad[i][j].getCadeiraQuebrada()){
					retorno[1]++;
				}
			}
		}
		
		return retorno;
	}


}
