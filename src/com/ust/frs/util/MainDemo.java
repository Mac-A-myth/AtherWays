package com.ust.frs.ui;

import com.ust.frs.util.DataStructure;
import com.ust.frs.bean.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUserID = null;
    private static String currentUserType = null;
    
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   FLIGHT RESERVATION SYSTEM (FRS)");
        System.out.println("====================================");
        
        while (true) {
            if (currentUserID == null) {
                showMainMenu();
            } else {
                if ("A".equals(currentUserType)) {
                    showAdminMenu();
                } else {
                    showCustomerMenu();
                }
            }
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thank you for using FRS. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option! Please try again.");
        }
    }
    
    private static void showAdminMenu() {
        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. View All Flights");
        System.out.println("2. View All Routes");
        System.out.println("3. View All Schedules");
        System.out.println("4. Add Flight");
        System.out.println("5. Add Route");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                viewAllFlights();
                break;
            case 2:
                viewAllRoutes();
                break;
            case 3:
                viewAllSchedules();
                break;
            case 4:
                addFlight();
                break;
            case 5:
                addRoute();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private static void showCustomerMenu() {
        System.out.println("\n--- CUSTOMER MENU ---");
        System.out.println("1. View All Flights");
        System.out.println("2. View All Routes");
        System.out.println("3. View All Schedules");
        System.out.println("4. View My Profile");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                viewAllFlights();
                break;
            case 2:
                viewAllRoutes();
                break;
            case 3:
                viewAllSchedules();
                break;
            case 4:
                viewMyProfile();
                break;
            case 5:
                logout();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private static void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("User ID: ");
        String userID = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        // Simple authentication
        for (CredentialsBean cred : DataStructure.getAllCredentials()) {
            if (cred.getUserID().equals(userID) && cred.getPassword().equals(password)) {
                currentUserID = userID;
                currentUserType = cred.getUserType();
                System.out.println("Login successful! Welcome " + userID);
                return;
            }
        }
        System.out.println("Invalid credentials! Please try again.");
    }
    
    private static void register() {
        System.out.println("\n--- REGISTER ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        // Generate user ID (first 2 letters of first name + 4 digits)
        String userID = firstName.substring(0, 2).toUpperCase() + "100" + (DataStructure.getAllProfiles().size() + 1);
        
        // Create profile
        ProfileBean profile = new ProfileBean(userID, firstName, lastName, new java.util.Date(), 
                                            "Not Specified", "", "", "", "", "", "", email, password);
        DataStructure.addProfile(profile);
        
        // Create credentials
        CredentialsBean credentials = new CredentialsBean(userID, password, "C", 0);
        DataStructure.addCredential(credentials);
        
        System.out.println("Registration successful! Your User ID is: " + userID);
    }
    
    private static void viewAllFlights() {
        System.out.println("\n--- ALL FLIGHTS ---");
        ArrayList<FlightBean> flights = DataStructure.getAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            for (FlightBean flight : flights) {
                System.out.println(flight);
            }
        }
    }
    
    private static void viewAllRoutes() {
        System.out.println("\n--- ALL ROUTES ---");
        ArrayList<RouteBean> routes = DataStructure.getAllRoutes();
        if (routes.isEmpty()) {
            System.out.println("No routes available.");
        } else {
            for (RouteBean route : routes) {
                System.out.println(route);
            }
        }
    }
    
    private static void viewAllSchedules() {
        System.out.println("\n--- ALL SCHEDULES ---");
        ArrayList<ScheduleBean> schedules = DataStructure.getAllSchedules();
        if (schedules.isEmpty()) {
            System.out.println("No schedules available.");
        } else {
            for (ScheduleBean schedule : schedules) {
                System.out.println(schedule);
            }
        }
    }
    
    private static void viewMyProfile() {
        System.out.println("\n--- MY PROFILE ---");
        for (ProfileBean profile : DataStructure.getAllProfiles()) {
            if (profile.getUserID().equals(currentUserID)) {
                System.out.println("User ID: " + profile.getUserID());
                System.out.println("Name: " + profile.getFirstName() + " " + profile.getLastName());
                System.out.println("Email: " + profile.getEmailID());
                return;
            }
        }
        System.out.println("Profile not found!");
    }
    
    private static void addFlight() {
        if (!"A".equals(currentUserType)) {
            System.out.println("Access denied! Admin privileges required.");
            return;
        }
        
        System.out.println("\n--- ADD FLIGHT ---");
        System.out.print("Flight Name: ");
        String flightName = scanner.nextLine();
        System.out.print("Seating Capacity: ");
        int seatingCapacity = scanner.nextInt();
        System.out.print("Reservation Capacity: ");
        int reservationCapacity = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String flightID = flightName.substring(0, 2).toUpperCase() + "10" + (DataStructure.getAllFlights().size() + 1);
        FlightBean flight = new FlightBean(flightID, flightName, seatingCapacity, reservationCapacity);
        DataStructure.addFlight(flight);
        
        System.out.println("Flight added successfully! Flight ID: " + flightID);
    }
    
    private static void addRoute() {
        if (!"A".equals(currentUserType)) {
            System.out.println("Access denied! Admin privileges required.");
            return;
        }
        
        System.out.println("\n--- ADD ROUTE ---");
        System.out.print("Source: ");
        String source = scanner.nextLine();
        System.out.print("Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Distance: ");
        int distance = scanner.nextInt();
        System.out.print("Fare: ");
        double fare = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        String routeID = source.substring(0, 2).toUpperCase() + destination.substring(0, 2).toUpperCase() + 
                        "0" + (DataStructure.getAllRoutes().size() + 1);
        RouteBean route = new RouteBean(routeID, source, destination, distance, fare);
        DataStructure.addRoute(route);
        
        System.out.println("Route added successfully! Route ID: " + routeID);
    }
    
    private static void logout() {
        System.out.println("Logout successful! Goodbye " + currentUserID);
        currentUserID = null;
        currentUserType = null;
    }
}
