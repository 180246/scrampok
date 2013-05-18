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

public interface ServerEventListener {
	
	public void onTableStatusEvent(TableStatusEvent event);
	
	public void onSeatStatusEvent(SeatStatusEvent event);
	
	public void onBetEvent(BetEvent event);
	
	public void onDealCardEvent(DealCardsEvent event);
	
	public void onTurnEvent(TurnEvent event);	
	
	public void onFlopEvent(FlopEvent event);
	
	public void onHandStartEvent(HandStartEvent event);
	
	public void onTimeOutEvent(TimeOutEvent event);
	
	public void onWinEvent(WinEvent event);
	
}
