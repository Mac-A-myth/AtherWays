package com.ust.frs.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.RouteBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.bean.PassengerBean;
import com.ust.frs.dao.AdministratorDAO;

public class Main {
	public static void main(String[] args) {
		AdministratorDAO  ad = new AdministratorDAO();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("<<<<<<<<<<<<< MAIN MENU >>>>>>>>>>>>>>>\n" + "1.Admin\n" + "2.Customer");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				while (true) {
					sc.nextLine();
					System.out.println("<<<<<<<<<<<<< ADMIN MENU >>>>>>>>>>>>>>>\n" + "AD001 Add Flight\n"
							+ "AD002 Delete Flight\n" + "AD003 View Flights\n" + "AD004 Update Flights\n"
							+ "AD005 Add Flight Route\n" + "AD006 Delete Flight Route\n" + "AD007 Display Flight Routes\n"
							+ "AD008 Update Flight Routes\n" + "AD009 Add Schedule\n" + "AD010 Remove Schedule\n"+ "AD011 View Schedule\n"+ "AD012 Update Schedule\n"+ "AD013 Passengers Details\n"+ "AD000 Exit\n");
					String op = sc.nextLine();
					switch (op) {
					case "AD001":
					System.out.println("Enter pin:");
					int pass = sc.nextInt();
					if (pass == 0000) {
						sc.nextLine();
						FlightBean flight= new FlightBean();
						System.out.println("Enter Flight ID : ");
						flight.setFlightID(sc.nextLine());
						System.out.println("Enter Flight Name : ");
						flight.setFlightName(sc.nextLine());
						System.out.println("Enter Seating Capacity : ");
						flight.setSeatingCapacity(sc.nextInt());
						System.out.println("Enter Reservation Capacity : ");
						flight.setReservationCapacity(sc.nextInt());
						System.out.println(ad.addFlight(flight));
					}
						break;
						case "AD002":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								ArrayList<String> list = new ArrayList<String>();
								while (true) {
									System.out.println("Enter Flight ID : ");
									String id = sc.nextLine();
									list.add(id);
									System.out.println("Do you want to delete more stores? (Y/N)");
									String ans = sc.nextLine();
									if (ans.equalsIgnoreCase("N")) {
										break;
									}
								}
								System.out.println(ad.removeFlight(list));
								break;
							}
							else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD003": // View Flights
							System.out.println("<<<<<<<<<<<<< VIEW FLIGHTS >>>>>>>>>>>>>>>");
							ArrayList<FlightBean> flights = ad.viewByAllFlights();
							if(flights.isEmpty()) {
								System.out.println("No flights available.");
							} else {
								for (FlightBean flight : flights) {
									System.out.println("Flight ID: " + flight.getFlightID() +
											" | Flight Name: " + flight.getFlightName() +
											" | Seating Capacity: " + flight.getSeatingCapacity() +
											" | Reservation Capacity: " + flight.getReservationCapacity());
								}
							}
							break;

						case "AD004": // Update Flight
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();  // Consume newline character
								System.out.println("Enter Flight ID to modify: ");
								String flightID = sc.nextLine();
								FlightBean flight = ad.viewByFlightId(flightID);
								if(flight != null) {
									System.out.println("Enter new Flight Name: ");
									flight.setFlightName(sc.nextLine());
									System.out.println("Enter new Seating Capacity: ");
									flight.setSeatingCapacity(sc.nextInt());
									System.out.println("Enter new Reservation Capacity: ");
									flight.setReservationCapacity(sc.nextInt());
									if (ad.modifyFlight(flight)) {
										System.out.println("Flight updated successfully!");
									} else {
										System.out.println("Failed to update flight.");
									}
								} else {
									System.out.println("Flight not found.");
								}
							}
							break;

						case "AD005": // Add Route
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();  // Consume newline character
								RouteBean route = new RouteBean();
								System.out.println("Enter Route ID: ");
								route.setRouteID(sc.nextLine());
								System.out.println("Enter Start City: ");
								route.setSource(sc.nextLine());
								System.out.println("Enter Destination City: ");
								route.setDestination(sc.nextLine());
								System.out.println("Enter Distance (in km): ");
								route.setDistance(sc.nextInt());
								System.out.println("Enter Cost: ");
								route.setFare(sc.nextInt());
								System.out.println(ad.addRoute(route));
							}
							break;

						case "AD006": // Delete Route
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								ArrayList<String> routeIds = new ArrayList<>();
								while (true) {
									System.out.println("Enter Route ID: ");
									String routeID = sc.nextLine();
									routeIds.add(routeID);
									System.out.println("Do you want to delete more routes? (Y/N)");
									String ans = sc.nextLine();
									if (ans.equalsIgnoreCase("N")) {
										break;
									}
								}
								System.out.println(ad.removeRoute(routeIds));
							}
							break;

						case "AD007": // View Routes
							System.out.println("<<<<<<<<<<<<< VIEW FLIGHT ROUTES >>>>>>>>>>>>>>>");
							ArrayList<RouteBean> routes = ad.viewByAllRoute();
							if(routes.isEmpty()) {
								System.out.println("No routes available.");
							} else {
								for (RouteBean routeBean : routes) {
									System.out.println("Route ID: " + routeBean.getRouteID() +
											" | Start City: " + routeBean.getSource() +
											" | Destination City: " + routeBean.getDestination() +
											" | Distance: " + routeBean.getDistance() +
											" km | Cost: " + routeBean.getFare());
								}
							}
							break;

						case "AD008": // Update Route
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine(); // Consume newline character
								System.out.println("Enter Route ID to modify: ");
								String routeID = sc.nextLine();
								RouteBean route = ad.viewByRouteId(routeID);
								if(route != null) {
									System.out.println("Enter new Start City: ");
									route.setSource(sc.nextLine());
									System.out.println("Enter new Destination City: ");
									route.setDestination(sc.nextLine());
									System.out.println("Enter new Distance (in km): ");
									route.setDistance(sc.nextInt());
									System.out.println("Enter new Cost: ");
									route.setFare(sc.nextInt());
									if (ad.modifyRoute(route)) {
										System.out.println("Route updated successfully!");
									} else {
										System.out.println("Failed to update route.");
									}
								} else {
									System.out.println("Route not found.");
								}
							}
							break;

						case "AD009": // Add Schedule
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine(); // Consume newline character
								ScheduleBean schedule = new ScheduleBean();
								System.out.println("Enter Schedule ID: ");
								schedule.setScheduleID(sc.nextLine());
								System.out.println("Enter Flight ID: ");
								schedule.setFlightID(sc.nextLine());
								System.out.println("Enter Route ID: ");
								schedule.setRouteID(sc.nextLine());
								System.out.println("Enter Days (e.g., Mon, Tue): ");
								schedule.setAvailableDays(sc.nextLine());
								System.out.println("Enter Departure Time: ");
								schedule.setDepartureTime(sc.nextLine());
								System.out.println("Enter Travel Schedule: ");
								schedule.setTravelDuration(sc.nextInt());
								System.out.println(ad.addSchedule(schedule));
							}
							break;

						case "AD010": // Remove Schedule
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								ArrayList<String> scheduleIds = new ArrayList<>();
								while (true) {
									System.out.println("Enter Schedule ID: ");
									String scheduleID = sc.nextLine();
									scheduleIds.add(scheduleID);
									System.out.println("Do you want to remove more schedules? (Y/N)");
									String ans = sc.nextLine();
									if (ans.equalsIgnoreCase("N")) {
										break;
									}
								}
								System.out.println(ad.removeSchedule(scheduleIds));
							}
							break;

						case "AD011": // View Schedule
							System.out.println("<<<<<<<<<<<<< VIEW FLIGHT SCHEDULE >>>>>>>>>>>>>>>");
							ArrayList<ScheduleBean> schedules = ad.viewByAllSchedule();
							if(schedules.isEmpty()) {
								System.out.println("No schedules available.");
							} else {
								for (ScheduleBean scheduleBean : schedules) {
									System.out.println("Schedule ID: " + scheduleBean.getScheduleID() +
											" | Flight ID: " + scheduleBean.getFlightID() +
											" | Route ID: " + scheduleBean.getRouteID() +
											" | Days: " + scheduleBean.getAvailableDays() +
											" | Departure Time: " + scheduleBean.getDepartureTime() +
											" | Travel Duration: " + scheduleBean.getTravelDuration());
								}
							}
							break;

						case "AD012": // Update Schedule
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine(); // Consume newline character
								System.out.println("Enter Schedule ID to modify: ");
								String scheduleID = sc.nextLine();
								ScheduleBean schedule = ad.viewByScheduleId(scheduleID);
								if(schedule != null) {
									System.out.println("Enter new Flight ID: ");
									schedule.setFlightID(sc.nextLine());
									System.out.println("Enter new Route ID: ");
									schedule.setRouteID(sc.nextLine());
									System.out.println("Enter new Days: ");
									schedule.setAvailableDays(sc.nextLine());
									System.out.println("Enter new Departure Time: ");
									schedule.setDepartureTime(sc.nextLine());
									System.out.println("Enter new Travel Duration: ");
									schedule.setTravelDuration(sc.nextInt());
									if (ad.modifySchedule(schedule)) {
										System.out.println("Schedule updated successfully!");
									} else {
										System.out.println("Failed to update schedule.");
									}
								} else {
									System.out.println("Schedule not found.");
								}
							}
							break;

						case "AD013": // View Passenger Details
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine(); // Consume newline character
								System.out.println("<<<<<<<<<<<<< PASSENGER DETAILS >>>>>>>>>>>>>>>");
								
								// Sub-menu for passenger details
								System.out.println("1. View All Passenger Bookings");
								System.out.println("2. View Passengers by Flight");
								System.out.println("3. View Passengers by Route");
								System.out.println("4. Search Passenger by ID/Name");
								System.out.println("5. Back to Admin Menu");
								
								int passengerChoice = sc.nextInt();
								sc.nextLine(); // Consume newline
								
								switch(passengerChoice) {
									case 1:
										// View all passenger bookings
										ArrayList<PassengerBean> allPassengers = ad.viewAllPassengers();
										if(allPassengers.isEmpty()) {
											System.out.println("No passenger bookings found.");
										} else {
											System.out.println("Total Bookings: " + allPassengers.size());
											for (PassengerBean passenger : allPassengers) {
												displayPassengerInfo(passenger);
											}
										}
										break;
										
									case 2:
										// View by flight
										System.out.println("Enter Flight ID: ");
										String flightId = sc.nextLine();
										ArrayList<PassengerBean> flightPassengers = ad.viewPassengersByFlight(flightId);
										if(flightPassengers.isEmpty()) {
											System.out.println("No passengers found for flight: " + flightId);
										} else {
											System.out.println("Passengers for Flight " + flightId + ": " + flightPassengers.size());
											for (PassengerBean passenger : flightPassengers) {
												displayPassengerInfo(passenger);
											}
										}
										break;
										
									case 3:
										// View by route
										System.out.println("Enter Route ID: ");
										String routeId = sc.nextLine();
										ArrayList<PassengerBean> routePassengers = ad.viewPassengersByRoute(routeId);
										if(routePassengers.isEmpty()) {
											System.out.println("No passengers found for route: " + routeId);
										} else {
											System.out.println("Passengers for Route " + routeId + ": " + routePassengers.size());
											for (PassengerBean passenger : routePassengers) {
												displayPassengerInfo(passenger);
											}
										}
										break;
										
									case 4:
										// Search passenger
										System.out.println("Enter Passenger ID or Name: ");
										String searchTerm = sc.nextLine();
										ArrayList<PassengerBean> searchedPassengers = ad.searchPassengers(searchTerm);
										if(searchedPassengers.isEmpty()) {
											System.out.println("No passengers found for: " + searchTerm);
										} else {
											for (PassengerBean passenger : searchedPassengers) {
												displayPassengerInfo(passenger);
											}
										}
										break;
										
									case 5:
										// Back to admin menu
										System.out.println("Returning to Admin Menu...");
										break;
										
									default:
										System.out.println("Invalid choice. Please try again.");
								}
							} else {
								System.out.println("Invalid Pin");
							}
							break;

						case "AD000": // Exit
							System.out.println("Exiting Admin menu.");
							return;

						default:
							System.out.println("Invalid option, please try again.");
							break;
					}
				}
			case 2:
				// Customer Menu (This can be added similarly to Admin Menu if needed)
				System.out.println("Customer Menu (Not implemented yet)");
				break;

			default:
				System.out.println("Invalid choice. Please select 1 for Admin or 2 for Customer.");
				break;
			}
		}
	}
	
	/**
	 * Helper method to display passenger information in a formatted way
	 * @param passenger The passenger bean object containing passenger details
	 */
	private static void displayPassengerInfo(PassengerBean passenger) {
		System.out.println("----------------------------------------");
		System.out.println("Booking ID: " + passenger.getBookingID());
		System.out.println("Passenger ID: " + passenger.getPassengerID());
		System.out.println("Passenger Name: " + passenger.getPassengerName());
		System.out.println("Flight: " + passenger.getFlightID());
		System.out.println("Route: " + passenger.getRouteID());
		System.out.println("Schedule: " + passenger.getScheduleID());
		System.out.println("Booking Date: " + passenger.getBookingDate());
		System.out.println("Seat Number: " + passenger.getSeatNumber());
		System.out.println("Class: " + passenger.getTravelClass());
		System.out.println("Fare: $" + passenger.getFare());
		System.out.println("Status: " + passenger.getBookingStatus());
		System.out.println("Contact: " + passenger.getContactNumber());
		System.out.println("Email: " + passenger.getEmail());
		System.out.println("----------------------------------------");
	}
}
