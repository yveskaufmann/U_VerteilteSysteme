package de.htw.vs.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Niels on 20.06.2015.
 * The Api for an Chat Client
 */
public interface IChatClient extends Remote
{
	/**
	 * Get the name of the client
	 * @return The name of the client
	 * @throws RemoteException
	 */
	String getName() throws RemoteException;

	/**
	 * Sends a message to the Client
	 * @param message The message
	 * @throws RemoteException
	 */
	void sendMessageToClient(String message) throws RemoteException;
}
