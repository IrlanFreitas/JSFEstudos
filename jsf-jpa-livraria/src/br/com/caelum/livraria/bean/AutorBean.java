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
	
	@PostConstruct
	public void inicializar() {
		dao = new DAO<Autor>(Autor.class);
	}
	
	public Autor getAutor() {
		return autor;
	}

	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		new DAO<Autor>(Autor.class).adiciona(this.autor);
		
		this.autor = new Autor();
		
		/*
		 Depois que o livro � criado, � feito o redirecionamento pelo navegado
		 para a p�gina de Livro */
		return new RedirectView("livro");
	}
	
	public List<Autor> getAutores() {
		return dao.listaTodos();
	}
	
	public void remover(Autor autor) {
		dao.remove(autor);
		System.out.println("LOG - Removendo autor");
	}
	
}
