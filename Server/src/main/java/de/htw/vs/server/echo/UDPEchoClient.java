package de.htw.vs.server.echo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import de.htw.vs.server.RunnableLifeCycle;

/**
 * <p>The UDPEcho Client for exercise 3.3.</p>
 * <p>This is a simple echo sends a message to the server
 * an receive the answers.</p>
 */
public class UDPEchoClient extends RunnableLifeCycle implements Runnable {

	/**
	 * The encoding which is used to encode the messages.
	 */
	private static final String MESSAGE_ENCODING = "UTF-8";

	/**
	 * The timeout in milliseconds which is used for this client.
	 */
	private int timeout;

	/**
	 * Contains the messages which should send to the server.
	 */
	private BlockingQueue<byte[]> messages = null;

	/**
	 * The messages which was received from the server.
	 */
	private BlockingQueue<byte[]> answers = null;

	/**
	 * The socket address which contains the address of the remote server.
	 */
	private InetSocketAddress address = null;

	/**
	 * <p>Create a client which connects to the server at localhost:4242</p>
	 */
	public UDPEchoClient() {
		this("127.0.0.1", UDPEchoServer.DEFAULT_PORT);
	}

	/**
	 * <p>Create the server which conncets to the server at localhost:{@code port}</p>
	 * @param port the port on which the server is listening.
	 */
	public UDPEchoClient(int port){this("127.0.0.1", port);}

	/**
	 * <p>Create the server which conncets to the server at {@code host}:4242</p>
	 * @param host the hostname or ip address of the server.
	 */
	public UDPEchoClient(String host){this(host, UDPEchoServer.DEFAULT_PORT);}

	/**
	 * <p>Create the server which conncets to the server at {@code host}:{@code port}</p>
	 * @param host the hostname or ip address of the server.
	 * @param port the port on which the server is listening.
	 */
	public UDPEchoClient(String host, int port) {

		this.timeout = 10;
		messages = new LinkedBlockingDeque<byte[]>();
		answers = new LinkedBlockingDeque<byte[]>();
		address = new InetSocketAddress(host, port);
		setRunnable(this);
	}

	/**
	 * <p>Send a message to the remote server.</p>
	 *
	 * <p>
	 * NOTE: the message is put into a message queue
	 * before it is send to the server.
	 * </p>
	 *
	 * @param message the message which should be send to the server.
	 */
	public void sendMessage(String message) {
		byte[] messageBuffer;
		try {
			messageBuffer = message.getBytes(MESSAGE_ENCODING);
			messages.put(messageBuffer);
		} catch (InterruptedException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Return a message which was received from the server.</p>
	 *
	 * @return the received message or null if no message was receive.
	 */
	public String receiveMessage() {
		if ( messages.isEmpty() ) return null;
		try {
			return new String(answers.take(), MESSAGE_ENCODING);
		} catch (InterruptedException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 *     The echo client thread which sends repeatedly messages from the message queue
	 *     to the server and writes the received messages into the answer queue.
	 * </p>
	 */
	public void run() {
		try(DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(timeout);
			while(isRunning()) {
				try {
					if(!messages.isEmpty()) {
						byte []message = messages.take();
						DatagramPacket msgPacket = new DatagramPacket(message, message.length, address);
						socket.send(msgPacket);
					}
					DatagramPacket msgPacket = new DatagramPacket(new byte[1024], 1024);
					socket.receive(msgPacket);
					answers.put(msgPacket.getData());
				} catch (SocketTimeoutException ex) {
					notifyObservers("timeout");
					continue;
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}

