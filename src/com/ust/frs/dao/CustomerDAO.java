package com.ust.frs.dao;

import java.util.ArrayList;
import java.util.Map;

import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.ReservationBean;
import com.ust.frs.bean.ScheduleBean;
import com.ust.frs.service.Customer;

public class CustomerDAO implements Customer{

	@Override
	public ArrayList<ScheduleBean> viewScheduleByRoute(String source, String destination, String date) {
		
		return null;
	}

	@Override
	public boolean cancelTicket(String reservationId) {
		
		return false;
	}

	@Override
	public String reserveTicket(ReservationBean reservationBean, ArrayList<PassengerBean> passengers) {
		
		return null;
	}

	@Override
	public Map<ReservationBean, PassengerBean> viewTicket(String reservationId) {
		
		return null;
	}

	@Override
	public Map<ReservationBean, PassengerBean> printTicket(String reservationId) {

		return null;
	}
	
}
