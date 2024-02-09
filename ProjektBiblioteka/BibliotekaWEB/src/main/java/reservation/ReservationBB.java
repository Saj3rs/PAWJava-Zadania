package reservation;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.entities.Reservation;
import com.entities.User;
import com.entities.Book;

@Named
@ViewScoped
public class ReservationBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOK_LIST = "index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
 
	private Reservation reservation = new Reservation();
	private Book loaded = null;
	private Reservation tempRes = new Reservation();
	@EJB
	ReservationDAO reservationDAO;
	@EJB
	BookDAO bookDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Reservation getReservation() {
		return reservation;
	}

	public void	reserve() {
		
		// User currentUser = (User) flash.get("cUser");
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true) ;
		User currentUser = (User) session.getAttribute("cUser");
		long timer = session.getLastAccessedTime();
		//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "usergot"+currentUser.getImie(), null));
		reservation.newReservation(currentUser,timer);
		
	}
	
	public void reserveBook(Book book){
		this.reserve();
		
		//ArrayList<Book> test = new ArrayList<Book>();
		//test.add(book);
		//List<Book> why = Arrays.asList(book) ;
		//reservation.setBooks(why);
		//reservation booklist is not initiated
		//reservation.addBook(book);
		//book.setReservation(reservation);
		
		this.saveData();
		book.setReservation(reservation);
		bookDAO.merge(book);
	}
	
	public void dropReservation(Book book) {
		tempRes = book.getReservation();
		book.setReservation(null);
		bookDAO.merge(book);
		this.deleteReservation(tempRes);
		
	}
	public String deleteReservation(Reservation res){
		reservationDAO.remove(res);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public boolean checkExpired(Integer bookId) {
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true) ;
		long timer = session.getLastAccessedTime();	
		Book book = bookDAO.find(bookId);
		tempRes = book.getReservation();
		if(tempRes!=null) {
			if(!tempRes.validReservation(timer)) {	
				book.setReservation(null);				
				bookDAO.merge(book);					
				reservationDAO.remove(tempRes);			
													
			}
		}
		return false;
	}	
	
		
	public boolean checkOwner(Book book) {
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true) ;
		User currentUser = (User) session.getAttribute("cUser");
		tempRes = book.getReservation();
		
		if(tempRes==null) return false;
		else if(tempRes.getUser().getIdUser()==currentUser.getIdUser())return true;
		else return false;
	}
	
	
	
	//public void onLoad() throws IOException {
		// 1. load Reservation passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true) ;
		//loaded = (Reservation) session.getAttribute("Reservation");

		// 2. load Reservation passed through flash
	//	loaded = (Book) flash.get("book");

		// cleaning: attribute received => delete it from session
		//if (loaded != null) {
		//	reservation = loaded;
			// session.removeAttribute("person");
	//	} else {
	//		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("ReservationList.xhtml");
			// context.responseComplete();
			// }
		//}


	//}

	public String saveData() {
		

		try {
			if (reservation.getIdReservation() == null) {
				// new record
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "did create", null));

				reservationDAO.create(reservation);
				return PAGE_STAY_AT_THE_SAME;
			}else {
				// existing record
				reservationDAO.merge(reservation);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "did savedata", null));

				return PAGE_STAY_AT_THE_SAME;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
		}
		return PAGE_STAY_AT_THE_SAME;

		
	}
	
}

