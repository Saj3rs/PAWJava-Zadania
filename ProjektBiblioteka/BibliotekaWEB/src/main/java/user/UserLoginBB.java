package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

import com.dao.UserDAO;
import com.entities.User;

@Named
@RequestScoped
public class UserLoginBB {
	//private static final String PAGE_User_Login = "logged?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Integer nr;
	private String haslo;
	private 
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO UserDAO;
		
	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public User getUser(){
		User u = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (nr != null && nr >= 0){
			searchParams.put("nr", nr);
			
		} 
		if (haslo != null && haslo.length() > 0){
			searchParams.put("haslo", haslo);
			
		} 
		
		//2. Get list
			u = UserDAO.validateLogin(searchParams);
		
		
		
		return u;
	}
	
	public String newUser(){
		User user = new User();
		
		
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("user", user);
		
		//2. Pass object through flash	
		flash.put("user", user);
		
		return PAGE_STAY_AT_THE_SAME;

	}

	//public String editUser(User user){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("user", user);
		
		//2. Pass object through flash 
		//flash.put("user", user);
		
		//return PAGE_STAY_AT_THE_SAME;

	//}

	public String deleteUser(User user){
		UserDAO.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}
}

