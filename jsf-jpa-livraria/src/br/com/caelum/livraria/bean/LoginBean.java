package br.com.caelum.livraria.bean;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {

	private Usuario usuario = new Usuario();
	
	private DAO<Usuario> dao;
	
	private boolean usuarioLogado;
	
	@PostConstruct
	public void inicializa() {
		dao = new DAO<Usuario>(Usuario.class);
	}
	
	public String acessar() {
		Usuario usuario = dao.acessar(this.usuario);
		
		if (usuario != null) {
			
			//Indicando o usuário logado
			this.usuarioLogado = true;
			
			//Guardando os dados do usuário logado na sessão
			
			//Obtendo a sessão
			FacesContext context = FacesContext.getCurrentInstance();
			//Obtendo o contexto da sessão
			Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
			//Inserindo os dados necessários na sessão para utiliza-los.
			sessionMap.put("usuarioLogado", usuario);
			
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
	
	public String logout() {
		
		//Obtendo a sessão
		FacesContext context = FacesContext.getCurrentInstance();
		//Obtendo o contexto da sessão
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		//Removendo os dados da sessão
		sessionMap.remove("usuarioLogado");
		
		this.usuarioLogado = false;
		return "login?faces-redirect=true";
	}

	public boolean isUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(boolean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
}
