package com.ust.frs.dao;

import java.util.ArrayList;
import java.util.Iterator;

import com.ust.frs.bean.FlightBean;
import com.ust.frs.bean.PassengerBean;
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
	
	

	@Override
	public String addFlight(FlightBean flightBean) {
			ds.setFlight(flightBean);
		return "success";
	}

	@Override
	public boolean modifyFlight(FlightBean flightBean) {
		Iterator<FlightBean> itr= ds.getFlight().iterator();
		while(itr.hasNext()) {
			if(itr.next().getFlightID().equals(flightBean.getFlightID()))
			{
				itr.remove();
				ds.setFlight(flightBean);
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public int removeFlight(ArrayList<String> flightID) {
		Iterator<String> itr =flightID.iterator();
		while(itr.hasNext()) {
			Iterator<FlightBean> itr1=ds.getFlight().iterator();
			while(itr1.hasNext()) {
				if(itr1.next().getFlightID().equals(itr.next()));
				{
					itr1.remove();
					return 1;
				}
			}
		}
		return 0;
	}

	@Override
	public String addSchedule(ScheduleBean scheduleBean) {
		ds.setSchedule(scheduleBean);
		return null;
	}

	@Override
	public boolean modifySchedule(ScheduleBean scheduleBean) {
		Iterator<ScheduleBean> itr= ds.getSchedule().iterator();
		while(itr.hasNext()) {
			if(itr.next().getFlightID().equals(scheduleBean.getFlightID()))
			{
				itr.remove();
				ds.setSchedule(scheduleBean);
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public int removeSchedule(ArrayList<String> scheduleId) {
		Iterator<String> itr =scheduleId.iterator();
		while(itr.hasNext()) {
			Iterator<ScheduleBean> itr1=ds.getSchedule().iterator();
			while(itr1.hasNext()) {
				if(itr1.next().getScheduleID().equals(itr.next()));
				{
					itr1.remove();
					return 1;
				}
			}
		}
		return 0;
	}

	@Override
	public String addRoute(RouteBean routeBean) {
		ds.setRoute(routeBean);
		return null;
	}

	@Override
	public boolean modifyRoute(RouteBean routeBean) {
		Iterator<RouteBean> itr = ds.getRoute().iterator();
		while(itr.hasNext()) {
			Iterator<RouteBean> itr1=ds.getRoute().iterator();
			while(itr1.hasNext()) {
				if(itr1.next().getRouteID().equals(itr.next()));
				{
					itr1.remove();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int removeRoute(ArrayList<String> routeId) {
		Iterator<String> itr =routeId.iterator();
		while(itr.hasNext()) {
			Iterator<RouteBean> itr1=ds.getRoute().iterator();
			while(itr1.hasNext()) {
				if(itr1.next().getRouteID().equals(itr.next()));
				{
					itr1.remove();
					return 1;
				}
			}
		}
		return 0;
	}

	@Override
	public FlightBean viewByFlightId(String flightId) {
		Iterator<FlightBean> itr = ds.getFlight().iterator();
		while(itr.hasNext()) {
			if(itr.next().getFlightID().equals(flightId))
			{
				return itr.next();
			}
		}
		return null;
	}

	@Override
	public RouteBean viewByRouteId(String routeId) {
		Iterator<RouteBean> itr = ds.getRoute().iterator();
		while(itr.hasNext()) {
			if(itr.next().getRouteID().equals(routeId))
			{
				return itr.next();
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
		Iterator<ScheduleBean> itr = ds.getSchedule().iterator();
		while(itr.hasNext()) {
			if(itr.next().getRouteID().equals(scheduleId))
			{
				return itr.next();
			}
		}
		return null;
	}

	@Override
	public ArrayList<PassengerBean> viewPassengersByFlight(String scheduleId) {
	return null;
	}

}
