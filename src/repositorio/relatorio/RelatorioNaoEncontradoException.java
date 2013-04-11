package repositorio.relatorio;

public class RelatorioNaoEncontradoException extends Exception {

	public RelatorioNaoEncontradoException() {
		super("Relatório não encontrado");
	}
}
