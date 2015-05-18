package de.htw.vs.shell.util;

import static java.util.stream.Collectors.joining;

import java.sql.SQLException;
import java.util.stream.Stream;

public class StringUtil {
	
	/**
	 * <p>
	 * Repeat a String <code>str</code> times to create a new string.<br>
	 * 
	 * </p>
	 * @param str the String to repeat.
	 * @param times the number of times to repeat str.
	 * @return the new string which contains <code>str</code> repeated.
	 * @throws IllegalArgumentException if times &lt;= 0
	 */
	public static String repeatString(String str, int times ) {
		if(times <= 0) {
			throw new IllegalArgumentException("Invalid times: times must be > 0");
		}
		return Stream.generate(()-> str).limit(times).collect(joining());
	}

	public static String toString(Exception ex) {
		if(ex instanceof SQLException) {
			return String.format("Error: connection failed: %s ", ex.getMessage());
		} else {
			return ex.getMessage();
		}
	}

	public static String[] splitIntoChunks(String text, int chunkSize) {
		if(text.length() < chunkSize) {
			return new String[] { text };
		}
		
		int countOfChunks = (int )Math.ceil((float)text.length() /  chunkSize);
		String[] chunks = new String[countOfChunks];
		for(int i = 0; i < chunks.length; i++) {
			int offset = i * chunkSize;
			int offsetEnd = offset + chunkSize;
				
			chunks[i] = text.substring(offset, offsetEnd < text.length() ? offsetEnd : text.length());
		}
		
		return chunks;
	
	}
}
