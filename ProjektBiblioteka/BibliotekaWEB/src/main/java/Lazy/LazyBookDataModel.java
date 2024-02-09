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

public class LazyBookDataModel extends LazyDataModel<Book> {

	private BookListBB bookListBB;
	// private List<Book> datasource;
	private LazyDataModel<Book> lazyModel;
	private Book selectedBook;

	public LazyBookDataModel(BookListBB bookListBB) {
		this.bookListBB = bookListBB;

	}

	@Override
	public List<Book> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		// apply offset & filters
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (bookListBB.getTytul() != null && bookListBB.getTytul().length() > 0) {
			searchParams.put("tytul", bookListBB.getTytul());

		}
		List<Book> books = bookListBB.getBookDAO().getList(searchParams, offset, pageSize);
		return books;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (bookListBB.getTytul() != null && bookListBB.getTytul().length() > 0) {
			searchParams.put("tytul", bookListBB.getTytul());

		}
		return bookListBB.getBookDAO().getListCount(searchParams);
		
	}

}
