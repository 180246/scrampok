package com.pokware.server.game.commands.fromgame;

import com.pokware.protobuf.EventResponseFactory;
import com.pokware.protobuf.Events.HandStartEvent;
import com.pokware.protobuf.Events.ServerEvent;
import com.pokware.protobuf.Events.ServerEvent.Builder;
import com.pokware.protobuf.Events.ServerEvent.Type;
import com.pokware.protobuf.Events.TimeOutEvent;
import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.game.Game;
import com.pokware.server.game.GameCommand;
import com.pokware.server.game.Player;
import com.pokware.server.game.commands.CommandError;

public class TimeOutCommand extends GameCommand {

	private final Player player;

	public TimeOutCommand(long gameId, Player player) {
		super(gameId);
		this.player = player;
	}

	@Override
	public int checkPermission(Game game) {
		if (!game.getCurrentPlayer().equals(player)) {
			return CommandError.NOT_YOUR_TURN;
		}
		return 0;
	}

	@Override
	public void executeOn(Game game, CommandScheduler<Game> commandScheduler) {
		game.addObserver(player);
		game.getTable().getSeat(player).setFoldFlag(true);
		game.getTable().getSeat(player).setAwayFlag(true);
		
		game.broadcastMessage(EventResponseFactory.getTimeOutEventResponse(game.getGameId()));
				
		game.getTurnManager().nextTurn(commandScheduler);
	}

}
