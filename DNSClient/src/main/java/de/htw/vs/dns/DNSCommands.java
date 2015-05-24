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

@Component
public class DNSCommands implements CommandMarker {
	
	private static final String OPT_HELP_SHOW_ALL = "If provided all corresponding ip addresses for the given host will be returned.";
	private static final String OPT_HELP_INCLUDE_HOSTNAME = "If provided, the hostname will be included";
	private static final String OPT_HELP_HOSTNAME = "The hostname which should be resolved.";
	
	private static final Logger LOGGER = HandlerUtils.getLogger(DNSCommands.class);

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
