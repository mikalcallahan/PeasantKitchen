package framework;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    
    public Set<String> getUniqueIngredients()
    {
    	HashSet<String> uniqueRecipeIngredients = new HashSet<String>();
		
		for(IngredientQuantity ingredient : this.ingredientQuantities)
			uniqueRecipeIngredients.add(ingredient.ingredient);
		
		return uniqueRecipeIngredients;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Recipe [recipeName=");
		builder.append(recipeName);
		builder.append(", recipeRequirements=");
		builder.append(recipeRequirements);
		builder.append(", recipeProcess=");
		builder.append(recipeProcess);
		builder.append(", ingredientQuantities=");
		builder.append(ingredientQuantities);
		builder.append(", recipeID=");
		builder.append(recipeID);
		builder.append("]");
		return builder.toString();
	}
    
    
}
