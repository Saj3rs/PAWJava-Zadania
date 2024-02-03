package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
//import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
///import jakarta.servlet.http.HttpSession;

import com.dao.BookDAO;
import reservation.ReservationBB;

import com.entities.Book;

@Named
@RequestScoped
public class BookListBB {
	private static final String PAGE_BOOK_EDIT = "bookEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_RESERVATION = "bookReservation?faces-redirect=true";


	private String tytul;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	BookDAO BookDAO;
		
	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public List<Book> getFullList(){
		return BookDAO.getFullList();
	}

	public List<Book> getList(){
		List<Book> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (tytul != null && tytul.length() > 0){
			searchParams.put("tytul", tytul);
			
		} 
		//Variable used as Limit of results
		int amm = 20;
		//2. Get list
			list = BookDAO.getList(searchParams,amm);
		
		
		
		return list;
	}
	
	public ArrayList<Book> getArrayList(){
		ArrayList<Book> ArrList = new ArrayList<>(this.getList());
		
		return ArrList;
	}

	public String newBook(){
		Book book = new Book();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("book", book);
		
		//2. Pass object through flash	
		flash.put("book", book);
		
		return PAGE_BOOK_EDIT;
	}

	public String editBook(Book book){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("book", book);
		
		//2. Pass object through flash 
		flash.put("book", book);
		
		return PAGE_BOOK_EDIT;
	}

	public String deleteBook(Book book){
		BookDAO.remove(book);
		return PAGE_STAY_AT_THE_SAME;
	}
	//public String reserveBook(Book book){
		//ReservationBB.reserve();
		//flash.put("book", book);
		//flash.put
		
	//	return PAGE_RESERVATION;
	//}
	
	
	
}
