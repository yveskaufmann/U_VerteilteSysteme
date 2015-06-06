package de.htw.vs.carpark.command;

import de.htw.vs.carpark.Car;
import de.htw.vs.carpark.CarParkHolder;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class CarParkCommand implements CommandMarker {

	@CliCommand(value = "car-park", help = "Executes the demo for exercise 4.1.")
	public void performCarParkDemo(

		@CliOption( key = {"parkingSpaces","p"},
				mandatory=false,
				unspecifiedDefaultValue="5",
				help="The number of cars which tries to park in the park house."
		) final int parkingSpaces,

		@CliOption( key = {"cars", "c"},
					mandatory=false,
					unspecifiedDefaultValue="7",
					help="The number of cars which tries to park in the park house."
		) final int numberOfCars) {


		CarParkHolder.init(parkingSpaces);

		List<Thread> carThreads = IntStream.rangeClosed(1, numberOfCars)
			.mapToObj(CarParkCommand::createCarThreadAndStart)
			.collect(toList());

		carThreads.forEach((thread) -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});


	}

	/**
	 *
	 * Convenient method for creating and starting a car thread.
	 *
	 * @param number the number of new car.
	 * @return the created car thread.
	 *
	 */
	public static Thread createCarThreadAndStart(int number) {
		Car car = new Car(number);
		Thread carThread = new Thread(car::parkAndStayForAwhile);
		carThread.start();
		return carThread;
	}



}
