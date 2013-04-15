package fachada;

public class FalhaNaConfiguracaoException extends Exception {

	public FalhaNaConfiguracaoException() {
		super("Falha no arquivo de confuguração, apague-o");
	}
}
