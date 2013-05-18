package com.pokware.client;

import com.pokware.protobuf.Events.BetEvent;
import com.pokware.protobuf.Events.DealCardsEvent;
import com.pokware.protobuf.Events.FlopEvent;
import com.pokware.protobuf.Events.HandStartEvent;
import com.pokware.protobuf.Events.SeatStatusEvent;
import com.pokware.protobuf.Events.TableStatusEvent;
import com.pokware.protobuf.Events.TimeOutEvent;
import com.pokware.protobuf.Events.TurnEvent;
import com.pokware.protobuf.Events.WinEvent;

public class ServerEventAdapter implements ServerEventListener {

	private boolean verbose = false;

	public ServerEventAdapter() {
		this(false);
	}

	public ServerEventAdapter(boolean verbose) {
		this.verbose = verbose;
	}

	@Override
	public void onWinEvent(WinEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onTimeOutEvent(TimeOutEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onSeatStatusEvent(SeatStatusEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onTableStatusEvent(TableStatusEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onBetEvent(BetEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onDealCardEvent(DealCardsEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onFlopEvent(FlopEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onHandStartEvent(HandStartEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

	@Override
	public void onTurnEvent(TurnEvent event) {
		if (verbose) {
			System.out.println(event);
		}
	}

}
