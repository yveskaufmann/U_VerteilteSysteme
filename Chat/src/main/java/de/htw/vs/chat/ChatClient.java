package de.htw.vs.chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Niels on 20.06.2015.
 * The implementation of the <code>IChatClient</code> interface
 */
public class ChatClient extends UnicastRemoteObject implements IChatClient
{
	/**
	 * The chat server
	 */
	private IChatServer server;
	/**
	 * The Nickname
	 */
	private String nickName;
	/**
	 * The server name
	 */
	private String serverName;

	/**
	 * Creates an ChatClient
	 * @param nickName The name of the Client
	 * @param serverName The name of the Server
	 * @throws RemoteException
	 */
	public ChatClient(String nickName, String serverName) throws RemoteException
	{
		super();
		this.nickName = nickName;
		this.serverName = serverName;
			}

	/**
	 * Connects to the ChatServer
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	public void connect() throws RemoteException, NotBoundException, MalformedURLException
	{
		server = (IChatServer) Naming.lookup("rmi://"+serverName+"/chatserver");
	}

	/**
	 * Broadcasts a message to all chat clients
	 * @param message The message
	 */
	public void broadcastMessage(String message)
	{
		try
		{
			server.broadcastMessage(nickName, message);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Get the name of the client
	 *
	 * @return The name of the client
	 *
	 * @throws RemoteException
	 */
	@Override
	public String getName() throws RemoteException
	{
		return nickName;
	}

	/**
	 * Sends a message to the Client and prints it on the Clients screen
	 *
	 * @param message The message
	 *
	 * @throws RemoteException
	 */
	@Override
	public void sendMessageToClient(String message) throws RemoteException
	{
		System.out.print(message);
	}
}
