package com.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

//import java.time.LocalDateTime;  
//import java.time.format.DateTimeFormatter;  

/**
 * The persistent class for the reservations database table.
 * 
 */
@Entity
@Table(name="reservations")
@NamedQuery(name="Reservation.findAll", query="SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_reservation")
	private Integer idReservation;

	@Temporal(TemporalType.DATE)
	private Date beginning;

	@Temporal(TemporalType.DATE)
	private Date end;

	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="reservation" )
	private List<Book> books;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	public Reservation() {
	//	this.books = new List<Book>();
	}

	public Integer getIdReservation() {
		return this.idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public Date getBeginning() {
		return this.beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		getBooks().add(book);
		book.setReservation(this);

		//return book;
	}

	public void removeBook(Book book) {
		getBooks().remove(book);
		book.setReservation(null);

		//return book;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	public void newReservation(User cUser,long timer) {
		
		this.setUser(cUser);
		
		Date start = new java.util.Date();
		
		start.setTime(timer);
		Date end = new Date(start.getTime() + (86400000*7)); //Current time + (day milliseconds * days) 
		this.setBeginning(start);
		this.setEnd(end);
		
		//this.setBooks(books);
		
	}
	
}	
	

