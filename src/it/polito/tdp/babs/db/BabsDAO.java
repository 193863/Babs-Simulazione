package it.polito.tdp.babs.db;

import it.polito.tdp.babs.model.Station;
import it.polito.tdp.babs.model.Trip;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BabsDAO {
	
	private Station buildStation(ResultSet rs) throws SQLException {
		return new Station(
				rs.getInt("station_id"),
				rs.getString("name"),
				rs.getDouble("lat"),
				rs.getDouble("long"),
				rs.getInt("dockcount"),
				rs.getString("landmark"),
				rs.getDate("installation").toLocalDate()
				) ;
	}

	
	private Trip buildTrip(ResultSet rs) throws SQLException {
		return new Trip(
				rs.getInt("tripid"),
				rs.getInt("duration"),
				
				rs.getTimestamp("startdate").toLocalDateTime(),
				rs.getString("startstation"),
				rs.getInt("startterminal"),
				
				rs.getTimestamp("enddate").toLocalDateTime(),
				rs.getString("endstation"),
				rs.getInt("endterminal"),

				rs.getInt("bikenum"),
				rs.getString("SubscriptionType"),
				rs.getInt("Zip Code")
				) ;
	}
	
	
	public List<Station> getAllStations() {
		List<Station> result = new ArrayList<Station>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		String sql = "SELECT * FROM station" ;
		//String sql = "SELECT * FROM station ORDER BY name" ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				result.add( buildStation(rs) ) ;
			}
			
			st.close() ;
			conn.close() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e) ;
		}
		
		return result ;
	}
	
	public List<Trip> getAllTrips() {
		List<Trip> result = new LinkedList<Trip>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		String sql = "SELECT * FROM trip" ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				result.add( buildTrip(rs) ) ;
			}
			
			st.close() ;
			conn.close() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e) ;
		}
		
		return result ;
	}
	
	public List<Trip> getTripsOfDay(LocalDate day) {
		List<Trip> result = new LinkedList<Trip>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		String sql = "SELECT * FROM trip WHERE DATE(startDate)=?" ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDate(1, Date.valueOf(day));
			
			ResultSet rs = st.executeQuery() ;
			
			while(rs.next()) {
				result.add( buildTrip(rs) ) ;
			}
			
			st.close() ;
			conn.close() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e) ;
		}
		
		return result ;
	}

	
	public int numTrip(Station partenza, Station arrivo) {
		
		Connection conn = DBConnect.getConnection() ;
		
		String sql = "SELECT count(*) " +
					"FROM trip " +
					"WHERE StartTerminal = ? " +
					"AND EndTerminal = ?" ;
		
		int result = 0 ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, partenza.getStationID());
			st.setInt(2, arrivo.getStationID());
			
			ResultSet rs = st.executeQuery() ;
			
			rs.first() ;
			result = rs.getInt(1) ;
			
			st.close() ;
			conn.close() ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e) ;
		}
		
		return result ;
	}
	
	/**
	 * Simple test of the main methods
	 * @param args <i>unused</i>
	 */
	public static void main(String args[]) {
		BabsDAO dao = new BabsDAO() ;
		
		List<Station> stations = dao.getAllStations() ;
		
		for(Station s : stations) {
			System.out.format("%2d %-20s\n", s.getStationID(), s.getName()) ;
		}
			
		List<Trip> trips = dao.getAllTrips() ;
		
		System.out.println("We have "+trips.size()+" trips") ;

	}



	
}
