package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
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

	public User sessionCUser(Integer id) {
		User u = UserDAO.find(id);
		HttpSession session = (HttpSession) extcontext.getSession(true);
		session.setAttribute("cUser", u);
		return u;
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
		
		//2. Get result
			u = UserDAO.validateLogin(searchParams);
			//if(u!=null) {
				//HttpSession session = (HttpSession) extcontext.getSession(true);
				//session.setAttribute("cUser", u);
			//}
		
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
	
	
	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get User from "database"
		User user = this.getUser();

		// 2. if bad login or password - stay with error info
		if (user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny login lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get User roles, save in RemoteClient and store it in session
		
		RemoteClient<User> client = new RemoteClient<User>(); //create new RemoteClient
		client.setDetails(user);
		
		boolean check = UserDAO.checkRole(user); //get User roles 
		
		if (check) { //save roles in RemoteClient
				client.getRoles().add("admin");
		}else {
				client.getRoles().add("reader");

		}
		
	
		//store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		// and enter the system (now SecurityFilter will pass the request)
		return PAGE_MAIN;
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		//Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_LOGIN;
	}
}

