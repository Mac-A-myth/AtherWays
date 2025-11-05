package com.ust.frs.dao;

import java.util.ArrayList;

import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.RouteBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.service.*;

public class AdministratorDAO implements Administrator {

	@Override
	public String addFlight(FlightBean flightBean) {
	
		return null;
	}

	@Override
	public boolean modifyFlight(FlightBean flightBean) {
		
		return false;
	}

	@Override
	public int removeFlight(ArrayList<String> flightID) {
		
		return 0;
	}

	@Override
	public String addSchedule(ScheduleBean scheduleBean) {
		
		return null;
	}

	@Override
	public boolean modifySchedule(ScheduleBean scheduleBean) {
		
		return false;
	}

	@Override
	public int removeSchedule(ArrayList<String> scheduleId) {
		
		return 0;
	}

	@Override
	public String addRoute(RouteBean routeBean) {
		
		return null;
	}

	@Override
	public boolean modifyRoute(RouteBean routeBean) {
		
		return false;
	}

	@Override
	public int removeRoute(ArrayList<String> routeId) {
		
		return 0;
	}

	@Override
	public FlightBean viewByFlightId(String flightId) {
		
		return null;
	}

	@Override
	public RouteBean viewByRouteId(String routeId) {
		
		return null;
	}

	@Override
	public ArrayList<FlightBean> viewByAllFlights() {
		
		return null;
	}

	@Override
	public ArrayList<RouteBean> viewByAllRoute() {
	
		return null;
	}

	@Override
	public ArrayList<ScheduleBean> viewByAllSchedule() {
	
		return null;
	}

	@Override
	public ScheduleBean viewByScheduleId(String scheduleId) {

		return null;
	}

	@Override
	public ArrayList<PassengerBean> viewPassengersByFlight(String scheduleId) {
		
		return null;
	}

}
