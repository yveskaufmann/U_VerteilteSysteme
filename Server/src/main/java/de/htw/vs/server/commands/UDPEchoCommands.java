package de.htw.vs.server.commands;

import de.htw.vs.server.echo.UDPEchoClient;
import de.htw.vs.server.echo.UDPEchoServer;
import de.htw.vs.shell.ConsoleUtil;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintStream;

@Component
public class UDPEchoCommands implements CommandMarker {

	@CliCommand(value= "udp echo", help="Starts an echo server and a echo client")
	public void echoServer() throws IOException {

		PrintStream out = ConsoleUtil.getConsoleWriter();
		UDPEchoServer server = new UDPEchoServer();
		UDPEchoClient client = new UDPEchoClient();
		try {
			server.start();
			client.start();

			String line = null;
			while((line = ConsoleUtil.readline("Please enter your message: ")) != null) {
				client.sendMessage(line);
				String answer = null;
				while((answer = client.receiveMessage()) != null) {
					out.println("Server said:" + answer);
					out.flush();
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
