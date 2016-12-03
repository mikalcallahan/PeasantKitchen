package framework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * Contains all of the relevant information from the database about a recipe,
 * and can perform actions upon this data, as appropriate.
 */

/**
 * The type Recipe.
 */
public class Recipe implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -7015655411269528838L;
    /**
     * The Recipe name.
     */
    public String recipeName = "";
    /**
     * The Recipe requirements.
     */
    public String recipeRequirements = "";
    /**
     * The Recipe process.
     */
    public String recipeProcess = "";
    /**
     * The Num of ingr.
     */
    public int numOfIngr;
    /**
     * The Ingredient quantities.
     */
    public ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<IngredientQuantity>();

    /**
     * The Recipe id.
     */
    public int recipeID;

    /**
     * Instantiates a new Recipe.
     */
    public Recipe()
    {

    }

    /**
     * Gets unique ingredients.
     *
     * @return the unique ingredients
     */
    public Set<String> getUniqueIngredients()
    {
        HashSet<String> uniqueRecipeIngredients = new HashSet<String>();

        for (IngredientQuantity ingredient : this.ingredientQuantities)
            uniqueRecipeIngredients.add(ingredient.ingredient);

        return uniqueRecipeIngredients;
    }

    @Override
    public String toString()
    {
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
