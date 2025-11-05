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
        profiles.add(new ProfileBean("AD1001", "Admin", "User", new Date(), "Male", 
                                   "Admin St", "Tech Park", "Bangalore", "Karnataka", 
                                   "560001", "9876543210", "admin@frs.com", "admin123"));
        profiles.add(new ProfileBean("JO1002", "John", "Doe", new Date(), "Male", 
                                   "MG Road", "City Center", "Mumbai", "Maharashtra", 
                                   "400001", "9876543211", "john@email.com", "john123"));
        profiles.add(new ProfileBean("JA1003", "Jane", "Smith", new Date(), "Female", 
                                   "Park Ave", "Downtown", "Delhi", "Delhi", 
                                   "110001", "9876543212", "jane@email.com", "jane123"));
        profiles.add(new ProfileBean("BO1004", "Bob", "Wilson", new Date(), "Male", 
                                   "Lake View", "Suburb", "Chennai", "Tamil Nadu", 
                                   "600001", "9876543213", "bob@email.com", "bob123"));
        profiles.add(new ProfileBean("AL1005", "Alice", "Brown", new Date(), "Female", 
                                   "Hill Road", "Uptown", "Kolkata", "West Bengal", 
                                   "700001", "9876543214", "alice@email.com", "alice123"));
        
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
        schedules.add(new ScheduleBean("BLMU01", "IN1001", "BLMU01", 120, "Mon,Wed,Fri", "08:00"));
        schedules.add(new ScheduleBean("BLDE02", "AI1002", "BLDE02", 180, "Tue,Thu,Sat", "10:30"));
        schedules.add(new ScheduleBean("MUCH03", "SG1003", "MUCH03", 90, "Mon,Wed,Fri,Sun", "14:15"));
        schedules.add(new ScheduleBean("DEKO04", "GO1004", "DEKO04", 150, "Daily", "16:45"));
        schedules.add(new ScheduleBean("CHMU05", "VI1005", "CHMU05", 90, "Tue,Thu,Sat", "19:20"));
        
        // Initialize Reservations (2 sample rows)
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date nextWeek = cal.getTime();
        
        reservations.add(new ReservationBean("RS1001", "JO1002", "BLMU01", "Economy", 
                                           today, nextWeek, 2, 10000, 1));
        reservations.add(new ReservationBean("RS1002", "JA1003", "BLDE02", "Business", 
                                           today, nextWeek, 1, 8000, 1));
        
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
