package io.github.wypeboard.adoassistant.ado.model.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Helper class to create generic JSON objects used for request bodies in API calls.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestObject {
    @JsonAnyGetter
    private final Map<String, Object> fields = new HashMap<>();

    private RequestObject() {
    }

    /**
     * Initialize a new {@link RequestObject}.
     *
     * @return an empty request object builder
     */
    public static RequestObject create() {
        return new RequestObject();
    }

    /**
     * Add a named field to the request object builder.
     *
     * @param field name of the field
     * @param value value of the field
     * @return ongoing request object builder
     */
    @JsonIgnore
    public RequestObject withValue(String field, Object value) {
        fields.put(field, value);
        return this;
    }

    /**
     * Adds a named field to the request object builder, if present, does nothing otherwise.
     *
     * @param field name of the field
     * @param value potentially empty value of the field
     * @return ongoing request object builder
     */
    @JsonIgnore
    public RequestObject withOptionalValue(String field, Optional<Object> value) {
        value.ifPresent(val -> fields.put(field, val));
        return this;
    }

    /**
     * Add a named array field to the request object builder.
     *
     * @param field  name of the field
     * @param values values to list in the array
     * @return ongoing request object builder
     */
    @JsonIgnore
    public RequestObject withArray(String field, Object... values) {
        fields.put(field, Arrays.asList(values));
        return this;
    }

    /**
     * /**
     * Serialize the request object to JSON using the supplied serializer.
     *
     * @param serializer function producing a JSON string
     * @return JSON string
     */
    @JsonIgnore
    public String toJson(Function<RequestObject, String> serializer) {
        return serializer.apply(this);
    }
}
