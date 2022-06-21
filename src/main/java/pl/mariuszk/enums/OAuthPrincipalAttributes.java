package pl.mariuszk.enums;

import lombok.Getter;

public enum OAuthPrincipalAttributes {

	EMAIL("email");

	@Getter
	private final String key;

	OAuthPrincipalAttributes(String key) {
		this.key = key;
	}
}
