package repositorio.filme;

public class FilmeJaExistenteException extends Exception {

	public FilmeJaExistenteException() {
		super("Este nome pertence a outro filme já existente!");
	}
}
