package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.wypeboard.adoassistant.ado.model.api.Identifiable;

/**
 * General identity type, used by the undocumented {@link AdoConnectionData} structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoIdentity implements Identifiable {
    private String id;
    private String providerDisplayName;

    public AdoIdentity() {
    }

    @Override
    public String getId() {
        return id;
    }

    public String getProviderDisplayName() {
        return providerDisplayName;
    }
}
