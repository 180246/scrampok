package com.pokware.ramjet;

public abstract class Command<E extends CommandTarget> {

	private long schedulingTimestamp = 0;

	public abstract long getTargetId();

	public abstract int checkPermission(E game);

	public abstract void executeOn(E target, CommandScheduler<E> commandManager);

	public void setSchedulingTimestamp(long l) {
		this.schedulingTimestamp = l;
	}

	public long getSchedulingTimestamp() {
		return schedulingTimestamp;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " for target #" + getTargetId();
	}

}
