package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.babs.db.BabsDAO;

public class Model {
	
	List<Station> stations = null ;
	
	public List<Station> getStations() {
		if (stations!=null)
			return stations ;
		else {
			BabsDAO dao = new BabsDAO() ;
			stations = dao.getAllStations() ;
			return this.stations ;
		}
	}
	
	public List<StationStats> calcolaStats(LocalDate day) {
		
		List<StationStats> stats = new ArrayList<>() ;
		BabsDAO dao = new BabsDAO() ;

		//TODO: ordinare le Station per latitudine decrescente
		
		for( Station s : this.getStations() ) {
			int tripStarted = dao.countTripStarted(s, day) ;
			int tripEnded = dao.countTripEnded(s, day) ;
			
			StationStats st = new StationStats(s, tripStarted, tripEnded) ;
			stats.add(st) ;
		}
		
		return stats ;
		
	}

	public Station getStationByID(int startStationID) {
		// TODO sostituire con una MAP se abbiamo problemi di prestazioni
		for(Station s : this.getStations()) {
			if(s.getStationID()==startStationID)
				return s ;
		}
		return null ;
	}
	

}
