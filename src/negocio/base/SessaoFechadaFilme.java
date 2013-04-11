package negocio.base;

import java.util.Date;

public class SessaoFechadaFilme extends SessaoPublicaFilme {

	public SessaoFechadaFilme(String id, Filme filme, Sala sala,
			Date horaInicio) {
		super(id, filme, sala, horaInicio);
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
