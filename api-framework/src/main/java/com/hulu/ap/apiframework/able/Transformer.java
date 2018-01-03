package com.hulu.ap.apiframework.able;

/**
 * Represents a function that accepts two arguments and produces a transformed
 * result.
 *
 * @param <FROM> the type of the to be transformed class
 * @param <TO>   the type of the target class
 * @author xu.zhang
 */
public interface Transformer<FROM, TO> {

    /**
     * Transform object with type of <code>FROM</code> to type of <code>TO</code>
     *
     * @param from Source object
     * @return Target object
     */
    TO transform(FROM from);
}
