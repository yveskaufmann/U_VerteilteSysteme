package de.htw.vs.chat.command;

import de.htw.vs.chat.impl.ChatServer;
import de.htw.vs.chat.IChatServer;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Component
public class ServerCommand implements CommandMarker {

	@CliCommand(value = "chat server", help = "Starts the chat server for the exercise 5.1")
	public void startServer() {
		try {
			String superChat = IChatServer.DEFAULT_SERVER_NAME;
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			ChatServer serverImplementation = new ChatServer(superChat);
			registry.rebind(superChat, (IChatServer) serverImplementation);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
