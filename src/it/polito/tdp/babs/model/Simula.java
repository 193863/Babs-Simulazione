package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simula {

	private Map<Station, Integer> occupazione ;
	
	// input parameters
	private LocalDate day ;
	private double K ; // compreso tra 0.0 e 1.0
	
	// output parameters
	private int preseMancate = 0 ;
	private int consegneMancate = 0 ;
	
	private Model model ;
	
	private enum EventType {
		PICK, DROP ;
	}
	
	private class Event {
		private EventType type ;
		private LocalDateTime tempo ;
		private Station station ;
		
		private Trip trip ;
		
		public Event(EventType type, LocalDateTime tempo, Station station, Trip trip) {
			super();
			this.type = type;
			this.tempo = tempo;
			this.station = station;
			this.trip = trip ;
		}
		public EventType getType() {
			return type;
		}
		public void setType(EventType type) {
			this.type = type;
		}
		public LocalDateTime getTempo() {
			return tempo;
		}
		public void setTempo(LocalDateTime tempo) {
			this.tempo = tempo;
		}
		public Station getStation() {
			return station;
		}
		public void setStation(Station s) {
			this.station = s;
		}
		public Trip getTrip() {
			return this.trip ;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((station == null) ? 0 : station.hashCode());
			result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Event other = (Event) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (station == null) {
				if (other.station != null)
					return false;
			} else if (!station.equals(other.station))
				return false;
			if (tempo == null) {
				if (other.tempo != null)
					return false;
			} else if (!tempo.equals(other.tempo))
				return false;
			if (type != other.type)
				return false;
			return true;
		}
		private Simula getOuterType() {
			return Simula.this;
		}
		
		public int compareTo(Event other) {
			return this.tempo.compareTo(other.getTempo()) ;
		}
		
	}
	
	
	private PriorityQueue<Event> coda ;
	
	public Simula(Model model, LocalDate day, double K) {
		this.day = day ;
		this.K = K ;
		
		this.model = model ;
		
		for(Station s : model.getStations()) {
			int bici = (int)(s.getDockCount() * K) ;
			occupazione.put(s, bici) ;
		}
	}
	
	public void loadTrips(List<Trip> trips) {
		
		this.coda = new PriorityQueue<>() ;
		
		// Inseriamo tutti gli eventi di PICK
		for(Trip t : trips) {
			coda.add(new Event( 
					EventType.PICK,
					t.getStartDate(),
					model.getStationByID(t.getStartStationID()),
					t)) ;
		}
		
	}
	
	public void run() {
		
		Event e ;
		while( (e = coda.poll())!=null ) {
			
			switch(e.getType()) {
			case PICK:
				// posti occupati
				int posti = occupazione.get(e.getStation()) ;
				if(posti==0){
					// impossibile prendere bici, stazione vuota
					setPreseMancate(getPreseMancate() + 1) ;
				} else {
					// ci sono bici da prendere: prendila!
					occupazione.put(e.getStation(), posti-1) ;
					
					// schedula evento di riconsegna
					coda.add(new Event( 
							EventType.DROP,
							e.getTrip().getEndDate(),
							model.getStationByID(e.getTrip().getEndStationID()),
							e.getTrip()
							));
				}
				break;
				
			case DROP:
				// posti occupati
				int posti2 = occupazione.get(e.getStation()) ;
				if(posti2 == e.getStation().getDockCount()) {
					// tutto pieno: consegna impossibile
					setConsegneMancate(getConsegneMancate() + 1) ;
				} else {
					// ok, posso riconsegnare
					occupazione.put(e.getStation(), posti2+1) ;
				}
				
				break;
				
			default:
				throw new RuntimeException("Evento sconosciuto") ;
			}
			
		}
		
	}

	public int getPreseMancate() {
		return preseMancate;
	}

	public void setPreseMancate(int preseMancate) {
		this.preseMancate = preseMancate;
	}

	public int getConsegneMancate() {
		return consegneMancate;
	}

	public void setConsegneMancate(int consegneMancate) {
		this.consegneMancate = consegneMancate;
	}
}
