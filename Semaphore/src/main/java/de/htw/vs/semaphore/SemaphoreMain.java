package de.htw.vs.semaphore;


import java.util.Random;

/**
 * A simple semaphore demo
 */
public class SemaphoreMain
{

	private static Random rnd = new Random();

	/**
	 * Returns a random int between <code>min</code> and <code>max</code>
	 *
	 * @param min The minimum value
	 * @param max The maximum value
	 *
	 * @return A Random value between <code>min</code> and <code>max</code>
	 *
	 * @throws IllegalArgumentException The max value needs to be bigger than the min value"
	 */
	private static int getRandomDuration(int min, int max) throws IllegalArgumentException
	{
		if (min >= max)
			throw new IllegalArgumentException("The max value needs to be bigger than the min value");
		return (min + rnd.nextInt(max - min));
	}

	/**
	 * Starts the example
	 */
	public static void StartExample()
	{
		SimpleSemaphore[] simpleSemaphores = new SimpleSemaphore[6];
		for (int i = 0; i < simpleSemaphores.length; i++)
			simpleSemaphores[i] = new SimpleSemaphore(0);

		SomeThread t1 = new SomeThread("T1")
		{
			@Override
			public void run()
			{
				doTask(getRandomDuration(50, 100));
				simpleSemaphores[0].NotifyToWakeUp();
				simpleSemaphores[1].NotifyToWakeUp();
				simpleSemaphores[2].NotifyToWakeUp();
			}
		};

		SomeThread t2 = new SomeThread("T2")
		{
			@Override
			public void run()
			{
				simpleSemaphores[0].WaitForNotify();
				doTask(getRandomDuration(200, 500));
				simpleSemaphores[3].NotifyToWakeUp();
			}
		};
		SomeThread t3 = new SomeThread("T3")
		{
			@Override
			public void run()
			{
				simpleSemaphores[1].WaitForNotify();
				doTask(getRandomDuration(200, 500));
				simpleSemaphores[4].NotifyToWakeUp();
			}
		};

		SomeThread t4 = new SomeThread("T4")
		{
			@Override
			public void run()
			{
				simpleSemaphores[2].WaitForNotify();
				doTask(getRandomDuration(200, 500));
				simpleSemaphores[5].NotifyToWakeUp();
			}
		};
		SomeThread t5 = new SomeThread("T5")
		{
			@Override
			public void run()
			{
				simpleSemaphores[3].WaitForNotify();
				simpleSemaphores[4].WaitForNotify();
				simpleSemaphores[5].WaitForNotify();
				doTask(getRandomDuration(50, 100));
			}
		};

		// Ensures that the control is only send back to
		// the shell if all threads are runned.
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
		} catch(InterruptedException ex) {}
	}

}
