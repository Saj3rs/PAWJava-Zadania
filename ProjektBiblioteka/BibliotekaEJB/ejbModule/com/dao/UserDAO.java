package com.dao;

import java.util.List;


import java.util.Map;

import com.entities.User;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//DAO - Data Access Object for User entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class UserDAO {
	
	private final static String UNIT_NAME = "BibliotekaPU"; //Don't know what this value changes

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(User User) {
		em.persist(User);
	}

	public User merge(User User) {
		return em.merge(User);
	}

	public void remove(User User) {
		em.remove(em.merge(User));
	}

	public User find(Integer id_user) {
		return em.find(User.class, id_user);
	}

	public User validateLogin(Map<String, Object> searchParams) {
		User answ = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from User p "; //from NAZWA dao
		String where = "";

		// search for login
		String nr = (String) searchParams.get("nr");
		if (nr != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.id_user = :nr ";
		}
		String haslo = (String) searchParams.get("haslo");
		if (haslo != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.haslo = :haslo ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where);

		// 3. Set configured parameters
		if (nr != null) {
			query.setParameter("id_user", nr+"%");
		}
		if (haslo != null) {
			query.setParameter("haslo", haslo+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of User objects
		try {
			answ = (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return answ;
	}
	
	public User simpleValidate(Integer id_user) {
		User u = null;
		User t = this.find(id_user);
		if(t!=null) {
			
		}
		
		return u;
	}
	
	public boolean checkRole(User user) {
		byte admin = user.getRole().getAdmin();
		if(admin==1) {
			return true;
		}else {
			return false;
		}
	}

}
