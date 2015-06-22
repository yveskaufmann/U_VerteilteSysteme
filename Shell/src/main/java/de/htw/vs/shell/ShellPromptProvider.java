package de.htw.vs.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

/**
 * <p>This a plugin for the spring shell which customize the prompt.</p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShellPromptProvider extends DefaultPromptProvider {

	@Override
	public String getPrompt() {
		return ShellBannerProvider.SHELL_NAME + ">";
	}
}
