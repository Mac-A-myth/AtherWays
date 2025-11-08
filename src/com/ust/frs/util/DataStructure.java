package com.ust.frs.util;

import com.ust.frs.bean.*;
import java.util.*;

public class DataStructure {
    // Collections to store data (simulating database tables)
    public static ArrayList<ProfileBean> profiles = new ArrayList<>();
    public static ArrayList<CredentialsBean> credentials = new ArrayList<>();
    public static ArrayList<FlightBean> flights = new ArrayList<>();
    public static ArrayList<RouteBean> routes = new ArrayList<>();
    public static ArrayList<ScheduleBean> schedules = new ArrayList<>();
    public static ArrayList<ReservationBean> reservations = new ArrayList<>();
    public static ArrayList<PassengerBean> passengers = new ArrayList<>();
    
    // Initialize with sample data
    static {
        initializeData();
    }
    
    private static void initializeData() {
        // Initialize Profiles (2 rows as per original)
        profiles.add(new ProfileBean("Customer", "LetoAtredies@gmail.com", "Leto", "Atredies", 34));
        profiles.add(new ProfileBean("Admin", "PaulAtredies@gmail.com", "Paul", "Atredies", 17));
        
        // Initialize Credentials (2 rows matching profiles)
        credentials.add(new CredentialsBean("Customer", "password", "Customer", 0));
        credentials.add(new CredentialsBean("Admin", "admin123", "Administrator", 0));
        
        // Initialize Flights (5 rows as per original)
        flights.add(new FlightBean("IN-001", "Indigo", 120, 100));
        flights.add(new FlightBean("AI-001", "Air India", 150, 120));
        flights.add(new FlightBean("JA-001", "Jet Airways", 150, 120));
        flights.add(new FlightBean("IN-002", "Indigo", 140, 110));
        flights.add(new FlightBean("IN-003", "Indigo", 130, 120));
        
        // Initialize Routes (5 rows as per original)
        routes.add(new RouteBean("R1", "Los Angeles", "New York City", 1200, 100000));
        routes.add(new RouteBean("R2", "San Francisco", "Washington DC", 500, 60000));
        routes.add(new RouteBean("R3", "Seattle", "Los Angeles", 210, 50000));
        routes.add(new RouteBean("R4", "London", "Stockholm", 400, 65000));
        routes.add(new RouteBean("R5", "Paris", "London", 600, 75000));
        
        // Initialize Schedules (5 rows with consistent flight and route IDs)
        schedules.add(new ScheduleBean("S001", "IN-001", "R1", "Mon,Tue,Wed,Thu,Fri", "08:00 AM", 6));
        schedules.add(new ScheduleBean("S002", "AI-001", "R2", "Mon,Wed,Fri", "09:30 AM", 5));
        schedules.add(new ScheduleBean("S003", "JA-001", "R3", "Tue,Thu,Sat", "11:00 AM", 4));
        schedules.add(new ScheduleBean("S004", "IN-002", "R4", "Mon,Thu,Sat", "01:00 PM", 7));
        schedules.add(new ScheduleBean("S005", "IN-003", "R5", "Tue,Fri,Sun", "03:00 PM", 6));
        
        // Initialize Reservations (5 rows with consistent user and schedule IDs)
        reservations.add(new ReservationBean("RES001", "Customer", "S001", "Round Trip", "2023-11-01", "2023-12-15", 2, 1, 350.50));
        reservations.add(new ReservationBean("RES002", "Admin", "S002", "One Way", "2023-11-05", "2023-12-10", 1, 1, 150.75));
        reservations.add(new ReservationBean("RES003", "Customer", "S003", "Round Trip", "2023-11-10", "2023-12-20", 3, 0, 650.90));
        reservations.add(new ReservationBean("RES004", "Customer", "S004", "One Way", "2023-11-15", "2023-12-05", 1, 1, 200.30));
        reservations.add(new ReservationBean("RES005", "Customer", "S005", "Round Trip", "2023-11-20", "2023-12-25", 4, 0, 850.00));
        
        // Initialize Passengers (5 rows with consistent reservation IDs)
        passengers.add(new PassengerBean("RES001", "John Smith", "M", 29, 23));
        passengers.add(new PassengerBean("RES002", "Emily Johnson", "F", 34, 14));
        passengers.add(new PassengerBean("RES003", "Michael Williams", "M", 22, 5));
        passengers.add(new PassengerBean("RES004", "Sarah Brown", "F", 40, 3));
        passengers.add(new PassengerBean("RES005", "David Lee", "M", 26, 10));
    }
    
    // Utility methods to get data
    public static ArrayList<FlightBean> getAllFlights() {
        return new ArrayList<>(flights);
    }
    
    public static ArrayList<RouteBean> getAllRoutes() {
        return new ArrayList<>(routes);
    }
    
    public static ArrayList<ScheduleBean> getAllSchedules() {
        return new ArrayList<>(schedules);
    }
    
    public static ArrayList<ProfileBean> getAllProfiles() {
        return new ArrayList<>(profiles);
    }
    
    public static ArrayList<CredentialsBean> getAllCredentials() {
        return new ArrayList<>(credentials);
    }
    
    public static ArrayList<ReservationBean> getAllReservations() {
        return new ArrayList<>(reservations);
    }
    
    public static ArrayList<PassengerBean> getAllPassengers() {
        return new ArrayList<>(passengers);
    }
    
    // Add new objects
    public static void addFlight(FlightBean flight) {
        flights.add(flight);
    }
    
    public static void addRoute(RouteBean route) {
        routes.add(route);
    }
    
    public static void addSchedule(ScheduleBean schedule) {
        schedules.add(schedule);
    }
    
    public static void addProfile(ProfileBean profile) {
        profiles.add(profile);
    }
    
    public static void addCredential(CredentialsBean credential) {
        credentials.add(credential);
    }
    
    public static void addReservation(ReservationBean reservation) {
        reservations.add(reservation);
    }
    
    public static void addPassenger(PassengerBean passenger) {
        passengers.add(passenger);
    }
}