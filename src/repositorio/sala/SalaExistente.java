package repositorio.sala;


public class SalaExistente extends ErrosSala{
	private String menssagem;
	
	public SalaExistente(){
		super("Ja Existe uma sala cadastrada com o mesmo codigo!");
	}
}