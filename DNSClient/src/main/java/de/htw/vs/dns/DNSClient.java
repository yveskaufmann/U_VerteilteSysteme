package de.htw.vs.dns;

import static java.util.stream.Collectors.toList;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * A simple DNSClient which provides the addresses for a corresponding host
 * as textual representation.
 * </p>
 */
public class DNSClient {
	
	/**
	 * <p>
	 * Given the name of a host, 
	 * returns a string with its IP address,
	 * based on the configured name service on the system.
	 * </p>
	 * 
	 * @param host the name of the host
	 * @param includeHost if true the returned string will contain the host name too.
	 * @return a string which contains the IP address of the host.
	 * @throws UnknownHostException if no IP address for the host could be found.
	 */
	public String getAddressByHost(String host, boolean includeHost) throws UnknownHostException {
		return getAllAddressesByHost(host, includeHost).get(0);
	}

	/**
	 * <p>
	 * Given the name of a host, returns a list of its IP addresses, 
	 * based on the configured name service on the system.
	 * </p>
	 * @param host the name of the host
	 * @param includeHost if true the returned strings will contain the host name too.
	 * @return a list of all the IP addresses for a given host name.
	 * @throws UnknownHostException if no IP address for the host could be found.
	 */
	public List<String> getAllAddressesByHost(String host, boolean includeHost) throws UnknownHostException {
		InetAddress[] addresses = InetAddress.getAllByName(host);
			
		return Arrays.stream(addresses)
				.map(getAddressRenderer(includeHost))
				.collect(toList());
	}
	
	private Function<InetAddress, String> getAddressRenderer(boolean includeHost) {
		if(includeHost) {
			return this::renderAddressWithHost;
		}
		return this::renderAddress;
	}
	
	private String renderAddress(InetAddress address) {
		return address.getHostAddress();
	}
	
	private String renderAddressWithHost(InetAddress address) {
		return address.toString().replace("/", " - ");
	}
	
	
}
