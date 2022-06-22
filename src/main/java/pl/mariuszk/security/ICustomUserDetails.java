package pl.mariuszk.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface ICustomUserDetails extends UserDetails {

	String getName();
}
