package com.ust.frs.ui;

import com.ust.frs.service.*;
import com.ust.frs.bean.*;
import com.ust.frs.util.DataStructure;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUserID = null;
    private static String currentUserType = null;
    
    private static UserServiceImpl userService = new UserServiceImpl();
    private static AdministratorServiceImpl adminService = new AdministratorServiceImpl();
    private static CustomerServiceImpl customerService = new CustomerServiceImpl();
    
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
        System.out.println("\n--- ADMINISTRATOR MENU ---");
        System.out.println("=== FLIGHT MANAGEMENT (P1) ===");
        System.out.println("1.  Add Flight Details (AD-001)");
        System.out.println("2.  Delete Flight Details (AD-002)");
        System.out.println("3.  View Flight Details (AD-003)");
        System.out.println("4.  Modify Flight Details (AD-004)");
        
        System.out.println("=== ROUTE MANAGEMENT ===");
        System.out.println("5.  Add Route Details (AD-005 P1)");
        System.out.println("6.  Delete Route Details (AD-006 P2)");
        System.out.println("7.  View Route Details (AD-007 P2)");
        System.out.println("8.  Modify Route Details (AD-008 P2)");
        
        System.out.println("=== SCHEDULE MANAGEMENT ===");
        System.out.println("9.  Add Schedule Details (AD-009 P1)");
        System.out.println("10. Delete Schedule Details (AD-010 P2)");
        System.out.println("11. View Schedule Details (AD-011 P2)");
        System.out.println("12. Modify Schedule Details (AD-012 P2)");
        
        System.out.println("=== PASSENGER MANAGEMENT ===");
        System.out.println("13. View Passenger Details (AD-013 P1)");
        
        System.out.println("=== SYSTEM ===");
        System.out.println("14. Change Password");
        System.out.println("15. Logout");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: addFlight(); break;
            case 2: deleteFlight(); break;
            case 3: viewFlightDetails(); break;
            case 4: modifyFlight(); break;
            case 5: addRoute(); break;
            case 6: deleteRoute(); break;
            case 7: viewRouteDetails(); break;
            case 8: modifyRoute(); break;
            case 9: addSchedule(); break;
            case 10: deleteSchedule(); break;
            case 11: viewScheduleDetails(); break;
            case 12: modifySchedule(); break;
            case 13: viewPassengerDetails(); break;
            case 14: changePassword(); break;
            case 15: logout(); break;
            default: System.out.println("Invalid option!");
        }
    }
    
    private static void showCustomerMenu() {
        System.out.println("\n--- CUSTOMER MENU ---");
        System.out.println("1. Register to FRS (US-001 P1)");
        System.out.println("2. View Flight/Schedule Details (US-002 P1)");
        System.out.println("3. Reserve Flight Tickets (US-003 P1)");
        System.out.println("4. Cancel Flight Tickets (US-004 P2)");
        System.out.println("5. View E-Ticket (US-005 P1)");
        System.out.println("6. Print E-Ticket (US-006 P2)");
        System.out.println("7. Change Password");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: register(); break; // Can register another user
            case 2: viewFlightScheduleDetails(); break;
            case 3: reserveTickets(); break;
            case 4: cancelTickets(); break;
            case 5: viewETicket(); break;
            case 6: printETicket(); break;
            case 7: changePassword(); break;
            case 8: logout(); break;
            default: System.out.println("Invalid option!");
        }
    }
    
    private static void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("User ID: ");
        String userID = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        CredentialsBean credentials = new CredentialsBean(userID, password, "", 0);
        String result = userService.login(credentials);
        
        if (!"INVALID".equals(result)) {
            currentUserID = userID;
            currentUserType = result;
            System.out.println("Login successful! Welcome " + userID);
            System.out.println("User Type: " + ("A".equals(result) ? "Administrator" : "Customer"));
        } else {
            System.out.println("Invalid credentials! Please try again.");
        }
    }
    
    private static void register() {
        System.out.println("\n--- CUSTOMER REGISTRATION (US-001) ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Street: ");
        String street = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Pincode: ");
        String pincode = scanner.nextLine();
        System.out.print("Mobile No: ");
        String mobileNo = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        ProfileBean profile = new ProfileBean();
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        // profile.setDateOfBirth(parseDate(dob)); // Implement date parsing if needed
        profile.setGender(gender);
        profile.setStreet(street);
        profile.setLocation(location);
        profile.setCity(city);
        profile.setState(state);
        profile.setPincode(pincode);
        profile.setMobileNo(mobileNo);
        profile.setEmailID(email);
        profile.setPassword(password);
        
        String userID = userService.register(profile);
        if (!"FAIL".equals(userID)) {
            System.out.println("Registration successful! Your User ID is: " + userID);
        } else {
            System.out.println("Registration failed! Please try again.");
        }
    }
    
    // ADMINISTRATOR FUNCTIONS
    
    private static void addFlight() {
        System.out.println("\n--- ADD FLIGHT DETAILS (AD-001) ---");
        System.out.print("Flight Name: ");
        String flightName = scanner.nextLine();
        System.out.print("Seating Capacity: ");
        int seatingCapacity = scanner.nextInt();
        System.out.print("Reservation Capacity: ");
        int reservationCapacity = scanner.nextInt();
        scanner.nextLine();
        
        FlightBean flight = new FlightBean();
        flight.setFlightName(flightName);
        flight.setSeatingCapacity(seatingCapacity);
        flight.setReservationCapacity(reservationCapacity);
        
        String result = adminService.addFlight(flight);
        if ("SUCCESS".equals(result)) {
            System.out.println("Flight added successfully!");
        } else {
            System.out.println("Failed to add flight!");
        }
    }
    
    private static void deleteFlight() {
        System.out.println("\n--- DELETE FLIGHT DETAILS (AD-002) ---");
        viewAllFlights();
        System.out.print("Enter Flight ID to delete: ");
        String flightID = scanner.nextLine();
        
        ArrayList<String> flightIDs = new ArrayList<>();
        flightIDs.add(flightID);
        
        int count = adminService.removeFlight(flightIDs);
        if (count > 0) {
            System.out.println("Flight deleted successfully!");
        } else {
            System.out.println("Failed to delete flight!");
        }
    }
    
    private static void viewFlightDetails() {
        System.out.println("\n--- VIEW FLIGHT DETAILS (AD-003) ---");
        viewAllFlights();
    }
    
    private static void modifyFlight() {
        System.out.println("\n--- MODIFY FLIGHT DETAILS (AD-004) ---");
        viewAllFlights();
        System.out.print("Enter Flight ID to modify: ");
        String flightID = scanner.nextLine();
        
        FlightBean flight = adminService.viewByFlightId(flightID);
        if (flight != null) {
            System.out.println("Current Details: " + flight);
            System.out.print("New Flight Name: ");
            String flightName = scanner.nextLine();
            System.out.print("New Seating Capacity: ");
            int seatingCapacity = scanner.nextInt();
            System.out.print("New Reservation Capacity: ");
            int reservationCapacity = scanner.nextInt();
            scanner.nextLine();
            
            flight.setFlightName(flightName);
            flight.setSeatingCapacity(seatingCapacity);
            flight.setReservationCapacity(reservationCapacity);
            
            if (adminService.modifyFlight(flight)) {
                System.out.println("Flight modified successfully!");
            } else {
                System.out.println("Failed to modify flight!");
            }
        } else {
            System.out.println("Flight not found!");
        }
    }
    
    private static void addRoute() {
        System.out.println("\n--- ADD ROUTE DETAILS (AD-005) ---");
        System.out.print("Source: ");
        String source = scanner.nextLine();
        System.out.print("Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Distance (miles): ");
        int distance = scanner.nextInt();
        System.out.print("Fare per mile: ");
        double fare = scanner.nextDouble();
        scanner.nextLine();
        
        RouteBean route = new RouteBean();
        route.setSource(source);
        route.setDestination(destination);
        route.setDistance(distance);
        route.setFare(fare);
        
        String result = adminService.addRoute(route);
        if ("SUCCESS".equals(result)) {
            System.out.println("Route added successfully!");
        } else {
            System.out.println("Failed to add route!");
        }
    }
    
    private static void deleteRoute() {
        System.out.println("\n--- DELETE ROUTE DETAILS (AD-006) ---");
        viewAllRoutes();
        System.out.print("Enter Route ID to delete: ");
        String routeID = scanner.nextLine();
        
        ArrayList<String> routeIDs = new ArrayList<>();
        routeIDs.add(routeID);
        
        int count = adminService.removeRoute(routeIDs);
        if (count > 0) {
            System.out.println("Route deleted successfully!");
        } else {
            System.out.println("Failed to delete route!");
        }
    }
    
    private static void viewRouteDetails() {
        System.out.println("\n--- VIEW ROUTE DETAILS (AD-007) ---");
        viewAllRoutes();
    }
    
    private static void modifyRoute() {
        System.out.println("\n--- MODIFY ROUTE DETAILS (AD-008) ---");
        viewAllRoutes();
        System.out.print("Enter Route ID to modify: ");
        String routeID = scanner.nextLine();
        
        RouteBean route = adminService.viewByRouteId(routeID);
        if (route != null) {
            System.out.println("Current Details: " + route);
            System.out.print("New Source: ");
            String source = scanner.nextLine();
            System.out.print("New Destination: ");
            String destination = scanner.nextLine();
            System.out.print("New Distance: ");
            int distance = scanner.nextInt();
            System.out.print("New Fare: ");
            double fare = scanner.nextDouble();
            scanner.nextLine();
            
            route.setSource(source);
            route.setDestination(destination);
            route.setDistance(distance);
            route.setFare(fare);
            
            if (adminService.modifyRoute(route)) {
                System.out.println("Route modified successfully!");
            } else {
                System.out.println("Failed to modify route!");
            }
        } else {
            System.out.println("Route not found!");
        }
    }
    
    private static void addSchedule() {
        System.out.println("\n--- ADD SCHEDULE DETAILS (AD-009) ---");
        viewAllFlights();
        System.out.print("Enter Flight ID: ");
        String flightID = scanner.nextLine();
        
        viewAllRoutes();
        System.out.print("Enter Route ID: ");
        String routeID = scanner.nextLine();
        
        System.out.print("Travel Duration (minutes): ");
        int travelDuration = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Available Days (comma separated): ");
        String availableDays = scanner.nextLine();
        System.out.print("Departure Time (HH:MM): ");
        String departureTime = scanner.nextLine();
        
        ScheduleBean schedule = new ScheduleBean();
        schedule.setFlightID(flightID);
        schedule.setRouteID(routeID);
        schedule.setTravelDuration(travelDuration);
        schedule.setAvailableDays(availableDays);
        schedule.setDepartureTime(departureTime);
        
        String result = adminService.addSchedule(schedule);
        if ("SUCCESS".equals(result)) {
            System.out.println("Schedule added successfully!");
        } else {
            System.out.println("Failed to add schedule!");
        }
    }
    
    private static void deleteSchedule() {
        System.out.println("\n--- DELETE SCHEDULE DETAILS (AD-010) ---");
        viewAllSchedules();
        System.out.print("Enter Schedule ID to delete: ");
        String scheduleID = scanner.nextLine();
        
        ArrayList<String> scheduleIDs = new ArrayList<>();
        scheduleIDs.add(scheduleID);
        
        int count = adminService.removeSchedule(scheduleIDs);
        if (count > 0) {
            System.out.println("Schedule deleted successfully!");
        } else {
            System.out.println("Failed to delete schedule!");
        }
    }
    
    private static void viewScheduleDetails() {
        System.out.println("\n--- VIEW SCHEDULE DETAILS (AD-011) ---");
        viewAllSchedules();
    }
    
    private static void modifySchedule() {
        System.out.println("\n--- MODIFY SCHEDULE DETAILS (AD-012) ---");
        viewAllSchedules();
        System.out.print("Enter Schedule ID to modify: ");
        String scheduleID = scanner.nextLine();
        
        ScheduleBean schedule = adminService.viewByScheduleId(scheduleID);
        if (schedule != null) {
            System.out.println("Current Details: " + schedule);
            
            viewAllFlights();
            System.out.print("New Flight ID: ");
            String flightID = scanner.nextLine();
            
            viewAllRoutes();
            System.out.print("New Route ID: ");
            String routeID = scanner.nextLine();
            
            System.out.print("New Travel Duration: ");
            int travelDuration = scanner.nextInt();
            scanner.nextLine();
            System.out.print("New Available Days: ");
            String availableDays = scanner.nextLine();
            System.out.print("New Departure Time: ");
            String departureTime = scanner.nextLine();
            
            schedule.setFlightID(flightID);
            schedule.setRouteID(routeID);
            schedule.setTravelDuration(travelDuration);
            schedule.setAvailableDays(availableDays);
            schedule.setDepartureTime(departureTime);
            
            if (adminService.modifySchedule(schedule)) {
                System.out.println("Schedule modified successfully!");
            } else {
                System.out.println("Failed to modify schedule!");
            }
        } else {
            System.out.println("Schedule not found!");
        }
    }
    
    private static void viewPassengerDetails() {
        System.out.println("\n--- VIEW PASSENGER DETAILS (AD-013) ---");
        viewAllSchedules();
        System.out.print("Enter Schedule ID: ");
        String scheduleID = scanner.nextLine();
        
        ArrayList<PassengerBean> passengers = adminService.viewPassengersByFlight(scheduleID);
        if (passengers.isEmpty()) {
            System.out.println("No passengers found for this schedule.");
        } else {
            System.out.println("Passengers for Schedule " + scheduleID + ":");
            for (PassengerBean passenger : passengers) {
                System.out.println(passenger);
            }
        }
    }
    
    // CUSTOMER FUNCTIONS
    
    private static void viewFlightScheduleDetails() {
        System.out.println("\n--- VIEW FLIGHT/SCHEDULE DETAILS (US-002) ---");
        System.out.println("1. View All Flights");
        System.out.println("2. View All Routes");
        System.out.println("3. View All Schedules");
        System.out.println("4. Search Schedules by Route");
        System.out.print("Choose option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1: viewAllFlights(); break;
            case 2: viewAllRoutes(); break;
            case 3: viewAllSchedules(); break;
            case 4: 
                System.out.print("Enter Source: ");
                String source = scanner.nextLine();
                System.out.print("Enter Destination: ");
                String destination = scanner.nextLine();
                ArrayList<ScheduleBean> schedules = customerService.viewScheduleByRoute(source, destination, new Date());
                if (schedules.isEmpty()) {
                    System.out.println("No schedules found for this route.");
                } else {
                    for (ScheduleBean schedule : schedules) {
                        System.out.println(schedule);
                    }
                }
                break;
            default: System.out.println("Invalid option!");
        }
    }
    
    private static void reserveTickets() {
        System.out.println("\n--- RESERVE FLIGHT TICKETS (US-003) ---");
        viewAllSchedules();
        System.out.print("Enter Schedule ID: ");
        String scheduleID = scanner.nextLine();
        
        System.out.print("Number of Seats: ");
        int noOfSeats = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Reservation Type (Economy/Business/First): ");
        String reservationType = scanner.nextLine();
        
        ReservationBean reservation = new ReservationBean();
        reservation.setUserID(currentUserID);
        reservation.setScheduleID(scheduleID);
        reservation.setNoOfSeats(noOfSeats);
        reservation.setReservationType(reservationType);
        reservation.setBookingDate(new Date());
        reservation.setJourneyDate(new Date()); // Should get actual journey date
        
        ArrayList<PassengerBean> passengers = new ArrayList<>();
        for (int i = 0; i < noOfSeats; i++) {
            System.out.println("Passenger " + (i+1) + " details:");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Gender: ");
            String gender = scanner.nextLine();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            
            PassengerBean passenger = new PassengerBean();
            passenger.setName(name);
            passenger.setGender(gender);
            passenger.setAge(age);
            passenger.setSeatNo(i+1);
            passengers.add(passenger);
        }
        
        String reservationID = customerService.reserveTicket(reservation, passengers);
        if (!"FAIL".equals(reservationID)) {
            System.out.println("Ticket reserved successfully! Reservation ID: " + reservationID);
        } else {
            System.out.println("Failed to reserve ticket!");
        }
    }
    
    private static void cancelTickets() {
        System.out.println("\n--- CANCEL FLIGHT TICKETS (US-004) ---");
        System.out.print("Enter Reservation ID to cancel: ");
        String reservationID = scanner.nextLine();
        
        if (customerService.cancelTicket(reservationID)) {
            System.out.println("Ticket cancelled successfully!");
        } else {
            System.out.println("Failed to cancel ticket!");
        }
    }
    
    private static void viewETicket() {
        System.out.println("\n--- VIEW E-TICKET (US-005) ---");
        System.out.print("Enter Reservation ID: ");
        String reservationID = scanner.nextLine();
        
        Map<ReservationBean, ArrayList<PassengerBean>> ticket = customerService.viewTicket(reservationID);
        if (!ticket.isEmpty()) {
            for (Map.Entry<ReservationBean, ArrayList<PassengerBean>> entry : ticket.entrySet()) {
                System.out.println("Reservation Details: " + entry.getKey());
                System.out.println("Passengers:");
                for (PassengerBean passenger : entry.getValue()) {
                    System.out.println("  " + passenger);
                }
            }
        } else {
            System.out.println("Ticket not found!");
        }
    }
    
    private static void printETicket() {
        System.out.println("\n--- PRINT E-TICKET (US-006) ---");
        System.out.print("Enter Reservation ID: ");
        String reservationID = scanner.nextLine();
        
        Map<ReservationBean, ArrayList<PassengerBean>> ticket = customerService.printTicket(reservationID);
        if (!ticket.isEmpty()) {
            System.out.println("=== E-TICKET (PRINT FORMAT) ===");
            for (Map.Entry<ReservationBean, ArrayList<PassengerBean>> entry : ticket.entrySet()) {
                System.out.println("Reservation: " + entry.getKey());
                System.out.println("Passengers:");
                for (PassengerBean passenger : entry.getValue()) {
                    System.out.println("  - " + passenger.getName() + " | Seat: " + passenger.getSeatNo());
                }
            }
            System.out.println("=== END OF TICKET ===");
        } else {
            System.out.println("Ticket not found!");
        }
    }
    
    // COMMON FUNCTIONS
    
    private static void changePassword() {
        System.out.println("\n--- CHANGE PASSWORD ---");
        System.out.print("Current Password: ");
        String currentPassword = scanner.nextLine();
        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();
        
        CredentialsBean credentials = new CredentialsBean(currentUserID, currentPassword, "", 0);
        String result = userService.changePassword(credentials, newPassword);
        if ("SUCCESS".equals(result)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Failed to change password!");
        }
    }
    
    private static void logout() {
        userService.logout(currentUserID);
        System.out.println("Logout successful! Goodbye " + currentUserID);
        currentUserID = null;
        currentUserType = null;
    }
    
    // UTILITY METHODS
    
    private static void viewAllFlights() {
        ArrayList<FlightBean> flights = adminService.viewByAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("=== ALL FLIGHTS ===");
            for (FlightBean flight : flights) {
                System.out.println(flight);
            }
        }
    }
    
    private static void viewAllRoutes() {
        ArrayList<RouteBean> routes = adminService.viewByAllRoute();
        if (routes.isEmpty()) {
            System.out.println("No routes available.");
        } else {
            System.out.println("=== ALL ROUTES ===");
            for (RouteBean route : routes) {
                System.out.println(route);
            }
        }
    }
    
    private static void viewAllSchedules() {
        ArrayList<ScheduleBean> schedules = adminService.viewByAllSchedule();
        if (schedules.isEmpty()) {
            System.out.println("No schedules available.");
        } else {
            System.out.println("=== ALL SCHEDULES ===");
            for (ScheduleBean schedule : schedules) {
                System.out.println(schedule);
            }
        }
    }
}
