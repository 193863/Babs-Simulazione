package it.polito.tdp.babs;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.Simula;
import it.polito.tdp.babs.model.StationStats;
import it.polito.tdp.babs.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {

	private Model model ;
	
    public void setModel(Model model) {
		this.model = model;
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker pickData;

    @FXML
    private Slider sliderK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doContaTrip(ActionEvent event) {
    	
    	LocalDate day = pickData.getValue();
    	
		List<StationStats> stats = model.calcolaStats(day) ;

    	txtResult.clear();
    	txtResult.appendText(
    			String.format("Viaggi del %s\n", 
    					day.format(DateTimeFormatter.ISO_LOCAL_DATE)) );
    	
    	for (StationStats st : stats) {
    		txtResult.appendText(
    				String.format("%-50s %3d %3d\n",
    						st.getStation().getName(),
    						st.getTripStarted(),
    						st.getTripEnded() ));
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {

    	LocalDate day = pickData.getValue();

    	double K = sliderK.getValue() / 100.0 ;
    	
    	Simula sim = new Simula(model, day, K) ;
    	
    	List<Trip> trips = model.getTripsByDate(day) ;
    	
    	sim.loadTrips(trips);
    	
    	sim.run();
    	
    	// stampa:
    	txtResult.appendText(String.format("\nData: %s - Riempimento %.2f\n",
    			day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
    			K*100.0 )) ;

    	txtResult.appendText(String.format("Consegne mancate: %d\n", sim.getConsegneMancate())) ;
    	txtResult.appendText(String.format("Prese    mancate: %d\n", sim.getPreseMancate())) ;   	
    	
    }

    @FXML
    void initialize() {
        assert pickData != null : "fx:id=\"pickData\" was not injected: check your FXML file 'Babs.fxml'.";
        assert sliderK != null : "fx:id=\"sliderK\" was not injected: check your FXML file 'Babs.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Babs.fxml'.";

        pickData.setValue(LocalDate.of(2013, 9, 1));
    }
}
