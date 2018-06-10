package br.com.caelum.livraria.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.caelum.livraria.model.Livro;

@ManagedBean
public class LivroBean {
	
	private Livro livro;
	
	@PostConstruct
	public void inicializa() {
		this.livro = new Livro();
	}
	
	public void gravar() {
		
		System.out.println(livro);
		
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
