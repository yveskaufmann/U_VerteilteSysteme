package de.htw.vs.shell;

import jline.WindowsTerminal;
import jline.console.ConsoleReader;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.shell.core.JLineShell;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.logging.Logger;

/**
 * Helper methods for handling console commands,
 * such as reading from the console and so on.
 *
 */
public class ConsoleUtil {


	private static final Logger logger = HandlerUtils.getLogger(ConsoleUtil.class);

	private static final String ANSI_CONSOLE_CLASSNAME = "org.fusesource.jansi.AnsiConsole";

	private static final boolean JANSI_AVAILABLE = ClassUtils.isPresent(ANSI_CONSOLE_CLASSNAME,
		JLineShell.class.getClassLoader());

	/**
	 * Returns a PrintStream for writing to console.
	 *
	 * @return a PrintStream.
	 */
	public static PrintStream getConsoleWriter() {
		return AnsiConsole.out != null ? AnsiConsole.out : System.out;
	};

	/**
	 * Read a line from the console and provide a message 'prompt' for the user.
	 *
	 * @param prompt the prompt message
	 * @return the string line or null if a empty line was entered.
	 * @throws IOException if an io error occurred.
	 */
	public static String readline(String prompt) throws IOException {
			getConsoleWriter().print(prompt);
			getConsoleWriter().flush();
			String line =  getConsoleReader().readLine().trim();
			return line == null || line.length() <= 0 ? null : line;
	}

	/**
	 * Return a ConsoleReader for reading from the console
	 * within a spring shell command.
	 *
	 * This code is extracted from the org.springframework.shell.core.JLineShell
	 * class in order to enable reading from the console while a spring shell command
	 * is executing.
	 *
	 * @return a console reader for reading from the console.
	 * @throws IllegalStateException if a console reader could not be started.
	 */
	public static ConsoleReader getConsoleReader() {
		ConsoleReader consoleReader = null;
		try {
			if (isJansiAvailable()) {
				try {
					consoleReader = createAnsiWindowsReader();
				}
				catch (Exception e) {
					// Try again using default ConsoleReader constructor
					logger.warning("Can't initialize jansi AnsiConsole, falling back to default: " + e);
				}
			}
			if (consoleReader == null) {
				consoleReader = new ConsoleReader();
			}
		}
		catch (IOException ioe) {
			throw new IllegalStateException("Cannot start console class", ioe);
		}
		consoleReader.setExpandEvents(false);
		return consoleReader;
	}

	/**
	 * Create a ConsoleReader for Windows based applications.
	 *
	 * @return consoleReader with windows support.
	 * @throws Exception if the console reader could not be created.
	 */
	private static ConsoleReader createAnsiWindowsReader() throws Exception {
		// Get decorated OutputStream that parses ANSI-codes
		final PrintStream ansiOut = (PrintStream) ClassUtils
			.forName(ANSI_CONSOLE_CLASSNAME, JLineShell.class.getClassLoader()).getMethod("out").invoke(null);
		WindowsTerminal ansiTerminal = new WindowsTerminal() {
			@Override
			public synchronized boolean isAnsiSupported() {
				return true;
			}
		};
		ansiTerminal.init();
		OutputStream out = AnsiConsole.wrapOutputStream(ansiOut);
		return new ConsoleReader(new FileInputStream(FileDescriptor.in), out, ansiTerminal);
	}

	/**
	 * Check if the jansi implementation is aviable
	 * @return true if jansi is aviable.
	 */
	public static boolean isJansiAvailable() {
		return JANSI_AVAILABLE && OsUtils.isWindows() && System.getProperty("jline.terminal") == null;
	}
}
