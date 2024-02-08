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

import com.dao.BookDAO;
import com.entities.Book;
import book.BookListBB;


@Named("LazyView")
@ViewScoped
public class LazyView implements Serializable {

    private LazyDataModel<Book> lazyModel;
    private List<Book> lista;
    

    //private Book selectedBook;
    @EJB
	BookDAO BookDAO;
    @Inject
    private BookListBB bookListBB;
    
    public LazyView(List<Book> lista) {
    	this.lista = lista;
    }

    @PostConstruct
    public void init() {
    	
        lazyModel = new LazyBookDataModel(lista);
    }

    public LazyDataModel<Book> getLazyModel() {
        return lazyModel;
    }






}