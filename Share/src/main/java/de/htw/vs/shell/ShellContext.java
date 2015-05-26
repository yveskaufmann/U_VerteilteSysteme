package de.htw.vs.shell;


import org.springframework.beans.factory.DisposableBean;

/**
 * This class acts as context object for each
 * shell command in order to share information
 * between commands.
 *
 * This class is only added for future use.
 */
public class ShellContext implements DisposableBean {

	@Override
	public void destroy() throws Exception {

	}
}
