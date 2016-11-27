package framework;

import java.util.HashSet;

public class AddnSet<T> extends HashSet<T>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8085094930498135413L;

	public T put(T item)
    {
        this.add(item);
        return item;
    }
}
