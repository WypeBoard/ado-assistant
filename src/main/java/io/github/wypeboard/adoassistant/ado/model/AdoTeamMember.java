package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identity as a member of a team.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/core/teams/get-team-members-with-extended-properties?view=azure-devops-rest-7.1&tabs=HTTP#teammember">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoTeamMember {
    private AdoIdentityRef identity;
    @JsonProperty("isTeamAdmin")
    private boolean isTeamAdmin;

    public AdoTeamMember() {
    }

    public AdoIdentityRef getIdentity() {
        return identity;
    }

    public boolean isTeamAdmin() {
        return isTeamAdmin;
    }
}
