package com.ust.frs.bean;

public class ReservationBean {
	private String reservationID,userID,scheduleID,reservationType,bookingDate,journeyDate;
	private int noOfSeats,bookingStatus;
	private double totalFare;
	public ReservationBean(String reservationID, String userID, String scheduleID, String reservationType,
			String bookingDate, String journeyDate, int noOfSeats, int bookingStatus, double totalFare) {
		super();
		this.reservationID = reservationID;
		this.userID = userID;
		this.scheduleID = scheduleID;
		this.reservationType = reservationType;
		this.bookingDate = bookingDate;
		this.journeyDate = journeyDate;
		this.noOfSeats = noOfSeats;
		this.bookingStatus = bookingStatus;
		this.totalFare = totalFare;
	}
	@Override
	public String toString() {
		return "ReservationBean [reservationID=" + reservationID + ", userID=" + userID + ", scheduleID=" + scheduleID
				+ ", reservationType=" + reservationType + ", bookingDate=" + bookingDate + ", journeyDate="
				+ journeyDate + ", noOfSeats=" + noOfSeats + ", bookingStatus=" + bookingStatus + ", totalFare="
				+ totalFare + "]";
	}
	public String getReservationID() {
		return reservationID;
	}
	public void setReservationID(String reservationID) {
		this.reservationID = reservationID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(String scheduleID) {
		this.scheduleID = scheduleID;
	}
	public String getReservationType() {
		return reservationType;
	}
	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;
	}
	public int getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	public int getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(int bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public double getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}
	

}
