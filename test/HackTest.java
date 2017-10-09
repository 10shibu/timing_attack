package edu.brandeis.cs12b.pa9;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa9.Hacks;
import edu.brandeis.cs12b.pa9.UserAuthenticator;

public class HackTest {

	private UserAuthenticator auth;
	private Hacks h;
	
	@Before
	public void setUp() throws Exception {
		auth = new UserAuthenticator();
		h = new Hacks();

	}

	@Test
	public void hack1Test() {
		h.hack1(auth);
		assertEquals(auth.authenticateUser("Eliot", "1234"), "admin");
		
	}
	
	@Test
	public void hack2Test() {
		h.hack2(auth);
		try {
			auth.authenticateUser("Bob", "test");
			fail("User authenticator still working...");
		} catch (Exception e) {
			// pass!
			return;
		}
		
	}

}
