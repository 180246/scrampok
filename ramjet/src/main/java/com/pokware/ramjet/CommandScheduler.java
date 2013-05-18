package com.pokware.ramjet;

import java.util.concurrent.TimeUnit;

public interface CommandScheduler<E extends CommandTarget> {

	public void schedule(Command<E> command, long delay, TimeUnit timeUnit);

	public boolean isScheduled(Command<E> command);

	public void unscheduleAll(Long targetId, Class<? extends Command<E>> commandClazz);

}
