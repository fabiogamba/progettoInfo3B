package progettoOspedale;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Observable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ProgettoModel extends Observable{
	// Campo privato, il valore iniziale della "memoria" del modello
		// Campo privato, la "memoria" vera e propria del modello 
		private String clearString; 
		private String error;
		public String visits[];

		// Costruttore: chiama il reset per (re)impostare il valore inizale
		ProgettoModel() {
			//visits[0] = new String("2021/02/03" + "08:00");
			reset();
		}
		
		private static void removeEmptyText(Node node){
		    Node child = node.getFirstChild();
		    while(child!=null){
		        Node sibling = child.getNextSibling();
		        if(child.getNodeType()==Node.TEXT_NODE){
		            if(child.getTextContent().trim().isEmpty())
		                node.removeChild(child);
		        }else
		            removeEmptyText(child);
		        child = sibling;
		    }
		}

		// Reset del valore iniziale
		public void reset() {
			System.out.println("[MODEL] reset ");
			clearString = new String("");
			// Comunica un cambio dello stato
			setChanged();
			// Notifica gli observer (la view)
			String arg[] = {"reset"};
			notifyObservers(arg);
			System.out.println("[MODEL] Observers notified (reset)");
		}


		// Moltiplica per il valore passato come stringa 
		// Attenzione: non dalla GUI ma dal controller
		public void checkInfo(String cf, String cod) {
			// Moltiplicazione vera e propria
			//m_total = m_total * new Integer(operand);
			//System.out.println("[MODEL] Multiply "+ operand);
			System.out.println("ricevuto: " + cf + " " + cod);
			String cfxml = "", crxml = "", dataxml = "", timexml = "";
			boolean flag = false;
			try {
				File file = new File("src/progettoOspedale/visite.xml");
				//System.out.println(System.getProperty("user.dir"));
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				        .newInstance();
				DocumentBuilder documentBuilder;
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document;
				try {
					document = documentBuilder.parse(file);
					int l = document.getElementsByTagName("visita").getLength();
					for(int i = 0; i < l; i++) {
						cfxml = document.getElementsByTagName("cf").item(i).getTextContent();
						crxml = document.getElementsByTagName("cr").item(i).getTextContent();
						dataxml = document.getElementsByTagName("data").item(i).getTextContent();
						timexml = document.getElementsByTagName("ora").item(i).getTextContent();
						if(cf.equals(cfxml) && cod.equals(crxml)) {
							flag = true;
							break;
						}
					}
					
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(cf.length() != 0 && cod.length() != 0) {
					if(cf.length() != 16)
						setError("Codice fiscale sbagliato");
					else
						setError("");
				}else
					setError("Tutti i campi sono obbligatori");
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(!(cf.equals(cfxml) && cod.equals(crxml))) {
			// Comunica un cambio dello stato
			setChanged();
			// Notifica gli observer (la view)
			String arg[] = {"check"};
			notifyObservers(arg);
			}else{
				// Comunica un cambio dello stato
				setChanged();
				// Notifica gli observer (la view)
				String arg[] = {"already", dataxml, timexml};
				notifyObservers(arg);
			}
			System.out.println("[MODEL] Observers notified (mult)");
		}
		
		public void checkDate(LocalDate d, LocalTime t, String cf, String cr) {
			System.out.println("ricevuto: " + d + " " + t);
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder;
			try {
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.parse("src/progettoOspedale/visite.xml");
		        Element root = document.getDocumentElement();

		        //Collection<Server> servers = new ArrayList<Server>();
		        //servers.add(new Server());

		        //for (Server server : servers) {
		            // server elements
		            Element newServer = document.createElement("visita");

		            Element name = document.createElement("cf");
		            name.appendChild(document.createTextNode(cf));
		            newServer.appendChild(name);

		            Element port = document.createElement("cr");
		            port.appendChild(document.createTextNode(cr));
		            newServer.appendChild(port);
		            
		            Element data = document.createElement("data");
		            data.appendChild(document.createTextNode(d.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
		            newServer.appendChild(data);
		            
		            Element ora = document.createElement("ora");
		            ora.appendChild(document.createTextNode(t.toString()));
		            newServer.appendChild(ora);

		            root.appendChild(newServer);
		        //}

		        DOMSource source = new DOMSource(document);

		        removeEmptyText(document.getDocumentElement());
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
		        StreamResult result = new StreamResult("src/progettoOspedale/visite.xml");
		        transformer.transform(source, result);
		        
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			// Comunica un cambio dello stato
			setChanged();
			// Notifica gli observer (la view)
			String arg[] = {"datefree"};
			notifyObservers(arg);
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
