package com.pokware.server.game.commands.fromplayer;

import java.util.concurrent.TimeUnit;

import com.pokware.client.errors.Errors;
import com.pokware.common.Deck;
import com.pokware.protobuf.EventResponseFactory;
import com.pokware.protobuf.RPCMessages.Response;
import com.pokware.protobuf.RPCMessages.SitRequest;
import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.ResponseFactory;
import com.pokware.server.game.Game;
import com.pokware.server.game.GameCommand;
import com.pokware.server.game.Player;
import com.pokware.server.game.Seat;
import com.pokware.server.game.commands.CommandError;
import com.pokware.server.game.commands.fromgame.StartHandCommand;
import com.pokware.server.states.GameState;

public class SitCommand extends GameCommand {

	private SitRequest sitRequest;
	private int requestId;
	private long playerId;

	public SitCommand(int requestId, long playerId, SitRequest sitRequest) {
		super(sitRequest.getGameId());
		this.sitRequest = sitRequest;
		this.requestId = requestId;
		this.playerId = playerId;
	}

	@Override
	public int checkPermission(Game game) {
		Player player = game.getPlayerLocator().getPlayer(playerId);

		if (player.isObserving(game)) {
			return 0;
		} else {
			return CommandError.PLAYER_IS_NOT_OBSERVER;
		}
	}

	@Override
	public void executeOn(Game game, CommandScheduler<Game> commandScheduler) {
		Player player = game.getPlayerLocator().getPlayer(playerId);

		boolean sitPlayerSuccess = game.sitPlayer(player, sitRequest.getSitIndex());

		if (!sitPlayerSuccess) {
			player.sendMessage(ResponseFactory.getErrorResponse(requestId, Errors.Key.SitRequestFailedSeatNotAvailable));
			return;
		}

		Seat seat = game.getTable().getSeat(player);
		Response response = EventResponseFactory.getSeatStatusEventResponse(game.getGameId(), seat);
		game.broadcastMessage(response);

		if (game.getTable().getNumberOfPlayers() == 2 && game.getCurrentState() == GameState.INIT) {
			commandScheduler.schedule(new StartHandCommand(getTargetId(), new Deck(true)), 1, TimeUnit.SECONDS);
		}
	}

}
