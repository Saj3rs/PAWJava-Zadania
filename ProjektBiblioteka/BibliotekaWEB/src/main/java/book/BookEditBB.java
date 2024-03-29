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
import com.dao.UserDAO;
import com.entities.Book;
import com.entities.User;

@Named
@ViewScoped
public class BookEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOK_LIST = "/admin/AdminView?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
 
	private Book book = new Book();
	private Book loaded = null;

	@EJB
	BookDAO bookDAO;
	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Book getBook() {
		return book;
	}

	public String setId(int id) {
		User u = userDAO.find((Integer)id);
		
		book.setUser(u);
		bookDAO.merge(book);
		return PAGE_BOOK_LIST;
	}
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

	public String saveData() {
		// no Book object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (book.getIdBook() == null) {
				// new record
				bookDAO.create(book);
				return PAGE_BOOK_LIST;
			}else {
				// existing record
				bookDAO.merge(book);
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

