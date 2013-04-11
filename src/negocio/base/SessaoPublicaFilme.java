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
		retorno = "Sess�o filme\nTitulo da sess�o: "+this.getTitulo()+"	Sala: "+super.getSala().getCodigo()+"\n";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		retorno+="Hora de in�cio: "+df.format(super.getHoraInicio())+" Hora de t�rmino: "+df.format(super.getHoraFim())+"\n";
		int[] cad = super.contaCadeiras();
		retorno += "N�mero de cadeiras quebradas durante sess�o: "+ cad[1]+"\nN�mero de cadeiras vendidas ocupadas na sess�o: " 
				+ cad[0];

		return retorno;
	}

}
