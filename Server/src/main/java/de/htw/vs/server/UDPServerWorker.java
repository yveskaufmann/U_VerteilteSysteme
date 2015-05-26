package de.htw.vs.server;

import java.net.DatagramPacket;

/**
 * <p>
 * A interface to be implemented by any object
 * which should process incoming udp messages.
 * </p>
 */
public interface UDPServerWorker extends AutoCloseable {

	/**
	 * <p>
	 * Returns the buffer which should used for storing incoming {@link DatagramPacket}s.
	 * </p>
	 *
	 * @return a byte array of the size &gt; 0.
	 */
	byte[] getReceiveBuffer();

	/**
	 * <p>
	 * Handles an incoming {@link DatagramPacket} and should return a {@link DatagramPacket}
	 * for resending to the client.
	 * </p>
	 * <p>
	 * Is called by a {@link UDPServer} when a incoming datagram packet
	 * is received.
	 * </p>
	 *
	 * @param packet the received {@link DatagramPacket}.
	 *
	 * @return the {@link DatagramPacket} which should send to the sender.
	 */
	DatagramPacket onReceived(final DatagramPacket packet);

	@Override
	default void close() throws Exception {}
}
