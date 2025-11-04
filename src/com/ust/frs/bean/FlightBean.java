package com.ust.frs.bean;

public class FlightBean {
    private String flightID,flightName;
    private int seatingCapacity,reservationCapacity;
	public FlightBean(String flightID, String flightName, int seatingCapacity, int reservationCapacity) {
		super();
		this.flightID = flightID;
		this.flightName = flightName;
		this.seatingCapacity = seatingCapacity;
		this.reservationCapacity = reservationCapacity;
	}
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public int getSeatingCapacity() {
		return seatingCapacity;
	}
	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}
	public int getReservationCapacity() {
		return reservationCapacity;
	}
	public void setReservationCapacity(int reservationCapacity) {
		this.reservationCapacity = reservationCapacity;
	}
	@Override
	public String toString() {
		return "FlightBean [flightID=" + flightID + ", flightName=" + flightName + ", seatingCapacity="
				+ seatingCapacity + ", reservationCapacity=" + reservationCapacity + "]";
	}
    
}
