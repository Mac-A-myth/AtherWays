package com.ust.frs.bean;

public class RouteBean {
	private String routeID,source,destination;
	private int distance;
	private double fare;
	public RouteBean(String routeID, String source, String destination, int distance, double fare) {
		super();
		this.routeID = routeID;
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.fare = fare;
	}
	@Override
	public String toString() {
		return "RouteBean [routeID=" + routeID + ", source=" + source + ", destination=" + destination + ", distance="
				+ distance + ", fare=" + fare + "]";
	}
	public String getRouteID() {
		return routeID;
	}
	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	
    }
