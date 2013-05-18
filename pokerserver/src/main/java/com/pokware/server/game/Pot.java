package com.pokware.server.game;

public class Pot {

	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void incrementAmount(int amount) {
		this.amount += amount;
	}

}
