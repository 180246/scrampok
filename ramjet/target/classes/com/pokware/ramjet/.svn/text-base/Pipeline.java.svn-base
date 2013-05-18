package com.pokware.ramjet;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A highly scalable command execution pipeline. Consisting of a queue of {@link Command} that operates exclusivery on its own sets of {@link CommandTarget}
 * (This is to guarantee that two commands can't be concurrently executed on one single {@link CommandTarget}).
 * 
 * @author Fabien Benoit <fabien.benoit@pokware.com>
 * 
 * @param <E>
 */
public class Pipeline<E extends CommandTarget> {

	private static final int QUEUE_SIZE = 40;

	private final Thread thread;
	private final Thread waitingListThread;

	private Logger logger;

	private transient final ArrayBlockingQueue<Command<E>> commandQueue = new ArrayBlockingQueue<Command<E>>(QUEUE_SIZE);

	private final PriorityBlockingQueue<Command<E>> commandWaitingList;
	private final Set<Long> targetWaitingSet;

	public Pipeline(int id, final RamJetEngine<E> engine) {
		this.logger = LoggerFactory.getLogger("Pipeline-" + id);

		this.commandWaitingList = new PriorityBlockingQueue<Command<E>>(100, new Comparator<Command<E>>() {
			@Override
			public int compare(Command<E> o1, Command<E> o2) {
				long diff = o1.getSchedulingTimestamp() - o2.getSchedulingTimestamp();
				if (diff < 0) {
					return -1;
				} else if (diff > 0) {
					return 1;
				}
				return 0;
			}
		});

		this.targetWaitingSet = Collections.synchronizedSet(new HashSet<Long>());

		this.thread = new Thread("Pipeline-" + id) {
			@Override
			public void run() {
				boolean terminated = false;
				while (!terminated) {
					try {
						Command<E> command = commandQueue.take();
						if (command != null) {
							long targetId = command.getTargetId();
							TargetLocator<E> targetLocator = engine.getTargetLocator();
							E target = targetLocator.locate(targetId);

							int result = command.checkPermission(target);
							if (result == 0) {
								try {
									logger.debug("Executing command {} ...", command);
									command.executeOn(target, engine);
								} catch (Exception e) {
									logger.error("Exception ({}) during command execution of {}", e, command);
									logger.debug("Stack trace", e);
								}
							} else {
								logger.error("Permission denied for command {} : Error {}", command, result);
							}
						}
					} catch (InterruptedException e) {
						terminated = true;
					}
				}
			}
		};

		this.waitingListThread = new Thread("Pipeline-Scheduled-" + id) {
			@Override
			public void run() {
				while (true) {
					while (!commandWaitingList.isEmpty() && commandWaitingList.peek().getSchedulingTimestamp() < System.currentTimeMillis()) {
						Command<E> waitingCommand = commandWaitingList.remove();
						if (targetWaitingSet.contains(waitingCommand.getTargetId())) {
							putCommand(waitingCommand);
						}
					}
					try {
						synchronized (commandWaitingList) {
							while (commandWaitingList.isEmpty() || commandWaitingList.peek().getSchedulingTimestamp() < System.currentTimeMillis()) {
								commandWaitingList.wait(500);
							}
						}
					} catch (InterruptedException e) {
					}
				}
			}
		};

		this.waitingListThread.setDaemon(true);
		this.waitingListThread.start();

		this.thread.setDaemon(false);
		this.thread.start();
	}

	/**
	 * Add a command to the pipeline.
	 * 
	 * @param command
	 *            The command to add.
	 */
	public void putCommand(Command<E> command) {
		try {
			this.commandQueue.put(command);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void scheduleCommand(Command<E> command, long delay, TimeUnit timeUnit) {
		command.setSchedulingTimestamp(System.currentTimeMillis() + timeUnit.toMillis(delay));
		synchronized (commandWaitingList) {
			this.commandWaitingList.put(command);
			this.commandWaitingList.notify();
		}
		this.targetWaitingSet.add(command.getTargetId());
	}

	/**
	 * Wait for the pipeline to empty. Caution: this does not prevent other commands to be added. Client code must make sure no other threads will add new
	 * commands.
	 */
	public void sync() {
		while (!this.commandQueue.isEmpty()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void unscheduleAll(Long targetId) {
		this.targetWaitingSet.remove(targetId);
	}
}
