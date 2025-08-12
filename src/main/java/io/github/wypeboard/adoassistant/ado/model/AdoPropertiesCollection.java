package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * General property bag type, available for, e.g., {@link AdoGitPullRequest}s and {@link AdoThread}s.
 * These bags follow the general structure:
 * <pre>{@code
 * {
 *     "<property name>": {
 *         "$type": "<property type>",
 *         "$value": "<property value>"
 *     },
 *     ...
 * }
 * }</pre>
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-properties/list?view=azure-devops-rest-7.1&tabs=HTTP#propertiescollection">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoPropertiesCollection {
    @JsonAnyGetter
    private Map<String, Property> properties = new HashMap<>();

    public AdoPropertiesCollection() {
    }

    private AdoPropertiesCollection(Map<String, String> properties) {
        this.properties = properties.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, Property::new));
    }

    /**
     * Format an {@link AdoPropertiesCollection} from a simple string map.
     *
     * @param properties map with string keys and string values
     * @return {@link AdoPropertiesCollection} with the given properties
     */
    public static AdoPropertiesCollection from(Map<String, String> properties) {
        return new AdoPropertiesCollection(properties);
    }

    /**
     * Set the value of a property, replacing a value if the given key already exists.
     *
     * @param key   property key to set
     * @param value property value to set
     * @apiNote This method is mainly provided for deserialization.
     */
    @JsonAnySetter
    public void setProperty(String key, Property value) {
        properties.put(key, value);
    }

    /**
     * Inner shape of property items.
     */
    public static class Property {
        @JsonProperty("$type")
        private String type = "System.String";
        @JsonProperty("$value")
        private String value;

        public Property() {
        }

        Property(Map.Entry<String, String> entry) {
            this.value = entry.getValue();
        }

        String getType() {
            return type;
        }

        String getValue() {
            return value;
        }
    }
}
