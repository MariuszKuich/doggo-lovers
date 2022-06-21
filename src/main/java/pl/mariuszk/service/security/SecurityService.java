package pl.mariuszk.service.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import static pl.mariuszk.enums.Route.LOG_IN;

@Service
public class SecurityService {

	public UserDetails getAuthenticatedUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Object principal = context.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (UserDetails) context.getAuthentication().getPrincipal();
		}
		// Anonymous or no authentication.
		return null;
	}

	public void logout() {
		UI.getCurrent().navigate(LOG_IN.getUrl());
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
	}
}
