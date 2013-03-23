package negocio;

public class Cadeira {
	private boolean vendida;
	private boolean quebrada;
	private String numero;
	
	public Cadeira(String numero){
		this.setVendida(false);
		this.setQuebrada(false);
		this.setNumero(numero);
		
	}

	public boolean isVendida() {
		return vendida;
	}

	public void setVendida(boolean vendida) {
		this.vendida = vendida;
	}

	public boolean isQuebrada() {
		return quebrada;
	}

	public void setQuebrada(boolean quebrada) {
		this.quebrada = quebrada;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	

}
