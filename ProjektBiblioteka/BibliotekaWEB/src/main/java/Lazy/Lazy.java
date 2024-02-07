package Lazy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
//import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


import com.entities.Book;
import book.BookListBB;

@Named("Lazy")
@ViewScoped
public class Lazy implements Serializable {

	
    private List<Book> lazyList;

    private Book selectedBook;
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
   
    

    public Lazy(BookListBB BookList) {
    	
       this.lazyList = BookList.getList();
    }
    
    public List<Book> load(int offset, int pageSize) {
        // apply offset & filters
        List<Book> Books = lazyList.stream()
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());
        
        return Books;
    }

    public List<Book> getLazyList() {
        return lazyList;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }
}

