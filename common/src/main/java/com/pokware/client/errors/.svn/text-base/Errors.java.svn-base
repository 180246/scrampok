package com.pokware.client.errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Errors {

	public static enum Key {
		UnhandledRequestType(-1, "Unhandled Request.Type: %s"),

		LoginFailed(1, "Login failed"), SitRequestFailedSeatNotAvailable(2, "Seat is not available"), NotAuthenticated(3, "Not authenticated.");

		private int code;
		private int parametersNumber;
		private String format;

		private Key(int code, String format) {
			this.code = code;
			this.format = format;

			int tokensNb = format.split("\\%").length;
			this.parametersNumber = tokensNb - 1;
		}

		public int getIntegerCode() {
			return code;
		}

		public String getFormat() {
			return format;
		}

		public int getParametersNumber() {
			return parametersNumber;
		}
	}

	private static Map<Integer, Key> errorMap = new HashMap<Integer, Key>();

	static {
		Key[] values = Key.values();
		for (Key key : values) {
			errorMap.put(key.getIntegerCode(), key);
		}
	}

	public static Key getKey(int code) {
		return errorMap.get(code);
	}

	public static String getFormattedError(int code, List<String> errorMessageParameterList) {
		Key key = getKey(code);
		String message = null;
		switch (errorMessageParameterList.size()) {
		case 0:
			message = key.getFormat();
			break;
		case 1:
			message = String.format(key.getFormat(), errorMessageParameterList.get(0));
			break;
		case 2:
			message = String.format(key.getFormat(), errorMessageParameterList.get(0), errorMessageParameterList.get(1));
			break;
		case 3:
			message = String.format(key.getFormat(), errorMessageParameterList.get(0), errorMessageParameterList.get(1), errorMessageParameterList.get(2));
			break;
		default:
			message = String.format(key.getFormat(), errorMessageParameterList.get(0), errorMessageParameterList.get(1), errorMessageParameterList.get(2));
			break;
		}
		return message;
	}

}
