package com.ust.frs.dao;

import java.util.ArrayList;
import java.util.Iterator;

import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.ReservationBean;
import com.ust.frs.bean.RouteBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.service.*;
import com.ust.frs.util.DataStructure;

public class AdministratorDAO implements Administrator {
	public DataStructure ds = new DataStructure();
	public DataStructure getDs() {
		return ds;
	}

	public void setDs(DataStructure ds) {
		this.ds = ds;
	}
	
	// Helper method to get reservations from DataStructure
	public ArrayList<ReservationBean> getReservation() {
		return ds.getReservation();
	}
	
	// Helper method to get passengers from DataStructure
	public ArrayList<PassengerBean> getPassenger() {
		return ds.getPassenger();
	}
	
	// Helper method to get all passengers
	public ArrayList<PassengerBean> viewAllPassengers() {
		return ds.getPassenger();
	}
	
	// Helper method to search passengers by ID or name
	public ArrayList<PassengerBean> searchPassengers(String searchTerm) {
		ArrayList<PassengerBean> result = new ArrayList<>();
		for (PassengerBean passenger : ds.getPassenger()) {
			if (passenger.getReservationID().toLowerCase().contains(searchTerm.toLowerCase()) ||
				passenger.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
				result.add(passenger);
			}
		}
		return result;
	}
	
	// Helper method to view passengers by route
	public ArrayList<PassengerBean> viewPassengersByRoute(String routeId) {
		ArrayList<PassengerBean> passengersByRoute = new ArrayList<>();
		
		// Find all schedules for this route
		ArrayList<String> scheduleIdsForRoute = new ArrayList<>();
		for (ScheduleBean schedule : ds.getSchedule()) {
			if (schedule.getRouteID().equals(routeId)) {
				scheduleIdsForRoute.add(schedule.getScheduleID());
			}
		}
		
		// Find all reservations for these schedules
		ArrayList<String> reservationIds = new ArrayList<>();
		for (ReservationBean reservation : ds.getReservation()) {
			if (scheduleIdsForRoute.contains(reservation.getScheduleID())) {
				reservationIds.add(reservation.getReservationID());
			}
		}
		
		// Find all passengers for these reservations
		for (PassengerBean passenger : ds.getPassenger()) {
			if (reservationIds.contains(passenger.getReservationID())) {
				passengersByRoute.add(passenger);
			}
		}
		
		return passengersByRoute;
	}

	@Override
	public ArrayList<PassengerBean> viewPassengersByFlight(String scheduleId) {
		ArrayList<PassengerBean> passengersByFlight = new ArrayList<>();
		
		// First, find the schedule to get the flight ID
		ScheduleBean targetSchedule = null;
		for (ScheduleBean schedule : ds.getSchedule()) {
			if (schedule.getScheduleID().equals(scheduleId)) {
				targetSchedule = schedule;
				break;
			}
		}
		
		if (targetSchedule == null) {
			return passengersByFlight; // Return empty list if schedule not found
		}
		
		String flightId = targetSchedule.getFlightID();
		
		// Find all schedules for this flight
		ArrayList<String> scheduleIdsForFlight = new ArrayList<>();
		for (ScheduleBean schedule : ds.getSchedule()) {
			if (schedule.getFlightID().equals(flightId)) {
				scheduleIdsForFlight.add(schedule.getScheduleID());
			}
		}
		
		// Find all reservations for these schedules
		ArrayList<String> reservationIds = new ArrayList<>();
		for (ReservationBean reservation : ds.getReservation()) {
			if (scheduleIdsForFlight.contains(reservation.getScheduleID())) {
				reservationIds.add(reservation.getReservationID());
			}
		}
		
		// Find all passengers for these reservations
		for (PassengerBean passenger : ds.getPassenger()) {
			if (reservationIds.contains(passenger.getReservationID())) {
				passengersByFlight.add(passenger);
			}
		}
		
		return passengersByFlight;
	}

	@Override
	public String addFlight(FlightBean flightBean) {
			ds.setFlight(flightBean);
		return "Flight added successfully!";
	}

	@Override
	public boolean modifyFlight(FlightBean flightBean) {
		Iterator<FlightBean> itr= ds.getFlight().iterator();
		while(itr.hasNext()) {
			FlightBean flight = itr.next();
			if(flight.getFlightID().equals(flightBean.getFlightID()))
			{
				itr.remove();
				ds.setFlight(flightBean);
				return true;
			}
		}
		return false;
	}

	@Override
	public int removeFlight(ArrayList<String> flightID) {
		int count = 0;
		for (String id : flightID) {
			Iterator<FlightBean> itr = ds.getFlight().iterator();
			while(itr.hasNext()) {
				if(itr.next().getFlightID().equals(id)) {
					itr.remove();
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public String addSchedule(ScheduleBean scheduleBean) {
		ds.setSchedule(scheduleBean);
		return "Schedule added successfully!";
	}

	@Override
	public boolean modifySchedule(ScheduleBean scheduleBean) {
		Iterator<ScheduleBean> itr= ds.getSchedule().iterator();
		while(itr.hasNext()) {
			ScheduleBean schedule = itr.next();
			if(schedule.getScheduleID().equals(scheduleBean.getScheduleID()))
			{
				itr.remove();
				ds.setSchedule(scheduleBean);
				return true;
			}
		}
		return false;
	}

	@Override
	public int removeSchedule(ArrayList<String> scheduleId) {
		int count = 0;
		for (String id : scheduleId) {
			Iterator<ScheduleBean> itr = ds.getSchedule().iterator();
			while(itr.hasNext()) {
				if(itr.next().getScheduleID().equals(id)) {
					itr.remove();
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public String addRoute(RouteBean routeBean) {
		ds.setRoute(routeBean);
		return "Route added successfully!";
	}

	@Override
	public boolean modifyRoute(RouteBean routeBean) {
		Iterator<RouteBean> itr = ds.getRoute().iterator();
		while(itr.hasNext()) {
			RouteBean route = itr.next();
			if(route.getRouteID().equals(routeBean.getRouteID())) {
				itr.remove();
				ds.setRoute(routeBean);
				return true;
			}
		}
		return false;
	}

	@Override
	public int removeRoute(ArrayList<String> routeId) {
		int count = 0;
		for (String id : routeId) {
			Iterator<RouteBean> itr = ds.getRoute().iterator();
			while(itr.hasNext()) {
				if(itr.next().getRouteID().equals(id)) {
					itr.remove();
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public FlightBean viewByFlightId(String flightId) {
		for (FlightBean flight : ds.getFlight()) {
			if(flight.getFlightID().equals(flightId)) {
				return flight;
			}
		}
		return null;
	}

	@Override
	public RouteBean viewByRouteId(String routeId) {
		for (RouteBean route : ds.getRoute()) {
			if(route.getRouteID().equals(routeId)) {
				return route;
			}
		}
		return null;
	}

	@Override
	public ArrayList<FlightBean> viewByAllFlights() {
		return (ArrayList<FlightBean>) ds.getFlight();	
	}

	@Override
	public ArrayList<RouteBean> viewByAllRoute() {
		return (ArrayList<RouteBean>) ds.getRoute();	
	}

	@Override
	public ArrayList<ScheduleBean> viewByAllSchedule() {
	   return (ArrayList<ScheduleBean>)ds.getSchedule();
	}

	@Override
	public ScheduleBean viewByScheduleId(String scheduleId) {
		for (ScheduleBean schedule : ds.getSchedule()) {
			if(schedule.getScheduleID().equals(scheduleId)) {
				return schedule;
			}
		}
		return null;
	}
}
