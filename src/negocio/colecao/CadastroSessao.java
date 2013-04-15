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
import repositorio.sessao.TipoDeObjetoNaoSuportado;

public class CadastroSessao {

	private RepositorioSessao repSessao;
	private ControleRelatorios ctrRelatorio;

	public CadastroSessao(RepositorioSessao repSessao, ControleRelatorios ctrRelatorio) throws IOException{
		this.repSessao = repSessao;
		this.ctrRelatorio = ctrRelatorio;
	}

	public IteratorSessao getIteratorSessao() throws TipoDeObjetoNaoSuportado {
		IteratorSessao retorno = null;

		retorno = this.repSessao.getIterator();

		return retorno;
	}

	public void removerSessao(String idSessao) throws FileNotFoundException, SessaoNaoEncontradaException, IOException, RelatorioNaoEncontradoException, TipoDeObjetoNaoSuportado {
		Sessao sessao = this.repSessao.buscar(idSessao);//procura a sessão e copia
		this.repSessao.remover(idSessao);//remove do repositorio
		if(this.ctrRelatorio.temRelatorio(sessao.getSala().getCodigo())){
			//Checa se já existe relatório para sua sala, se sim insere os seus dados no relatorio
			this.ctrRelatorio.buscarRelatorio(sessao.getSala().getCodigo()).getTexto().inserir(sessao.geraRelatorio());			
		} else {
			//Se não cria um novo relatorio já inserindo seus dados
			this.ctrRelatorio.inserirRelatorio(sessao.getSala().getCodigo(), sessao.geraRelatorio());		
		}
	}	

	public void inserirSessao(Sessao sessao) throws FileNotFoundException, IOException, SessaoConflitanteException, TipoDeObjetoNaoSuportado{

		repSessao.inserir(sessao);

	}

	public Sessao buscarSessao(String id) throws SessaoNaoEncontradaException, TipoDeObjetoNaoSuportado{
		Sessao retorno = null;
		retorno = repSessao.buscar(id);
		return retorno;
	}

}
