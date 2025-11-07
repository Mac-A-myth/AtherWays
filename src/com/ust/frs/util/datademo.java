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
        // Initialize Profiles (5 rows)
    	profiles.add(new ProfileBean("Customer","LetoAtredies@gmail.com","Leto","Atredies",34));
		profiles.add(new ProfileBean("Admin","PaulAtredies@gmail.com","Paul","Atredies",17));
        
        // Initialize Credentials (5 rows)
        credentials.add(new CredentialsBean("AD1001", "admin123", "A", 0));
        credentials.add(new CredentialsBean("JO1002", "john123", "C", 0));
        credentials.add(new CredentialsBean("JA1003", "jane123", "C", 0));
        credentials.add(new CredentialsBean("BO1004", "bob123", "C", 0));
        credentials.add(new CredentialsBean("AL1005", "alice123", "C", 0));
        
        // Initialize Flights (5 rows)
        flights.add(new FlightBean("IN1001", "Indigo Air", 180, 150));
        flights.add(new FlightBean("AI1002", "Air India", 200, 180));
        flights.add(new FlightBean("SG1003", "SpiceJet", 160, 140));
        flights.add(new FlightBean("GO1004", "Go Air", 170, 150));
        flights.add(new FlightBean("VI1005", "Vistara", 190, 170));
        
        // Initialize Routes (5 rows)
        routes.add(new RouteBean("BLMU01", "Bangalore", "Mumbai", 800, 5000));
        routes.add(new RouteBean("BLDE02", "Bangalore", "Delhi", 1700, 8000));
        routes.add(new RouteBean("MUCH03", "Mumbai", "Chennai", 1000, 6000));
        routes.add(new RouteBean("DEKO04", "Delhi", "Kolkata", 1300, 7000));
        routes.add(new RouteBean("CHMU05", "Chennai", "Mumbai", 1000, 6000));
        
        // Initialize Schedules (5 rows)
        schedules.add(new ScheduleBean("S001", "IN-001", "R1", "Mon,Tue,Wed,Thu,Fri", "08:00 AM", 6));
		schedules.add(new ScheduleBean("S002", "AI-001", "R2", "Mon,Wed,Fri", "09:30 AM", 5));
		schedules.add(new ScheduleBean("S003", "JA-001", "R3", "Tue,Thu,Sat", "11:00 AM", 4));
		schedules.add(new ScheduleBean("S004", "IN-002", "R4", "Mon,Thu,Sat", "01:00 PM", 7));
		schedules.add(new ScheduleBean("S005", "IN-003", "R5", "Tue,Fri,Sun", "03:00 PM", 6));
		schedules.add(new ScheduleBean("S006", "AI-002", "R6", "Mon,Wed,Thu", "05:00 PM", 8));
		schedules.add(new ScheduleBean("S007", "JA-002", "R7", "Tue,Thu,Sat,Sun", "07:00 PM", 5));
        
        reservations.add(new ReservationBean("RES001", "Customer", "S001", "Round Trip", "2023-11-01", "2023-12-15", 2, 1, 350.50));
		reservations.add(new ReservationBean("RES002", "Admin", "S002", "One Way", "2023-11-05", "2023-12-10", 1, 1, 150.75));
		reservations.add(new ReservationBean("RES003", "Agent", "S003", "Round Trip", "2023-11-10", "2023-12-20", 3, 0, 650.90));
		reservations.add(new ReservationBean("RES004", "Manager", "S004", "One Way", "2023-11-15", "2023-12-05", 1, 1, 200.30));
		reservations.add(new ReservationBean("RES005", "Guest", "S005", "Round Trip", "2023-11-20", "2023-12-25", 4, 0, 850.00));
        
        // Initialize Passengers (3 sample rows)
        passengers.add(new PassengerBean("RS1001", "John Doe", "Male", 35, 15));
        passengers.add(new PassengerBean("RS1001", "Mary Doe", "Female", 32, 16));
        passengers.add(new PassengerBean("RS1002", "Jane Smith", "Female", 28, 8));
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
}
