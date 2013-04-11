package negocio.base;

import java.text.SimpleDateFormat;
import java.util.*;

public class SessaoPublicaFilme extends Sessao {

	private Filme filme;

	public SessaoPublicaFilme(String id, Filme filme, Sala sala, Date horaInicio) {
		super(id, sala, horaInicio, null);
		this.setFilme(filme);	

		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar duracao = new GregorianCalendar();
		duracao.setTime(filme.getDuracao());
		gc.setTime(horaInicio);
		gc.add(Calendar.HOUR, duracao.get(Calendar.HOUR_OF_DAY));
		gc.add(Calendar.MINUTE, duracao.get(Calendar.MINUTE));
		gc.add(Calendar.SECOND, duracao.get(Calendar.SECOND));
		Date horaFim = gc.getTime();
		super.setHoraFim(horaFim);


	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public String getTitulo() {

		return this.filme.getNome();
	}

	public String geraRelatorio() {
		String retorno;
		retorno = "Sessão filme\nTitulo da sessão: "+this.getTitulo()+"	Sala: "+super.getSala().getCodigo()+"\n";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		retorno+="Hora de início: "+df.format(super.getHoraInicio())+" Hora de término: "+df.format(super.getHoraFim())+"\n";
		int[] cad = super.contaCadeiras();
		retorno += "Número de cadeiras quebradas durante sessão: "+ cad[1]+"\nNúmero de cadeiras vendidas ocupadas na sessão: " 
				+ cad[0];

		return retorno;
	}

}
