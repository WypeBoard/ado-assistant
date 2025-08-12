package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This endpoint is undocumented in <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/">the Azure DevOps API docs</a>.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoConnectionData {
    private AdoIdentity authenticatedUser;
    private AdoIdentity authorizedUser;

    public AdoConnectionData() {
    }

    public AdoIdentity getAuthenticatedUser() {
        return authenticatedUser;
    }

    public AdoIdentity getAuthorizedUser() {
        return authorizedUser;
    }
}
