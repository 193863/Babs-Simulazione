package it.polito.tdp.babs.model;

import it.polito.tdp.babs.db.BabsDAO;

import java.time.LocalDate;
import java.util.List;

public class Model {
	
	private List<Station> stations ;
	private List<Trip> tripsOfTheDay ;

	/**
	 * The list of all loaded {@link Station}s.
	 * @return all the {@link Station}s
	 */
	public List<Station> getStations() {
		return stations;
	}

	public List<Trip> getTripsOfTheDay() {
		return tripsOfTheDay;
	}

	/**
	 * Load the list of all available {@link Station}s from the database. Must be called before the other methods.
	 */
	public void loadStations() {
		BabsDAO dao = new BabsDAO() ;
		
		stations = dao.getAllStations() ; 
	}

	/**
	 * Carica tutti e soli i trip della giornata specificata
	 * @param day giorno da caricare
	 * @return numero di trip caricati
	 */
	public int loadTripsOfTheDay(LocalDate day) {
		BabsDAO dao = new BabsDAO() ;
		tripsOfTheDay = dao.getTripsOfDay(day) ;
		return tripsOfTheDay.size() ;
	}
	
	/** 
	 * Conta il numero di trip, nel giorno corrente, che partono dalla stazione specificata
	 * @param depart stazione di partenza
	 * @return numero di trip
	 */
	public int countDepartingTrips(Station depart) {
		int count=0 ;
		for(Trip t : tripsOfTheDay) {
			if( depart.getStationID() == t.getStartStationID())
				count++ ;
		}
		
		return count ;
	}

	/** 
	 * Conta il numero di trip, nel giorno corrente, che rientrano nella stazione specificata
	 * @param arrival stazione di arrivo
	 * @return numero di trip
	 */
	public int countArrivingTrips(Station arrival) {
		int count=0 ;
		for(Trip t : tripsOfTheDay) {
			if( arrival.getStationID() == t.getEndStationID())
				count++ ;
		}
		
		return count ;
	}

	/**
	 * Dato l'ID di una Station, trova l'oggetto Station corrispondente nella lista del modello.
	 * Nota: potrebbe essere ottimizzato costruendo una mappa ID->Station
	 * @param stationID identificavo numerico di una stazione
	 * @return oggetto Station corrispondente, oppure null se non esiste
	 */
	public Station getStationByID(int stationID) {
		for(Station s: stations) {
			if( s.getStationID()==stationID)
				return s ;
		}
		return null ;
	}
	
}
