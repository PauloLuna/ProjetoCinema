package repositorio.sessao;

public class SessaoConflitanteException extends Exception {
	
	public SessaoConflitanteException(){
		super("Esta sess�o est� entrando em conflito com outra existente");
	}

}
