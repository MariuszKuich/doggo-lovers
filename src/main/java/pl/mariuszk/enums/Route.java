package pl.mariuszk.enums;

import lombok.Getter;

public enum Route {
	LOG_IN("/login"),
	SIGN_IN("/signin"),
	DOG_DATA("/dogdata"),
	MAIN_VIEW("/"),
	SCHEDULER("/scheduler"),
	VET_HISTORY("/vet"),
	GALLERY("/gallery");
	@Getter
	private final String url;

	Route(String url) {
		this.url = url;
	}
}
