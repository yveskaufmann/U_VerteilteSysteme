package de.htw.vs.server;

public interface LifeCycle extends AutoCloseable {
	
	
	public void start();
	public void stop();
	public boolean isRunning();
	
	@Override
	default public void close() {
		stop();
	};

	
}
