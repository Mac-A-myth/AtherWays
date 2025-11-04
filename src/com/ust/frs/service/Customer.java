package com.ust.frs.service;

import java.util.ArrayList;
import java.util.Map;

import com.ust.frs.bean.PassengerBean;
import com.ust.frs.bean.ReservationBean;
import com.ust.frs.bean.ScheduleBean;

public interface Customer {
	ArrayList<ScheduleBean> viewScheduleByRoute(String source, String destination, String date) ;
	boolean cancelTicket(String reservationId) ;
	String reserveTicket(ReservationBean reservationBean, ArrayList<PassengerBean> passengers) ;
	Map<ReservationBean,PassengerBean> viewTicket(String reservationId) ;
	Map<ReservationBean,PassengerBean> printTicket(String reservationId);
}
