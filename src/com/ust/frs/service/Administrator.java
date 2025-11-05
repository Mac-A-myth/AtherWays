package com.ust.frs.service;
import java.util.ArrayList;
import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.RouteBean;
import com.ust.frs.bean.ScheduleBean;
public interface Administrator {
	public String addFlight(FlightBean flightBean);
	public boolean modifyFlight(FlightBean flightBean) ;
	public int removeFlight(ArrayList<String> flightID) ;
	public String addSchedule(ScheduleBean scheduleBean);
	public boolean modifySchedule(ScheduleBean scheduleBean);
	public int removeSchedule(ArrayList<String> scheduleId) ;
	public String addRoute(RouteBean routeBean) ;
	public boolean modifyRoute(RouteBean routeBean) ;
	public int removeRoute(ArrayList<String> routeId) ;
	public FlightBean viewByFlightId(String flightId);
	public RouteBean viewByRouteId(String routeId);
	public ArrayList<FlightBean> viewByAllFlights();
	public ArrayList<RouteBean> viewByAllRoute();
	public ArrayList<ScheduleBean> viewByAllSchedule();
	public ScheduleBean viewByScheduleId(String scheduleId);
	public ArrayList<PassengerBean> viewPassengersByFlight(String scheduleId);
}
