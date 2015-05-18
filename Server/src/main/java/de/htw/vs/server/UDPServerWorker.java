package de.htw.vs.server;

import java.net.DatagramPacket;

public interface UDPServerWorker extends AutoCloseable {
	public byte[] getRecvBuffer();
	public DatagramPacket onReceived(final DatagramPacket packet);
	default public void close() throws Exception {}
}
