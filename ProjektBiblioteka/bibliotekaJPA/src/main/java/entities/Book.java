package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the books database table.
 * 
 */
@Entity
@Table(name="books")
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_book")
	private int idBook;

	private String gatunek;

	private String tytul;

	//bi-directional many-to-one association to Author
	@ManyToOne
	@JoinColumn(name="id_author")
	private Author author;

	//bi-directional many-to-one association to Reservation
	@ManyToOne
	@JoinColumn(name="id_reservation")
	private Reservation reservation;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;

	public Book() {
	}

	public int getIdBook() {
		return this.idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public String getGatunek() {
		return this.gatunek;
	}

	public void setGatunek(String gatunek) {
		this.gatunek = gatunek;
	}

	public String getTytul() {
		return this.tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}