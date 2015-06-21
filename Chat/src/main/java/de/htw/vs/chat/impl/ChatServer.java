package de.htw.vs.chat.impl;

import de.htw.vs.chat.IChatClient;
import de.htw.vs.chat.IChatServer;
import de.htw.vs.chat.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Niels on 20.06.2015.
 *
 * Implementation of the <code>IChatServer</code> interface
 */
public class ChatServer extends UnicastRemoteObject implements IChatServer
{
	/**
	 * The list of added Clients
	 */
	private HashMap<String, IChatClient> connectedClients;

	/**
	 * The name of the Server
	 */
	private String name;

	/**
	 * Creates a new <code>ChatServer</code>.
	 *
	 * @param name The name of teh Server
	 * @throws RemoteException
	 */
	public ChatServer(String name) throws RemoteException {
		super();
		this.name = name;
		connectedClients = new HashMap<>();
	}


	/**
	 * Adds a Client to the Server
	 *
	 * @param client The reference to the Client
	 *
	 * @return True if the adding was successful
	 *
	 * @throws RemoteException
	 */
	@Override
	synchronized
	public boolean connect(IChatClient client) throws RemoteException {
		String name = client.getName();
		if (connectedClients.containsKey(name)) {
			return false;
		}
		connectedClients.put(client.getName(), client);
		return true;
	}

	/**
	 * Removes a Client from the Server
	 *
	 * @param client The reference to the client
	 *
	 * @throws RemoteException
	 */
	@Override
	synchronized
	public void disconnect(IChatClient client) throws RemoteException {
		connectedClients.remove(client.getName());
	}

	/**
	 * Sends a message to all Clients
	 *
	 * @param message The message
	 *
	 * @throws RemoteException
	 */
	@Override
	synchronized
	public void broadcastMessage(Message message) throws RemoteException
	{
		for ( IChatClient client : connectedClients.values()) {
			client.sendMessageToClient(message);
		}
	}

}
