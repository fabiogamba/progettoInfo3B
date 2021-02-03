package progettoOspedale;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class ProgettoView extends JFrame implements Observer{
	// Campi della view
    private JTextField m_userInputCF = new JTextField(16);
    private JTextField m_userInputCod = new JTextField(10);
    private JButton m_submitBtn = new JButton("Invia");
    private JButton m_clearBtn = new JButton("Cancella");
    private JButton m_submitBtn2 = new JButton("Invia");
    private JButton m_clearBtn2 = new JButton("Cancella");
    private JLabel m_message = new JLabel("");
    JPanel infopanel = new JPanel();
    JPanel datepick = new JPanel();
    JFrame jF = new JFrame("Inserisci dati");
    public DatePicker datePicker;
    TimePicker timePicker;
    DatePickerSettings dateSettings;
    TimePickerSettings timeSettings;
    JPanel eventpanel = new JPanel();
    LocalDate today = LocalDate.now();
    JLabel eventData = new JLabel();
    JLabel eventTime = new JLabel();
    
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
        JPanel buttoncontent2 = new JPanel();
        JPanel datecontent = new JPanel();
        JPanel eventinfo = new JPanel();
        
        //JLabel eventData = new JLabel();
        //JLabel eventTime = new JLabel();
        
        dateSettings = new DatePickerSettings();
        datePicker = new DatePicker(dateSettings);
        datePicker.addDateChangeListener(new SampleDateChangeListener("Ciao"));
        dateSettings.setAllowKeyboardEditing(false);
        //dateSettings.setAllowEmptyDates(false);
        dateSettings.setVetoPolicy(new SampleDateVetoPolicy());
        //dateSettings.setDateRangeLimits(today, null);
        //timeSettings.setAllowKeyboardEditing(false);
        //timeSettings.generatePotentialMenuTimes(TimeIncrement.ThirtyMinutes, LocalTime.of(8, 00), LocalTime.of(17, 00));
        timeSettings = new TimePickerSettings();
        timePicker = new TimePicker(timeSettings);
        timeSettings.setAllowKeyboardEditing(false);
        //dateSettings.setAllowEmptyDates(false);
        timeSettings.setVetoPolicy(new SampleTimeVetoPolicy());
        

        
        
        infopanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infopanel.setLayout(new BorderLayout());
        infopanel.setPreferredSize(new Dimension(400, 250));
        
        datepick.setBorder(new EmptyBorder(10, 10, 10, 10));
        datepick.setLayout(new BorderLayout());
        datepick.setPreferredSize(new Dimension(200, 250));
        
        eventpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        eventpanel.setLayout(new BorderLayout());
        eventpanel.setPreferredSize(new Dimension(400, 250));
        
        textcontentExt.setLayout(new FlowLayout());
        
        textcontent.setLayout(new GridLayout(0, 1));
        textcontent.setPreferredSize(new Dimension(300,  150));
        
        datecontent.setLayout(new GridLayout(0, 1));
        //datecontent.setPreferredSize(new Dimension(100,  200));
        
        eventinfo.setLayout(new GridLayout(0, 1));
        eventinfo.setPreferredSize(new Dimension(300,  150));
        
        //textcontent.setBorder(new EmptyBorder(10, 10, 10, 10));
        textcontent.add(new JLabel("Codice fiscale"));
        textcontent.add(m_userInputCF);
        textcontent.add(new JLabel("Codice ricetta"));
        textcontent.add(m_userInputCod);
        textcontent.add(m_message);
        
        buttoncontent.setLayout(new FlowLayout());
        buttoncontent.add(m_submitBtn);
        buttoncontent.add(m_clearBtn);
        
        buttoncontent2.setLayout(new FlowLayout());
        buttoncontent2.add(m_submitBtn2);
        buttoncontent2.add(m_clearBtn2);
        
        datecontent.add(new JLabel("Scegli una data"));
        datecontent.add(datePicker);
        datecontent.add(new JLabel("Scegli un orario"));
        datecontent.add(timePicker);
        
        eventinfo.add(new JLabel("Data visita"));
        eventinfo.add(eventData);
        eventinfo.add(new JLabel("Orario visita"));
        eventinfo.add(eventTime);
        
        //date.setPreferredSize(new Dimension(300,  150));
        
        textcontentExt.add(textcontent);
        infopanel.add(textcontentExt, BorderLayout.CENTER);
        infopanel.add(buttoncontent, BorderLayout.SOUTH);
        
        datepick.add(datecontent, BorderLayout.NORTH);
        datepick.add(buttoncontent2, BorderLayout.SOUTH);
        
        eventpanel.add(eventinfo, BorderLayout.NORTH);
        //eventpanel.add(buttoncontent2, BorderLayout.SOUTH);
        
        // Creo il contenitore...
        //jF.setContentPane(maincontent);
        jF.getContentPane().add(infopanel);
        jF.pack();
        jF.setVisible(true);
        // Imposto il titolo alla view
        //jF.setTitle("Inserisci info");
        // Imposto il meccanismo di chiusura sulla finestra
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private static class SampleDateVetoPolicy implements DateVetoPolicy {

        /**
         * isDateAllowed, Return true if a date should be allowed, or false if a date should be
         * vetoed.
         */
        @Override
        public boolean isDateAllowed(LocalDate date) {
        	
        	LocalDate now = LocalDate.now();
        	//System.out.println(date.toString());
            // Disallow days 7 to 11.
            if(date.isBefore(now))
            	return false;
            // Allow all other days.
            return true;
        }
    }
    
    private static class SampleTimeVetoPolicy implements TimeVetoPolicy {

		@Override
		public boolean isTimeAllowed(LocalTime time) {
			
        	return PickerUtilities.isLocalTimeInRange(
                    time, LocalTime.of(8, 00), LocalTime.of(17, 00), true);
		}

    }
    
    private static class SampleDateChangeListener implements DateChangeListener {

        /**
         * datePickerName, This holds a chosen name for the date picker that we are listening to,
         * for generating date change messages in the demo.
         */
        public String datePickerName;

        /**
         * Constructor.
         */
        private SampleDateChangeListener(String datePickerName) {
            this.datePickerName = datePickerName;
        }

        /**
         * dateChanged, This function will be called each time that the date in the applicable date
         * picker has changed. Both the old date, and the new date, are supplied in the event
         * object. Note that either parameter may contain null, which represents a cleared or empty
         * date.
         */
        @Override
        public void dateChanged(DateChangeEvent event) {
        	//timeSettings.setVetoPolicy(new SampleTimeVetoPolicy());
        	System.out.println(datePickerName);
        }
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
    
    LocalDate getDate() {
    	return datePicker.getDate();
    }
    
    LocalTime getTime() {
    	return timePicker.getTime();
    }
    
    // Rende disponibile all'esterno l'eventuale testo del messaggio di errore 
    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    
    // Permette di impostare dall'esterno il listener del bottone moltiplica
    void submitInfo(ActionListener mal) {
        m_submitBtn.addActionListener(mal);
    }
    
    void submitDate(ActionListener mal) {
        m_submitBtn2.addActionListener(mal);
    }

    
    // Permette di impostare dall'esterno il listener del bottone clear
    void addClearListener(ActionListener cal) {
        m_clearBtn.addActionListener(cal);
        m_clearBtn2.addActionListener(cal);
    }

	// Ereditato da Observer, chiama il metodo update definito localmente 
    // quando l'osservato (il modello) effettua una notifica
	@Override
	public void update(Observable o, Object arg) {
		String args[] = (String[]) arg;
		if(args[0] == "reset")
			updateReset();
		else if(args[0] == "check")
			updateCheck();
		else if(args[0] == "datefree")
			updateDate();
		else if(args[0] == "already")
			updateShow(args[1], args[2]);
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
		datePicker.clear();
		timePicker.clear();
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
			jF.getContentPane().remove(infopanel);
			jF.getContentPane().add(datepick);
			jF.revalidate();
			jF.repaint();
		}
		
		//m_userInputCF.setText(m_model.getValue());
		//m_userInputCod.setText(m_model.getValue());
			
	}
	
	private void updateDate() {
		// Estraggo il valore corrente della "memoria" del modello dal
		// riferimento al modello e aggiorno il textField.
		System.out.println("[VIEW] Notified by the model");
		if(!(m_model.getError().equals(""))) { 
			m_message.setText(m_model.getError());
			}
		else {
			jF.getContentPane().remove(datepick);
			jF.getContentPane().add(infopanel);
			jF.revalidate();
			jF.repaint();
		}
		
		//m_userInputCF.setText(m_model.getValue());
		//m_userInputCod.setText(m_model.getValue());
			
	}
	
	private void updateShow(String cf, String cr) {
		// Estraggo il valore corrente della "memoria" del modello dal
		// riferimento al modello e aggiorno il textField.
		System.out.println("[VIEW] Notified by the model");
		if(!(m_model.getError().equals(""))) { 
			//m_message.setText("");
			}
		else {
			eventData.setText(cf);
			eventTime.setText(cr);
			jF.getContentPane().remove(infopanel);
			jF.getContentPane().add(eventpanel);
			jF.revalidate();
			jF.repaint();
		}
		
		//m_userInputCF.setText(m_model.getValue());
		//m_userInputCod.setText(m_model.getValue());
			
	}
}
