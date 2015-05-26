package de.htw.vs.server;

import org.springframework.context.SmartLifecycle;

public class RunnableLifeCycle implements SmartLifecycle {

	private Thread thread;
	private Runnable runnable;
	private boolean isRunning;

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

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

	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

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
