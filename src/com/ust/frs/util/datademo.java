package com.ust.frs.util;

import java.util.ArrayList;

import com.ust.frs.bean.*;

public class DataStructure {

    ArrayList<CredentialsBean> Cred = new ArrayList<>();
    ArrayList<FlightBean> Flight = new ArrayList<>();
    ArrayList<PassengerBean> Passenger = new ArrayList<>();
    ArrayList<ProfileBean> Profile = new ArrayList<>();
    ArrayList<ReservationBean> Reservation = new ArrayList<>();
    ArrayList<RouteBean> Route = new ArrayList<>();
    ArrayList<ScheduleBean> Schedule = new ArrayList<>();
    
    public DataStructure() {
        // Initialize Credentials
        Cred.add(new CredentialsBean("customer", "password", "Customer", 1));
        Cred.add(new CredentialsBean("admin", "admin123", "Administrator", 1));

        // Initialize Flights - only 5 as per original
        Flight.add(new FlightBean("IN-001", "Indigo", 120, 100));
        Flight.add(new FlightBean("AI-001", "Air India", 150, 120));
        Flight.add(new FlightBean("JA-001", "Jet Airways", 150, 120));
        Flight.add(new FlightBean("IN-002", "Indigo", 140, 110));
        Flight.add(new FlightBean("IN-003", "Indigo", 130, 120));
        
        // Initialize Passengers - linked to reservations
        Passenger.add(new PassengerBean("RES001", "John Smith", "M", 29, 23));
        Passenger.add(new PassengerBean("RES002", "Emily Johnson", "F", 34, 14));
        Passenger.add(new PassengerBean("RES003", "Michael Williams", "M", 22, 5));
        Passenger.add(new PassengerBean("RES004", "Sarah Brown", "F", 40, 3));
        Passenger.add(new PassengerBean("RES005", "David Lee", "M", 26, 10));
        
        // Initialize Profiles
        Profile.add(new ProfileBean("customer", "LetoAtredies@gmail.com", "Leto", "Atredies", 34));
        Profile.add(new ProfileBean("admin", "PaulAtredies@gmail.com", "Paul", "Atredies", 17));
    
        // Initialize Routes - only 5 as per original
        Route.add(new RouteBean("R1", "Los Angeles", "New York City", 1200, 100000));
        Route.add(new RouteBean("R2", "San Francisco", "Washington DC", 500, 60000));
        Route.add(new RouteBean("R3", "Seattle", "Los Angeles", 210, 50000));
        Route.add(new RouteBean("R4", "London", "Stockholm", 400, 65000));
        Route.add(new RouteBean("R5", "Paris", "London", 600, 75000));
        
        // Initialize Reservations
        Reservation.add(new ReservationBean("RES001", "customer", "S001", "Round Trip", "2023-11-01", "2023-12-15", 2, 1, 350.50));
        Reservation.add(new ReservationBean("RES002", "admin", "S002", "One Way", "2023-11-05", "2023-12-10", 1, 1, 150.75));
        Reservation.add(new ReservationBean("RES003", "customer", "S003", "Round Trip", "2023-11-10", "2023-12-20", 3, 0, 650.90));
        Reservation.add(new ReservationBean("RES004", "customer", "S004", "One Way", "2023-11-15", "2023-12-05", 1, 1, 200.30));
        Reservation.add(new ReservationBean("RES005", "customer", "S005", "Round Trip", "2023-11-20", "2023-12-25", 4, 0, 850.00));

        // Initialize Schedules - only 5 as per original
        Schedule.add(new ScheduleBean("S001", "IN-001", "R1", "Mon,Tue,Wed,Thu,Fri", "08:00 AM", 6));
        Schedule.add(new ScheduleBean("S002", "AI-001", "R2", "Mon,Wed,Fri", "09:30 AM", 5));
        Schedule.add(new ScheduleBean("S003", "JA-001", "R3", "Tue,Thu,Sat", "11:00 AM", 4));
        Schedule.add(new ScheduleBean("S004", "IN-002", "R4", "Mon,Thu,Sat", "01:00 PM", 7));
        Schedule.add(new ScheduleBean("S005", "IN-003", "R5", "Tue,Fri,Sun", "03:00 PM", 6));
    }

    public ArrayList<FlightBean> getFlight() {
        return Flight;
    }
    
    public void setFlight(FlightBean flight) {
        this.Flight.add(flight);
    }
    
    public ArrayList<PassengerBean> getPassenger() {
        return Passenger;
    }
    
    public void setPassenger(PassengerBean passenger) {
        this.Passenger.add(passenger);
    }
    
    public ArrayList<ProfileBean> getProfile() {
        return Profile;
    }
    
    public void setProfile(ProfileBean profile) {
        this.Profile.add(profile);
    }
    
    public ArrayList<ReservationBean> getReservation() {
        return Reservation;
    }
    
    public void setReservation(ReservationBean reservation) {
        this.Reservation.add(reservation);
    }
    
    public ArrayList<RouteBean> getRoute() {
        return Route;
    }
    
    public void setRoute(RouteBean route) {
        this.Route.add(route);    
    }
    
    public ArrayList<ScheduleBean> getSchedule() {
        return Schedule;
    }
    
    public void setSchedule(ScheduleBean schedule) {
        this.Schedule.add(schedule);    
    }
}
