package de.htw.vs.dns;

import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Commands related to the dns client which are loaded by the spring shell.
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
public class DNSCommands implements CommandMarker {

	private static final String OPT_HELP_SHOW_ALL = "If provided all corresponding ip addresses for the given host will be returned.";
	private static final String OPT_HELP_INCLUDE_HOSTNAME = "If provided, the hostname will be included";
	private static final String OPT_HELP_HOSTNAME = "The hostname which should be resolved.";
	private static final Logger LOGGER = HandlerUtils.getLogger(DNSCommands.class);

	/**
	 * <p>
	 * The 'dns resolve' command resolves a given dns name to it's first possible ip address
	 * and print the ip address to the shell.
	 * </p>
	 *
	 * <p>
	 * By default only the first ip address which was found on a dns lookup table will
	 * be returned.
	 * </p>
	 *
	 * @param host the dns name
	 * @param includeHost if true then the host is included into the output.
	 * @param showAll if true the all ip addresses will be printed to the shell.
	 * @return The output which should be printed to the shell.
	 * @see CliCommand
	 * @see CliOption
	 */
	@CliCommand(value="dns resolve", help="Returns the ip address for a given hostname")
	public String resolve(
			@CliOption(
				key = { "", "host" },
				mandatory=false,
				unspecifiedDefaultValue="",
				help=OPT_HELP_HOSTNAME) final String host,
			@CliOption(
				key = { "include-host", "h"},
				mandatory=false,
				specifiedDefaultValue="true",
				unspecifiedDefaultValue="false" ,
				help=OPT_HELP_INCLUDE_HOSTNAME) final boolean includeHost,
			@CliOption(key = { "show-all", "a"},
				mandatory=false,
				specifiedDefaultValue="true",
				unspecifiedDefaultValue="false" ,
				help=OPT_HELP_SHOW_ALL) final boolean showAll) {

		DNSClient client = new DNSClient();
		String output = null;

		if (StringUtils.isEmpty(host)) {
			LOGGER.warning("You have to assign a host in order to resolve it's ip address.");
			return output;
		}

		try {
			if(showAll) {
				List<String> addresses = client.getAllAddressesByHost(host, includeHost);
				output = addresses.stream().collect(Collectors.joining(System.lineSeparator()));
			} else {
				output = client.getAddressByHost(host, includeHost);
			}
		} catch (UnknownHostException e) {
			LOGGER.warning(String.format("The specified hostname '%s' wasn't found.", host));
		}

		return output;
	}

}
