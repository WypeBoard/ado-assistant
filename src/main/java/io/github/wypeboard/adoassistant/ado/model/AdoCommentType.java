package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type of thread comment at the time of creation.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-thread-comments/get?view=azure-devops-rest-7.1#commenttype">Definition in Azure DevOps API docs</a>
 */
public enum AdoCommentType {
    CODE_CHANGE("codeChange"),
    SYSTEM("system"),
    TEXT("text"),
    UNKNOWN("unknown"),
    ;
    private final String value;

    AdoCommentType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
