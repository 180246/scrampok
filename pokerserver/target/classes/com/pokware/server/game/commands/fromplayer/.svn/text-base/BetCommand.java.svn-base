package com.pokware.server.game.commands.fromplayer;

import com.pokware.protobuf.EventResponseFactory;
import com.pokware.protobuf.Events.BetEvent;
import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.game.Game;
import com.pokware.server.game.GameCommand;
import com.pokware.server.game.Player;
import com.pokware.server.game.commands.CommandError;
import com.pokware.server.game.commands.fromgame.TimeOutCommand;
import com.pokware.server.states.GameState;

public class BetCommand extends GameCommand {

	private final Player player;
	private final int amount;

	public BetCommand(long gameId, Player player, int amount) {
		super(gameId);
		this.player = player;
		this.amount = amount;
	}

	@Override
	public int checkPermission(Game game) {
		if (game.getCurrentState() != GameState.WAIT_FOR_PLAYER_BET) {
			return CommandError.GAME_NOT_WAITING_FOR_BET;
		}
		if (!game.getCurrentPlayer().equals(player)) {
			return CommandError.NOT_YOUR_TURN;
		}
		return 0;
	}

	@Override
	public void executeOn(Game game, CommandScheduler<Game> commandScheduler) {
		commandScheduler.unscheduleAll(game.getTargetId(), TimeOutCommand.class);

		game.bet(player, amount);

		BetEvent.Builder betEvent = BetEvent.newBuilder();
		betEvent.setGameId(game.getGameId());
		betEvent.setPlayerId(player.getPlayerId());
		betEvent.setChipsAmount(amount);

		game.broadcastMessage(EventResponseFactory.getBetEventResponse(player.getPlayerId(), game.getGameId(), amount));

		game.getTurnManager().nextTurn(commandScheduler);
	}

}
