package com.pokware.server.game;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.pokware.common.Card;
import com.pokware.protobuf.EventResponseFactory;
import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.game.commands.fromgame.TimeOutCommand;

/**
 * Holds the state of a turn (a round of bet)
 *
 * @author Fabien Benoit <fabien.benoit@pokware.com>
 */
public class TurnManager {

	Logger logger = Logger.getLogger(TurnManager.class.getName());

	private final Table table;
	private final Game game;

	private int round;
	public static final int PREFLOP = 0;
	public static final int FLOP = 1;
	public static final int TURN = 2;
	public static final int RIVER = 3;

	private int initialPlayersNumber = 0;

	private Seat dealerPlayerSeat;
	private Seat smallBlindPlayerSeat;
	private Seat bigBlindPlayerSeat;
	private Seat currentPlayerSeat;

	public TurnManager(Game game, Table table) {
		this.table = table;
		this.game = game;
	}

	public Table getTable() {
		return table;
	}

	public void init(CommandScheduler<Game> scheduler) {
		Seat[] seats = table.initDealerButtonAndPostBlinds(1, 2);
		dealerPlayerSeat = seats[0];
		smallBlindPlayerSeat = seats[1];
		bigBlindPlayerSeat = seats[2];
		currentPlayerSeat = table.nextOccupiedActiveSeat(seats[2]);
		round = FLOP;

		initialPlayersNumber = 0;
		for (int i = 0; i < Table.MAX_NUMBER_OF_SEATS; i++) {
			if (table.get(i).isActive()) {
				initialPlayersNumber++;
			}
		}

		scheduler.schedule(new TimeOutCommand(game.getGameId(), currentPlayerSeat.getPlayer()), 5, TimeUnit.SECONDS);
	}

	public void nextTurn(CommandScheduler<Game> scheduler) {

		if (playerLeft() == 1) {
			logger.fine("only one player");

			Player lastPlayer = getLastPlayer();
		
			// Broadcast win event
			game.broadcastMessage(EventResponseFactory.getWinEventResponse(lastPlayer.getPlayerId(), game.getGameId()));

			init(scheduler);
			return;
		}
		else {
			logger.fine("playerLeft() == " + playerLeft());
		}

		if (allBetsAreCalled()) {
			round++;

			// Deal new cards
			if (round == FLOP) {
				Card flop1 = game.getDeck().deal();
				Card flop2 = game.getDeck().deal();
				Card flop3 = game.getDeck().deal();
				
				game.broadcastMessage(EventResponseFactory.getFlopEventResponse(flop1.getIndex(), flop2.getIndex(), flop3.getIndex(), game.getGameId()));
			}
			else if (round == TURN) {
				Card card = game.getDeck().deal();				
				game.broadcastMessage(EventResponseFactory.getTurnEventResponse(card.getIndex(), game.getGameId()));			
			}
			else if (round == RIVER) {
				Card card = game.getDeck().deal();				
				game.broadcastMessage(EventResponseFactory.getRiverEventResponse(card.getIndex(), game.getGameId()));			
			}
			return;
		}
		else {
			currentPlayerSeat = table.nextOccupiedActiveSeat(currentPlayerSeat);
			scheduler.schedule(new TimeOutCommand(game.getGameId(), currentPlayerSeat.getPlayer()), 5, TimeUnit.SECONDS);
			return;
		}
	}

	private int playerLeft() {
		int foldNb = 0;
		for (int i = 0; i < Table.MAX_NUMBER_OF_SEATS; i++) {
			if (table.get(i).isFoldFlag()) {
				foldNb++;
			}
		}
		assert foldNb < initialPlayersNumber;
		return initialPlayersNumber - foldNb;
	}

	private Player getLastPlayer() {
		for (int i = 0; i < Table.MAX_NUMBER_OF_SEATS; i++) {
			if (table.get(i).isActive()) {
				return table.get(i).getPlayer();
			}
		}
		return null;
	}

	private boolean allBetsAreCalled() {
		int lastBet = -1;
		for (int i = 0; i < Table.MAX_NUMBER_OF_SEATS; i++) {
			if (table.get(i).isActive()) {
				int bet = table.get(i).getBet();
				if (lastBet == -1) {
					lastBet = bet;
				}
				else {
					if (lastBet != bet) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public Seat getDealerPlayerSeat() {
		return dealerPlayerSeat;
	}

	public void setDealerPlayerSeat(Seat dealerPlayerSeat) {
		this.dealerPlayerSeat = dealerPlayerSeat;
	}

	public Seat getCurrentPlayerSeat() {
		return currentPlayerSeat;
	}

	public void setCurrentPlayerSeat(Seat currentPlayerSeat) {
		this.currentPlayerSeat = currentPlayerSeat;
	}

	public Seat getSmallBlindPlayerSeat() {
		return smallBlindPlayerSeat;
	}

	public Seat getBigBlindPlayerSeat() {
		return bigBlindPlayerSeat;
	}

}
