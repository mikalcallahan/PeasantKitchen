package utilities;

import java.util.ArrayList;


/**
 * A smattering of helpful String-related functions
 */

public class StringUtilites
{
    /**
     * Remove ending characters string.
     *
     * @param string                     the string
     * @param numberOfCharactersToRemove the number of characters to remove
     * @return the string
     */
    public static String removeEndingCharacters(String string, int numberOfCharactersToRemove)
    {
        if (numberOfCharactersToRemove >= string.length())
            return "";

        return string.substring(0, string.length() - numberOfCharactersToRemove);
    }

    /**
     * Join the given strings together into a single result string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String join(Iterable<String> strings)
    {
        if (strings == null)
            return null;

        StringBuilder joined = new StringBuilder();

        for (String substring : strings)
            joined.append(substring);

        return joined.toString();
    }

    /**
     * Clean ingredients array list.
     *
     * @param ingredients the ingredients
     * @return the array list
     */
    public static ArrayList<String> cleanIngredients(ArrayList<String> ingredients)
    {
        ArrayList<String> cleaned = new ArrayList<String>();

        for (String ingredient : ingredients)
            cleaned.add(StringUtilites.cleanIngredient(ingredient));

        return cleaned;
    }

    /**
     * Clean ingredient strings. This means that the ingredient string will be converted into a normal
     * format, which is lowercase with no leading or trailing whitespace.
     *
     * @param ingredient the ingredient
     * @return the string
     */
    public static String cleanIngredient(String ingredient)
    {
        if (StringUtilites.isVoidString(ingredient))
            return "";

        return ingredient.trim().toLowerCase();
    }

    /**
     * Checks to see if the string contains no other characters besides whitespace, as defined by
     * Character.isWhitespace(currentChar)
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isOnlyWhitespace(String str)
    {
        if (str == null || str.isEmpty())
            return false;

        char currentChar;

        for (int index = 0; index < str.length(); index++)
        {
            currentChar = str.charAt(index);

            if (!Character.isWhitespace(currentChar))
                return false;
        }


        return true;
    }

    /**
     * 1) null or
     * 2) empty or
     * 3) only contains whitespace (as defined by Character.isWhitespace(ch))
     *
     * @param str the str
     * @return boolean boolean
     */
    public static boolean isVoidString(String str)
    {
        if (str == null ||
                str.isEmpty() ||
                StringUtilites.isOnlyWhitespace(str))
            return true;

        return false;
    }
}
