package utilities;

import java.util.ArrayList;

public class CollectionUtils 
{
	public static <T> ArrayList<T> arrayList(T ... items)
	{
		ArrayList<T> arrList = new ArrayList<>();
		
		for(T item : items) arrList.add(item);
		
		return arrList;
	}
}
