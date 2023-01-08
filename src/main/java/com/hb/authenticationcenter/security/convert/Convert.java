package com.hb.authenticationcenter.security.convert;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2023/1/7
 */
@FunctionalInterface
public interface Convert<T, R> {

    /**
     * Gets a collection type result.
     *
     * @return a result
     */
    Collection<T> get();

    /**
     * collection result convert attr set.
     * @param mapper mapper function
     * @return attr set
     */
    default Set<R> convert(Function<? super T, ? extends R> mapper) {
        Collection<T> collection = this.get();
        Objects.requireNonNull(collection);
        return collection
                .stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }
}
