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

public class UDPEchoClient extends RunnableLifeCycle implements Runnable {

	private static final String MESSAGE_ENCODING = "UTF-8";
	private int timeout;
	private BlockingQueue<byte[]> messages = null;
	private BlockingQueue<byte[]> answers = null;
	private InetSocketAddress address = null;


	public UDPEchoClient() {
		this("127.0.0.1", UDPEchoServer.DEFAULT_PORT);
	}

	public UDPEchoClient(String host, int port) {

		this.timeout = 10;
		messages = new LinkedBlockingDeque<byte[]>();
		answers = new LinkedBlockingDeque<byte[]>();
		address = new InetSocketAddress(host, port);
		setRunnable(this);
	}


	public void sendMessage(String message) {
		byte[] messageBuffer;
		try {
			messageBuffer = message.getBytes(MESSAGE_ENCODING);
			messages.put(messageBuffer);
		} catch (InterruptedException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String receiveMessage() {
		if ( messages.isEmpty() ) return null;
		try {
			return new String(answers.take(), MESSAGE_ENCODING);
		} catch (InterruptedException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

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
					continue;
				}
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}

