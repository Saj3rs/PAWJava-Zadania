package reservation;

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

import com.dao.ReservationDAO;
import com.entities.Reservation;
import com.entities.Book;

@Named
@ViewScoped
public class ReservationBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOK_LIST = "index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
 
	private Reservation reservation = new Reservation();
	private Book loaded = null;

	@EJB
	ReservationDAO reservationDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Reservation getReservation() {
		return reservation;
	}

	public void	reserve() {
		// User currentUser = (User) flash.get("cUser");
		//HttpSession session = (HttpSession) context.getExternalContext().getSession(true) ;
		//Integer cUserId = (Integer) session.getAttribute("cUserId");
		reservation.newReservation(currentUser);
		
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
		// no Reservation object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (reservation.getIdReservation() == null) {
				// new record
				reservationDAO.create(reservation);
				return PAGE_BOOK_LIST;
			}else {
				// existing record
				reservationDAO.merge(reservation);
				//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "did savedata", null));

				return PAGE_BOOK_LIST;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
		}
		return PAGE_STAY_AT_THE_SAME;

		
	}
	
}

