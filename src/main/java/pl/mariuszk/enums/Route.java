package pl.mariuszk.enums;

import lombok.Getter;

public enum Route {
	LOG_IN("/login"),
	SIGN_IN("/signin");
	@Getter
	private final String url;

	Route(String url) {
		this.url = url;
	}
}
