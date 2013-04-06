package repositorio;

import iterator.IteratorSalaArray;
import basicas.Sala;
public class RepositorioSalaArray implements RepositorioSala {
	private Sala salaArray[];
	private int indice;
	
	public RepositorioSalaArray(){
		this.salaArray = new Sala[50];
		this.indice = 0;
	}
	
	// Métodos GET
	public int getIndice(){
		return this.indice;
	}
	
	public Sala[] getSala(){
		return this.salaArray;
	}
	
	// Métodos SET
	public void setIndice(int indice){
		this.indice = indice;
	}
	

	// Outros Métodos
	public Sala buscar(String codigo) throws SalaNaoAchada{
		Sala salaProcurada = null;
		boolean achado = false;
		
		for (int i = 0; i < this.indice; i++){
			if (this.salaArray[i].getCodigo().equals(codigo)){
				// Confirma que foi achado
				achado = true;
				// Jogo o objeto na variavel que será retornada
				salaProcurada = this.salaArray[i];
			}
		}
		
		// Checando se a sala não foi achada
		if(achado){
			;
		}
		else {
			// lançando Erro, SALA NAO ENCONTRADA
			SalaNaoAchada erro;
			erro = new SalaNaoAchada();
			throw erro;
		}
		
		// Retorna a sala
		return salaProcurada;
	} // Fim do método buscar
	
	
	//Método Inserir
	public void inserir(Sala sala) throws SalaExistente {
		
		// Checando se o array não está cheio
		if (this.salaArray.length == this.indice){
			
			// Criar um novo array com um tamanho maior, duas vezes maior
			Sala novoArray[] = new Sala[this.salaArray.length*2];
			
			//Passando todos os objetos do antigo array para o novo
			for (int i = 0; i < this.indice; i++){
				novoArray[i] = this.salaArray[i];
			}
			
			// Sobrescrevendo o antigo array pelo novo
			this.salaArray = novoArray;
			
		} // fim do if
		
		// Checando se o objeto já se encontra no Array
		for (int i = 0; i < this.indice; i++){
			if (this.salaArray[i].getCodigo() == sala.getCodigo()){
				// Lançando Erro
				SalaExistente erro;
				erro = new SalaExistente();
				throw erro;
			}
		} // Fim do for
		
		
	// Cadastrando o objeto sala no array, e somando 1 ao indice
	this.salaArray[this.indice] = sala;
	this.indice++;
		
	} // Fim do método Inserir
	
	
	//Método Remover
	public void remover(String codigo) throws SalaNaoAchada {
		
		// Procurando o objeto no Array
		for (int i = 0; i < this.indice ; i++){
			
			// Se o codigo for o mesmo do objeto achado
			if (this.salaArray[i].getCodigo().equals(codigo)){
				
				//Pegamos o último objeto do array e sobrescrevemos nessa posicao e diminuimos o indice
				this.salaArray[i] = this.salaArray[this.indice];
				this.indice--;
			}
		} // Fim do for
	} // Fim do método remover
	
	public void atualizar(Sala sala, String codigo) throws SalaNaoAchada {
		// Primeiro checar se o objeto j� est� no array, se n�o estiver um erro ser� lan�ado por buscar
		this.buscar(sala.getCodigo());
		
		for (int i = 0; i < this.indice; i++){
			//Percorrendo o array
			if(this.salaArray[i].getCodigo() == codigo){
				// Sobrescrevendo o objeto sala no array
				this.salaArray[i] = sala;
			}
		} // Fim do for

	} // Fim do método atualiza

	public IteratorSalaArray getIterator() {
		IteratorSalaArray iterator = new IteratorSalaArray(this.salaArray);

		return iterator;
	} // Fim do m�todo getIterator

	
	
} // Fim da Classe
