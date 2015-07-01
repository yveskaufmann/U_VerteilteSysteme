package de.htw.vs.chat.command;

import de.htw.vs.chat.impl.ChatClient;
import de.htw.vs.shell.ConsoleUtil;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Created by Niels on 07.06.2015.
 */
@Component
public class ClientCommand implements CommandMarker {
	/**
	 * The command to start the demo for exercise 5.1
	 *
	 * @param username  the nickname which should be used during the chat session.
	 * @param serverHost the chat server which should be used.
	 */
	@CliCommand(value = "chat client", help = "Starts a chat client for exercise 5.1")
	public void startClient(
		@CliOption(
			key = {"", "username"},
			mandatory = true,
			help = "The username which should be used for the chat.") final String username,
		@CliOption(
			key = {"host"},
			unspecifiedDefaultValue = "localhost",
			help = "The host on which the chat server is running.") final String serverHost) {

		try {

			ChatClient client = new ChatClient(username, serverHost);
			client.connect();
			String message = null;
			while ((message = ConsoleUtil.readline("Please enter your message (type \"exit\" to disconnect): ")) != null
				&& ! "exit".equalsIgnoreCase(message)) {
				client.sendMessage(message);
			}
			client.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
