package io.github.wypeboard.adoassistant.ado.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrapper type for the return type of certain endpoints, such as those returning array-like structures.
 * Such responses have the shape {@code {"value": [...], ...}} instead of {@code [...]} and need the wrapping to deserialize properly.
 * Any properties other than {@code value} are ignored.
 *
 * @param <T> inner type
 * @apiNote This class is only meant to be used for type references, i.e., {@code TypeReference<Wrapped<T>>}
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class Wrapped<T> {
    private T value;

    public Wrapped() {
    }

    public T getValue() {
        return value;
    }
}
