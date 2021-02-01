package progettoOspedale;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
        view.submitDate(new checkDate());
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
    
    class checkDate implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	System.out.println("[Controller]Submit date action");
            LocalDate userDate = null;
            LocalTime userTime = null;
            String cf = "";
            String cr = "";
        	//uso il riferimento alla view per catturare l'input
        	//inserito dall'utente
            userDate = m_view.getDate();
            userTime = m_view.getTime();
            cf = m_view.getUserCF();
            cr = m_view.getUserCod();
            //uso il riferimento al modello per fargli eseguire la moltiplicazione
            m_model.checkDate(userDate, userTime, cf, cr);
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
