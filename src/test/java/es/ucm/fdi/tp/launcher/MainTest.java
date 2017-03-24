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
		assertTrue(errContent.toString().startsWith("Error: " + Main.WrongPlayersNumber));
		errContent.reset();
		
		Main.main(new String[] { "ttt", "random" });
		assertTrue(errContent.toString().startsWith("Error: " + Main.WrongPlayersNumber));
	}

	
	@Test
	public void testGameName() {
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		
		Main.main(new String[] { "chess", "console", "console" });
		assertTrue(errContent.toString().startsWith("Error: " + Main.WrongGame));
		errContent.reset();
	}
}
