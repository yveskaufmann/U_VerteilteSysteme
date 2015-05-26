package de.htw.vs.server.echo;

import java.net.DatagramPacket;

import de.htw.vs.server.UDPServer;
import de.htw.vs.server.UDPServerWorker;

public class UDPEchoServer extends UDPServer {

	public static final int DEFAULT_PORT = 4242;

	public UDPEchoServer() {
		this(DEFAULT_PORT);
	}

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
