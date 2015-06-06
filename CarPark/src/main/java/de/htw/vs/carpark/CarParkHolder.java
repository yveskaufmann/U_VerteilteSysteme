package de.htw.vs.carpark;

/**
 * Provides cars access to the same instance of a car park.
 */
public class CarParkHolder {

	/**
	 * The instance of a car park which should shared between all cars.
	 */
	static
	private CarPark carParkInstance;

	/**
	 * <p>
	 * Returns the the shared car park instance which
	 * was created by the call of <code>CarParkHolder.init</code>.
	 * </p>
	 * <p>
	 * If the caller omits the call of <code>CarParkHolder.init</code>
	 * then a CarPark with ten parking spaces will be returned.
	 * </p>
	 *
	 * @return the CarPark instance which is shared between all cars.
	 */
	public static CarPark getCarPark() {
		if (CarParkHolder.carParkInstance == null) {
			CarParkHolder.carParkInstance = new CarPark();
		}
		return CarParkHolder.carParkInstance;
	}

	/**
	 * Initializes the CarParkHolder with
	 * a <code>CarPark</code> instance with the <code>parkingSpaces</code>.
	 *
	 * @param parkingSpaces the number of parking spaces of the containing car park.
	 */
	public static void init(int parkingSpaces) {
		CarParkHolder.carParkInstance = new CarPark(parkingSpaces);
	}
}
