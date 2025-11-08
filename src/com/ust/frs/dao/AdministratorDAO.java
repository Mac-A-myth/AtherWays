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
    
    @Override
    public String addFlight(FlightBean flightBean) {
        // Check if flight already exists
        for (FlightBean flight : DataStructure.getAllFlights()) {
            if (flight.getFlightID().equals(flightBean.getFlightID())) {
                return "Flight with ID " + flightBean.getFlightID() + " already exists!";
            }
        }
        DataStructure.addFlight(flightBean);
        return "Flight added successfully!";
    }

    @Override
    public boolean modifyFlight(FlightBean flightBean) {
        ArrayList<FlightBean> flights = DataStructure.flights;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightID().equals(flightBean.getFlightID())) {
                flights.set(i, flightBean);
                return true;
            }
        }
        return false;
    }

    @Override
    public int removeFlight(ArrayList<String> flightIDs) {
        int count = 0;
        Iterator<FlightBean> itr = DataStructure.flights.iterator();
        while (itr.hasNext()) {
            FlightBean flight = itr.next();
            if (flightIDs.contains(flight.getFlightID())) {
                itr.remove();
                count++;
            }
        }
        return count;
    }

    @Override
    public String addSchedule(ScheduleBean scheduleBean) {
        // Check if schedule already exists
        for (ScheduleBean schedule : DataStructure.getAllSchedules()) {
            if (schedule.getScheduleID().equals(scheduleBean.getScheduleID())) {
                return "Schedule with ID " + scheduleBean.getScheduleID() + " already exists!";
            }
        }
        DataStructure.addSchedule(scheduleBean);
        return "Schedule added successfully!";
    }

    @Override
    public boolean modifySchedule(ScheduleBean scheduleBean) {
        ArrayList<ScheduleBean> schedules = DataStructure.schedules;
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getScheduleID().equals(scheduleBean.getScheduleID())) {
                schedules.set(i, scheduleBean);
                return true;
            }
        }
        return false;
    }

    @Override
    public int removeSchedule(ArrayList<String> scheduleIds) {
        int count = 0;
        Iterator<ScheduleBean> itr = DataStructure.schedules.iterator();
        while (itr.hasNext()) {
            ScheduleBean schedule = itr.next();
            if (scheduleIds.contains(schedule.getScheduleID())) {
                itr.remove();
                count++;
            }
        }
        return count;
    }

    @Override
    public String addRoute(RouteBean routeBean) {
        // Check if route already exists
        for (RouteBean route : DataStructure.getAllRoutes()) {
            if (route.getRouteID().equals(routeBean.getRouteID())) {
                return "Route with ID " + routeBean.getRouteID() + " already exists!";
            }
        }
        DataStructure.addRoute(routeBean);
        return "Route added successfully!";
    }

    @Override
    public boolean modifyRoute(RouteBean routeBean) {
        ArrayList<RouteBean> routes = DataStructure.routes;
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getRouteID().equals(routeBean.getRouteID())) {
                routes.set(i, routeBean);
                return true;
            }
        }
        return false;
    }

    @Override
    public int removeRoute(ArrayList<String> routeIds) {
        int count = 0;
        Iterator<RouteBean> itr = DataStructure.routes.iterator();
        while (itr.hasNext()) {
            RouteBean route = itr.next();
            if (routeIds.contains(route.getRouteID())) {
                itr.remove();
                count++;
            }
        }
        return count;
    }

    @Override
    public FlightBean viewByFlightId(String flightId) {
        for (FlightBean flight : DataStructure.getAllFlights()) {
            if (flight.getFlightID().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }

    @Override
    public RouteBean viewByRouteId(String routeId) {
        for (RouteBean route : DataStructure.getAllRoutes()) {
            if (route.getRouteID().equals(routeId)) {
                return route;
            }
        }
        return null;
    }

    @Override
    public ArrayList<FlightBean> viewByAllFlights() {
        return DataStructure.getAllFlights();
    }

    @Override
    public ArrayList<RouteBean> viewByAllRoute() {
        return DataStructure.getAllRoutes();
    }

    @Override
    public ArrayList<ScheduleBean> viewByAllSchedule() {
        return DataStructure.getAllSchedules();
    }

    @Override
    public ScheduleBean viewByScheduleId(String scheduleId) {
        for (ScheduleBean schedule : DataStructure.getAllSchedules()) {
            if (schedule.getScheduleID().equals(scheduleId)) {
                return schedule;
            }
        }
        return null;
    }

    @Override
    public ArrayList<PassengerBean> viewPassengersByFlight(String scheduleId) {
        ArrayList<PassengerBean> passengersOnFlight = new ArrayList<>();
        for (PassengerBean passenger : DataStructure.getAllPassengers()) {
            // Find reservation for this passenger
            for (ReservationBean reservation : DataStructure.getAllReservations()) {
                if (reservation.getReservationID().equals(passenger.getReservationID()) &&
                    reservation.getScheduleID().equals(scheduleId)) {
                    passengersOnFlight.add(passenger);
                    break;
                }
            }
        }
        return passengersOnFlight;
    }
}