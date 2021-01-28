package progettoOspedale;

import java.awt.event.*;

public class ProgettoController {
	//Riferimenti, il controller deve interagire sia con la view che con il modello
    private ProgettoModel m_model;
    private ProgettoView  m_view;
    
    
    //Costruttore
    ProgettoController(ProgettoModel model, ProgettoView view) {
    	//alloco i riferimenti passati
        m_model = model;
        m_view  = view;
        
        //Aggiungo i listener (definiti qui) alla view attraverso i metodi appositi 
        //messi a disposizione della view
        view.submitInfo(new checkInfo());
        view.addClearListener(new ClearListener());
    }
    
    
    /*
     * Definizione dei listener (Inner classes)
     */
    //Listener per la moltiplicazione. Questa azione viene eseguita quando 
    //l'utente preme il tasto di moltiplicazione
    class checkInfo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	System.out.println("[Controller]Submit action");
            String userInputCF = "";
            String userInputCod = "";
        	//uso il riferimento alla view per catturare l'input
        	//inserito dall'utente
            userInputCF = m_view.getUserCF();
            userInputCod = m_view.getUserCod();
            //uso il riferimento al modello per fargli eseguire la moltiplicazione
            m_model.checkInfo(userInputCF, userInputCod);
            //m_view.showError(m_model.getError());
        }
    }
    
    //Listener per il reset. Questa azione viene eseguita quando 
    //l'utente preme il tasto di clear
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	System.out.println("[CONTROLLER] reset action received");
        	//Uso il riferimento al modello per fargli effettuare il reset
            m_model.reset();
         }
    }
}
