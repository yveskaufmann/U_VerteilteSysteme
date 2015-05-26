package de.htw.vs.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShellPromptProvider extends DefaultPromptProvider {


	@Override
	public String getPrompt() {
		return ShellBannerProvider.SHELL_NAME + ">";
	}
}
