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
	 * The default name of a server for convenient use.
	 */
	public static final String DEFAULT_SERVER_NAME = "SuperChat";

	/**
	 * Connects a client to this server.
	 *
	 * @param client The reference to the Client
	 *
	 * @return True if the connection was successful
	 *
	 * @throws RemoteException if there was a error while the client tried to
	 * connect.
	 */
	boolean connect(IChatClient client) throws RemoteException;

	/**
	 * Disconnects a Client from the Server
	 *
	 * @param client The reference to the client
	 *
	 * @throws RemoteException if there was a error while the client tried to
	 * disconnect
	 */
	void disconnect(IChatClient client) throws RemoteException;

	/**
	 * Sends a message to all Clients
	 *
	 * @param message The message
	 *
	 * @throws RemoteException if there was a error while the server sends a
	 * message to all connected clients
	 */
	void broadcastMessage(Message message) throws RemoteException;

}
