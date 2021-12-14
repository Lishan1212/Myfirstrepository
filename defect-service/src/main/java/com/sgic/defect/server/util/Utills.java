package com.sgic.defect.server.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Utills {

	public static boolean stringValidation(String name) {
		if (name != null && !name.trim().isEmpty()) {
			String word[] = name.split("[;:-]");
			int size = word.length;
			if (size > 0) {
				return false;
			}

		}
		return true;
	}

	public static boolean stringValidationUsingPattern(String name) {
		if (Pattern.matches("^[a-zA-Z]{4,20}$", name.trim())) {
			return true;
		}
		return false;
	}

	public static boolean patternMatches(String emailAddress) {
		if (Pattern.matches("^(.+)@(\\S+)$", emailAddress.trim())) {
			return true;
		}
		return false;

	}

	public static boolean notNullValidation(String name) {
		if (name != null && !name.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean idValidation(Long id) {
		if (id != null)
			return true;
		return false;
	}

	public static boolean dateValidation(String string, String string2) {
		LocalDate date1 = LocalDate.parse(string);
		LocalDate date2 = LocalDate.parse(string2);
		if (date1.isAfter(date2)) {
			return true;
		} else {
			return false;
		}

	}
}
