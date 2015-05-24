package de.htw.vs.server.commands;

import java.io.IOException;
import java.io.PrintStream;

import jline.TerminalSupport;
import jline.console.ConsoleReader;

import org.fusesource.jansi.AnsiConsole;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import de.htw.vs.server.echo.UDPEchoClient;
import de.htw.vs.server.echo.UDPEchoServer;

@Component
public class UDPEchoCommands implements CommandMarker {
	
	@CliCommand(value= "udp echo", help="Starts an echo server and a echo client")
	public void echoServer() throws IOException {
		
		PrintStream out = AnsiConsole.out;
		ConsoleReader reader = new ConsoleReader(System.in, out, new TerminalSupport(true) {});
		UDPEchoServer server = new UDPEchoServer();
		UDPEchoClient client = new UDPEchoClient();
		try {
			server.start();
			client.start();
			
			String line = null;
			while((line = reader.readLine().trim()) != "" && line.length() > 0) {
				client.sendMessage(line);
				String answer = null;
				
				while((answer = client.receiveMessage()) != null) {
					out.println(answer);
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
