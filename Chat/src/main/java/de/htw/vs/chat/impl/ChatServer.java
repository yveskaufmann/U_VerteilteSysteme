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
public class ChatServer extends UnicastRemoteObject implements IChatServer {

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

	/**
	 * The RMI registry
	 */
	private Registry registry;


	/**
	 * Creates a new <code>ChatServer</code>.
	 *
	 * @throws RemoteException if failed to export object
	 */
	public ChatServer() throws RemoteException {
		this(DEFAULT_SERVER_NAME);
	}

	/**
	 * Creates a new <code>ChatServer</code>.
	 *
	 * @param name The name of the Server
	 *
	 * @throws RemoteException if failed to export object
	 */
	public ChatServer(String name) throws RemoteException {
		super();
		this.name = name;
		connectedClients = new HashMap<>();
		registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	}

	/**
	 * Starts the Server and register it into the rmi registry.
	 *
	 * @throws RemoteException if there was an error while starting the server or the server
	 * 						   is already bind to the rmi registry
	 */
	public void start() throws RemoteException {
		registry.rebind(name, (IChatServer) this);
		isRunning = true;
		LOG.info("Server " + name + " started");
	}

	/**
	 * Stops the Server and unbind this server from the rmi registry.
	 *
	 * @throws RemoteException  if there was an error while stoping the server
	 * @throws NotBoundException if the there was not already started.
	 */
	public void stop() throws RemoteException, NotBoundException {
		registry.unbind(name);
		isRunning = false;
		LOG.info("Server " + name + " stopped");
	}

	@Override
	synchronized public boolean connect(IChatClient client) throws RemoteException {
		String name = client.getName();
		if (connectedClients.containsKey(name)) {
			LOG.info("Client " + name + " cannot connect because another client has the name already");
			return false;
		}
		connectedClients.put(client.getName(), client);
		LOG.info("Client " + name + " connected");
		return true;
	}


	@Override
	synchronized public void disconnect(IChatClient client) throws RemoteException {
		connectedClients.remove(client.getName());
		LOG.info("Client " + client.getName() + " removed");
	}


	@Override
	synchronized public void broadcastMessage(Message message) {
		LOG.info("Broadcasting message from " + message.getUsername() + ": " + message.getText());
		for (IChatClient client : connectedClients.values()) {
			try {
				client.sendMessageToClient(message);
			} catch (RemoteException e) {
				connectedClients.remove(client);
			}
		}
	}

}
