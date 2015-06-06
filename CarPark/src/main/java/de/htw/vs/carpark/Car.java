package de.htw.vs.carpark;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * A abstraction of a car which tries to park
 * in a car park and stay their for awhile.
 */
public class Car {

	/**
	 * The name of this car.
	 */
	private String name;

	/**
	 * Create a car and named it Car-<code>'carNumber'</code>.
	 *
	 * @param carNumber the number of this car.
	 */
	public Car(int carNumber) {
		this.name = "Car-" + String.format("%02d", carNumber);
	}

	/**
	 * Lets the car park somewhere, waiting for a while and carrying on.
	 */
	public void parkAndStayForAwhile() {
		CarPark carPark = CarParkHolder.getCarPark();
		Random rnd = new Random();

		try {
			Thread.sleep(1000 *  1 + rnd.nextInt(5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driveIn(carPark);


		try {
			Thread.sleep(1000 * 1 + rnd.nextInt(5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driveOut(carPark);
	}

	/**
	 * Drives the car into a car park and parked it there.
	 *
	 * @param carPark where should the car drive in and parked.
	 */
	private void driveIn(CarPark carPark) {
		carPark.letCarEnter(this);
	}

	/**
	 * Drives the car out of a car park.
	 *
	 * @param carPark from where should the car leave.
	 */
	private void driveOut(CarPark carPark) {
		carPark.letCarLeave(this);
	}

	/**
	 * @return the name of this car.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
