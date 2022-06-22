package pl.mariuszk.enums;

import lombok.Getter;

public enum QueryParamKey {

	CREATED("created"),
	ERROR("error"),
	SAVED("saved");

	@Getter
	private final String value;

	QueryParamKey(String value) {
		this.value = value;
	}
}
