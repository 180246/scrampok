package com.pokware.server.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.inject.Singleton;

@Singleton
public class GameNameGenerator {

	public enum Roman {
		M(1000), CM(900), D(500), CD(400), C(100), XC(90), L(50), XL(40), X(10), IX(9), V(5), IV(4), I(1);

		private final int number;

		private Roman(int number) {
			this.number = number;
		}

		public int getNumber() {
			return number;
		}

		public static String toRoman(int arabicInt) {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < values().length; i++) {
				int termCount = arabicInt / values()[i].getNumber();
				for (int j = 0; j < termCount; j++) {
					result.append(values()[i].toString());
				}
				arabicInt %= values()[i].getNumber();
			}
			return result.toString();
		}

	}

	private final List<String> names = new ArrayList<String>();
	private final List<AtomicInteger> counters = new ArrayList<AtomicInteger>();

	public synchronized String newName() {
		if (names.size() == 0) {
			init();
		}
		int index = (int) (Math.random() * names.size());
		int counter = counters.get(index).incrementAndGet();
		return names.get(index) + " " + Roman.toRoman(counter);
	}

	private void init() {
		try {
			InputStream resourceStream = getClass().getResourceAsStream("countries.csv");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] split = line.split(",");
				names.add(split[0].trim());
				counters.add(new AtomicInteger(0));
			}
			bufferedReader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
