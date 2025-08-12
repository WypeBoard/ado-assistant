package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type representing a reason for which a pull request iteration is created.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-iterations/list?view=azure-devops-rest-7.1#iterationreason">Definition in Azure DevOps API docs</a>
 */
public enum AdoIterationReason {
    CREATE("create"),
    FORCE_PUSH("forcePush"),
    PUSH("push"),
    REBASE("rebase"),
    RESOLVE_CONFLICTS("resolveConflicts"),
    RETARGET("retarget"),
    UNKNOWN("unknown"),
    ;
    private final String value;

    AdoIterationReason(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
