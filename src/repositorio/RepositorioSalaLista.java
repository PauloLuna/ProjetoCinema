package repositorio;

import iterator.IteratorSala;
import iterator.IteratorSalaArray;
import basicas.Sala;

public class RepositorioSalaLista implements RepositorioSala {
	private Sala sala;
	private RepositorioSalaLista salaLista;
	
	
	// Primeiro construtor
	public RepositorioSalaLista(){
		// Sempre come�ar como NULL
		this.sala = null;
		salaLista = null;
	}
	
	
	public void inserir(Sala sala) throws SalaExistente {
		
		// Primeiro checamos se acaba na primeira posi��o
		if (this.sala == null) {
			this.sala = sala;
		}
		//Se o objeto j� se encontrar na lista lan�ar� um erro
		else if (this.sala.getCodigo().equals(sala.getCodigo())){
			SalaExistente erro;
			erro = new SalaExistente();
			throw erro;
		}
		// Depois checamos se acaba na segunda posi��o
		else if (this.salaLista == null){
			this.salaLista = new RepositorioSalaLista();
			this.salaLista.sala = sala;
		}
		else {
			this.salaLista.inserir(sala);
		}
		
	} // Fim do m�todo inserir
	
	
	public Sala buscar(String codigo) throws SalaNaoAchada {
		
		//Declarando objeto a ser retornado
		Sala objetoSala = null;
				
		// Checando se j� foi adicionado algum objeto na lista
		if (this.sala == null){
			//Lan�ando ERRO
			SalaNaoAchada erro;
			erro = new SalaNaoAchada();
			throw erro;
		}
		else if(this.sala.getCodigo().equals(codigo)){
			objetoSala = this.sala;
		}
		else if(this.salaLista == null){
			//Lan�ando ERRO
			SalaNaoAchada erro;
			erro = new SalaNaoAchada();
			throw erro;
		}
		else {
			//Chamando a fun��o recursivamente
			this.salaLista.buscar(codigo);
		}
		
		return objetoSala;
	}
	


	public void remover(String codigo) throws SalaNaoAchada {
		
		// Checando se chegamos ao final
		if (this.sala == null){
			SalaNaoAchada erro;
			erro = new SalaNaoAchada();
			throw erro;
		}
		else if (this.salaLista == null ){
			// Se salaLista = null, n�o existir continua��o e this.dado for o objeto a ser deletado
			if (this.sala.getCodigo().equals(codigo)){
				this.sala = null;
			}
			else {
				SalaNaoAchada erro;
				erro = new SalaNaoAchada();
				throw erro;
			}
		}
		else if (this.sala.getCodigo().equals(codigo)){
			//Se o this.sala for o objeto a ser procurado e existe mais objetos na sequencia...
			this.sala = this.salaLista.sala;
			this.salaLista = this.salaLista.salaLista;
		}
		else {
			//Chamando a fun��o recursivamente
			this.salaLista.remover(codigo);
		}
	
	} // Fim do m�todo remover

	
	public void atualizar(Sala sala,String codigo) throws SalaNaoAchada {
		// Procurando o objeto recursivamente
		
		// Checando se j� foi adicionado algum objeto na lista
		if (this.sala == null){
			//Lan�ando ERRO
			SalaNaoAchada erro;
			erro = new SalaNaoAchada();
			throw erro;
		}
		else if(this.sala.getCodigo().equals(codigo)){
			this.sala = sala;
		}
		else if(this.salaLista == null){
			//Lan�ando ERRO
			SalaNaoAchada erro;
			erro = new SalaNaoAchada();
			throw erro;
		}
		else {
			//Chamando a fun��o recursivamente
			this.salaLista.atualizar(sala,codigo);
		}
	
	} // Fim do m�todo Atualizar

	
	
	public IteratorSalaArray getIterator() {
		// Criando objeto Array
		Sala[] salas;
		salas = new Sala[this.contaIndice()];
		int indice = 0;
		
		// Modificando o array salas para o nosso array requerido
		this.getSalaArray(salas, indice);
		
		// Inicializado o iterator
		IteratorSalaArray iterator;
		iterator = new IteratorSalaArray(salas);
		
		return iterator ;	
	}
	
	private Sala[] getSalaArray(Sala[] salas, int indice){

		if (this.sala instanceof Sala){
			salas[indice] = this.sala;
			this.salaLista.getSalaArray(salas, indice++);
		}
		
		return salas;
	}
	
	private int contaIndice(){
		int indice = 0;
		
		if (this.sala != null){
			indice += this.salaLista.contaIndice();
		}
		
		return indice;
	}

} // Fim da Classe RepositorioSalaLista
