package repositorio;

public class RelatorioNaoEncontradoException extends Exception {

	public RelatorioNaoEncontradoException() {
		super("Relatório não encontrado");
	}
}
