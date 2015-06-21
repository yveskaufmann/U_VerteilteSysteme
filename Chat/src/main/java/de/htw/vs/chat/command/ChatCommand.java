package de.htw.vs.chat.command;

import de.htw.vs.chat.ChatClient;
import de.htw.vs.chat.ChatServer;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Niels on 07.06.2015.
 */
@Component
public class ChatCommand implements CommandMarker
{
	/**
	 * The command to start the demo for exercise 5.1
	 */
	@CliCommand(value = "chat", help = "Executes the demo for exercise 4.2")
	public void startChat(){
		try
		{
			//Create a server
			ChatServer server = new ChatServer("SuperServer");
			//Create  clients
			ChatClient clientA = new ChatClient("Alfred", "SuperServer");
			ChatClient clientB = new ChatClient("Bernd", "SuperServer");

			//Starts teh server
			server.start();

			//Connects the clients
			clientA.connect();

			clientA.broadcastMessage("I am alone.... *sad face*");

			clientB.connect();


			clientB.broadcastMessage("Ich bin ein Chat Client");
			clientA.broadcastMessage("I am not alone!");
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}
}
