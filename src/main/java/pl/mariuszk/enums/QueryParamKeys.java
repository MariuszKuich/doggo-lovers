package pl.mariuszk.enums;

import lombok.Getter;

public enum QueryParamKeys {

	CREATED("created"),
	ERROR("error");

	@Getter
	private final String value;

	QueryParamKeys(String value) {
		this.value = value;
	}
}
