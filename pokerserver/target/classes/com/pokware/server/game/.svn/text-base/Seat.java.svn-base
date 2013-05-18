package com.pokware.server.game;

import com.pokware.common.Card;
import com.pokware.protobuf.Protobufable;

public class Seat implements Protobufable<com.pokware.protobuf.Data.Seat> {

	@Override
	public String toString() {
		return "Seat [index=" + index + ", stack=" + stack + ", bet=" + bet + ", awayFlag=" + awayFlag + ", bigBlindFlag=" + bigBlindFlag + ", dealerFlag=" + dealerFlag + ", foldFlag=" + foldFlag
				+ ", player=" + player + ", smallBlindFlag=" + smallBlindFlag + ", waitingFlag=" + waitingFlag + "]";
	}

	private final short index;
	private Player player;
	private int stack;
	private int bet;
	private boolean dealerFlag;
	private boolean smallBlindFlag;
	private boolean bigBlindFlag;
	private boolean waitingFlag;
	private boolean awayFlag;
	private boolean foldFlag;
	private transient Card firstCard;
	private transient Card secondCard;

	public Card getFirstCard() {
		return firstCard;
	}

	public Card getSecondCard() {
		return secondCard;
	}

	public void setSecondCard(Card secondHole) {
		this.secondCard = secondHole;
	}

	public void setFirstCard(Card firstHole) {
		this.firstCard = firstHole;
	}

	public Seat(short position) {
		this.index = position;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player, int stack) {
		this.player = player;
		this.stack = stack;
	}

	public void removePlayer() {
		this.player = null;
	}

	public boolean isEmpty() {
		return player == null;
	}

	public boolean isDealerFlag() {
		return dealerFlag;
	}

	public void setDealerFlag(boolean dealerFlag) {
		this.dealerFlag = dealerFlag;
	}

	public boolean isSmallBlindFlag() {
		return smallBlindFlag;
	}

	public void setSmallBlindFlag(boolean smallBlindFlag, int amount) {
		this.smallBlindFlag = smallBlindFlag;
		this.bet = amount;
		this.stack -= amount;
	}

	public boolean isBigBlindFlag() {
		return bigBlindFlag;
	}

	public void setBigBlindFlag(boolean bigBlindFlag, int amount) {
		this.bigBlindFlag = bigBlindFlag;
		this.bet = amount;
		this.stack -= amount;
	}

	public boolean isWaitingFlag() {
		return waitingFlag;
	}

	public void setWaitingFlag(boolean waitingFlag) {
		this.waitingFlag = waitingFlag;
	}

	public short getIndex() {
		return index;
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public void decrementStack(int amount) {
		this.stack -= amount;
	}

	public boolean isAwayFlag() {
		return awayFlag;
	}

	public void setAwayFlag(boolean awayFlag) {
		this.awayFlag = awayFlag;
	}

	public void setFoldFlag(boolean foldFlag) {
		this.foldFlag = foldFlag;
	}

    public boolean isFoldFlag() {
		return foldFlag;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public boolean isActive() {
		return player != null && !foldFlag && !awayFlag && !waitingFlag ;
	}

	@Override
	public com.pokware.protobuf.Data.Seat toProtocolBuffer() {
		com.pokware.protobuf.Data.Seat.Builder seatBuilder = com.pokware.protobuf.Data.Seat.newBuilder();
		seatBuilder.setIndex(getIndex());			
		if(!isEmpty()) {			
			seatBuilder.setPlayer(getPlayer().toProtocolBuffer());
			seatBuilder.setStack(getStack());
			seatBuilder.setBet(getBet());
			seatBuilder.setDealerFlag(isDealerFlag());
			seatBuilder.setSmallBlindFlag(isSmallBlindFlag());
			seatBuilder.setBigBlindFlag(isBigBlindFlag());
			seatBuilder.setWaitingFlag(isWaitingFlag());
			seatBuilder.setAwayFlag(isAwayFlag());
			seatBuilder.setFoldFlag(isFoldFlag());						
		}			
		return seatBuilder.build();
	}

}
