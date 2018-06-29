package br.com.caelum.livraria.utils;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LogPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -8266545933776202465L;

	@Override
	public void afterPhase(PhaseEvent event) {
//		System.out.println("LOG - Depois da fase:" + event.getPhaseId().getName() );
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("LOG - FASE: " + event.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		//Indicando que queremos logar todas as fases.
		return PhaseId.ANY_PHASE;
	}

}
