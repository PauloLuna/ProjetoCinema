package repositorio;

public class ErrosSala extends Exception{
	private String menssagem;
	
	public ErrosSala(String menssagem){
		super(menssagem);
		this.menssagem = menssagem;
	}
	
	// Métodos Get Menssagem
	public String getMessage(){
		return this.menssagem;
	}
	
}

