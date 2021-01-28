package progettoOspedale;

import java.util.Observable;

public class ProgettoModel extends Observable{
	// Campo privato, il valore iniziale della "memoria" del modello
		// Campo privato, la "memoria" vera e propria del modello 
		private String clearString; 
		private String error;

		// Costruttore: chiama il reset per (re)impostare il valore inizale
		ProgettoModel() {
			reset();
		}

		// Reset del valore iniziale
		public void reset() {
			System.out.println("[MODEL] reset ");
			clearString = new String("");
			// Comunica un cambio dello stato
			setChanged();
			// Notifica gli observer (la view)
			notifyObservers();
			System.out.println("[MODEL] Observers notified (reset)");
		}


		// Moltiplica per il valore passato come stringa 
		// Attenzione: non dalla GUI ma dal controller
		public void checkInfo(String cf, String cod) {
			// Moltiplicazione vera e propria
			//m_total = m_total * new Integer(operand);
			//System.out.println("[MODEL] Multiply "+ operand);
			System.out.println("ricevuto: " + cf + " " + cod);
			if(cf.length() != 16)
				setError("Codice fiscale sbagliato");
			else
				setError("");
			// Comunica un cambio dello stato
			setChanged();
			// Notifica gli observer (la view)
			notifyObservers();
			System.out.println("[MODEL] Observers notified (mult)");
		}
		
		public void setError(String e) {
			error = e;
		}
		
		public String getError() {
			return error;
		}

		// Ritorna il valore della "memoria"
		public String getValue() {
			return clearString.toString();
		}
}
