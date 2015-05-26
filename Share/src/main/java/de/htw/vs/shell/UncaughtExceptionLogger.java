package de.htw.vs.shell;

import org.apache.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * An {@link UncaughtExceptionHandler} which logs all uncaught exceptions
 * to a Logger.
 */
public class UncaughtExceptionLogger implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable ex) {
		 if (! (ex instanceof ThreadDeath)) {
			 logException(t, ex);
		 }
	}

	private void logException(Thread t, Throwable ex) {
		String errorMessage = String.format("Exception in thread '%s'",t);
		Logger.getLogger(getNameOfClassWhichThrowed(ex)).error(errorMessage, ex);
	}

	private String getNameOfClassWhichThrowed(Throwable ex) {
		StackTraceElement[] stacktrace = ex.getStackTrace();
		return  stacktrace.length > 0 ? stacktrace[0].getClassName() : "Unknown" ;
	}

}
