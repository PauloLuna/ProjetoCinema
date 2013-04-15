package repositorio.sala;


public class SalaNaoEncontradaException extends ErrosSala{
	private String menssagem;
	
	public SalaNaoEncontradaException(){
		super("Sala Não Encontrada!!");
	}
}
