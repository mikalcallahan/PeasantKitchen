package designPatterns;

/**
 * The interface Visitor.
 *
 * @param <T> the type parameter
 */
public interface Visitor<T>
{
    /**
     * Visit.
     *
     * @param item the item
     */
    void visit(T item);
}
