package de.htw.vs.server;

public class RunnableLifeCycle implements LifeCycle {

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

	public synchronized void stop() {
		isRunning = false;
	}

	public synchronized boolean isRunning() {
		return isRunning;
	}
	
	public void close() {
		stop();
	}

}