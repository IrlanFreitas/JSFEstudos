package br.com.caelum.livraria.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = -7629755427189418856L;

	@Override
	public void afterPhase(PhaseEvent event) {
		/*
		 * Verificando as credenciais do usuário depois de recuperar a arvore de
		 * elementos da página
		 */
		System.out.println("AUTH - FASE - " + event.getPhaseId());

		FacesContext context = event.getFacesContext();

		// Obtendo o início da arvore de componentes programaticamente
		// Para depois obter o nome da página, chamado viewId
		String nomePagina = context.getViewRoot().getViewId();
		
		logger(nomePagina);
		
		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}
		
		//Obtendo o contexto da sessão
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		//Inserindo os dados necessários na sessão para utiliza-los.
		Usuario usuarioLogado = (Usuario) sessionMap.get("usuarioLogado");
		
		if (usuarioLogado != null) {
			return;
		}
		
		//Obtendo objeto necessário para navegação
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		//Redirecionamendo para login.xhtml
		//Onde está null - poderia ser um sinal indicativo para outra página chamado de outcome
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		
		//Renderizando a resposta
		context.renderResponse();
		
		

	}

	private void logger(String nomePagina) {
		SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
		String dataFormatada = "";

		dataFormatada = dt.format(new Date());

		System.out.println("AUTH - " + nomePagina +" - "+ dataFormatada);
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		// Chencando somente na primeira fase
		return PhaseId.RESTORE_VIEW;
	}

}
