package negocio.base;

import negocio.base.Cadeira;

public class Sala {
	private String codigo;
	private Cadeira[][] cadeiras;
	private int numFilas;
	private int numColunas;
	
	
	public Sala(String codigo,int numFilas,int numColunas){
		this.codigo = codigo;
		this.numColunas = numColunas;
		this.numFilas = numFilas;
		this.cadeiras = new Cadeira[this.numFilas][this.numColunas];
		
		// Inicializando as cadeiras
		for (int i = 0; i < this.numFilas; i ++){
			for (int j = 0; j < this.numColunas; j++){
				this.cadeiras[i][j] = new Cadeira();
			}// Fim do FOR J
		}// Fim do FOR I	}
	
	} // Fim do contrutor
	

	
	// Metodos GET
	public String getCodigo(){
		return this.codigo;
	}
	
	public Cadeira[][] getCadeiras(){
		return this.cadeiras;
	}
	
	public int getNumFilas(){
		return this.numFilas;
	}
	
	public int getNumColunas(){
		return this.numColunas;
	}
	
	
	// Métodos SET
	public void setCodigo(String codigo){
		this.codigo = codigo;
	}
	public void setCadeiras(Cadeira[][] cadeiras){
		this.cadeiras = cadeiras;
	}
	
	
	// OUTROS MÉTODOS
	public Sala getCopiaObjeto(){
		/*
		 * Faz um copia do objeto  e a retorna 
		 */		
		
		boolean estado;
		Sala sala;

		
		// Instanciando a variável a ser devolvida	
		sala = new Sala(this.codigo,this.numFilas, this.numColunas);
		
		// Preenchendo as cadeiras
		for (int i = 0; i < this.numFilas; i ++){
			for (int j = 0; j < this.numColunas; j++){
				//Pega o estado da cadeira atual e passa para nova cadeira
				estado = this.cadeiras[i][j].getCadeiraComprada();
				sala.cadeiras[i][j].setCadeiraComprada(estado);

				estado = this.cadeiras[i][j].getCadeiraQuebrada();
				sala.cadeiras[i][j].setCadeiraQuebrada(estado);
			}// Fim do FOR J
		}// Fim do FOR I

		return sala;
	}
	
	// Método CompareTO
	public boolean compareTo(Sala sala){
		/*
		 * Compara se o objeto this.sala é igual ao objeto sala
		 */
		boolean  compareTo;
		
		// Checango se é a mesma SALA
		if (this.codigo.equals(sala.getCodigo())){
			compareTo = true;
		}
		else {
			compareTo = false;
		}
		
		
		return compareTo;
	}// Fim do método compareTo
	
} // Fim da classe SALA
