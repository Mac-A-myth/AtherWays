package com.ust.frs.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.ust.frs.bean.FlightBean;
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
							+ "AD008 Update Flight Routes\n" + "AD009 Add Schedule\n" + "AD010 Remove Schedule"+ "AD011 View Schedule"+ "AD012 Update Schedule"+ "AD013 Passengers Details"+ "AD000 Exit");
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
						case "AD003":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								System.out.println("Enter Store ID : ");
								String sid = sc.nextLine();
								System.out.println(ad.viewByAllFlights(sid));
								break;
							} else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD004":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								StoreBean store = new StoreBean();
								System.out.println("Enter Store ID : ");
								store.setStoreId(sc.nextLine());
								System.out.println("Enter Name : ");
								store.setName(sc.nextLine());
								System.out.println("Enter Street : ");
								store.setStreet(sc.nextLine());
								System.out.println("Enter City : ");
								store.setCity(sc.nextLine());
								System.out.println("Enter State : ");
								store.setState(sc.nextLine());
								System.out.println("Enter Pin code: ");
								store.setPincode(sc.nextLine());
								System.out.println(ad.modifyStore(store));
								break;
							} else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD005":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								FoodBean food = new FoodBean();
								System.out.println("Enter Store ID : ");
								food.setStoreId(sc.nextLine());
								System.out.println("Enter Food ID : ");
								food.setFoodId(sc.nextLine());
								System.out.println("Enter size : ");
								food.setFoodSize(sc.nextLine());
								System.out.println("Enter Name : ");
								food.setName(sc.nextLine());
								System.out.println("Enter Type : ");
								food.setType(sc.nextLine());
								System.out.println("Enter Price : ");
								food.setPrice(sc.nextLine());
								System.out.println("Enter Quantity : ");
								food.setQuantity(sc.nextInt());
								System.out.println(ad.addFood(food));
								break;
							} else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD006":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								System.out.println("Enter Store ID : ");
								String sid = sc.nextLine();
								System.out.println("Enter Food ID : ");
								String fid = sc.nextLine();
								System.out.println(ad.removeFood(sid, fid));
								break;
							} else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD007":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								System.out.println("Enter Food ID : ");
								String fid = sc.nextLine();
								System.out.println(ad.viewFood(fid));
								break;
							} else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD008":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								FoodBean food = new FoodBean();
								System.out.println("Enter Store ID : ");
								food.setStoreId(sc.nextLine());
								System.out.println("Enter Food ID : ");
								food.setFoodId(sc.nextLine());
								System.out.println("Enter size : ");
								food.setFoodSize(sc.nextLine());
								System.out.println("Enter Name : ");
								food.setName(sc.nextLine());
								System.out.println("Enter Type : ");
								food.setType(sc.nextLine());
								System.out.println("Enter Price : ");
								food.setPrice(sc.nextLine());
								System.out.println("Enter Quantity : ");
								food.setQuantity(sc.nextInt());
								System.out.println(ad.modifyFood(food));
								break;
							}
							else {
								System.out.println("Invalid Pin");
								break;
							}
						case "AD009":
							System.out.println("Enter pin:");
							pass = sc.nextInt();
							if (pass == 0000) {
								sc.nextLine();
								System.out.println("Enter Order ID : ");
								String oid = sc.nextLine();
								System.out.println(ad.changeOrderStatus(oid));
								break;
								
							} else {
								System.out.println("Invalid Pin");
								break;
							}
					}
					}
					}
					}
	}
	}

