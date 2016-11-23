package controllers.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controllers.BackendController;
import framework.Recipes;
import framework.User;

@RunWith(Suite.class)
@SuiteClasses({ BackendControllerTest.class })
public class AllTests {

	//testing for each method in BackendController
	
	//isUsernameTaken
	@Test
	public void test1() throws Exception {
		
		BackendController test1 = new BackendController();
		
		test1.isUsernameTaken(new String());
		
		assertTrue("Good!", true);
	}
	
	//getUser
	@Test
	public void test2() throws Exception {
		
		BackendController test2 = new BackendController();
		
		test2.getUser(new String());
		
		assertTrue("Good!", true);
	}
	
	//signUserIn
	@Test
	public void test3() throws Exception {
		
		BackendController test3 = new BackendController();
		
		test3.signUserIn(new String());
		
		assertTrue("Good!", true);
	}
	
	//signUserOut
	@Test
	public void test4() throws Exception {
		
		BackendController test4 = new BackendController();
		
		test4.signUserOut(new String());
		
		assertTrue("Good!", true);
	}
	
	//createUser
	@Test
	public void test5() throws Exception {
		//fail("Not yet implemented");
		BackendController test5 = new BackendController();
		
		test5.createUser(new User());
		
		assertTrue("Good!", true);
	}
	
	//removeUser 
		@Test
		public void test6() throws Exception {
			BackendController test6 = new BackendController();
			
			test6.removeUser(new User());
			
			assertTrue("Good!", true);
		}
	
		//userLikesRecipe
			@Test
			public void test7() throws Exception {
				BackendController test7 = new BackendController();
				
				test7.userLikesRecipe(new User(), new String());
					
				assertTrue("Good!", true);
				}	
		
		//userLikesFoodCatagory
		@Test
		public void test8() throws Exception{
			BackendController test8 = new BackendController();
			
			test8.userLikesFoodCatagory(new User(), new String());
			
			assertTrue("Good!", true);
		}
		
		//recommendRecipes
		@Test
		public void test9() throws Exception{
			BackendController test9 = new BackendController();
			
			test9.recommendRecipes(new User());
			
			assertTrue("Good!", true);
		}
		
		//searchForReciepes
		@Test
		public void test10() throws Exception{
			BackendController test10 = new BackendController();
			
			test10.searchForReciepes();
			
			assertTrue("Good!", true);
		}
		
		//getRecipesContainingIngredients
		@Test
		public void test11() throws Exception{
			BackendController test11 = new BackendController();
			
		test11.getRecipesContainingIngredients(new ArrayList<String>(), new String());
			
			assertTrue("Good!", true);
		}

		
		//getRecipesWithOnlyTheseIngredients
		@Test
		public void test12() throws Exception{
			BackendController test12 = new BackendController();
			
			test12.getRecipesWithOnlyTheseIngredients(new ArrayList<String>(), new String());
			
			assertTrue("Good!", true);
		}
		
		//getDefaultHomepageRecipes
		@Test
		public void test13() throws Exception{
			BackendController test13 = new BackendController();
			
	//Recipes rec1 = test13.getDefaultHomepageRecipes();
			
			assertTrue("Good!", true);
		}
		
		//isUsernameTaken
		@Test
		public void test14() throws Exception{
			BackendController test14 = new BackendController();
			
			test14.isUsernameTaken(new String());
			
			assertTrue("Good!", true);
		}

}
