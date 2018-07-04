package br.com.caelum.livraria.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();
	
	private DAO<Usuario> dao;
	
	@PostConstruct
	public void inicializa() {
		dao = new DAO<Usuario>(Usuario.class);
	}
	
	public String acessar() {
		Usuario usuario = dao.acessar(this.usuario);
		
		if (usuario != null) {
			return "livro?faces-redirect=true";
			
		} else {
			FacesContext.getCurrentInstance().addMessage("login", new FacesMessage("Usuário não encontrado.") );
			return null;
		}
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
