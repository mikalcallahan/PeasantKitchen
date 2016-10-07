package framework;

import java.util.HashSet;

/*
 * Holds all of the relavent info on a user's search
 */

public class SearchQuery 
{
	public HashSet<String> ingredients = new HashSet<String>();
	public Interval cookTime; //This'll need some more thought
}
