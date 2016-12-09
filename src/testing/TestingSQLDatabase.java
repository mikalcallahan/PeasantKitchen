package testing;

import framework.Recipe;

/**
 *
 * @author Rodge
 */
public class TestingSQLDatabase {

//
//
//    /**
//     * Test of getRecipeInfo method, of class DataBase.
//     */
//    @Test
//    public void testGetRecipeInfo() throws Exception {
//        System.out.println("getRecipeInfo");
//        Recipe recipe = new Recipe();
//        recipe.recipeName = "Spaghetti";
//        ArrayList<Recipe> tempRecipe = new ArrayList<Recipe>();
//        tempRecipe.add(0, recipe);
//        TestingSQLDatabase instance = new TestingSQLDatabase;
//        ArrayList<Recipe> expResult = tempRecipe;
//        ArrayList<Recipe> result = instance.getRecipeInfo(tempRecipe);
//        assertEquals(expResult, result);
//
//    }
//
//    /**
//     * Test of getIngredientId method, of class DataBase.
//     */
//    @Test
//    public void testGetIngredientId() throws Exception {
//        System.out.println("getIngredientId");
//        ArrayList<String> ingredientsList = new ArrayList<String>();
//        ingredientsList.add("Ground Beef");
//        ingredientsList.add("Spaghetti Noodles");
//        ingredientsList.add("Pasta Sauce");
//        TestingSQLDatabase instance = new TestingSQLDatabase;
//        ArrayList<Integer> expResult = new ArrayList<Integer>();
//        expResult.add(2);
//        expResult.add(5);
//        expResult.add(4);
//        ArrayList<Integer> result = instance.getIngredientId(ingredientsList);
//        assertEquals(expResult, result);
//
//    }
//
//    /**
//     * Test of getUser method, of class DataBase.
//     */
//    @Test
//    public void testGetUser() throws Exception {
//        System.out.println("getUser");
//        String username = "CreedFan420";
//        TestingSQLDatabase instance = new TestingSQLDatabase;
//        User expResult = new User("CreedFan420","WithArmsWideOpen@gmail.com",true,"takemehigher","Mike","Smith"," ","Vegan");
//        User result = instance.getUser(username);
//
//        assertEquals(expResult.username, result.username);
//        assertEquals(expResult.firstname,result.firstname);
//        assertEquals(expResult.lastname,result.lastname);
//        assertEquals(expResult.emailAddress,result.emailAddress);
//        assertEquals(expResult.password,result.password);
//        assertEquals(expResult.ppn,result.ppn);
//        assertEquals(expResult.signedIn,result.signedIn);
//        assertEquals(expResult.diets,result.diets);
//
//    }
//
//    /**
//     * Test of createUser method, of class DataBase.
//     */
//    @Test
//    public void testCreateUser() {
//        System.out.println("createUser");
//        User tempUserObject = new User("CreedFan420","WithArmsWideOpen@gmail.com",true,"takemehigher","Mike","Smith"," ","Vegan");
//
//        TestingSQLDatabase instance = new TestingSQLDatabase;
//        User expResult = tempUserObject;
//        User result = instance.createUser(tempUserObject);
//        assertEquals(expResult, result);
//
//    }
//
//    /**
//     * Test of updateSignedIn method, of class DataBase.
//     */
//    @Test
//    public void testUpdateSignedIn() throws Exception {
//        System.out.println("updateSignedIn");
//        String username = "CreedFan420";
//        TestingSQLDatabase instance = new TestingSQLDatabase;
//        User expResult =new User("CreedFan420","WithArmsWideOpen@gmail.com",true,"takemehigher","Mike","Smith"," ","Vegan"); ;
//        User result = instance.updateSignedIn(username);
//        assertEquals(expResult.signedIn, result.signedIn);
//
//    }
//
//
//    /**
//     * Test of updateSignedOut method, of class DataBase.
//     */
//    @Test
//    public void testUpdateSignedOut() throws Exception {
//        System.out.println("updateSignedOut");
//        String username = "";
//        TestingSQLDatabase instance = new TestingSQLDatabase;
//        User expResult = new User("CreedFan420","WithArmsWideOpen@gmail.com",false,"takemehigher","Mike","Smith"," ","Vegan");
//        User result = instance.updateSignedOut(username);
//        assertEquals(expResult.signedIn, result.signedIn);
//
//    }
//
}
