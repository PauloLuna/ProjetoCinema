package repositorio.sessao;

public class SessaoNaoEncontradaException extends Exception {
	
	public SessaoNaoEncontradaException() {
		super("Sessão não encontrada");
	}

}
