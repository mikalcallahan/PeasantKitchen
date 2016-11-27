package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CollectionUtils 
{
	public static <T> ArrayList<T> arrayList(T ... items)
	{
		ArrayList<T> arrList = new ArrayList<T>();
		
		for(T item : items)
			arrList.add(item);
		
		return arrList;
	}
	
	public static <T> HashSet<T> hashSet(Iterable<T> items)
	{
		if(items == null)
			return null;
		
		HashSet<T> set = new HashSet<T>();
		
		for(T item : items)
			set.add(item);
		
		return set;
	}
	
	public static <T> LinkedHashSet<T> linkedHashSet(Iterable<T> items)
	{
		if(items == null)
			return null;
		
		LinkedHashSet<T> set = new LinkedHashSet<T>();
		
		for(T item : items)
			set.add(item);
		
		return set;
	}
	
	public static <T> boolean equalSets(Set<T> a, Set<T> b)
	{
		if(a == null && b == null)
			return true;
		else if(a == null || b == null)
			return false;
		
		for(T item : a)
			if(!b.contains(item))
				return false;
		
		for(T item : b)
			if(!a.contains(item))
				return false;
		
		return true;
	}
	
 }
