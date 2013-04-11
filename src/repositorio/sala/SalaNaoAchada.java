package repositorio.sala;


public class SalaNaoAchada extends ErrosSala{
	private String menssagem;
	
	public SalaNaoAchada(){
		super("Sala Não Encontrada!!");
	}
}
