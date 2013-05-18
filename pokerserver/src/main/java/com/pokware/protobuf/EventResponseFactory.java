package com.pokware.protobuf;

import com.pokware.protobuf.Events.ServerEvent;
import com.pokware.protobuf.Events.ServerEvent.Type;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.server.game.*;

public class EventResponseFactory {


	public static Response getBetEventResponse(long playerId, long gameId, int chipsAmount) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.BetEvent);
		
		Events.BetEvent.Builder eventBuilder = Events.BetEvent.newBuilder();	

		eventBuilder.setPlayerId(playerId);
		eventBuilder.setGameId(gameId);
		eventBuilder.setChipsAmount(chipsAmount);		
		
		serverEventBuilder.setBetEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getDealCardsEventResponse(long gameId) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.DealCardsEvent);
		
		Events.DealCardsEvent.Builder eventBuilder = Events.DealCardsEvent.newBuilder();	

		eventBuilder.setGameId(gameId);		
		
		serverEventBuilder.setDealCardsEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getFlopEventResponse(int card1, int card2, int card3, long gameId) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.FlopEvent);
		
		Events.FlopEvent.Builder eventBuilder = Events.FlopEvent.newBuilder();	

		eventBuilder.setCard1(card1);
		eventBuilder.setCard2(card2);
		eventBuilder.setCard3(card3);
		eventBuilder.setGameId(gameId);		
		
		serverEventBuilder.setFlopEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getHandStartEventResponse(long gameId, int hole1, int hole2) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.HandStartEvent);
		
		Events.HandStartEvent.Builder eventBuilder = Events.HandStartEvent.newBuilder();	

		eventBuilder.setGameId(gameId);
		eventBuilder.setHole1(hole1);
		eventBuilder.setHole2(hole2);		
		
		serverEventBuilder.setHandStartEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getRiverEventResponse(int card, long gameId) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.RiverEvent);
		
		Events.RiverEvent.Builder eventBuilder = Events.RiverEvent.newBuilder();	

		eventBuilder.setCard(card);
		eventBuilder.setGameId(gameId);		
		
		serverEventBuilder.setRiverEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getSeatStatusEventResponse(long gameId, Seat seat) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.SeatStatusEvent);
		
		Events.SeatStatusEvent.Builder eventBuilder = Events.SeatStatusEvent.newBuilder();	

		eventBuilder.setGameId(gameId);
		eventBuilder.setSeat(seat.toProtocolBuffer());		
		
		serverEventBuilder.setSeatStatusEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getTableStatusEventResponse(long gameId, Table table) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.TableStatusEvent);
		
		Events.TableStatusEvent.Builder eventBuilder = Events.TableStatusEvent.newBuilder();	

		eventBuilder.setGameId(gameId);
		eventBuilder.setTable(table.toProtocolBuffer());		
		
		serverEventBuilder.setTableStatusEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getTimeOutEventResponse(long gameId) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.TimeOutEvent);
		
		Events.TimeOutEvent.Builder eventBuilder = Events.TimeOutEvent.newBuilder();	

		eventBuilder.setGameId(gameId);		
		
		serverEventBuilder.setTimeOutEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getTurnEventResponse(int card, long gameId) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.TurnEvent);
		
		Events.TurnEvent.Builder eventBuilder = Events.TurnEvent.newBuilder();	

		eventBuilder.setCard(card);
		eventBuilder.setGameId(gameId);		
		
		serverEventBuilder.setTurnEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}

	public static Response getWinEventResponse(long playerId, long gameId) {
		Response.Builder responseBuilder = Response.newBuilder();
		ServerEvent.Builder serverEventBuilder = ServerEvent.newBuilder().setType(Type.WinEvent);
		
		Events.WinEvent.Builder eventBuilder = Events.WinEvent.newBuilder();	

		eventBuilder.setPlayerId(playerId);
		eventBuilder.setGameId(gameId);		
		
		serverEventBuilder.setWinEvent(eventBuilder);				
		responseBuilder.setServerEvent(serverEventBuilder);
		
		return responseBuilder.build();
	}


}