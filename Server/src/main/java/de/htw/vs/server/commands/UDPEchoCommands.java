package de.htw.vs.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import de.htw.vs.server.echo.UDPEchoClient;
import de.htw.vs.server.echo.UDPEchoServer;

@Component
public class UDPEchoCommands implements CommandMarker {
	
	@CliCommand(value= "udp echo", help="Starts an echo server and a echo client")
	public void echoServer() throws IOException {
		UDPEchoServer server = new UDPEchoServer();
		UDPEchoClient client = new UDPEchoClient();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			server.start();
			client.start();
			String line = null;
			while((line = reader.readLine().trim()) != "" && line.length() > 0) {
				client.sendMessage(line);
				String answer = null;
				
				while((answer = client.recvMessage()) != null) {
					System.out.println(answer);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			server.close();
			client.close();
		}
	}
}
