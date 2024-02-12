package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.LazyDataModel;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
//import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
///import jakarta.servlet.http.HttpSession;

import com.dao.BookDAO;
import reservation.ReservationBB;

import com.entities.Book;
import com.entities.Reservation;

import Lazy.LazyBookDataModel;

@Named
@RequestScoped
public class BookListBB {
	

	private static final String PAGE_BOOK_EDIT = "/bookEdit?faces-redirect=true";
	private static final String PAGE_BOOK_LEND = "/bookLend?faces-redirect=true";

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_RESERVATION = "/bookReservation?faces-redirect=true";

    private LazyDataModel<Book> lazyModel;
	private String tytul;
//	private List<Book> lazyList;	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	BookDAO bookDAO;
	
	@PostConstruct
    public void init() {
        lazyModel = new LazyBookDataModel(this);
    }


    public LazyDataModel<Book> getLazyModel() {
    	//this.getList();
    //    lazyModel = new LazyBookDataModel(this);
        return lazyModel;
    }
    
    public BookDAO getBookDAO() {
		return bookDAO;
	}
	
	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public List<Book> getFullList(){
		return bookDAO.getFullList();
	}

	public List<Book> getList(){
		List<Book> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (tytul != null && tytul.length() > 0){
			searchParams.put("tytul", tytul);
			
		} 
		//Variable used as Limit of results
		//int pageSize = 20;
		//int offset=1;
		//2. Get list
			//list = bookDAO.getList(searchParams);
			//this.setLazyList(list);
		
		
		return list;
	}
	public List<Book> getList(int pageSize,int offset){
		List<Book> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (tytul != null && tytul.length() > 0){
			searchParams.put("tytul", tytul);
			
		} 
		//Variable used as Limit of results
		//int pageSize = 20;
		//int offset=1;
		//2. Get list
	//		list = bookDAO.getList(searchParams,pageSize,offset);
		//this.setLazyList(list);
		
		
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
	
	public String lendBook(Book book){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("book", book);
		Book send = bookDAO.find((Integer)book.getIdBook());
		//2. Pass object through flash 
		flash.put("book", send);
		
		return PAGE_BOOK_LEND;
	}

	public String deleteBook(Book book){
		bookDAO.remove(book);
		return PAGE_STAY_AT_THE_SAME;
		//doesn't remove stray reservations
	}
	
	public void returnBook(Book book){
		
		Book returned = bookDAO.find((Integer)book.getIdBook());
		returned.setUser(null);
		bookDAO.merge(returned);
		
		
	}
	//public String reserveBook(Book book){
		//ReservationBB.reserve();
		//flash.put("book", book);
		//flash.put
		
	//	return PAGE_RESERVATION;
	//}
	
	 public List<Book> load(int offset, int pageSize) {
	        // apply offset & filters
	        List<Book> Books = this.getList().stream()
	                .skip(offset)
	                .limit(pageSize)
	                .collect(Collectors.toList());
	        
	        return Books;
	    }

	
	
	
	
    
    
}
