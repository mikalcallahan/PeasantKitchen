package framework;

import java.util.HashSet;

/**
 * The type Addn set.
 *
 * @param <T> the type parameter
 */
public class AddnSet<T> extends HashSet<T>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8085094930498135413L;

    /**
     * Put t.
     *
     * @param item the item
     * @return the t
     */
    public T put(T item)
    {
        this.add(item);
        return item;
    }
}
