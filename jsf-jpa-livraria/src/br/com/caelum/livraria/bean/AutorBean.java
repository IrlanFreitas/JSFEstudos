package br.com.caelum.livraria.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.utils.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	
	private DAO<Autor> dao;
	
	private Integer autorId;
	
	@PostConstruct
	public void inicializar() {
		dao = new DAO<Autor>(Autor.class);
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if (autor.getId() != null && autor.getId() != 0) {
			dao.atualiza(this.autor);
		} else {
			dao.adiciona(this.autor);			
		}
		
		this.autor = new Autor();
		
		/*
		 Depois que o livro é criado, é feito o redirecionamento pelo navegado
		 para a página de Livro */
//		return new RedirectView("livro");
	}
	
	public RedirectView irParaLivro() {
		return new RedirectView("livro");
	}
	
	public List<Autor> getAutores() {
		return dao.listaTodos();
	}
	
	public void remover(Autor autor) {
		dao.remove(autor);
		System.out.println("LOG - Removendo autor");
	}
	
	public void carregar(Autor autor) {
		System.out.println(autor);
		this.autor = autor;
		System.out.println("LOG - Carregando autor");
	}
	
	public void carregarAutorPelaId() {
		this.autor = dao.buscaPorId(this.autorId);
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
}
