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
	 *
	 * @return The name of the client
	 *
	 * @throws RemoteException when there is a error while retrieving the the
	 * username
	 */
	String getName() throws RemoteException;

	/**
	 * Sends a message to the Client
	 *
	 * @param message The message
	 *
	 * @throws RemoteException when there is a error while sending the message
	 * to the client
	 */
	void sendMessageToClient(Message message) throws RemoteException;
}
