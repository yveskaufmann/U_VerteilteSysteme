package de.htw.vs.server;

import org.springframework.context.SmartLifecycle;

/**
 * A RunnableLifeCycle is encapsulated thread
 * which have support for starting and stopping a thread.
 *
 * @see SmartLifecycle
 */
public class RunnableLifeCycle implements SmartLifecycle {

	private Thread thread;
	private Runnable runnable;
	private boolean isRunning;

	/**
	 * Set the {@link Runnable} which should used for the thread.
	 *
	 * @param runnable the runnable which should used for the thread.
	 */
	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	/**
	 * Start the encapsulated thread.
	 */
	public synchronized void start() {
		if(runnable == null) {
			throw new IllegalArgumentException("Please provide a Runnyable by using setRunnable.");
		}

		if(isRunning()) {
			throw new IllegalStateException("The " + this.getClass().getName() +  " is already running");
		}

		thread = new Thread(runnable);
		thread.start();
		isRunning = true;
	}

	/**
	 * Notify the thread that the thread should be terminated.
	 */
	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}


	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public boolean isAutoStartup() {
		return false;
	}


	@Override
	public int getPhase() {
		return 1;
	}
}
