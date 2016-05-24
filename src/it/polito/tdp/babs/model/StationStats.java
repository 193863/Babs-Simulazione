package it.polito.tdp.babs.model;

public class StationStats {
	
	private Station station ;
	private int tripStarted ;
	private int tripEnded ;
	
	public StationStats(Station station, int tripStarted, int tripEnded) {
		super();
		this.station = station;
		this.tripStarted = tripStarted;
		this.tripEnded = tripEnded;
	}
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public int getTripStarted() {
		return tripStarted;
	}
	public void setTripStarted(int tripStarted) {
		this.tripStarted = tripStarted;
	}
	public int getTripEnded() {
		return tripEnded;
	}
	public void setTripEnded(int tripEnded) {
		this.tripEnded = tripEnded;
	}
	
	

}
