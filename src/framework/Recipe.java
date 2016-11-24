package framework;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Contains all of the relevant information from the database about a recipe,
 * and can perform actions upon this data, as appropriate.
 */

public class Recipe implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7015655411269528838L;
	//protected ArrayList<IngredientQuantity> ingredients = new ArrayList<IngredientQuantity>();
	//protected String directions;
	//protected File image = null;
    public String recipeName = "";
    public String recipeRequirements = "";
    public String recipeProcess = "";
    public ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<IngredientQuantity>();

    
    public int recipeID;
    
    public Recipe()
    {

    }


}
