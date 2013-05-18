package com.pokware.ramjet;

public interface CommandManager<E extends CommandTarget> extends CommandScheduler<E> {

	public void submitCommand(Command<E> command);

	public TargetLocator<E> getTargetLocator();

}
