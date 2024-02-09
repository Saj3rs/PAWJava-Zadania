package book;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import com.dao.BookDAO;
import com.dao.ReservationDAO;
import com.dao.UserDAO;
import com.entities.Book;
import com.entities.Reservation;
import com.entities.User;

@Named
@ViewScoped
public class BookLendBB implements Serializable {
	

	

	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOK_LIST = "/admin/AdminView?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private int id;
	private Book book = null;
	private Book loaded = null;

	@EJB
	BookDAO bookDAO;
	@EJB
	UserDAO userDAO;
	@EJB
	ReservationDAO reservationDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	public void onLoad() throws IOException {
		// 1. load Book passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Book) session.getAttribute("Book");

		// 2. load Book passed through flash
		loaded = (Book) flash.get("book");
		
		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			book = loaded;

			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("BookList.xhtml");
			// context.responseComplete();
			// }
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Book getBook() {
		return book;
	}

	public Book getLoaded() {
		return loaded;
	}
	
	public String setId() {
		User u = userDAO.find((Integer)id);
		if (book.getTytul()== null) {
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne przekazanie book", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("BookList.xhtml");
			// context.responseComplete();
			// }
		}
		
		book.setReservation(null);
		book.setUser(u);
		bookDAO.merge(book);
		//Reservation res = book.getReservation();
		
		//reservationDAO.remove(res);
		return PAGE_BOOK_LIST;
	}
	


	

	
	
}

