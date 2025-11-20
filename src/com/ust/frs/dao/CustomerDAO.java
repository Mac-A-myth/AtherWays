package com.ust.frs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.ReservationBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.service.Customer;
import com.ust.frs.util.DBUtil;

public class CustomerDAO implements Customer {
    
    private Connection con = DBUtil.getDBConnection();
    private PreparedStatement ps;

    @Override
    public ArrayList<ScheduleBean> viewScheduleByRoute(String source, String destination, String date) {
        ArrayList<ScheduleBean> scheduleList = new ArrayList<>();
        
        // Convert date to day of week to check available days
        String dayOfWeek = getDayOfWeekFromDate(date);
        
        String query = "SELECT s.*, f.FlightName, r.Source, r.Destination, r.Fare, " +
                      "(f.Reservationcapacity - COALESCE(SUM(CASE WHEN res.Journeydate = ? AND res.Bookingstatus = 1 THEN res.Noofseats ELSE 0 END), 0)) as AvailableSeats " +
                      "FROM frs_tbl_schedule s " +
                      "JOIN frs_tbl_flight f ON s.Flightid = f.Flightid " +
                      "JOIN frs_tbl_route r ON s.Routeid = r.Routeid " +
                      "LEFT JOIN frs_tbl_reservation res ON s.Scheduleid = res.Scheduleid " +
                      "WHERE r.Source = ? AND r.Destination = ? AND s.AvailableDays LIKE ? " +
                      "GROUP BY s.Scheduleid, f.FlightName, r.Source, r.Destination, r.Fare, f.Reservationcapacity " +
                      "HAVING AvailableSeats > 0 " +
                      "ORDER BY s.DepartureTime";
        
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, source);
            ps.setString(3, destination);
            ps.setString(4, "%" + dayOfWeek + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ScheduleBean schedule = new ScheduleBean();
                schedule.setScheduleID(rs.getString("Scheduleid"));
                schedule.setFlightID(rs.getString("Flightid"));
                schedule.setRouteID(rs.getString("Routeid"));
                schedule.setTravelDuration(rs.getInt("TravelDuration"));
                schedule.setAvailableDays(rs.getString("AvailableDays"));
                schedule.setDepartureTime(rs.getString("DepartureTime"));
                schedule.setFlightName(rs.getString("FlightName"));
                schedule.setSource(rs.getString("Source"));
                schedule.setDestination(rs.getString("Destination"));
                schedule.setFare(rs.getDouble("Fare"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                
                scheduleList.add(schedule);
            }
            
        } catch (SQLException e) {
            System.out.println("Error in viewScheduleByRoute: " + e.getMessage());
        }
        
        return scheduleList;
    }

    @Override
    public boolean cancelTicket(String reservationId) {
        try {
            con.setAutoCommit(false);
            
            // First, get the reservation details to calculate seat release
            ReservationBean reservation = getReservationById(reservationId);
            if (reservation == null) {
                return false;
            }
            
            // Update reservation status to 0 (cancelled)
            ps = con.prepareStatement("UPDATE frs_tbl_reservation SET Bookingstatus = 0 WHERE Reservationid = ?");
            ps.setString(1, reservationId);
            int rowsUpdated = ps.executeUpdate();
            
            if (rowsUpdated > 0) {
                // Delete associated passengers
                ps = con.prepareStatement("DELETE FROM frs_tbl_passenger WHERE Reservationid = ?");
                ps.setString(1, reservationId);
                ps.executeUpdate();
                
                con.commit();
                return true;
            }
            
            con.rollback();
            return false;
            
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error in cancelTicket: " + e.getMessage());
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String reserveTicket(ReservationBean reservationBean, ArrayList<PassengerBean> passengers) {
        try {
            con.setAutoCommit(false);
            
            // Check if enough seats are available
            if (!checkSeatAvailability(reservationBean.getScheduleID(), reservationBean.getJourneyDate(), passengers.size())) {
                return "Not enough seats available";
            }
            
            // Generate Reservation ID
            String reservationId = generateReservationId(reservationBean.getScheduleID());
            reservationBean.setReservationID(reservationId);
            
            // Calculate total fare
            double totalFare = calculateTotalFare(reservationBean.getScheduleID(), passengers.size());
            reservationBean.setTotalFare(totalFare);
            
            // Set booking status to 1 (confirmed)
            reservationBean.setBookingStatus(1);
            
            // Insert reservation
            String reservationQuery = "INSERT INTO frs_tbl_reservation (Reservationid, Userid, Scheduleid, Reservationtype, Bookingdate, Journeydate, Noofseats, Totalfare, Bookingstatus) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            ps = con.prepareStatement(reservationQuery);
            ps.setString(1, reservationBean.getReservationID());
            ps.setString(2, reservationBean.getUserID());
            ps.setString(3, reservationBean.getScheduleID());
            ps.setString(4, reservationBean.getReservationType());
            ps.setString(5, reservationBean.getBookingDate());
            ps.setString(6, reservationBean.getJourneyDate());
            ps.setInt(7, passengers.size());
            ps.setDouble(8, reservationBean.getTotalFare());
            ps.setInt(9, reservationBean.getBookingStatus());
            
            int reservationResult = ps.executeUpdate();
            
            if (reservationResult > 0) {
                // Insert passengers
                for (PassengerBean passenger : passengers) {
                    passenger.setReservationID(reservationId);
                    if (!insertPassenger(passenger)) {
                        con.rollback();
                        return "Failed to add passengers";
                    }
                }
                
                con.commit();
                return reservationId; // Return reservation ID on success
            } else {
                con.rollback();
                return "Reservation failed";
            }
            
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error in reserveTicket: " + e.getMessage());
            return "Error: " + e.getMessage();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<ReservationBean, ArrayList<PassengerBean>> viewTicket(String reservationId) {
        Map<ReservationBean, ArrayList<PassengerBean>> ticketDetails = new HashMap<>();
        
        try {
            // Get reservation details
            ReservationBean reservation = getReservationById(reservationId);
            if (reservation == null) {
                return ticketDetails;
            }
            
            // Get passenger details
            ArrayList<PassengerBean> passengers = getPassengersByReservationId(reservationId);
            
            ticketDetails.put(reservation, passengers);
            
        } catch (SQLException e) {
            System.out.println("Error in viewTicket: " + e.getMessage());
        }
        
        return ticketDetails;
    }

    @Override
    public Map<ReservationBean, ArrayList<PassengerBean>> printTicket(String reservationId) {
        // For printing, we can return the same data as viewTicket
        // In a real application, this might format the data differently or generate a PDF
        return viewTicket(reservationId);
    }

    // Helper Methods
    
    private String getDayOfWeekFromDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateObj = format.parse(date);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
            return dayFormat.format(dateObj);
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return "";
        }
    }
    
    private boolean checkSeatAvailability(String scheduleId, String journeyDate, int requiredSeats) throws SQLException {
        String query = "SELECT f.Reservationcapacity - COALESCE(SUM(r.Noofseats), 0) as AvailableSeats " +
                      "FROM frs_tbl_schedule s " +
                      "JOIN frs_tbl_flight f ON s.Flightid = f.Flightid " +
                      "LEFT JOIN frs_tbl_reservation r ON s.Scheduleid = r.Scheduleid AND r.Journeydate = ? AND r.Bookingstatus = 1 " +
                      "WHERE s.Scheduleid = ? " +
                      "GROUP BY f.Reservationcapacity";
        
        ps = con.prepareStatement(query);
        ps.setString(1, journeyDate);
        ps.setString(2, scheduleId);
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int availableSeats = rs.getInt("AvailableSeats");
            return availableSeats >= requiredSeats;
        }
        
        return false;
    }
    
    private String generateReservationId(String scheduleId) throws SQLException {
        // Get source and destination from schedule to create reservation ID prefix
        String query = "SELECT r.Source, r.Destination FROM frs_tbl_schedule s " +
                      "JOIN frs_tbl_route r ON s.Routeid = r.Routeid " +
                      "WHERE s.Scheduleid = ?";
        
        ps = con.prepareStatement(query);
        ps.setString(1, scheduleId);
        ResultSet rs = ps.executeQuery();
        
        String sourcePrefix = "";
        String destPrefix = "";
        
        if (rs.next()) {
            sourcePrefix = rs.getString("Source").substring(0, 2).toUpperCase();
            destPrefix = rs.getString("Destination").substring(0, 2).toUpperCase();
        }
        
        String prefix = sourcePrefix + destPrefix;
        
        // Get the next reservation number
        Statement stmt = con.createStatement();
        ResultSet countRs = stmt.executeQuery(
            "SELECT MAX(CAST(SUBSTRING(Reservationid, 5) AS SIGNED)) FROM frs_tbl_reservation WHERE Reservationid LIKE '" + prefix + "%'"
        );
        
        int reservationCount = 1000;
        if (countRs.next()) {
            int maxCount = countRs.getInt(1);
            if (maxCount > 0) {
                reservationCount = maxCount + 1;
            }
        }
        
        return prefix + String.format("%04d", reservationCount);
    }
    
    private double calculateTotalFare(String scheduleId, int numberOfSeats) throws SQLException {
        String query = "SELECT r.Fare, r.Distance FROM frs_tbl_schedule s " +
                      "JOIN frs_tbl_route r ON s.Routeid = r.Routeid " +
                      "WHERE s.Scheduleid = ?";
        
        ps = con.prepareStatement(query);
        ps.setString(1, scheduleId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            double farePerMile = rs.getDouble("Fare");
            int distance = rs.getInt("Distance");
            return farePerMile * distance * numberOfSeats;
        }
        
        return 0.0;
    }
    
    private boolean insertPassenger(PassengerBean passenger) throws SQLException {
        String query = "INSERT INTO frs_tbl_passenger (Reservationid, Name, Gender, Age, Seatno) VALUES (?, ?, ?, ?, ?)";
        
        ps = con.prepareStatement(query);
        ps.setString(1, passenger.getReservationID());
        ps.setString(2, passenger.getName());
        ps.setString(3, passenger.getGender());
        ps.setString(4, passenger.getAge());
        ps.setString(5, passenger.getSeatNo());
        
        return ps.executeUpdate() > 0;
    }
    
    private ReservationBean getReservationById(String reservationId) throws SQLException {
        String query = "SELECT r.*, s.Source, s.Destination, f.FlightName " +
                      "FROM frs_tbl_reservation res " +
                      "JOIN frs_tbl_schedule sch ON res.Scheduleid = sch.Scheduleid " +
                      "JOIN frs_tbl_route s ON sch.Routeid = s.Routeid " +
                      "JOIN frs_tbl_flight f ON sch.Flightid = f.Flightid " +
                      "WHERE res.Reservationid = ?";
        
        ps = con.prepareStatement(query);
        ps.setString(1, reservationId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            ReservationBean reservation = new ReservationBean();
            reservation.setReservationID(rs.getString("Reservationid"));
            reservation.setUserID(rs.getString("Userid"));
            reservation.setScheduleID(rs.getString("Scheduleid"));
            reservation.setReservationType(rs.getString("Reservationtype"));
            reservation.setBookingDate(rs.getString("Bookingdate"));
            reservation.setJourneyDate(rs.getString("Journeydate"));
            reservation.setNoOfSeats(rs.getInt("Noofseats"));
            reservation.setTotalFare(rs.getDouble("Totalfare"));
            reservation.setBookingStatus(rs.getInt("Bookingstatus"));
            // Additional info for display
            reservation.setSource(rs.getString("Source"));
            reservation.setDestination(rs.getString("Destination"));
            reservation.setFlightName(rs.getString("FlightName"));
            return reservation;
        }
        
        return null;
    }
    
    private ArrayList<PassengerBean> getPassengersByReservationId(String reservationId) throws SQLException {
        ArrayList<PassengerBean> passengers = new ArrayList<>();
        
        String query = "SELECT * FROM frs_tbl_passenger WHERE Reservationid = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, reservationId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            PassengerBean passenger = new PassengerBean();
            passenger.setReservationID(rs.getString("Reservationid"));
            passenger.setName(rs.getString("Name"));
            passenger.setGender(rs.getString("Gender"));
            passenger.setAge(rs.getString("Age"));
            passenger.setSeatNo(rs.getString("Seatno"));
            passengers.add(passenger);
        }
        
        return passengers;
    }
    
    // Additional helper method to get user reservations
    public ArrayList<ReservationBean> getUserReservations(String userId) {
        ArrayList<ReservationBean> reservations = new ArrayList<>();
        
        String query = "SELECT r.*, s.Source, s.Destination, f.FlightName " +
                      "FROM frs_tbl_reservation r " +
                      "JOIN frs_tbl_schedule sch ON r.Scheduleid = sch.Scheduleid " +
                      "JOIN frs_tbl_route s ON sch.Routeid = s.Routeid " +
                      "JOIN frs_tbl_flight f ON sch.Flightid = f.Flightid " +
                      "WHERE r.Userid = ? ORDER BY r.Bookingdate DESC";
        
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationID(rs.getString("Reservationid"));
                reservation.setUserID(rs.getString("Userid"));
                reservation.setScheduleID(rs.getString("Scheduleid"));
                reservation.setReservationType(rs.getString("Reservationtype"));
                reservation.setBookingDate(rs.getString("Bookingdate"));
                reservation.setJourneyDate(rs.getString("Journeydate"));
                reservation.setNoOfSeats(rs.getInt("Noofseats"));
                reservation.setTotalFare(rs.getDouble("Totalfare"));
                reservation.setBookingStatus(rs.getInt("Bookingstatus"));
                reservation.setSource(rs.getString("Source"));
                reservation.setDestination(rs.getString("Destination"));
                reservation.setFlightName(rs.getString("FlightName"));
                reservations.add(reservation);
            }
            
        } catch (SQLException e) {
            System.out.println("Error in getUserReservations: " + e.getMessage());
        }
        
        return reservations;
    }
    
    // Method to check if user exists
    public boolean userExists(String userId) {
        String query = "SELECT COUNT(*) FROM frs_tbl_user WHERE Userid = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error in userExists: " + e.getMessage());
        }
        return false;
    }
    
    // Close connection method
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
