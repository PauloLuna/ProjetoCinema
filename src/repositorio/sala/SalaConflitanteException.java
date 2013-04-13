package repositorio.sala;


public class SalaConflitanteException extends ErrosSala{
	private String menssagem;
	
	public SalaConflitanteException(){
		super("Ja Existe uma sala cadastrada com o mesmo codigo!");
	}
}