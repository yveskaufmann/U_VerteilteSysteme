package de.htw.vs.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Niels on 20.06.2015.
 * The API for an ChatServer
 */
public interface IChatServer extends Remote
{
	/**
	 * Adds a Client to the Server
	 * @param iChatClientRef The reference to the Client
	 * @return True if the adding was successful
	 * @throws RemoteException
	 */
	boolean addClient(IChatClient iChatClientRef) throws RemoteException;

	/**
	 * Removes a Client from the Server
	 * @param iChatClientRef The reference to the client
	 * @throws RemoteException
	 */
	void removeClient(IChatClient iChatClientRef) throws RemoteException;

	/**
	 * Sends a message to all Clients
	 * @param name The name of the Sender
	 * @param message The message
	 * @throws RemoteException
	 */
	void broadcastMessage(String name, String message) throws RemoteException;
}
