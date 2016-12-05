package testing;
import framework.DatabaseController;
import framework.Recipe;
import framework.Recipes;
import framework.User;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Rodge
 */
public class TestingSQLDatabase {
    
    @Test
    public void testConnection() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.getDBConnection();

        assertEquals("Success", result);
    }
    
      @Test
    public void testGetRecipeInfo() {
        MyUnit myUnit = new MyUnit();
        
        Recipe result = new Recipe();
        result.recipeName = "Mac & Cheese";
        result = myUnit.getRecipeInfo();

        assertEquals(temp, result);
    }
    
      @Test
    public void testGetRecipeId() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.getRecipeId();

        assertEquals(1, result);
    }
    
      @Test
    public void testGetIngredientId() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.getIngredientId();

        assertEquals(1, result);
    }
    
      @Test
    public void testGetRecipesContainingIngredients() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.getRecipesContainingIngredients();

        assertEquals(1, result);
    }
    
     @Test
    public void testGetRecipesWithOnlyTheseIngredients() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.getRecipesWithOnlyTheseIngredients();

        assertEquals(1, result);
    }
    
     @Test
    public void testGetUser() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.getUser("JimmyBuffet");

        assertEquals("JimmyBuffet", result);
    }     
}
