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
	
	private String testeMensagem = "Valor n�o aceito.";
	
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
			//Explica��o:
			
			//FacesContext.getCurrentInstance() - � a instancia atual de jsf que est� ligada ao ManagedBean
			//addMessage() - recebe a jsf que a mensagem ir� e a mensagem, id registrado no facesContext, como livro.xhtml onde o id � livro, isso aparecer� no h:messages
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
	
	//M�todo que navega para outra p�gina.
	public String formAutor() {
		System.out.println("Ser� chamado na Fase Process Validations - Chamando formul�rio Autor");
		/* - Sem o parametro ?faces-redirect=true o redirecionamento � feito pelo servidor 
			e o corredo � ser feito pelo navegador 
		*/
		return "autor?faces-redirect=true";
	}

	
	//Criando o validador personalizado.
	
	/*Um contexto que permite obter informa��es da view processada no momento, 
	o componente da view que est� sendo validado e um objeto que representa o valor digitado pelo usu�rio.*/
	
	/*O primeiro par�metro � do tipo javax.faces.context.FacesContext e permite obter informa��es da view processada no momento.

	O segundo par�metro � do tipo javax.faces.component.UIComponent e � um referencia ao componente que est� sendo validado, normalmente um UIInput.

	O terceiro par�metro � do tipo java.lang.Object e � um objeto que representa o valor digitado pelo usu�rio. O objeto j� foi convertido.*/
	public void comecarComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		
		String valor = String.valueOf(value);
		
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deve come�ar com d�gito 1")); //Sinaliza pro JSF que algo saiu errado.
		}
		
	}
	
	
	public void validarValores(FacesContext fc, UIComponent component, Object value ) throws ValidatorException {
		
		double valor = Double.parseDouble(value.toString());
		
		if (valor < 1) {
			throw new ValidatorException(new FacesMessage("Valor � muito baixo"));
		} else if ( valor > 1000000) {
			throw new ValidatorException(new FacesMessage("Valor � muito alto"));
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
