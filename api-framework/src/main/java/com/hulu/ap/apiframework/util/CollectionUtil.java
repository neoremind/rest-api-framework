package com.hulu.ap.apiframework.util;

import com.hulu.ap.apiframework.able.Closure;

import java.util.Collection;
import java.util.Iterator;

/**
 * Collection utility
 *
 * @author xu.zhang
 */
public class CollectionUtil {

    /**
     * Executes the given closure on each element in the collection.
     * <p>
     * If the input collection or closure is null, there is no change made.
     *
     * @param collection the collection to get the input from, may be null
     * @param closure    the closure to perform, may be null
     */
    public static void forAllDo(Collection collection, Closure closure) {
        if (collection != null && closure != null) {
            for (Iterator it = collection.iterator(); it.hasNext(); ) {
                closure.execute(it.next());
            }
        }
    }

    /**
     * Check if <code>Collection</code> is <code>null</code> or empty array<code>[]</code>。
     *
     * @param collection collection
     * @return If empty return true
     * @see Collection
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null) || (collection.size() == 0);
    }
}
