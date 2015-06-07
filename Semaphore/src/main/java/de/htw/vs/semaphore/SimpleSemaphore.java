package de.htw.vs.semaphore;

/**
 * Provides a simple to use Semaphore
 */
public class SimpleSemaphore
{
	private int value = 0;

	/**
	 * Initaites the as Simple Semaphore
	 * @param init The number of threads the semaphore can handle
	 */
	public SimpleSemaphore(int init) {
			if(init > 0)
				value = init;
	}

	/**
	 * Signals the thread to wait
	 */
	public synchronized void WaitForNotify()
	{
		while (value == 0)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		value--;
	}

	/**
	 * Notify´s the thread to wake up
	 */
	public synchronized void NotifyToWakeUp() {
		value++;
		notify();
	}
}
