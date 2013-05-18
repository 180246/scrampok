package com.pokware.server.game;

import java.util.ArrayList;

import com.pokware.protobuf.Data.TableStatus;
import com.pokware.protobuf.Data.TableStatus.Builder;
import com.pokware.protobuf.Protobufable;


public class Table extends ArrayList<Seat> implements Protobufable<TableStatus> {

	private static final long serialVersionUID = 1L;

	public static final short MAX_NUMBER_OF_SEATS = 10;

	private static final int NOT_FOUND = -1;

	public Table() {
		super(MAX_NUMBER_OF_SEATS);

		for (short i = 0; i < MAX_NUMBER_OF_SEATS; i++) {
			add(new Seat(i));
		}
	}

	public short getNumberOfPlayers() {
		short number = 0;
		for (int i = 0; i < size(); i++) {
			if (!get(i).isEmpty()) {
				number++;
			}
		}
		return number;
	}

	public Player getPlayer(String userName) {
		if (userName == null) {
			throw new RuntimeException("userName cannot be null");
		}

		for (int i = 0; i < size(); i++) {
			if (!get(i).isEmpty() && get(i).getPlayer().getUserName().equals(userName)) {
				return get(i).getPlayer();
			}
		}
		return null;
	}

	/**
	 * 0..9
	 *
	 * @param nthPlayer
	 * @return
	 */
	public Player getPlayer(int nthPlayer) {
		if (nthPlayer < 0 || nthPlayer >= MAX_NUMBER_OF_SEATS) {
			throw new RuntimeException("nthPlayer must be between 0 and " + (MAX_NUMBER_OF_SEATS - 1) + " : was " + nthPlayer);
		}

		for (int i = 0; i < size(); i++) {
			if (!get(i).isEmpty() && nthPlayer-- == 0) {
				return get(i).getPlayer();
			}
		}
		return null;
	}

	public Seat getSeat(Player player) {
		if (player == null) {
			throw new RuntimeException("Player cannot be null");
		}
		for (int i = 0; i < size(); i++) {
			if (player.equals(get(i).getPlayer())) {
				return get(i);
			}
		}
		return null;
	}

	public short getDealerIndex() {
		for (Seat seat : this) {
			if (seat.isDealerFlag()) {
				return seat.getIndex();
			}
		}
		return NOT_FOUND;
	}

	public short getSmallBlindIndex() {
		for (Seat seat : this) {
			if (seat.isSmallBlindFlag()) {
				return seat.getIndex();
			}
		}
		return NOT_FOUND;
	}

	public short getBigBlindIndex() {
		for (Seat seat : this) {
			if (seat.isBigBlindFlag()) {
				return seat.getIndex();
			}
		}
		return NOT_FOUND;
	}

	public Seat nextOccupiedActiveSeat(Seat seat) {
		if (seat == null) {
			throw new RuntimeException("Seat cannot be null");
		}
		for (int i = seat.getIndex() + 1; i < size(); i++) {
			Seat currentSeat = get(i);
			Player player = currentSeat.getPlayer();
			if (player != null && currentSeat.isActive()) {
				return currentSeat;
			}
		}
		for (int i = 0; i < seat.getIndex(); i++) {
			Seat currentSeat = get(i);
			Player player = currentSeat.getPlayer();
			if (player != null && currentSeat.isActive()) {
				return currentSeat;
			}
		}
		return seat;
	}

	/**
	 * 
	 * @param player
	 * @param sitIndex
	 * @return true is sit is successful, false if the seat was already occupied
	 */
	public boolean sitPlayer(Player player, int sitIndex) {
		Seat seat = get(sitIndex);
		
		if (!seat.isEmpty()) {
			return false;
		}
		
		seat.setPlayer(player, 100); // TODO : buy-in
		
		return true;
	}

	public boolean isFull() {
		return getNumberOfPlayers() == MAX_NUMBER_OF_SEATS;
	}

	public void removeAllWaitingFlags() {
		for (Seat seat : this) {
			seat.setWaitingFlag(false);
		}
	}

	public Seat[] initDealerButtonAndPostBlinds(int smallBlindAmount, int bigBlindAmount) {
		Seat[] result = new Seat[3];
		short previousDealerButtonPosition = getDealerIndex();
		short numberOfPlayers = getNumberOfPlayers();

		if (numberOfPlayers < 2) {
			throw new RuntimeException("Insufficient number of players : 2 required, found only " + numberOfPlayers);
		}

		// Clear all flags
		for (int i = 0; i < size(); i++) {
			get(i).setDealerFlag(false);
			get(i).setSmallBlindFlag(false, smallBlindAmount);
			get(i).setBigBlindFlag(false, bigBlindAmount);
		}

		Seat dealerSeat = null;
		if (previousDealerButtonPosition == NOT_FOUND) { // no dealer set
			dealerSeat = getSeat(getPlayer(0));
		} else { // dealer previously set
			Seat seat = get(previousDealerButtonPosition);
			dealerSeat = nextOccupiedActiveSeat(seat);
		}

		dealerSeat.setDealerFlag(true);
		result[0] = dealerSeat;

		if (numberOfPlayers == 2) {

			dealerSeat.setSmallBlindFlag(true, smallBlindAmount);
			dealerSeat.decrementStack(smallBlindAmount);
			result[1] = dealerSeat;

			Seat bbSeat = nextOccupiedActiveSeat(dealerSeat);
			bbSeat.setBigBlindFlag(true, bigBlindAmount);
			bbSeat.decrementStack(bigBlindAmount);
			result[2] = bbSeat;

		} else {
			Seat sbSeat = nextOccupiedActiveSeat(dealerSeat);
			sbSeat.setSmallBlindFlag(true, smallBlindAmount);
			sbSeat.decrementStack(smallBlindAmount);
			result[1] = sbSeat;

			Seat bbSeat = nextOccupiedActiveSeat(sbSeat);
			bbSeat.setBigBlindFlag(true, bigBlindAmount);
			bbSeat.decrementStack(bigBlindAmount);
			result[2] = bbSeat;
		}

		return result;
	}
	
	@Override
	public com.pokware.protobuf.Data.TableStatus toProtocolBuffer() {
		Builder tableStatusBuilder = TableStatus.newBuilder();
		for (Seat seat : this) {
			tableStatusBuilder.addSeat(seat.toProtocolBuffer());
		}
		return tableStatusBuilder.build();
	}
}
