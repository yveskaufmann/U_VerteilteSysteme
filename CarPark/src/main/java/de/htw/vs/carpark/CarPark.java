package de.htw.vs.carpark;

/**
 * A abstraction of a car park which is used
 * to practicing parallel programming.
 *
 * The task of this exercise is to simulate a car park
 * which is used by multiple cars (treads) in order
 * to do that this class must be thread safe.
 */
public class CarPark {

	/**
	 * The maximum number of parking spaces
	 * of this car park.
	 */
	private int parkingSpaces;

	/**
	 * The count of free parking spaces.
	 */
	volatile
	private int freeParkingSpaces;

	/**
	 * The next waiting number which
	 * lets cars park in fifo order.
	 */
	volatile
	private int nextWaitingNumber;

	/**
	 * The current waiting number which
	 * lets cars park in fifo order.
	 */
	volatile
	private int currentWaitingNumber;

	/**
	 * Creates a car park with 10 parking spaces.
	 */
	public CarPark() {
		this(10);
	}

	/**
	 * Creates a car park with <code>parkingSpaces</code> parking spaces.
	 *
	 * @param parkingSpaces the number of parking spaces this car park should provide.
	 */
	public CarPark(int parkingSpaces) {
		this.parkingSpaces = parkingSpaces;
		this.freeParkingSpaces = this.parkingSpaces;
		this.nextWaitingNumber = 0;
		this.currentWaitingNumber = 0;
	}

	/**
	 * @return the number of free parking spaces.
	 */
	synchronized
	public int getFreeParkingSpaces() {
		return this.freeParkingSpaces;
	}

	/**
	 * @return the number of parking spaces which are provided by this car park.
	 */
	public int getParkingSpaces() {
		return parkingSpaces;
	}

	/**
	 * <p>
	 * Let's a car enter in to the car park.
	 * </p>
	 * <p>
     * If the car park is already full then
	 * the car/thread have to wait for an empty place.
	 * </p>
	 *
	 * @param car the car which enter the car park.
	 */
	synchronized
	public void letCarEnter(Car car) {
		System.out.println(car + " try to park");
		int carsWaitingNumber = nextWaitingNumber++;
		while(this.currentWaitingNumber != carsWaitingNumber || this.getFreeParkingSpaces() == 0) {
			silentWait();
		}

		this.freeParkingSpaces--;
		this.currentWaitingNumber++;

		System.out.println(car + " parked");
		notifyAll();
	}

	/**
	 * <p>
	 * Let's a car leave car park.
	 * </p>
	 * <p>
	 * If the car park is already empty then
	 * this call is ignored.
	 * </p>
	 *
	 * @param car the car which leaves the car park.
	 */
	synchronized
	public void letCarLeave(Car car) {
		System.out.println(car + " try to leave");

		// There are no cars left ?
		if (this.getFreeParkingSpaces() >= this.getParkingSpaces()) {
			this.currentWaitingNumber = 0;
			this.nextWaitingNumber = 0;
			return;
		}

		this.freeParkingSpaces++;
		System.out.println(car + " leaved");
		this.notifyAll();
	}

	/**
	 * Helper method which calls this.wait
	 * and suppress the InterruptedException of
	 * object.wait.
	 */
	private void silentWait() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
