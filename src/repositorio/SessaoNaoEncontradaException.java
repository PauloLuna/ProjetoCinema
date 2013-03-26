package repositorio;

public class SessaoNaoEncontradaException extends Exception {
	
	public SessaoNaoEncontradaException() {
		super("Sessão não encontrada");
	}

}
