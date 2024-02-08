package Lazy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

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

@Named("LazyBookModel")
@ViewScoped
public class LazyBookDataModel extends LazyDataModel<Book>{

	
    private List<Book> datasource;
    private LazyDataModel<Book> lazyModel;
    private Book selectedBook;
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
   
    

    public LazyBookDataModel(List<Book> list) {
    	
       this.datasource = list;
    }
    @Override
    public List<Book> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        // apply offset & filters
        List<Book> Books = datasource.stream()
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());
        return Books;
    }

    @Override
    public Book getRowData(String rowKey) {
        for (Book book : datasource) {
            if (book.getIdBook() == Integer.parseInt(rowKey)) {
                return book;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Book book) {
        return String.valueOf(book.getIdBook());
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .count();
    }
	
}

