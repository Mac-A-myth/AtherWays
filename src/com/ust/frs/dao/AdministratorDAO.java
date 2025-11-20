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
    Connection con= DBUtil.getDBConnection();
    PreparedStatement ps ;
    
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
    	        
    	        String flightID = prefix + Integer.toString(flightcount);

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
    	try
    	{
    		ps=con.prepareStatement("update frs_tbl_flight set FlightName=? ,Seatingcapacity=?,Reservationcapacity=? where FlightId=?");
    		ps.setString(4,flightBean.getFlightID());
    		ps.setString(1, flightBean.getFlightName());
    		ps.setInt(2, flightBean.getSeatingCapacity());
    		ps.setInt(3, flightBean.getReservationCapacity());
    		ps.executeUpdate();
    		return true;
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
        return false;
    }

    @Override
    public int removeFlight(ArrayList<String> flightIDs) {
    	int count=0;
    	Iterator<String> itr = flightIDs.iterator();
    	while (itr.hasNext())
    	{
    		int i=0;
    		try
    		{
    			ps=con.prepareStatement("delete from frs_tbl_flight where FlightId=?");
    			ps.setString(1,itr.next());
    			i=ps.executeUpdate();
    			
    			if(i==0)
    			{
    				System.out.println("Invalid flight ID");
    			}
    			else
    			{
    				System.out.println("Successfully Deleted");
    			}
    			ps=con.prepareStatement("delete from frs_tbl_schedule where FlightId=?");
    			ps.setString(1,itr.next());
    			i=ps.executeUpdate();
    			if(i>0)
    			{
    				System.out.println("Deleted Schedule for the FlightID "+itr.next());
    			}
    			else
    			{
    				System.out.println("No FlightID found");
    			}
    		}
    		
    		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    	}
		return count;
    }

    @Override
    public String addSchedule(ScheduleBean scheduleBean) {
        
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
    	int i=0;
    	int routecount=1000;
    	String prefix = routeBean.getSource().substring(0, 2).toUpperCase();
    	prefix = prefix + routeBean.getDestination().substring(0, 2).toUpperCase();
    	prefix=prefix+Integer.toString(routecount);
    	routecount++;
    	routeBean.setRouteID(prefix);
    	try
    	{
    		ps=con.prepareStatement("insert into frs_tbl_route values(?,?,?,?,?)");
    		ps.setString(1,routeBean.getRouteID());
    		ps.setString(2, routeBean.getSource());
    		ps.setString(3, routeBean.getDestination());
    		ps.setInt(4, routeBean.getDistance());
    		ps.setDouble(5, routeBean.getFare());
    		i=ps.executeUpdate();
    		return Integer.toString(i)+" Rows Updated";
    	}
    	catch(SQLException sql)
    	{
    		System.out.println(sql);
    	}
    	return "Fail";
    }

    @Override
    public boolean modifyRoute(RouteBean routeBean) {
    	try
    	{
    		ps=con.prepareStatement("update frs_tbl_route set Source=? ,Destination=?,Distance=?,Fare=? where RouteId=?");
    		ps.setString(5,routeBean.getRouteID());
    		ps.setString(1, routeBean.getSource());
    		ps.setString(2, routeBean.getDestination());
    		ps.setInt(3, routeBean.getDistance());
    		ps.setDouble(4, routeBean.getFare());
    		ps.executeUpdate();
    		return true;
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
        return false;
    }

    @Override
    public int removeRoute(ArrayList<String> routeIds) {
    	int count=0;
    	Iterator<String> itr =	routeIds.iterator();
    	while (itr.hasNext())
    	{
    		int i=0;
    		try
    		{
    			ps=con.prepareStatement("delete from frs_tbl_route where RouteId=?");
    			ps.setString(1,itr.next());
    			i=ps.executeUpdate();
    			
    			if(i==0)
    			{
    				System.out.println("Invalid Route ID");
    			}
    			else
    			{
    				System.out.println("Successfully Deleted");
    			}
    			ps=con.prepareStatement("delete from frs_tbl_schedule where RouteId=?");
    			ps.setString(1,itr.next());
    			i=ps.executeUpdate();
    			if(i>0)
    			{
    				System.out.println("Deleted Schedule for the	RouteID "+itr.next());
    			}
    			else
    			{
    				System.out.println("No	RouteID found");
    			}
    		}
    		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
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
        String query = "SELECT * FROM frs_tbl_schedule";
        
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ScheduleBean schedule = new ScheduleBean();
                schedule.setScheduleID(rs.getString("Scheduleid"));
                schedule.setFlightID(rs.getString("Flightid"));
                schedule.setRouteID(rs.getString("Routeid"));
                schedule.setTravelDuration(rs.getInt("TravelDuration"));
                schedule.setAvailableDays(rs.getString("AvailableDays"));
                schedule.setDepartureTime(rs.getString("DepartureTime"));
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
        String query = "SELECT * FROM frs_tbl_schedule WHERE Scheduleid = ?";
        
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
        for (PassengerBean passenger : DataStructure.getAllPassengers()) {
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