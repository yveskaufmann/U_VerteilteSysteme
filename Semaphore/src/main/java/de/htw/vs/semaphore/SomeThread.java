package de.htw.vs.semaphore;


/**
 * A simple Thread that executes a imaginary task that takes the given time.
 */
public class SomeThread extends Thread
{
	private SimpleSemaphore[] sems;

	/**
	 * Initializes a Thread with the given name
	 * @param name The Name of the Thread
	 */
	public SomeThread(String name)
	{
		super(name);
		start();
	}

	/**
	 * Executes the imaginary task with the duration of 0
	 */
	protected void doTask()
	{
			doTask(0);
	}

	/**
	 * Executes the imaginary task with the given duration
	 * @param duration The duration of the task in milliseconds
	 */
	protected void doTask(long duration)
	{
		System.out.println(super.getName() + ": Started Task with duration: " + duration);
		try
		{
			Thread.sleep(duration);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println(super.getName() + ": Finished Task");
	}
}
