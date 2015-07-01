package de.htw.vs.chat.impl;

import de.htw.vs.chat.IChatClient;
import de.htw.vs.chat.IChatServer;
import de.htw.vs.chat.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * Created by Niels on 20.06.2015.
 * <p>
 * Implementation of the <code>IChatServer</code> interface
 */
public class ChatServer extends UnicastRemoteObject implements IChatServer
{

	private static final Logger LOG = LoggerFactory.getLogger(ChatServer.class);
	/**
	 * Flag to see if the server is running
	 */
	public boolean isRunning = false;
	/**
	 * The list of added Clients
	 */
	private HashMap<String, IChatClient> connectedClients;
	/**
	 * The name of the Server
	 */
	private String name;
	private Registry registry;


	public ChatServer() throws RemoteException
	{
		this(DEFAULT_SERVER_NAME);
		registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	}

	/**
	 * Creates a new <code>ChatServer</code>.
	 *
	 * @param name The name of teh Server
	 *
	 * @throws RemoteException
	 */
	public ChatServer(String name) throws RemoteException
	{
		super();
		this.name = name;
		connectedClients = new HashMap<>();
	}

	/**
	 * Starts the Server
	 *
	 * @throws RemoteException
	 */
	public void start() throws RemoteException
	{
		registry.rebind(name, (IChatServer) this);
		isRunning = true;
		LOG.info("Server " + name + " started");
	}

	public void stop() throws RemoteException, NotBoundException
	{
		registry.unbind(name);
		isRunning = false;
		LOG.info("Server " + name + " stopped");

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
	synchronized public boolean connect(IChatClient client) throws RemoteException
	{
		String name = client.getName();
		if (connectedClients.containsKey(name))
		{
			LOG.info("Client " + name + " cannot connect because another client has the name already");
			return false;
		}
		connectedClients.put(client.getName(), client);
		LOG.info("Client " + name + " connected");
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
	synchronized public void disconnect(IChatClient client) throws RemoteException
	{
		connectedClients.remove(client.getName());
		LOG.info("Client " + client.getName() + " removed");
	}

	/**
	 * Sends a message to all Clients
	 *
	 * @param message The message
	 *
	 * @throws RemoteException
	 */
	@Override
	synchronized public void broadcastMessage(Message message)
	{
		LOG.info("Broadcasting message from " + message.getUsername() + ": " + message.getText());
		for (IChatClient client : connectedClients.values())
		{
			try
			{
				client.sendMessageToClient(message);
			} catch (RemoteException e)
			{
				connectedClients.remove(client);
			}
		}
	}

}
