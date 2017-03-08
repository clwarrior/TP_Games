package es.ucm.fdi.tp.launcher;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class MainTest {
	
	@Test
	public void testNumberOfArguments() {
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		
		Main.main(new String[] { "was", "console", "console", "console" });
		assertEquals("Error: number of players not suitable\r\n", errContent.toString());
		errContent.reset();
		
		Main.main(new String[] { "ttt", "random" });
		assertEquals("Error: number of players not suitable\r\n", errContent.toString());
	}

	
	@Test
	public void testGameName() {
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		
		Main.main(new String[] { "chess", "console", "console" });
		assertEquals("Error: game not available\r\n", errContent.toString());
		errContent.reset();
	}
}
