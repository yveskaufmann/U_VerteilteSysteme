package de.htw.vs.chat.command;

import de.htw.vs.chat.impl.ChatServer;
import de.htw.vs.shell.ConsoleUtil;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

@Component
public class ServerCommand implements CommandMarker {

	@CliCommand(value = "chat server", help = "Starts the chat server for the exercise 5.1")
	public void startServer() {
		ChatServer server = null;
		try {
			server = new ChatServer();
			server.start();
			System.out.println("Type \"exit\" to stop the server");
			boolean exit = false;
			do {
				String input = null;
				System.out.println("Type \"exit\" to stop the server");
				while ((input = ConsoleUtil.readline("")) != null) {
					exit = "exit".equalsIgnoreCase(input);
				}
			} while (server.isRunning && !exit);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.stop();
				} catch (Exception e) {}
			}
 		}
	}
}
