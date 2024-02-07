package com.dao;

import java.util.List;


import java.util.Map;

import com.entities.Reservation;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ReservationDAO {
	private final static String UNIT_NAME = "BibliotekaPU"; //Don't know what this value changes

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Reservation Reservation) {
		em.persist(Reservation);
	}

	public Reservation merge(Reservation Reservation) {
		return em.merge(Reservation);
	}

	public void remove(Reservation Reservation) {
		em.remove(em.merge(Reservation));
	}

	public Reservation find(Object id_reservation) {
		return em.find(Reservation.class, id_reservation);
	} 
	
	
	
}