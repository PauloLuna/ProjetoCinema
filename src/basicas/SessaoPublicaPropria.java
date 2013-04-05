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
		retorno = "Sessão própria\nTitulo da sessão: "+this.getTitulo()+"	Sala: "+super.getSala().getCodigo()+"\n";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		retorno+="Hora de início: "+df.format(super.getHoraInicio())+" Hora de término: "+df.format(super.getHoraFim())+"\n";
		int[] cad = super.contaCadeiras();
		retorno += "Número de cadeiras quebradas durante sessão: "+ cad[1]+"\nNúmero de cadeiras vendidas ocupadas na sessão: " 
				+ cad[0];
		
		return retorno;
	}
	

}
