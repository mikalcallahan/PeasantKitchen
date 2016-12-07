package designPatterns;

/**
 * The Visitor interface.
 *
 * @param <T> the type parameter
 */
public interface Visitor<T>
{
    /**
     * Visits each item, one at a time
     *
     * @param item the item
     */
    void visit(T item);
}
