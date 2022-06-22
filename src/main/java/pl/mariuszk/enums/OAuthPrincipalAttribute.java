package pl.mariuszk.enums;

import lombok.Getter;

public enum OAuthPrincipalAttribute {

	EMAIL("email"),
	GIVEN_NAME("given_name");

	@Getter
	private final String key;

	OAuthPrincipalAttribute(String key) {
		this.key = key;
	}
}
