package controllers.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controllers.BackendController;
import framework.User;

@RunWith(Suite.class)
@SuiteClasses({ BackendControllerTest.class })
public class AllTests {

	//new user test
	@Test
	public void test() throws Exception {
		//fail("Not yet implemented");
		BackendController test = new BackendController();
		
		test.createUser(new User());
		
		assertTrue("Good!", true);
	}
}
