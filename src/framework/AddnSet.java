package framework;

import java.util.HashSet;

/**
 * Adds your item to the HashSet, and then returns the item you added to you
 *
 * @param <T> the type parameter
 */
public class AddnSet<T> extends HashSet<T>
{
	private static final long serialVersionUID = 8085094930498135413L;

    /**
     * @param item the item you'd like to add
     * @return the t
     */
    public T put(T item)
    {
        this.add(item);
        return item;
    }
}
