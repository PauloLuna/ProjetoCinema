package negocio.colecao;

import iterator.IteratorSessao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import negocio.base.Relatorio;
import negocio.base.Sessao;

import repositorio.relatorio.*;
import repositorio.sessao.RepositorioSessao;
import repositorio.sessao.SessaoConflitanteException;
import repositorio.sessao.SessaoNaoEncontradaException;

public class CadastroSessao {
	
	private RepositorioSessao repSessao;
	private ControleRelatorios ctrRelatorio;
	
	public CadastroSessao(RepositorioSessao repSessao, ControleRelatorios ctrRelatorio) throws IOException{
		this.repSessao = repSessao;
		this.ctrRelatorio = ctrRelatorio;
	}
	
	public IteratorSessao getIteratorSessao() {
		return this.repSessao.getIterator();
	}

	public void removerSessao(String idSessao) throws FileNotFoundException, SessaoNaoEncontradaException, IOException, RelatorioNaoEncontradoException {
		Sessao sessao = this.repSessao.buscar(idSessao);
		this.repSessao.remover(idSessao);
		if(this.ctrRelatorio.temRelatorio(sessao.getSala().getCodigo())){
			this.ctrRelatorio.buscarRelatorio(sessao.getSala().getCodigo()).addTexto(sessao.geraRelatorio());			
		} else {
			this.ctrRelatorio.inserirRelatorio(sessao.getSala().getCodigo(), sessao.geraRelatorio());		
		}
	}	
	
	public void inserirSessao(Sessao sessao) throws FileNotFoundException, IOException, SessaoConflitanteException{
		repSessao.inserir(sessao);
	}
	
	public Sessao buscarSessao(String id) throws SessaoNaoEncontradaException{
		Sessao retorno = null;
		retorno = repSessao.buscar(id);
		return retorno;
	}

}
