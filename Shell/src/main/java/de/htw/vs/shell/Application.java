package de.htw.vs.shell;

import de.htw.vs.shell.util.UncaughtExceptionLogger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.shell.Bootstrap;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


public class Application {

	/**
	 * The name of the variable which stores the path to the base directory
	 * for this application.
	 */
	public static final String APP_HOME_VAR = "user.app.home";

	/**
	 * Run the application with the specified program arguments.
	 *
	 * @param args the program arguments.
	 */
	public static void run(String[] args)  {
		registerAppHomeDirSystemVariable();
		registerUncaughtExceptionLogger();
		setConsoleCodePage();
		runSpringShellWithoutInternalCommands(args);
	}


	private static void setConsoleCodePage() {
		String charsetName = "UTF-8";

		if(SystemUtils.IS_OS_WINDOWS) {
			charsetName = "CP1252";
		}

		if(!Charset.isSupported(charsetName)) {
			charsetName = Charset.defaultCharset().name();
		}

		try {
			System.setProperty("console.encoding", charsetName);
			System.setOut(new PrintStream(System.out, true, charsetName));
			System.setErr(new PrintStream(System.err, true, charsetName));
		} catch(UnsupportedEncodingException ex) {}
	}


	private static void registerAppHomeDirSystemVariable() {
		File pathToLibDir = new File(Application.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.setProperty(APP_HOME_VAR, new File(pathToLibDir.getParent()).getParent());
	}

	private static void registerUncaughtExceptionLogger() {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionLogger());
	}

	private static void runSpringShellWithoutInternalCommands(String[] args) {
		try {
			Bootstrap.main(ArrayUtils.addAll(args, "--disableInternalCommands"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
