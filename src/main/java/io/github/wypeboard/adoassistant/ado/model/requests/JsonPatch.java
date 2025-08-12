package io.github.wypeboard.adoassistant.ado.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Representation of the JSON Patch format, used to describe changes to a JSON document.
 *
 * @see <a href="https://jsonpatch.com/">jsonpatch.com</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class JsonPatch {
    private final List<PatchUnit> units = new ArrayList<>();

    private JsonPatch() {
    }

    /**
     * Initialize a new patch builder.
     *
     * @return an empty {@link JsonPatch}
     */
    public static JsonPatch create() {
        return new JsonPatch();
    }

    @JsonValue
    public List<PatchUnit> getUnits() {
        return units;
    }

    /**
     * Serialize the list of patch units to JSON using the supplied serializer.
     *
     * @param serializer function producing a JSON string
     * @return JSON string
     */
    public String toJson(Function<JsonPatch, String> serializer) {
        return serializer.apply(this);
    }

    /**
     * Add a patch unit with the operation {@code add} to the list of units.
     * This operation adds a new value.
     *
     * @param path  path to the variable
     * @param value value to set
     * @return ongoing patch builder
     */
    public JsonPatch add(String path, String value) {
        return addPatchUnit(null, JsonPatchOperation.ADD, path, value);
    }

    /**
     * Add a patch unit with the operation {@code remove} to the list of units.
     * This operation removes the value at a given path if it exists.
     *
     * @param path path to the variable
     * @return ongoing patch builder
     */
    public JsonPatch remove(String path) {
        return addPatchUnit(null, JsonPatchOperation.REMOVE, path, null);
    }

    /**
     * Add a patch unit with the operation {@code replace} to the list of units.
     * This operation updates the value at a given path, equivalent to a {@code remove} followed by an {@code add}.
     *
     * @param path  path to the variable
     * @param value value to set
     * @return ongoing patch builder
     */
    public JsonPatch replace(String path, String value) {
        return addPatchUnit(null, JsonPatchOperation.REPLACE, path, value);
    }

    /**
     * Add a patch unit with the operation {@code copy} to the list of units.
     * This operation copies the value from one location to another.
     *
     * @param from path to the value to copy from
     * @param path path to the variable to copy to
     * @return ongoing patch builder
     */
    public JsonPatch copy(String from, String path) {
        return addPatchUnit(from, JsonPatchOperation.COPY, path, null);
    }

    /**
     * Add a patch unit with the operation {@code move} to the list of units.
     * This operation moves the value from one location to another.
     *
     * @param from path to the value to move from
     * @param path path to the variable to move to
     * @return ongoing patch builder
     */
    public JsonPatch move(String from, String path) {
        return addPatchUnit(from, JsonPatchOperation.MOVE, path, null);
    }

    /**
     * Add a patch unit with the operation {@code test} to the list of units.
     * This operation checks if the specified value is set on the given path.
     * If the test unit fails, the whole patch it's a part of is not applied
     *
     * @param path  path to the variable
     * @param value value to check for
     * @return ongoing patch builder
     */
    public JsonPatch test(String path, String value) {
        return addPatchUnit(null, JsonPatchOperation.TEST, path, value);
    }

    private JsonPatch addPatchUnit(String from, JsonPatchOperation operation, String path, String value) {
        units.add(new PatchUnit(from, operation, path, value));
        return this;
    }

    public static class PatchUnit {
        private final String from;
        private final JsonPatchOperation op;
        private final String path;
        private final String value;

        private PatchUnit(String from, JsonPatchOperation op, String path, String value) {
            this.from = from;
            this.op = op;
            this.path = path;
            this.value = value;
        }

        public String getFrom() {
            return from;
        }

        public JsonPatchOperation getOp() {
            return op;
        }

        public String getPath() {
            return path;
        }

        public String getValue() {
            return value;
        }
    }
}
