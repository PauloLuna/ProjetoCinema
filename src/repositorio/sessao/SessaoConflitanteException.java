package repositorio.sessao;

public class SessaoConflitanteException extends Exception {
	
	public SessaoConflitanteException(){
		super("Esta sessão está entrando em conflito com outra existente");
	}

}
