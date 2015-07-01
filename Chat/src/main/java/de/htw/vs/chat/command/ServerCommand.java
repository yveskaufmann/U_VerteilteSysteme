package de.htw.vs.chat.command;

import de.htw.vs.chat.impl.ChatServer;
import de.htw.vs.shell.ConsoleUtil;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Objects;

@Component
public class ServerCommand implements CommandMarker
{

	@CliCommand(value = "chat server", help = "Starts the chat server for the exercise 5.1")
	public void startServer()
	{
		try
		{
			ChatServer server = new ChatServer();
			server.start();
			System.out.println("Type \"exit\" to stop the server");
			boolean exit = false;
			do
			{
				String input = null;
				while ((input = ConsoleUtil.readline("")) != null)
				{
					if (Objects.equals(input.toLowerCase(), "exit"))
					{
						exit = true;
						server.stop();
					} else
					{
						System.out.println("Type \"exit\" to stop the server");
					}
				}
			} while (server.isRunning && !exit);

		} catch (IOException | NotBoundException e)
		{
			e.printStackTrace();
		}
	}
}
