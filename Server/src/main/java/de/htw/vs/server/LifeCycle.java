package de.htw.vs.server;

public interface LifeCycle extends AutoCloseable {
	
	
	void start();
	void stop();
	boolean isRunning();
	
	@Override
	default void close() {
		stop();
	}


}
