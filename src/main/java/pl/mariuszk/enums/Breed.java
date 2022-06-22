package pl.mariuszk.enums;

import lombok.Getter;

public enum Breed {
	MONGREL("Mongrel"),
	AKITA("Akita"),
	ALASKAN_MALAMUTE("Alaskan Malamute"),
	GERMAN_SHEPHERD("German Shepherd"),
	AMSTAFF("Amstaff");

	@Getter
	private final String label;

	Breed(String label) {
		this.label = label;
	}
}
