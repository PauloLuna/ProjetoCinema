package negocio.base;

import java.util.Date;

public class SessaoFechadaPropria extends SessaoPublicaPropria {

	public SessaoFechadaPropria(String id, Sala sala, Date horaInicio,
			Date duracao, String titulo) {
		//Sess�o com conte�do do comprador que come�a toda vendida
		super(id, sala, horaInicio, duracao, titulo);
		Cadeira[][] cad = this.getSala().getCadeiras();
		for(int i = 0; i < cad.length; i++){
			for(int j = 0; j < cad[i].length; j++){
				if(!cad[i][j].getCadeiraQuebrada()){
					cad[i][j].setCadeiraComprada(true);
				}
			}
		}
	}

}
