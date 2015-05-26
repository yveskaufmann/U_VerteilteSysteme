package de.htw.vs.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

/**
 * <p>
 * A simple datagram server which listen on a {@code port}
 * for a incoming udp messages until the server is stopped by using {@link RunnableLifeCycle#stop()}.
 * </p>
 *
 * <p>
 * A incoming message is processed by a {@link UDPServerWorker} which builds
 * the response {@link DatagramPacket} which is resend to the client by the server.
 * </p>
 */
public class UDPServer extends RunnableLifeCycle {

	/**
	 * Is used for processing incoming {@link DatagramPacket}s
	 * and building the response {@link DatagramPacket}.
	 */
	protected UDPServerWorker worker;

	/**
	 * The port number which should used by this server.
	 * @see #setPort(int)
	 */
	protected int port;

	/**
	 * <p>
	 * The timeout in milliseconds which should used by this server.
	 * </p>
	 *
	 * <p>
	 * The timeout is need because {@link DatagramSocket#receive(DatagramPacket)}
	 * is a blocking operation and this prevents the server from stopping.
	 * </p>
	 */
	protected int timeout;

	/**
	 * <p>Create the simple udp server.</p>
	 *
	 * @param port the port which should used by the server
	 *             @see UDPServer#setPort(int)
	 * @param worker the worker which should used by the server.
	 *
	 * @see UDPServerWorker
	 */
	public UDPServer(int port, UDPServerWorker worker) {
		setPort(port);
		this.timeout = 1000;
		this.worker = worker;
		setRunnable(this::doServerProcessing);
	}

	/**
	 * <p>Process incoming udp messages.</p>
	 */
	protected void doServerProcessing() {
		byte[] recvBuffer = worker.getReceiveBuffer();
		try(DatagramSocket ds = new DatagramSocket(getPort())) {
			ds.setSoTimeout(timeout);
			while(isRunning()) {
				DatagramPacket packet = new DatagramPacket(recvBuffer, recvBuffer.length);
				try {
					ds.receive(packet);
				} catch(SocketTimeoutException ex) {
					continue;
				}
				ds.send(worker.onReceived(packet));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the port which is used by the server.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Set the port which should used by  the server.
	 *
	 * @param port the port number must between 1025 and 65536
	 * @throws IllegalStateException if the server is already running.
	 * @throws IllegalArgumentException if the given port number is invalid
	 */
	public void setPort(int port) {
		if (isRunning()) {
			throw new IllegalStateException("The server is already running");
		}
		if (port <= 1024 || port > (Short.MAX_VALUE * 2)) {
			throw new IllegalArgumentException("The port must between 1025 and 65536");
		}
		this.port = port;
	}
}
