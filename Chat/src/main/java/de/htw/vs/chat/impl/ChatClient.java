package de.htw.vs.chat.impl;

import de.htw.vs.chat.IChatClient;
import de.htw.vs.chat.IChatServer;
import de.htw.vs.chat.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Niels on 20.06.2015.
 * <p>
 * The implementation of the <code>IChatClient</code> interface
 */
public class ChatClient extends UnicastRemoteObject implements IChatClient {

	private static final Logger LOG = LoggerFactory.getLogger(ChatClient.class);

	/**
	 * The chat server proxy reference.
	 */
	private IChatServer server;
	/**
	 * The Nickname
	 */
	private String nickName;
	/**
	 * The host name of the chat server.
	 */
	private String serverHost;

	/**
	 * Creates an ChatClient
	 *
	 * @param nickName   The name of the Client
	 * @param serverHost The hostname of the chat server
	 *
	 * @throws RemoteException if failed to export object
	 */
	public ChatClient(String nickName, String serverHost) throws RemoteException {
		super();
		this.nickName = nickName;
		this.serverHost = serverHost;
	}

	/**
	 * Connects the client to the ChatServer
	 *
	 * @throws RemoteException if the connection to the server failed.
	 * @throws NotBoundException if the IChatServer could not be found inside the rmi registry
	 * @return true if the connection was successful.
	 */
	public boolean connect() throws RemoteException, NotBoundException {
		LOG.info(String.format("'%s' try to connect to the server '%s.", this.nickName, this.serverHost));

		Registry registry = LocateRegistry.getRegistry(this.serverHost);
		server = (IChatServer) registry.lookup(IChatServer.DEFAULT_SERVER_NAME);

		if (server.connect(this)) {
			LOG.info(String.format("'%s' connected successfully to '%s'.", this.nickName, this.serverHost));
			return true;
		} else {
			LOG.warn(String.format("'%s' is already connected to '%s'.", this.nickName, this.serverHost));
		}
		return  false;
	}

	/**
	 * Disconnect the client from the server.
	 *
	 * @throws RemoteException if the disconnecting from the server failed.
	 */
	public void disconnect() throws RemoteException {
		server.disconnect(this);
		LOG.info(String.format("'%s' disconnected from '%s'.", this.nickName, this.serverHost));
	}

	/**
	 * Broadcasts a message to all chat clients
	 *
	 * @param message The message
	 */
	public void sendMessage(String message) {
		try {
			server.broadcastMessage(new Message(nickName, message));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() throws RemoteException {
		return nickName;
	}


	@Override
	public void sendMessageToClient(Message message) throws RemoteException {
		System.out.println(message);
	}
}
