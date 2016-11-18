package utilities;

import java.util.ArrayList;

public class StringUtils 
{
	public static ArrayList<String> cleanIngredients(ArrayList<String> ingredients)
	{
		ArrayList<String> cleaned = new ArrayList<String>();
		
		for(String ingredient : ingredients)
			cleaned.add(ingredient.trim().toLowerCase());
		
		return cleaned;
	}
	
	public static boolean isOnlyWhitespace(String str)
	{
		if(str == null || str.isEmpty())
			return false;
		
		char currentChar;
		
		for(int index = 0; index < str.length(); index++)
		{
			currentChar = str.charAt(index);
			
			if(!Character.isWhitespace(currentChar))
				return false;
		}



		return true;
	}
	
	/**
	 * 1) null or
	 * 2) empty or
	 * 3) only contains whitespace (as defined by Character.isWhitespace(ch))
	 * 
	 * 
	 * @param str
	 * @return boolean
	 */
	
	public static boolean isVoidString(String str)
	{
		if(str == null ||
		   str.isEmpty() || 
		   StringUtils.isOnlyWhitespace(str))
				return true;
		
		return false;
	}
}
