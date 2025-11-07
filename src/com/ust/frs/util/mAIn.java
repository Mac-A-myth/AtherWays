package com.ust.frs.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.RouteBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.dao.AdministratorDAO;

public class Main {
    public static void main(String[] args) {
        AdministratorDAO ad = new AdministratorDAO();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n<<<<<<<<<<<<< MAIN MENU >>>>>>>>>>>>>>>");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    adminMenu(ad, sc);
                    break;
                case 2:
                    System.out.println("Customer Menu (Not implemented yet)");
                    break;
                case 3:
                    System.out.println("Thank you for using Flight Reservation System!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1 for Admin or 2 for Customer.");
                    break;
            }
        }
    }
    
    private static void adminMenu(AdministratorDAO ad, Scanner sc) {
        while (true) {
            System.out.println("\n<<<<<<<<<<<<< ADMIN MENU >>>>>>>>>>>>>>>");
            System.out.println("AD001 - Add Flight");
            System.out.println("AD002 - Delete Flight");
            System.out.println("AD003 - View Flights");
            System.out.println("AD004 - Update Flights");
            System.out.println("AD005 - Add Flight Route");
            System.out.println("AD006 - Delete Flight Route");
            System.out.println("AD007 - Display Flight Routes");
            System.out.println("AD008 - Update Flight Routes");
            System.out.println("AD009 - Add Schedule");
            System.out.println("AD010 - Remove Schedule");
            System.out.println("AD011 - View Schedule");
            System.out.println("AD012 - Update Schedule");
            System.out.println("AD013 - View Passenger Details by Flight");
            System.out.println("AD000 - Exit to Main Menu");
            System.out.print("Enter your choice: ");
            
            sc.nextLine(); // consume newline
            String op = sc.nextLine();
            
            switch (op) {
                case "AD001":
                    addFlight(ad, sc);
                    break;
                case "AD002":
                    removeFlight(ad, sc);
                    break;
                case "AD003":
                    viewAllFlights(ad);
                    break;
                case "AD004":
                    updateFlight(ad, sc);
                    break;
                case "AD005":
                    addRoute(ad, sc);
                    break;
                case "AD006":
                    removeRoute(ad, sc);
                    break;
                case "AD007":
                    viewAllRoutes(ad);
                    break;
                case "AD008":
                    updateRoute(ad, sc);
                    break;
                case "AD009":
                    addSchedule(ad, sc);
                    break;
                case "AD010":
                    removeSchedule(ad, sc);
                    break;
                case "AD011":
                    viewAllSchedules(ad);
                    break;
                case "AD012":
                    updateSchedule(ad, sc);
                    break;
                case "AD013":
                    viewPassengersByFlight(ad, sc);
                    break;
                case "AD000":
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }
    
    private static void addFlight(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            FlightBean flight = new FlightBean();
            System.out.print("Enter Flight ID: ");
            flight.setFlightID(sc.nextLine());
            System.out.print("Enter Flight Name: ");
            flight.setFlightName(sc.nextLine());
            System.out.print("Enter Seating Capacity: ");
            flight.setSeatingCapacity(sc.nextInt());
            System.out.print("Enter Reservation Capacity: ");
            flight.setReservationCapacity(sc.nextInt());
            System.out.println(ad.addFlight(flight));
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void removeFlight(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            ArrayList<String> list = new ArrayList<>();
            while (true) {
                System.out.print("Enter Flight ID to delete: ");
                String id = sc.nextLine();
                list.add(id);
                System.out.print("Do you want to delete more flights? (Y/N): ");
                String ans = sc.nextLine();
                if (ans.equalsIgnoreCase("N")) {
                    break;
                }
            }
            int result = ad.removeFlight(list);
            System.out.println(result + " flight(s) removed successfully.");
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void viewAllFlights(AdministratorDAO ad) {
        System.out.println("\n<<<<<<<<<<<<< ALL FLIGHTS >>>>>>>>>>>>>>>");
        ArrayList<FlightBean> flights = ad.viewByAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.printf("%-10s %-15s %-18s %-22s%n", 
                "Flight ID", "Flight Name", "Seating Capacity", "Reservation Capacity");
            System.out.println("------------------------------------------------------------------------");
            for (FlightBean flight : flights) {
                System.out.printf("%-10s %-15s %-18d %-22d%n", 
                    flight.getFlightID(), flight.getFlightName(), 
                    flight.getSeatingCapacity(), flight.getReservationCapacity());
            }
        }
    }
    
    private static void updateFlight(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            System.out.print("Enter Flight ID to modify: ");
            String flightID = sc.nextLine();
            FlightBean flight = ad.viewByFlightId(flightID);
            if (flight != null) {
                System.out.print("Enter new Flight Name: ");
                flight.setFlightName(sc.nextLine());
                System.out.print("Enter new Seating Capacity: ");
                flight.setSeatingCapacity(sc.nextInt());
                System.out.print("Enter new Reservation Capacity: ");
                flight.setReservationCapacity(sc.nextInt());
                if (ad.modifyFlight(flight)) {
                    System.out.println("Flight updated successfully!");
                } else {
                    System.out.println("Failed to update flight.");
                }
            } else {
                System.out.println("Flight not found.");
            }
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void addRoute(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            RouteBean route = new RouteBean();
            System.out.print("Enter Route ID: ");
            route.setRouteID(sc.nextLine());
            System.out.print("Enter Source City: ");
            route.setSource(sc.nextLine());
            System.out.print("Enter Destination City: ");
            route.setDestination(sc.nextLine());
            System.out.print("Enter Distance (in km): ");
            route.setDistance(sc.nextInt());
            System.out.print("Enter Fare: ");
            route.setFare(sc.nextDouble());
            System.out.println(ad.addRoute(route));
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void removeRoute(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            ArrayList<String> routeIds = new ArrayList<>();
            while (true) {
                System.out.print("Enter Route ID to delete: ");
                String routeID = sc.nextLine();
                routeIds.add(routeID);
                System.out.print("Do you want to delete more routes? (Y/N): ");
                String ans = sc.nextLine();
                if (ans.equalsIgnoreCase("N")) {
                    break;
                }
            }
            int result = ad.removeRoute(routeIds);
            System.out.println(result + " route(s) removed successfully.");
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void viewAllRoutes(AdministratorDAO ad) {
        System.out.println("\n<<<<<<<<<<<<< ALL ROUTES >>>>>>>>>>>>>>>");
        ArrayList<RouteBean> routes = ad.viewByAllRoute();
        if (routes.isEmpty()) {
            System.out.println("No routes available.");
        } else {
            System.out.printf("%-8s %-15s %-15s %-10s %-10s%n", 
                "Route ID", "Source", "Destination", "Distance", "Fare");
            System.out.println("------------------------------------------------------------");
            for (RouteBean route : routes) {
                System.out.printf("%-8s %-15s %-15s %-10d %-10.2f%n", 
                    route.getRouteID(), route.getSource(), route.getDestination(), 
                    route.getDistance(), route.getFare());
            }
        }
    }
    
    private static void updateRoute(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            System.out.print("Enter Route ID to modify: ");
            String routeID = sc.nextLine();
            RouteBean route = ad.viewByRouteId(routeID);
            if (route != null) {
                System.out.print("Enter new Source City: ");
                route.setSource(sc.nextLine());
                System.out.print("Enter new Destination City: ");
                route.setDestination(sc.nextLine());
                System.out.print("Enter new Distance (in km): ");
                route.setDistance(sc.nextInt());
                System.out.print("Enter new Fare: ");
                route.setFare(sc.nextDouble());
                if (ad.modifyRoute(route)) {
                    System.out.println("Route updated successfully!");
                } else {
                    System.out.println("Failed to update route.");
                }
            } else {
                System.out.println("Route not found.");
            }
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void addSchedule(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            ScheduleBean schedule = new ScheduleBean();
            System.out.print("Enter Schedule ID: ");
            schedule.setScheduleID(sc.nextLine());
            System.out.print("Enter Flight ID: ");
            schedule.setFlightID(sc.nextLine());
            System.out.print("Enter Route ID: ");
            schedule.setRouteID(sc.nextLine());
            System.out.print("Enter Available Days (e.g., Mon,Tue,Wed): ");
            schedule.setAvailableDays(sc.nextLine());
            System.out.print("Enter Departure Time: ");
            schedule.setDepartureTime(sc.nextLine());
            System.out.print("Enter Travel Duration (hours): ");
            schedule.setTravelDuration(sc.nextInt());
            System.out.println(ad.addSchedule(schedule));
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void removeSchedule(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            ArrayList<String> scheduleIds = new ArrayList<>();
            while (true) {
                System.out.print("Enter Schedule ID to delete: ");
                String scheduleID = sc.nextLine();
                scheduleIds.add(scheduleID);
                System.out.print("Do you want to delete more schedules? (Y/N): ");
                String ans = sc.nextLine();
                if (ans.equalsIgnoreCase("N")) {
                    break;
                }
            }
            int result = ad.removeSchedule(scheduleIds);
            System.out.println(result + " schedule(s) removed successfully.");
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void viewAllSchedules(AdministratorDAO ad) {
        System.out.println("\n<<<<<<<<<<<<< ALL SCHEDULES >>>>>>>>>>>>>>>");
        ArrayList<ScheduleBean> schedules = ad.viewByAllSchedule();
        if (schedules.isEmpty()) {
            System.out.println("No schedules available.");
        } else {
            System.out.printf("%-10s %-10s %-8s %-20s %-15s %-15s%n", 
                "Schedule ID", "Flight ID", "Route ID", "Available Days", 
                "Departure Time", "Travel Duration");
            System.out.println("--------------------------------------------------------------------------------");
            for (ScheduleBean schedule : schedules) {
                System.out.printf("%-10s %-10s %-8s %-20s %-15s %-15d%n", 
                    schedule.getScheduleID(), schedule.getFlightID(), 
                    schedule.getRouteID(), schedule.getAvailableDays(), 
                    schedule.getDepartureTime(), schedule.getTravelDuration());
            }
        }
    }
    
    private static void updateSchedule(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter pin: ");
        int pass = sc.nextInt();
        if (pass == 0000) {
            sc.nextLine();
            System.out.print("Enter Schedule ID to modify: ");
            String scheduleID = sc.nextLine();
            ScheduleBean schedule = ad.viewByScheduleId(scheduleID);
            if (schedule != null) {
                System.out.print("Enter new Flight ID: ");
                schedule.setFlightID(sc.nextLine());
                System.out.print("Enter new Route ID: ");
                schedule.setRouteID(sc.nextLine());
                System.out.print("Enter new Available Days: ");
                schedule.setAvailableDays(sc.nextLine());
                System.out.print("Enter new Departure Time: ");
                schedule.setDepartureTime(sc.nextLine());
                System.out.print("Enter new Travel Duration (hours): ");
                schedule.setTravelDuration(sc.nextInt());
                if (ad.modifySchedule(schedule)) {
                    System.out.println("Schedule updated successfully!");
                } else {
                    System.out.println("Failed to update schedule.");
                }
            } else {
                System.out.println("Schedule not found.");
            }
        } else {
            System.out.println("Invalid Pin");
        }
    }
    
    private static void viewPassengersByFlight(AdministratorDAO ad, Scanner sc) {
        System.out.print("Enter Schedule ID: ");
        String scheduleId = sc.nextLine();
        ArrayList<PassengerBean> passengers = ad.viewPassengersByFlight(scheduleId);
        
        if (passengers.isEmpty()) {
            System.out.println("No passengers found for this flight schedule.");
        } else {
            System.out.println("\n<<<<<<<<<<<<< PASSENGERS FOR SCHEDULE " + scheduleId + " >>>>>>>>>>>>>>>");
            System.out.printf("%-12s %-20s %-8s %-5s %-8s%n", 
                "Reservation ID", "Name", "Gender", "Age", "Seat No");
            System.out.println("--------------------------------------------------------");
            for (PassengerBean passenger : passengers) {
                System.out.printf("%-12s %-20s %-8s %-5d %-8d%n", 
                    passenger.getReservationID(), passenger.getName(), 
                    passenger.getGender(), passenger.getAge(), passenger.getSeatNo());
            }
        }
    }
}
