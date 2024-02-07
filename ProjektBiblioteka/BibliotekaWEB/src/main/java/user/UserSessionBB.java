package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.annotation.PostConstruct;
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
public class UserSessionBB {
	private static final String PAGE_ADMIN = "/admin/AdminView?faces-redirect=true";
	private static final String PAGE_LOGGED = "/user/index?faces-redirect=true";
	private static final String PAGE_ANON = "/index?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User cUser;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO UserDAO;
	
	@PostConstruct
	public void init() {
			this.setSessioncUser();
	}
	
	public void setSessioncUser() {
		HttpSession session = (HttpSession) extcontext.getSession(true);
		this.setcUser((User) session.getAttribute("cUser"));
	}

	public User getcUser() {
		return cUser;
	}

	public void setcUser(User cUser) {
		this.cUser = cUser;
	}
	
	public void endcUser() {
		this.cUser = null;
		
	}
	
	public boolean admin() {
		if(cUser.getRole().getAdmin()==1) return true;
		else return false;
	}
	
}
		