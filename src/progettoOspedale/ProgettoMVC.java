package progettoOspedale;

public class ProgettoMVC {
	// Main
	public static void main(String[] args) {
		// Creo il model
		ProgettoModel model = new ProgettoModel();
		// Creo la view passando il rif al model
		ProgettoView view = new ProgettoView(model);
		// Creo il controller passando il rif al model e alla view
		ProgettoController controller = new ProgettoController(model, view);
		// Rendo visibile la view
		//view.setVisible(true);
	}
}
