package de.htw.vs.server.commands;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.htw.vs.server.echo.UDPEchoClient;
import de.htw.vs.server.echo.UDPEchoServer;
import de.htw.vs.shell.ConsoleUtil;


/**
 * <p>
 * Commands related to the udp echo exercises including 3.3 and 3.4.
 * </p>
 *
 * <p>
 * This class is a {@link Component Spring Component} which is
 * loaded by the <a href="http://docs.spring.io/spring-shell/docs/current/reference/html/">spring-shell</a> framework.
 * When this class was loaded by  the shell it is added to the possible shell commands.
 * </p>
 *
 * <p>
 * For more details please <a href="http://docs.spring.io/spring-shell/docs/current/reference/html/shell.html">see</a>.
 *</p>
 * @see CommandMarker
 * @see CliCommand
 */
@Component
public class UDPEchoCommands implements CommandMarker {

	/**
	 * This command starts the udp echo server and the client.
	 *
	 * @throws IOException if a connection problem occurred between client and server
	 */
	@CliCommand(value = "udp echo", help = "Starts an echo server and a echo client")
	public void echoServerAndClient() throws IOException {
		PrintStream out = ConsoleUtil.getConsoleWriter();
		UDPEchoServer server = new UDPEchoServer();
		UDPEchoClient client = new UDPEchoClient();
		try {
			server.start();
			client.start();

			String line = null;
			while (client.isRunning() && (line = ConsoleUtil.readline("Please enter your message: ")) != null) {
				client.sendMessage(line);
				String answer = null;
				while ((answer = client.receiveMessage()) != null) {
					out.println("Server said: " + answer);
					out.flush();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			server.stop();
			client.stop();
		}
	}

	/**
	 * This command starts up the udp echo server.
	 * 
	 * @param port the port number which should use by the server.
	 * @throws IOException if a connection problem occurred between client and server.
	 */
	@CliCommand(value = "udp server", help = "Starts a UDP echo server")
	public void echoServer(
			@CliOption(
					key = {"", "port"},
					mandatory=false,
					unspecifiedDefaultValue="4242",
					help="The port number which should used by the server."
			)final short port) throws IOException {
		
		UDPEchoServer server = new UDPEchoServer(port);
		try {
			server.start();
			while ((ConsoleUtil.readline("Press any key to exit: ")) != null) {}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			server.stop();
		}
	}

	/**
	 * This command starts up the udp echo client and connects to a udp server..
	 * 
	 * @param host the name  of the server to which a connection should be established.
	 * @param port the port number on which the server is listening..
	 * 
	 * @throws IOException IOException a connection problem occurred between client and server.
	 */
	@CliCommand(value = "udp client", help = "Starts a UDP echo client")
	public void echoClient(	
			@CliOption(
					key = {"", "host"},
					mandatory=false,
					unspecifiedDefaultValue="127.0.0.1",
					help="The hostname of the server to which a connetion should be etablished."
			)final String host,
			@CliOption(
					key = {"-p", "port"},
					mandatory=false,
					unspecifiedDefaultValue="4242",
					help="The port number on which the server is listening."
			) final short port) throws IOException {
		
		PrintStream out = ConsoleUtil.getConsoleWriter();
		UDPEchoClient client = new UDPEchoClient(host, port);
		
		try {
			
			client.addObserver((Observable observable, Object args) -> {
				if ( "timeout".equals(args)) {
					ConsoleUtil.getConsoleWriter().print("Timeout the server isn't accessible");
					client.stop();
					return;
				}
			});
			
			client.start();
			String line = null;
			while (client.isRunning() && (line = ConsoleUtil.readline("Please enter your message: ")) != null) {
				client.sendMessage(line);
				String answer = null;
				while ((answer = client.receiveMessage()) != null) {
					out.println("Server said: " + answer);
					out.flush();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			client.stop();
		}
	}
}
