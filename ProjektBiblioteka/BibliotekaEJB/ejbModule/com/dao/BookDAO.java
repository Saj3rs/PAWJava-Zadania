package com.dao;

import java.util.List;


import java.util.Map;

import com.entities.Book;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//DAO - Data Access Object for Book entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class BookDAO {
	private final static String UNIT_NAME = "BibliotekaPU"; //Don't know what this value changes

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Book Book) {
		em.persist(Book);
	}

	public Book merge(Book Book) {
		return em.merge(Book);
	}

	public void remove(Book Book) {
		em.remove(em.merge(Book));
	}

	public Book find(Object id_book) {
		return em.find(Book.class, id_book);
	}

	public List<Book> getFullList() {
		List<Book> list = null;

		Query query = em.createQuery("select p from Book p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Book> getList(Map<String, Object> searchParams) {
		List<Book> list = null;

		// 1. Build query string with parameters
		String select = "select p ";
		String from = "from Book p "; //from NAZWA dao
		String where = "";
		String orderby = "order by p.tytul asc, p.gatunek";

		// search for tytul
		String tytul = (String) searchParams.get("tytul");
		if (tytul != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.tytul like :tytul ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (tytul != null) {
			query.setParameter("tytul", tytul+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Book objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
