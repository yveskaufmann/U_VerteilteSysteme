package de.htw.vs.server;

import java.net.DatagramPacket;

public interface UDPServerWorker extends AutoCloseable {
	byte[] getReceiveBuffer();
	DatagramPacket onReceived(final DatagramPacket packet);
	default void close() throws Exception {}
}
