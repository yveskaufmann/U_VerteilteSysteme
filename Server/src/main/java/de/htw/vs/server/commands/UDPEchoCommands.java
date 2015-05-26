package de.htw.vs.server.commands;

import de.htw.vs.server.echo.UDPEchoClient;
import de.htw.vs.server.echo.UDPEchoServer;
import de.htw.vs.shell.ConsoleUtil;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintStream;

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
	 * @throws IOException a connection problem occurred between client and server
	 */
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
			server.stop();
			client.stop();
		}
	}


}
