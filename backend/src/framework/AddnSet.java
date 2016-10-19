package framework;

import java.util.HashSet;

public class AddnSet<T> extends HashSet<T> 
{
	public T put(T item)
	{
		this.add(item);
		return item;
	}
}
