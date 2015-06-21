package de.htw.vs.chat;

import javax.print.DocFlavor;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Niels on 20.06.2015.
 * The API for an ChatServer
 */
public interface IChatServer extends Remote {

	/**
	 * The default name of a server for convenient use.
	 */
	public static final String DEFAULT_SERVER_NAME = "SuperChat";

	/**
	 * Connects a client to this server.
	 *
	 * @param client The reference to the Client
	 * @return True if the connection was successful
	 * @throws RemoteException
	 */
	boolean connect(IChatClient client) throws RemoteException;

	/**
	 * Disconnects a Client from the Server
	 *
	 * @param client The reference to the client
	 * @throws RemoteException
	 */
	void disconnect(IChatClient client) throws RemoteException;

	/**
	 * Sends a message to all Clients
	 *
	 * @param message The message
	 * @throws RemoteException
	 */
	void broadcastMessage(Message message) throws RemoteException;

}
