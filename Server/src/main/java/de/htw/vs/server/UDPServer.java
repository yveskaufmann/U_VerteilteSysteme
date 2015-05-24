package de.htw.vs.server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPServer extends RunnableLifeCycle {
	
	protected UDPServerWorker worker;
	protected int port;
	protected int timeout;
	
	
	public UDPServer(int port, UDPServerWorker worker) {
		this.port = port;
		this.timeout = 1000;
		this.worker = worker;
		setRunnable(this::doServerProcessing);
	}

	protected void doServerProcessing() {
		byte[] recvBuffer = worker.getReceiveBuffer();
		try(DatagramSocket ds = new DatagramSocket(port)) {
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
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		if(port <= 1024 || port > (Short.MAX_VALUE * 2)) {
			throw new IllegalArgumentException("Default Ports(0 - 1024) are not allowed.");
		}
		this.port = port;
	}
	
}
