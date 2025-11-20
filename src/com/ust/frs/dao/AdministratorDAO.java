package com.ust.frs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.ReservationBean;
import com.ust.frs.bean.RouteBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.service.*;
import com.ust.frs.util.DBUtil;

public class AdministratorDAO implements Administrator {
    Connection con = DBUtil.getDBConnection();
    PreparedStatement ps;

    @Override
    public String addFlight(FlightBean flightBean) {
        int i = 0;
        int flightcount = 0;
        String prefix = flightBean.getFlightName().substring(0, 2).toUpperCase();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(CAST(SUBSTRING(flightID, 3) AS SIGNED)) FROM frs_tbl_flight WHERE flightID LIKE '" + prefix + "%'");

            if (rs.next()) {
                flightcount = rs.getInt(1);
            }
            flightcount++;

            String flightID = prefix + String.format("%04d", flightcount); // Format with leading zeros

            flightBean.setFlightID(flightID);

            ps = con.prepareStatement("INSERT INTO frs_tbl_flight (flightID, flightName, seatingCapacity, reservationCapacity) VALUES (?, ?, ?, ?)");
            ps.setString(1, flightBean.getFlightID());
            ps.setString(2, flightBean.getFlightName());
            ps.setInt(3, flightBean.getSeatingCapacity());
            ps.setInt(4, flightBean.getReservationCapacity());

            i = ps.executeUpdate();

            return Integer.toString(i) + " Rows Updated";
        } catch (SQLException sql) {
            System.out.println(sql);
        }

        return "Fail";
    }

    @Override
    public boolean modifyFlight(FlightBean flightBean) {
        try {
            // First get the current flight to compare names
            FlightBean currentFlight = viewByFlightId(flightBean.getFlightID());
            if (currentFlight == null) {
                return false; // Flight not found
            }

            String newFlightID = flightBean.getFlightID();

            // Check if flight name has changed
            if (!currentFlight.getFlightName().equals(flightBean.getFlightName())) {
                // Generate new flight ID based on new name
                String prefix = flightBean.getFlightName().substring(0, 2).toUpperCase();

                // Extract the numeric part from the old flight ID
                String oldNumericPart = flightBean.getFlightID().substring(2);

                // Create new flight ID with new prefix and old numeric part
                newFlightID = prefix + oldNumericPart;

                // Update flight ID in the bean
                flightBean.setFlightID(newFlightID);
            }

            // Update the flight
            ps = con.prepareStatement("UPDATE frs_tbl_flight SET FlightId=?, FlightName=?, Seatingcapacity=?, Reservationcapacity=? WHERE FlightId=?");
            ps.setString(1, newFlightID);
            ps.setString(2, flightBean.getFlightName());
            ps.setInt(3, flightBean.getSeatingCapacity());
            ps.setInt(4, flightBean.getReservationCapacity());
            ps.setString(5, currentFlight.getFlightID()); // Use original ID for WHERE clause

            int rowsUpdated = ps.executeUpdate();

            // If flight ID changed, update all related schedules
            if (!currentFlight.getFlightID().equals(newFlightID)) {
                updateFlightIdInSchedules(currentFlight.getFlightID(), newFlightID);
            }

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to update flight ID in schedules
    private void updateFlightIdInSchedules(String oldFlightId, String newFlightId) throws SQLException {
        ps = con.prepareStatement("UPDATE frs_tbl_schedule SET FlightId=? WHERE FlightId=?");
        ps.setString(1, newFlightId);
        ps.setString(2, oldFlightId);
        ps.executeUpdate();
    }

    @Override
    public int removeFlight(ArrayList<String> flightIDs) {
        int count = 0;
        Iterator<String> itr = flightIDs.iterator();
        
        try {
            con.setAutoCommit(false); // Start transaction
            
            while (itr.hasNext()) {
                String flightId = itr.next();
                int i = 0;

                // First delete related schedules
                ps = con.prepareStatement("DELETE FROM frs_tbl_schedule WHERE FlightId=?");
                ps.setString(1, flightId);
                i = ps.executeUpdate();
                
                if (i >= 0) {
                    System.out.println("Deleted " + i + " schedule(s) for FlightID: " + flightId);
                }

                // Then delete the flight
                ps = con.prepareStatement("DELETE FROM frs_tbl_flight WHERE FlightId=?");
                ps.setString(1, flightId);
                i = ps.executeUpdate();

                if (i == 0) {
                    System.out.println("Invalid flight ID: " + flightId);
                } else {
                    System.out.println("Successfully Deleted Flight: " + flightId);
                    count++;
                }
            }
            
            con.commit(); // Commit transaction
            con.setAutoCommit(true); // Reset auto-commit
            
        } catch (SQLException e) {
            try {
                con.rollback(); // Rollback in case of error
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        
        return count;
    }

    @Override
    public String addSchedule(ScheduleBean scheduleBean) {
        int i = 0;
        int scheduleCount = 0;
        String prefix = "SCH";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(CAST(SUBSTRING(ScheduleId, 4) AS SIGNED)) FROM frs_tbl_schedule WHERE ScheduleId LIKE '" + prefix + "%'");

            if (rs.next()) {
                scheduleCount = rs.getInt(1);
            }
            scheduleCount++;

            String scheduleID = prefix + String.format("%05d", scheduleCount); // SCH00001 format
            scheduleBean.setScheduleID(scheduleID);

            ps = con.prepareStatement("INSERT INTO frs_tbl_schedule (ScheduleId, FlightId, RouteId, TravelDuration, AvailableDays, DepartureTime) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, scheduleBean.getScheduleID());
            ps.setString(2, scheduleBean.getFlightID());
            ps.setString(3, scheduleBean.getRouteID());
            ps.setInt(4, scheduleBean.getTravelDuration());
            ps.setString(5, scheduleBean.getAvailableDays());
            ps.setString(6, scheduleBean.getDepartureTime());

            i = ps.executeUpdate();

            return i + " Schedule added successfully!";
        } catch (SQLException sql) {
            System.out.println(sql);
            return "Failed to add schedule";
        }
    }

    @Override
    public boolean modifySchedule(ScheduleBean scheduleBean) {
        try {
            ps = con.prepareStatement("UPDATE frs_tbl_schedule SET FlightId=?, RouteId=?, TravelDuration=?, AvailableDays=?, DepartureTime=? WHERE ScheduleId=?");
            ps.setString(1, scheduleBean.getFlightID());
            ps.setString(2, scheduleBean.getRouteID());
            ps.setInt(3, scheduleBean.getTravelDuration());
            ps.setString(4, scheduleBean.getAvailableDays());
            ps.setString(5, scheduleBean.getDepartureTime());
            ps.setString(6, scheduleBean.getScheduleID());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int removeSchedule(ArrayList<String> scheduleIds) {
        int count = 0;
        Iterator<String> itr = scheduleIds.iterator();

        try {
            con.setAutoCommit(false); // Start transaction

            while (itr.hasNext()) {
                String scheduleId = itr.next();
                int i = 0;

                // First check if there are any reservations for this schedule
                ps = con.prepareStatement("SELECT COUNT(*) FROM frs_tbl_reservation WHERE ScheduleId=?");
                ps.setString(1, scheduleId);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Cannot delete schedule " + scheduleId + " because it has existing reservations.");
                    continue; // Skip this schedule
                }

                // Delete the schedule
                ps = con.prepareStatement("DELETE FROM frs_tbl_schedule WHERE ScheduleId=?");
                ps.setString(1, scheduleId);
                i = ps.executeUpdate();

                if (i == 0) {
                    System.out.println("Invalid schedule ID: " + scheduleId);
                } else {
                    System.out.println("Successfully Deleted Schedule: " + scheduleId);
                    count++;
                }
            }

            con.commit(); // Commit transaction
            con.setAutoCommit(true); // Reset auto-commit

        } catch (SQLException e) {
            try {
                con.rollback(); // Rollback in case of error
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public String addRoute(RouteBean routeBean) {
        int i = 0;

        try {
            // Get the maximum numeric part for routes with similar prefix
            String sourcePrefix = routeBean.getSource().substring(0, 2).toUpperCase();
            String destPrefix = routeBean.getDestination().substring(0, 2).toUpperCase();
            String prefix = sourcePrefix + destPrefix;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(CAST(SUBSTRING(RouteId, 5) AS SIGNED)) FROM frs_tbl_route WHERE RouteId LIKE '" + prefix + "%'");

            int routeCount = 1000; // Default starting number
            if (rs.next()) {
                int maxCount = rs.getInt(1);
                if (maxCount > 0) {
                    routeCount = maxCount + 1;
                }
            }

            String routeID = prefix + String.format("%04d", routeCount);
            routeBean.setRouteID(routeID);

            ps = con.prepareStatement("INSERT INTO frs_tbl_route VALUES(?,?,?,?,?)");
            ps.setString(1, routeBean.getRouteID());
            ps.setString(2, routeBean.getSource());
            ps.setString(3, routeBean.getDestination());
            ps.setInt(4, routeBean.getDistance());
            ps.setDouble(5, routeBean.getFare());
            i = ps.executeUpdate();

            return Integer.toString(i) + " Rows Updated";

        } catch (SQLException sql) {
            System.out.println(sql);
            return "Fail";
        }
    }

    @Override
    public boolean modifyRoute(RouteBean routeBean) {
        try {
            // First get the current route to compare source/destination
            RouteBean currentRoute = viewByRouteId(routeBean.getRouteID());
            if (currentRoute == null) {
                return false; // Route not found
            }

            String newRouteID = routeBean.getRouteID();

            // Check if source or destination has changed
            boolean sourceChanged = !currentRoute.getSource().equals(routeBean.getSource());
            boolean destinationChanged = !currentRoute.getDestination().equals(routeBean.getDestination());

            if (sourceChanged || destinationChanged) {
                // Generate new route ID based on new source/destination
                String sourcePrefix = routeBean.getSource().substring(0, 2).toUpperCase();
                String destPrefix = routeBean.getDestination().substring(0, 2).toUpperCase();

                // Extract the numeric part from the old route ID
                String oldNumericPart = routeBean.getRouteID().substring(4); // After 4 letters

                // Create new route ID with new prefixes and old numeric part
                newRouteID = sourcePrefix + destPrefix + oldNumericPart;

                // Update route ID in the bean
                routeBean.setRouteID(newRouteID);
            }

            // Update the route
            ps = con.prepareStatement("UPDATE frs_tbl_route SET RouteId=?, Source=?, Destination=?, Distance=?, Fare=? WHERE RouteId=?");
            ps.setString(1, newRouteID);
            ps.setString(2, routeBean.getSource());
            ps.setString(3, routeBean.getDestination());
            ps.setInt(4, routeBean.getDistance());
            ps.setDouble(5, routeBean.getFare());
            ps.setString(6, currentRoute.getRouteID()); // Use original ID for WHERE clause

            int rowsUpdated = ps.executeUpdate();

            // If route ID changed, update all related schedules
            if (!currentRoute.getRouteID().equals(newRouteID)) {
                updateRouteIdInSchedules(currentRoute.getRouteID(), newRouteID);
            }

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to update route ID in schedules
    private void updateRouteIdInSchedules(String oldRouteId, String newRouteId) throws SQLException {
        ps = con.prepareStatement("UPDATE frs_tbl_schedule SET RouteId=? WHERE RouteId=?");
        ps.setString(1, newRouteId);
        ps.setString(2, oldRouteId);
        ps.executeUpdate();
    }

    @Override
    public int removeRoute(ArrayList<String> routeIds) {
        int count = 0;
        Iterator<String> itr = routeIds.iterator();

        try {
            con.setAutoCommit(false); // Start transaction

            while (itr.hasNext()) {
                String routeId = itr.next();
                int i = 0;

                // First check if there are any schedules for this route
                ps = con.prepareStatement("SELECT COUNT(*) FROM frs_tbl_schedule WHERE RouteId=?");
                ps.setString(1, routeId);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Cannot delete route " + routeId + " because it has existing schedules.");
                    continue; // Skip this route
                }

                // Delete the route
                ps = con.prepareStatement("DELETE FROM frs_tbl_route WHERE RouteId=?");
                ps.setString(1, routeId);
                i = ps.executeUpdate();

                if (i == 0) {
                    System.out.println("Invalid Route ID: " + routeId);
                } else {
                    System.out.println("Successfully Deleted Route: " + routeId);
                    count++;
                }
            }

            con.commit(); // Commit transaction
            con.setAutoCommit(true); // Reset auto-commit

        } catch (SQLException e) {
            try {
                con.rollback(); // Rollback in case of error
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public FlightBean viewByFlightId(String flightId) {
        FlightBean flight = null;
        String query = "SELECT * FROM frs_tbl_flight WHERE FlightId = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, flightId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flight = new FlightBean();
                    flight.setFlightID(rs.getString("FlightId"));
                    flight.setFlightName(rs.getString("FlightName"));
                    flight.setSeatingCapacity(rs.getInt("Seatingcapacity"));
                    flight.setReservationCapacity(rs.getInt("Reservationcapacity"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in viewByFlightId: " + e.getMessage());
        }

        return flight;
    }

    @Override
    public RouteBean viewByRouteId(String routeId) {
        RouteBean route = null;
        String query = "SELECT * FROM frs_tbl_route WHERE Routeid = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, routeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    route = new RouteBean();
                    route.setRouteID(rs.getString("Routeid"));
                    route.setSource(rs.getString("Source"));
                    route.setDestination(rs.getString("Destination"));
                    route.setDistance(rs.getInt("Distance"));
                    route.setFare(rs.getInt("Fare"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in viewByRouteId: " + e.getMessage());
        }

        return route;
    }

    @Override
    public ArrayList<FlightBean> viewByAllFlights() {
        ArrayList<FlightBean> flightList = new ArrayList<>();
        String query = "SELECT * FROM frs_tbl_flight";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                FlightBean flight = new FlightBean();
                flight.setFlightID(rs.getString("FlightId"));
                flight.setFlightName(rs.getString("FlightName"));
                flight.setSeatingCapacity(rs.getInt("Seatingcapacity"));
                flight.setReservationCapacity(rs.getInt("Reservationcapacity"));
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error in viewByAllFlights: " + e.getMessage());
        }

        return flightList;
    }

    @Override
    public ArrayList<RouteBean> viewByAllRoute() {
        ArrayList<RouteBean> routeList = new ArrayList<>();
        String query = "SELECT * FROM frs_tbl_route";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                RouteBean route = new RouteBean();
                route.setRouteID(rs.getString("Routeid"));
                route.setSource(rs.getString("Source"));
                route.setDestination(rs.getString("Destination"));
                route.setDistance(rs.getInt("Distance"));
                route.setFare(rs.getInt("Fare"));
                routeList.add(route);
            }
        } catch (SQLException e) {
            System.out.println("Error in viewByAllRoute: " + e.getMessage());
        }

        return routeList;
    }

    @Override
    public ArrayList<ScheduleBean> viewByAllSchedule() {
        ArrayList<ScheduleBean> scheduleList = new ArrayList<>();
        String query = "SELECT s.*, f.FlightName, r.Source, r.Destination " +
                      "FROM frs_tbl_schedule s " +
                      "JOIN frs_tbl_flight f ON s.FlightId = f.FlightId " +
                      "JOIN frs_tbl_route r ON s.RouteId = r.RouteId";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ScheduleBean schedule = new ScheduleBean();
                schedule.setScheduleID(rs.getString("Scheduleid"));
                schedule.setFlightID(rs.getString("Flightid"));
                schedule.setRouteID(rs.getString("Routeid"));
                schedule.setTravelDuration(rs.getInt("TravelDuration"));
                schedule.setAvailableDays(rs.getString("AvailableDays"));
                schedule.setDepartureTime(rs.getString("DepartureTime"));
                // Additional info for display
                schedule.setFlightName(rs.getString("FlightName"));
                schedule.setSource(rs.getString("Source"));
                schedule.setDestination(rs.getString("Destination"));
                scheduleList.add(schedule);
            }
        } catch (SQLException e) {
            System.out.println("Error in viewByAllSchedule: " + e.getMessage());
        }

        return scheduleList;
    }

    @Override
    public ScheduleBean viewByScheduleId(String scheduleId) {
        ScheduleBean schedule = null;
        String query = "SELECT s.*, f.FlightName, r.Source, r.Destination " +
                      "FROM frs_tbl_schedule s " +
                      "JOIN frs_tbl_flight f ON s.FlightId = f.FlightId " +
                      "JOIN frs_tbl_route r ON s.RouteId = r.RouteId " +
                      "WHERE s.Scheduleid = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, scheduleId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    schedule = new ScheduleBean();
                    schedule.setScheduleID(rs.getString("Scheduleid"));
                    schedule.setFlightID(rs.getString("Flightid"));
                    schedule.setRouteID(rs.getString("Routeid"));
                    schedule.setTravelDuration(rs.getInt("TravelDuration"));
                    schedule.setAvailableDays(rs.getString("AvailableDays"));
                    schedule.setDepartureTime(rs.getString("DepartureTime"));
                    // Additional info for display
                    schedule.setFlightName(rs.getString("FlightName"));
                    schedule.setSource(rs.getString("Source"));
                    schedule.setDestination(rs.getString("Destination"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in viewByScheduleId: " + e.getMessage());
        }

        return schedule;
    }

    @Override
    public ArrayList<PassengerBean> viewPassengersByFlight(String scheduleId) {
        ArrayList<PassengerBean> passengersOnFlight = new ArrayList<>();
        String query = "SELECT p.* FROM frs_tbl_passenger p " +
                      "JOIN frs_tbl_reservation r ON p.Reservationid = r.Reservationid " +
                      "WHERE r.Scheduleid = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, scheduleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PassengerBean passenger = new PassengerBean();
                    passenger.setReservationID(rs.getString("Reservationid"));
                    passenger.setName(rs.getString("Name"));
                    passenger.setGender(rs.getString("Gender"));
                    passenger.setAge(rs.getString("Age"));
                    passenger.setSeatNo(rs.getString("Seatno"));
                    passengersOnFlight.add(passenger);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in viewPassengersByFlight: " + e.getMessage());
        }

        return passengersOnFlight;
    }

    // Additional helper method to check if flight exists
    public boolean flightExists(String flightId) {
        String query = "SELECT COUNT(*) FROM frs_tbl_flight WHERE FlightId = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, flightId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in flightExists: " + e.getMessage());
        }
        return false;
    }

    // Additional helper method to check if route exists
    public boolean routeExists(String routeId) {
        String query = "SELECT COUNT(*) FROM frs_tbl_route WHERE RouteId = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, routeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in routeExists: " + e.getMessage());
        }
        return false;
    }

    // Close connection method (should be called when done)
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
