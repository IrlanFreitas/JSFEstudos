package br.com.caelum.livraria.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	
	private DAO<Livro> dao;
	
	private DAO<Autor> daoAutor;
	
	private Integer autorId;
	
	private String testeMensagem = "Valor não aceito.";
	
	@PostConstruct
	public void inicializacao() {
		this.dao = new DAO<Livro>(Livro.class);
		this.daoAutor = new DAO<Autor>(Autor.class);
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			//Explicação:
			
			//FacesContext.getCurrentInstance() - É a instancia atual de jsf que está ligada ao ManagedBean
			//addMessage() - recebe a jsf que a mensagem irá e a mensagem, id registrado no facesContext, como livro.xhtml onde o id é livro, isso aparecerá no h:messages
			return;
		}

		this.dao.adiciona(this.livro);
		this.livro = new Livro();
	}
	
	public void adicionarAutor() {
		
		Autor autor = this.daoAutor.buscaPorId(autorId);
		
		this.livro.adicionaAutor(autor);
		System.out.println("Adicionando livro ao autor.");
		
	}
	
	//Método que navega para outra página.
	public String formAutor() {
		System.out.println("Será chamado na Fase Process Validations - Chamando formulário Autor");
		/* - Sem o parametro ?faces-redirect=true o redirecionamento é feito pelo servidor 
			e o corredo é ser feito pelo navegador 
		*/
		return "autor?faces-redirect=true";
	}

	
	//Criando o validador personalizado.
	
	/*Um contexto que permite obter informações da view processada no momento, 
	o componente da view que está sendo validado e um objeto que representa o valor digitado pelo usuário.*/
	
	/*O primeiro parâmetro é do tipo javax.faces.context.FacesContext e permite obter informações da view processada no momento.

	O segundo parâmetro é do tipo javax.faces.component.UIComponent e é um referencia ao componente que está sendo validado, normalmente um UIInput.

	O terceiro parâmetro é do tipo java.lang.Object e é um objeto que representa o valor digitado pelo usuário. O objeto já foi convertido.*/
	public void comecarComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		
		String valor = String.valueOf(value);
		
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deve começar com dígito 1")); //Sinaliza pro JSF que algo saiu errado.
		}
		
	}
	
	
	public void validarValores(FacesContext fc, UIComponent component, Object value ) throws ValidatorException {
		
		double valor = Double.parseDouble(value.toString());
		
		if (valor < 1) {
			throw new ValidatorException(new FacesMessage("Valor é muito baixo"));
		} else if ( valor > 1000000) {
			throw new ValidatorException(new FacesMessage("Valor é muito alto"));
		}
		
	}

	public String getTesteMensagem() {
		return testeMensagem;
	}

	public void setTesteMensagem(String testeMensagem) {
		this.testeMensagem = testeMensagem;
	}
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public Livro getLivro() {
		return livro;
	}
	
	public List<Autor> getAutores() {
		return this.daoAutor.listaTodos();
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public List<Livro> getLivros() {
		return dao.listaTodos();
	}
}
