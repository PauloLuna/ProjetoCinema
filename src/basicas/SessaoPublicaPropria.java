package basicas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SessaoPublicaPropria extends Sessao {

	private String titulo;

	public SessaoPublicaPropria(String id, Sala sala, Date horaInicio,
			Date duracao, String titulo) {
		super(id, sala, horaInicio, null);
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar duracao2 = new GregorianCalendar();
		duracao2.setTime(duracao);
		gc.setTime(horaInicio);
		gc.add(Calendar.HOUR, duracao2.get(Calendar.HOUR_OF_DAY));
		gc.add(Calendar.MINUTE, duracao2.get(Calendar.MINUTE));
		gc.add(Calendar.SECOND, duracao2.get(Calendar.SECOND));
		Date horaFim = gc.getTime();
		super.setHoraFim(horaFim);
	}

	public String getTitulo() {
		return this.titulo;
	}

	public String geraRelatorio() {
		String retorno;
		retorno = "Sess�o pr�pria\nTitulo da sess�o: "+this.getTitulo()+"	Sala: "+super.getSala().getCodigo()+"\n";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		retorno+="Hora de in�cio: "+df.format(super.getHoraInicio())+" Hora de t�rmino: "+df.format(super.getHoraFim())+"\n";
		int[] cad = super.contaCadeiras();
		retorno += "N�mero de cadeiras quebradas durante sess�o: "+ cad[1]+"\nN�mero de cadeiras vendidas ocupadas na sess�o: " 
				+ cad[0];
		
		return retorno;
	}
	

}
