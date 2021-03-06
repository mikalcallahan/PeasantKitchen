package applicationData;

import com.opencsv.CSVReader;
import constants.Constants;
import framework.IngredientQuantity;
import framework.IngredientQuantity.Range;
import framework.Parser;
import framework.Recipe;
import framework.Recipes;
import utilities.StringUtilites;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the RECIPES.csv file into a list of string arrays. Each row in this list represents a row, and each string array
 * represents column values. This is essentially a 2-d array representation of a CSV file.
 */

/**
 * The type Recipes parser.
 */
public class RecipesCSVParser implements Parser<File, Recipes>
{
    @Override
    public Recipes parse(File input) throws Exception
    {
        List<String[]> recipesCSVData = parseCSV(input);
        return convertParsedCSVDataToRecipes(recipesCSVData);
    }

    private List<String[]> parseCSV(File csvFile) throws Exception
    {
        CSVReader reader = new CSVReader(new FileReader(csvFile.getCanonicalPath()));
        List<String[]> csvEntries = reader.readAll();

        reader.close();

        return csvEntries;
    }

    private Recipes convertParsedCSVDataToRecipes(List<String[]> recipesCSVData)
    {
        Recipes recipes = new Recipes();
        Recipe currRecipe;
        String[] lineEntries;

        //Ignore the header (line 1)
        for (int index = 1; index < recipesCSVData.size(); index++)
        {
            lineEntries = recipesCSVData.get(index);
            currRecipe = convertCSVLineToRecipe(lineEntries);

            if (currRecipe != null)
                recipes.add(currRecipe);
        }

        return recipes;
    }

    private Recipe convertCSVLineToRecipe(String[] lineEntries)
    {
        Recipe recipe = new Recipe();

        recipe.recipeID = getRecipeID(lineEntries[0]);
        recipe.recipeName = lineEntries[1];
        recipe.recipeRequirements = lineEntries[2];
        recipe.recipeProcess = lineEntries[3];
        recipe.ingredientQuantities = parseIngredientQuantities(recipe.recipeRequirements);

        return recipe;
    }

    private Integer getRecipeID(String rawRecipeID)
    {
        if (StringUtilites.isVoidString(rawRecipeID))
            return new Integer(Constants.ApplicationData.defaultRecipeID);
        else
            return Integer.parseInt(rawRecipeID.trim());
    }

    /**
     * Parse ingredient quantities array list.
     *
     * @param ingredientProcess the ingredient process
     * @return the array list
     */
    public ArrayList<IngredientQuantity> parseIngredientQuantities(String ingredientProcess)
    {
        ArrayList<IngredientQuantity> ingredientQuantities = new ArrayList<IngredientQuantity>();
        String[] ingredientQuantityItems;
        String element;

        //<Number><Unit><Ingredient>, ...
        ArrayList<String> elementValues = new ArrayList<String>(3);

        for (String ingredientQuantityString : ingredientProcess.split(Constants.ApplicationData.elementSeperator))
        {
            ingredientQuantityString = ingredientQuantityString.trim();
            ingredientQuantityItems = ingredientQuantityString.split(Constants.ApplicationData.openElement);

            for (int index = 0; index < ingredientQuantityItems.length; index++)
            {
                element = ingredientQuantityItems[index].trim();

                if (StringUtilites.isVoidString(element))
                    continue;

                if (!element.endsWith(Constants.ApplicationData.closingElement))
                    throw new NullPointerException("Parse error: The Ingredient quantity string [" + element + "], in the element [" + ingredientQuantityString + "], was malformed.");

                elementValues.add(StringUtilites.removeEndingCharacters(element, 1)); //Remove the trailing closingElement from the element string
            }

            ingredientQuantities.add(makeIngredientQuantity(elementValues));
            elementValues.clear();
        }

        return ingredientQuantities;
    }

    //<Number><Unit><Ingredient>, ...

    private IngredientQuantity makeIngredientQuantity(ArrayList<String> elementValues)
    {
        if (elementValues.size() != 3)
            throw new NullPointerException("Parse error: You must specify the ingredient in this format: <Number><Unit><Ingredient>. You must have exactly 3 opening < and 3 closing >; none of the < or > can be ommitted!");

        IngredientQuantity ingredientQuantity = new IngredientQuantity();

        ingredientQuantity.quantity = getQuantity(elementValues.get(0));
        ingredientQuantity.unit = elementValues.get(1).trim();
        ingredientQuantity.ingredient = getIngredient(elementValues.get(2));

        return ingredientQuantity;
    }

    private Range getQuantity(String rawQuantityString)
    {
        if (StringUtilites.isVoidString(rawQuantityString))
            return new Range(Constants.ApplicationData.defaultIngredientQuantity);

        String cleaned = rawQuantityString.trim();

        if (cleaned.contains(Constants.ApplicationData.quantityRange))
            return parseRange(cleaned);
        else
            return new Range(Double.parseDouble(cleaned));
    }

    //2.5 - 3
    private Range parseRange(String quantityRange)
    {
        Double lower = 0.0;
        Double upper = 0.0;

        String[] lowerAndUpperSplit = quantityRange.split(Constants.ApplicationData.quantityRange);

        if (lowerAndUpperSplit.length != 2)
            throw new RuntimeException("Parse error: You must specify the lower and upper bounds of the range: [" + quantityRange + "]");

        lower = extractVal(lowerAndUpperSplit[0]);
        upper = extractVal(lowerAndUpperSplit[1]);

        if (lower.equals(upper))
            return new Range(lower);

        //Check if we need to flip the values
        if (lower.compareTo(upper) > 0)
            return new Range(upper, lower);

        return new Range(lower, upper);
    }

    private Double extractVal(String str)
    {
        return
                Double.parseDouble(str.trim());
    }

    private String getIngredient(String rawIngredient)
    {
        if (StringUtilites.isVoidString(rawIngredient))
            throw new NullPointerException("Parse error: Missing ingredient!");

        return StringUtilites.cleanIngredient(rawIngredient);
    }
}