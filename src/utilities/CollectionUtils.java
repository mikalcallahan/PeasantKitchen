package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Collection utils.
 */
public class CollectionUtils
{
    public static <T> Set<T> set(T... items)
    {
        HashSet<T> set = new HashSet<T>();

        for (T item : items)
            set.add(item);

        return set;
    }


    /**
     * Array list array list.
     *
     * @param <T>   the type parameter
     * @param items the items
     * @return the array list
     */
    public static <T> ArrayList<T> arrayList(T... items)
    {
        ArrayList<T> arrList = new ArrayList<T>();

        for (T item : items)
            arrList.add(item);

        return arrList;
    }

    /**
     * Hash set hash set.
     *
     * @param <T>   the type parameter
     * @param items the items
     * @return the hash set
     */
    public static <T> HashSet<T> hashSet(Iterable<T> items)
    {
        if (items == null)
            return null;

        HashSet<T> set = new HashSet<T>();

        for (T item : items)
            set.add(item);

        return set;
    }

    /**
     * Linked hash set linked hash set.
     *
     * @param <T>   the type parameter
     * @param items the items
     * @return the linked hash set
     */
    public static <T> LinkedHashSet<T> linkedHashSet(Iterable<T> items)
    {
        if (items == null)
            return null;

        LinkedHashSet<T> set = new LinkedHashSet<T>();

        for (T item : items)
            set.add(item);

        return set;
    }

    /**
     * Equal sets boolean.
     *
     * @param <T> the type parameter
     * @param a   the a
     * @param b   the b
     * @return the boolean
     */
    public static <T> boolean equalSets(Set<T> a, Set<T> b)
    {
        if (a == null && b == null)
            return true;
        else if (a == null || b == null)
            return false;

        for (T item : a)
            if (!b.contains(item))
                return false;

        for (T item : b)
            if (!a.contains(item))
                return false;

        return true;
    }


    public static <SourceObject, Result> Set<Result> set(Iterable<SourceObject> container, FieldSelectionFunction<SourceObject, Result> fieldSelector)
    {
        HashSet<Result> results = new HashSet<Result>();

        for (SourceObject obj : container)
            results.add(fieldSelector.selectFrom(obj));

        return results;
    }

    public interface FieldSelectionFunction<SourceObject, Result>
    {
        Result selectFrom(SourceObject obj);
    }
}
