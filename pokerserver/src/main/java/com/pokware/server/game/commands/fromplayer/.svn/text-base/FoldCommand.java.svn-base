package com.pokware.server.game.commands.fromplayer;

import com.pokware.ramjet.CommandScheduler;
import com.pokware.server.game.Game;
import com.pokware.server.game.GameCommand;
import com.pokware.server.game.Player;
import com.pokware.server.game.commands.CommandError;
import com.pokware.server.game.commands.fromgame.TimeOutCommand;
import com.pokware.server.states.GameState;

public class FoldCommand extends GameCommand {

	private final Player player;

	public FoldCommand(long gameId, Player player) {
		super(gameId);
		this.player = player;
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

		game.getTurnManager().getCurrentPlayerSeat().setFoldFlag(true);

		game.getTurnManager().nextTurn(commandScheduler);
	}

}
