package de.htw.vs.server.echo;

import java.net.DatagramPacket;

import de.htw.vs.server.UDPServer;
import de.htw.vs.server.UDPServerWorker;

/**
 * <p>The UDPEcho Server for exercise 3.4.</p>
 * <p>This is a simple echo server which
 * sends each incoming message to the sender.</p>
 */
public class UDPEchoServer extends UDPServer {

	/**
	 * The default port which is used by this server.
	 */
	public static final int DEFAULT_PORT = 4242;

	/**
	 * <p>
	 * Create the {@code UDPEchoServer} bind
	 * to the default port 4242.
	 * <p>
	 */
	public UDPEchoServer() {
		this(DEFAULT_PORT);
	}

	/**
	 * <p>
	 * Create the {@code UDPEchoServer} bind
	 * to the port {@code port}.
	 * <p>
	 * @param port the port which should used by this server.
	 */
	public UDPEchoServer(int port) {
		super(port, new UDPServerWorker() {

			byte[] buffer = new byte[1024];

			@Override
			public DatagramPacket onReceived(DatagramPacket packet) {
				return new DatagramPacket(packet.getData(), packet.getLength(), packet.getSocketAddress());
			}

			@Override
			public byte[] getReceiveBuffer() {
				return buffer;
			}
		});
	}

}
