package com.ust.frs.bean;

public class PassengerBean {
private String reservationID,name,gender;
private int age,seatNo;
public PassengerBean(String reservationID, String name, String gender, int age, int seatNo) {
	super();
	this.reservationID = reservationID;
	this.name = name;
	this.gender = gender;
	this.age = age;
	this.seatNo = seatNo;
}

public PassengerBean() {
	super();
}

@Override
public String toString() {
	return "PassengerBean [reservationID=" + reservationID + ", name=" + name + ", gender=" + gender + ", age=" + age
			+ ", seatNo=" + seatNo + "]";
}
public String getReservationID() {
	return reservationID;
}
public void setReservationID(String reservationID) {
	this.reservationID = reservationID;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public int getSeatNo() {
	return seatNo;
}
public void setSeatNo(int seatNo) {
	this.seatNo = seatNo;
}
}
