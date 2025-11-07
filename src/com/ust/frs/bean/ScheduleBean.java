package com.ust.frs.bean;

public class ScheduleBean {
   private  String scheduleID,flightID,routeID,availableDays,departureTime;
   private int travelDuration;
   public ScheduleBean(String scheduleID, String flightID, String routeID, String availableDays, String departureTime,
		int travelDuration) {
	super();
	this.scheduleID = scheduleID;
	this.flightID = flightID;
	this.routeID = routeID;
	this.availableDays = availableDays;
	this.departureTime = departureTime;
	this.travelDuration = travelDuration;
   }
   
   public ScheduleBean() {
	super();
}

   @Override
   public String toString() {
	return "ScheduleBean [scheduleID=" + scheduleID + ", flightID=" + flightID + ", routeID=" + routeID
			+ ", availableDays=" + availableDays + ", departureTime=" + departureTime + ", travelDuration="
			+ travelDuration + "]";
   }
   public String getScheduleID() {
	return scheduleID;
   }
   public void setScheduleID(String scheduleID) {
	this.scheduleID = scheduleID;
   }
   public String getFlightID() {
	return flightID;
   }
   public void setFlightID(String flightID) {
	this.flightID = flightID;
   }
   public String getRouteID() {
	return routeID;
   }
   public void setRouteID(String routeID) {
	this.routeID = routeID;
   }
   public String getAvailableDays() {
	return availableDays;
   }
   public void setAvailableDays(String availableDays) {
	this.availableDays = availableDays;
   }
   public String getDepartureTime() {
	return departureTime;
   }
   public void setDepartureTime(String departureTime) {
	this.departureTime = departureTime;
   }
   public int getTravelDuration() {
	return travelDuration;
   }
   public void setTravelDuration(int travelDuration) {
	this.travelDuration = travelDuration;
   }
   
}
