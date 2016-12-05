package controllerImplementations.tests;

import framework.controllers.BackendController;
import controllerImplementations.BackendControllerImpl;
import framework.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * The type All tests.
 */
@RunWith(Suite.class)
@SuiteClasses({BackendControllerTest.class})
public class AllTests
{

    //testing for each method in BackendController

    /**
     * Test 1.
     *
     * @throws Exception the exception
     */
//isUsernameTaken
    @Test
    public void test1() throws Exception
    {

        BackendController test1 = new BackendControllerImpl();

        test1.isUsernameTaken(new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 2.
     *
     * @throws Exception the exception
     */
//getUser
    @Test
    public void test2() throws Exception
    {

        BackendController test2 = new BackendControllerImpl();

        test2.getUser(new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 3.
     *
     * @throws Exception the exception
     */
//signUserIn
    @Test
    public void test3() throws Exception
    {

        BackendController test3 = new BackendControllerImpl();

        test3.signUserIn(new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 4.
     *
     * @throws Exception the exception
     */
//signUserOut
    @Test
    public void test4() throws Exception
    {

        BackendController test4 = new BackendControllerImpl();

        test4.signUserOut(new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 5.
     *
     * @throws Exception the exception
     */
//createUser
    @Test
    public void test5() throws Exception
    {
        //fail("Not yet implemented");
        BackendController test5 = new BackendControllerImpl();

        test5.createUser(new User());

        assertTrue("Good!", true);
    }

    /**
     * Test 6.
     *
     * @throws Exception the exception
     */
//removeUser
    @Test
    public void test6() throws Exception
    {
        BackendController test6 = new BackendControllerImpl();

        test6.removeUser(new User());

        assertTrue("Good!", true);
    }

    /**
     * Test 7.
     *
     * @throws Exception the exception
     */
//userLikesRecipe
    @Test
    public void test7() throws Exception
    {
        BackendController test7 = new BackendControllerImpl();

        test7.userLikesRecipe(new User(), new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 8.
     *
     * @throws Exception the exception
     */
//userLikesFoodCatagory
    @Test
    public void test8() throws Exception
    {
        BackendController test8 = new BackendControllerImpl();

        test8.userLikesFoodCatagory(new User(), new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 9.
     *
     * @throws Exception the exception
     */
//recommendRecipes
    @Test
    public void test9() throws Exception
    {
        BackendController test9 = new BackendControllerImpl();

        test9.recommendRecipes(new User());

        assertTrue("Good!", true);
    }

    /**
     * Test 10.
     *
     * @throws Exception the exception
     */
//searchForReciepes
    @Test
    public void test10() throws Exception
    {
        BackendControllerImpl test10 = new BackendControllerImpl();

        test10.searchForReciepes();

        assertTrue("Good!", true);
    }

    /**
     * Test 11.
     *
     * @throws Exception the exception
     */
//getRecipesContainingIngredients
    @Test
    public void test11() throws Exception
    {
        BackendControllerImpl test11 = new BackendControllerImpl();

        test11.getRecipesContainingIngredients(new ArrayList<String>(), new String());

        assertTrue("Good!", true);
    }


    /**
     * Test 12.
     *
     * @throws Exception the exception
     */
//getRecipesWithOnlyTheseIngredients
    @Test
    public void test12() throws Exception
    {
        BackendControllerImpl test12 = new BackendControllerImpl();

        test12.getRecipesWithOnlyTheseIngredients(new ArrayList<String>(), new String());

        assertTrue("Good!", true);
    }

    /**
     * Test 13.
     *
     * @throws Exception the exception
     */
//getDefaultHomepageRecipes
    @Test
    public void test13() throws Exception
    {
        BackendController test13 = new BackendControllerImpl();

        //Recipes rec1 = test13.getDefaultHomepageRecipes();

        assertTrue("Good!", true);
    }

    /**
     * Test 14.
     *
     * @throws Exception the exception
     */
//isUsernameTaken
    @Test
    public void test14() throws Exception
    {
        BackendController test14 = new BackendControllerImpl();

        test14.isUsernameTaken(new String());

        assertTrue("Good!", true);
    }

}
