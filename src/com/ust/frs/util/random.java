package com.ust.frs.service;

import com.ust.frs.bean.*;
import com.ust.frs.util.DataStructure;
import java.util.*;

public class CustomerServiceImpl implements Customer {
    
    @Override
    public ArrayList<ScheduleBean> viewScheduleByRoute(String source, String destination, Date date) {
        ArrayList<ScheduleBean> matchingSchedules = new ArrayList<>();
        
        for (ScheduleBean schedule : DataStructure.schedules) {
            RouteBean route = findRouteById(schedule.getRouteID());
            if (route != null && route.getSource().equalsIgnoreCase(source) && 
                route.getDestination().equalsIgnoreCase(destination)) {
                matchingSchedules.add(schedule);
            }
        }
        return matchingSchedules;
    }
    
    @Override
    public String reserveTicket(ReservationBean reservationBean, ArrayList<PassengerBean> passengers) {
        try {
            // Generate reservation ID
            String reservationID = "RS" + String.format("%04d", new Random().nextInt(10000));
            reservationBean.setReservationID(reservationID);
            reservationBean.setBookingStatus(1); // Confirmed
            
            // Calculate total fare
            double totalFare = calculateTotalFare(reservationBean);
            reservationBean.setTotalFare(totalFare);
            
            DataStructure.reservations.add(reservationBean);
            
            // Add passengers
            for (PassengerBean passenger : passengers) {
                passenger.setReservationID(reservationID);
                DataStructure.passengers.add(passenger);
            }
            
            return reservationID;
        } catch (Exception e) {
            return "FAIL";
        }
    }
    
    @Override
    public boolean cancelTicket(String reservationId) {
        for (ReservationBean reservation : DataStructure.reservations) {
            if (reservation.getReservationID().equals(reservationId)) {
                reservation.setBookingStatus(0); // Cancelled
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Map<ReservationBean, ArrayList<PassengerBean>> viewTicket(String reservationId) {
        Map<ReservationBean, ArrayList<PassengerBean>> ticket = new HashMap<>();
        ReservationBean reservation = null;
        
        // Find reservation
        for (ReservationBean res : DataStructure.reservations) {
            if (res.getReservationID().equals(reservationId)) {
                reservation = res;
                break;
            }
        }
        
        if (reservation != null) {
            // Find passengers for this reservation
            ArrayList<PassengerBean> passengerList = new ArrayList<>();
            for (PassengerBean passenger : DataStructure.passengers) {
                if (passenger.getReservationID().equals(reservationId)) {
                    passengerList.add(passenger);
                }
            }
            ticket.put(reservation, passengerList);
        }
        
        return ticket;
    }
    
    @Override
    public Map<ReservationBean, ArrayList<PassengerBean>> printTicket(String reservationId) {
        // For now, same as viewTicket. In real implementation, this would generate PDF/HTML
        return viewTicket(reservationId);
    }
    
    private RouteBean findRouteById(String routeId) {
        for (RouteBean route : DataStructure.routes) {
            if (route.getRouteID().equals(routeId)) {
                return route;
            }
        }
        return null;
    }
    
    private double calculateTotalFare(ReservationBean reservation) {
        // Simple calculation: base fare * number of seats
        ScheduleBean schedule = findScheduleById(reservation.getScheduleID());
        if (schedule != null) {
            RouteBean route = findRouteById(schedule.getRouteID());
            if (route != null) {
                return route.getFare() * reservation.getNoOfSeats();
            }
        }
        return 0.0;
    }
    
    private ScheduleBean findScheduleById(String scheduleId) {
        for (ScheduleBean schedule : DataStructure.schedules) {
            if (schedule.getScheduleID().equals(scheduleId)) {
                return schedule;
            }
        }
        return null;
    }
}
