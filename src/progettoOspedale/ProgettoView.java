package progettoOspedale;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class ProgettoView extends JFrame implements Observer{
	// Campi della view
    private JTextField m_userInputCF = new JTextField(16);
    private JTextField m_userInputCod = new JTextField(10);
    private JButton m_submitBtn = new JButton("Invia");
    private JButton m_clearBtn = new JButton("Cancella");
    private JLabel m_message = new JLabel("");
    JPanel maincontent = new JPanel();
    JPanel maincontent2 = new JPanel();
    JFrame jF = new JFrame("Inserisci dati");
    
    // Riferimento a model
    private ProgettoModel m_model;
    
    // Costruttore
    ProgettoView(ProgettoModel model) {
    	// Alloco il riferimento passato relativo al modello
    	m_model = model;
    	// Il model implementa Observable, aggiungo al modello un Observer 
    	// (la view stessa)
    	m_model.addObserver(this);
    	
    	// Inizio a configurare la vista
        //m_totalTf.setText(model.getValue());
        //m_totalTf.setEditable(false);
        
        // Layout dei componenti  
        //JPanel maincontent = new JPanel();
        //JPanel maincontent2 = new JPanel();
        JPanel textcontent = new JPanel();
        JPanel textcontentExt = new JPanel();
        JPanel buttoncontent = new JPanel();
        
        maincontent.setBorder(new EmptyBorder(10, 10, 10, 10));
        maincontent.setLayout(new BorderLayout());
        maincontent.setPreferredSize(new Dimension(400, 250));
        maincontent2.setBorder(new EmptyBorder(10, 10, 10, 10));
        maincontent2.setLayout(new BorderLayout());
        maincontent2.setPreferredSize(new Dimension(400, 250));
        textcontentExt.setLayout(new FlowLayout());
        textcontent.setLayout(new GridLayout(0, 1));
        textcontent.setPreferredSize(new Dimension(300,  150));
        //textcontent.setBorder(new EmptyBorder(10, 10, 10, 10));
        textcontent.add(new JLabel("Codice fiscale"));
        textcontent.add(m_userInputCF);
        textcontent.add(new JLabel("Codice ricetta"));
        textcontent.add(m_userInputCod);
        textcontent.add(m_message);
        buttoncontent.setLayout(new FlowLayout());
        buttoncontent.add(m_submitBtn);
        buttoncontent.add(m_clearBtn);
        
        textcontentExt.add(textcontent);
        maincontent.add(textcontentExt, BorderLayout.CENTER);
        maincontent.add(buttoncontent, BorderLayout.SOUTH);
        
        maincontent2.add(new JLabel("porcoddio"));
        
        // Creo il contenitore...
        //jF.setContentPane(maincontent);
        jF.getContentPane().add(maincontent);
        jF.pack();
        jF.setVisible(true);
        // Imposto il titolo alla view
        //jF.setTitle("Inserisci info");
        // Imposto il meccanismo di chiusura sulla finestra
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
        
    /*
     * I metodi seguenti servono a chi detiene un riferimento alla view 
     * (il controller)
     * Se non ci fossero, il controller dovrebbe avere un riferimento 
     * esplicito a tutti gli elementi della view per poter svolgere 
     * operazioni. In questo modo, invece, � sufficiente avere il 
     * riferimento all'intera classe CalcView.
     */
    // Getter per rendere disponibile all'esterno il valore del campo 
    // testo del textField
    String getUserCF() {
        return m_userInputCF.getText();
    }    
    
    String getUserCod() {
        return m_userInputCod.getText();
    }   
    
    // Rende disponibile all'esterno l'eventuale testo del messaggio di errore 
    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    
    // Permette di impostare dall'esterno il listener del bottone moltiplica
    void submitInfo(ActionListener mal) {
        m_submitBtn.addActionListener(mal);
    }
    
    // Permette di impostare dall'esterno il listener del bottone clear
    void addClearListener(ActionListener cal) {
        m_clearBtn.addActionListener(cal);
    }

	// Ereditato da Observer, chiama il metodo update definito localmente 
    // quando l'osservato (il modello) effettua una notifica
	@Override
	public void update(Observable o, Object arg) {
		if(arg == "reset")
			updateReset();
		else
			updateCheck();
	}
	
    // Permette di fare l'update dall'esterno.
    // In questo caso è l'azione compiuta dalla GUI quando il model 
	// (che è stato impostato come Observable) effettua una notifica
	private void updateReset() {
		// Estraggo il valore corrente della "memoria" del modello dal
		// riferimento al modello e aggiorno il textField.
		System.out.println("[VIEW] Notified by the model");
		//m_message.setText(m_model.getError());
		m_userInputCF.setText(m_model.getValue());
		m_userInputCod.setText(m_model.getValue());
		//this.setContentPane(maincontent2);
	}
	
	private void updateCheck() {
		// Estraggo il valore corrente della "memoria" del modello dal
		// riferimento al modello e aggiorno il textField.
		System.out.println("[VIEW] Notified by the model");
		if(!(m_model.getError().equals(""))) { 
			m_message.setText(m_model.getError());
			}
		else {
			jF.getContentPane().remove(maincontent);
			jF.getContentPane().add(maincontent2);
			jF.revalidate();
			jF.repaint();
		}
		
		//m_userInputCF.setText(m_model.getValue());
		//m_userInputCod.setText(m_model.getValue());
			
	}
}
