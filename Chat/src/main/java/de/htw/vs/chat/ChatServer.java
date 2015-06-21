package de.htw.vs.chat;

import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Niels on 20.06.2015.
 * Implementation of the <code>IChatServer</code> interface
 */
public class ChatServer extends UnicastRemoteObject implements IChatServer
{
	/**
	 * The list of added Clients
	 */
	private ArrayList<IChatClient> allClients;
	/**
	 * The name of the Server
	 */
	private String name;

	/**
	 * Creates a new <code>ChatServer</code>.
	 * @param name The name of teh Server
	 * @throws RemoteException
	 */
	public ChatServer(String name) throws RemoteException
	{
		super();
		this.name = name;
	}

	/**
	 * Starts the <code>ChatServer</code>
	 * @throws MalformedURLException
	 * @throws RemoteException
	 */
	public void start() throws MalformedURLException, RemoteException
	{
		Naming.rebind(name, this);
		System.out.println("Server: "+ name+" started!");
	}

	/**
	 * Adds a Client to the Server
	 *
	 * @param iChatClientRef The reference to the Client
	 *
	 * @return True if the adding was successful
	 *
	 * @throws RemoteException
	 */
	@Override
	public boolean addClient(IChatClient iChatClientRef) throws RemoteException
	{
		String name = iChatClientRef.getName();
		for (Iterator<IChatClient> chatClientIterator = allClients.iterator(); chatClientIterator.hasNext();)
		{
			IChatClient nextChatClient = chatClientIterator.next();
			try
			{
				if(nextChatClient.getName().equals(name))
				{
					return false;
				}
			}catch (RemoteException exp)
			{
				chatClientIterator.remove();
			}
		}
		allClients.add(iChatClientRef);
		return true;
	}

	/**
	 * Removes a Client from the Server
	 *
	 * @param iChatClientRef The reference to the client
	 *
	 * @throws RemoteException
	 */
	@Override
	public void removeClient(IChatClient iChatClientRef) throws RemoteException
	{
		allClients.remove(iChatClientRef);
	}

	/**
	 * Sends a message to all Clients
	 *
	 * @param name    The name of the Sender
	 * @param message The message
	 *
	 * @throws RemoteException
	 */
	@Override
	public void broadcastMessage(String name, String message) throws RemoteException
	{
		for (Iterator<IChatClient> chatClientIterator = allClients.iterator(); chatClientIterator.hasNext();)
		{
			IChatClient nextChatClient = chatClientIterator.next();
			try
			{
				nextChatClient.sendMessageToClient(name + ":" + message);
			}catch (RemoteException exp)
			{
				chatClientIterator.remove();
			}
		}
	}
}
