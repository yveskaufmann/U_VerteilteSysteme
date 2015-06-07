package de.htw.vs.semaphore.command;

import de.htw.vs.semaphore.SemaphoreMain;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

/**
 * Created by Niels on 07.06.2015.
 */
@Component
public class SemaphoreCommand implements CommandMarker
{
	/**
	 * The command to start the demo for exercise 4.2
	 */
	@CliCommand(value = "semaphore", help = "Executes the demo for exercise 4.2")
	public void performSemaphoreDemo(){
		SemaphoreMain.StartExample();
	}
}
