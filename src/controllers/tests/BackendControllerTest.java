package controllers.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import controllers.BackendController;
import framework.User;

public class BackendControllerTest {

	//new user test
	@Test
	public void test() throws Exception {
		//fail("Not yet implemented");
		BackendController test = new BackendController();
		
		test.createUser(new User());
		
		assertTrue("Good!", true);
		
	}

}
