package basicas;

public class Cadeira {
	private boolean cadeiraQuebrada;
	private boolean cadeiraComprada;
	
	public Cadeira(){
		// Apenas especificando!
		this.cadeiraQuebrada = false;
		this.cadeiraComprada = false;
	}
	
	// Métodos GET
	public boolean getCadeiraQuebrada(){
		return this.cadeiraQuebrada;
	}
	
	public boolean getCadeiraComprada(){
		return this.cadeiraComprada;
	}
	
	// Métodos GET
	public void setCadeiraQuebrada(boolean estado){
		this.cadeiraQuebrada = estado;
	}
	
	public void setCadeiraComprada(boolean estado){
		this.cadeiraComprada = estado;
	}
	
	
} // Fim da classe cadeira
