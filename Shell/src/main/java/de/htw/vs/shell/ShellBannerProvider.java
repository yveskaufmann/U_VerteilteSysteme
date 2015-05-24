package de.htw.vs.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShellBannerProvider extends DefaultBannerProvider {
		
	
	public static final String SHELL_NAME = "VS-Runner";

	@Override
	public String getBanner() {
		StringBuffer banner = new StringBuffer();
		banner.append(FileUtils.readBanner(ShellBannerProvider.class, "banner.txt"));
		banner.append(OsUtils.LINE_SEPARATOR);
		banner.append(getVersion()).append(OsUtils.LINE_SEPARATOR);
		banner.append(OsUtils.LINE_SEPARATOR);
		                                                                                                       
		return banner.toString();
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public String getWelcomeMessage() {
		return "Welcome to " + getProviderName() + " shell." + OsUtils.LINE_SEPARATOR;
	}
	
	@Override
	public String getProviderName() {
		return SHELL_NAME;
	}

}
