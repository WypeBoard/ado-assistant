package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Type reprenting a vote cast on a pull request by a reviewer.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-reviewers/get?view=azure-devops-rest-7.1&tabs=HTTP#identityrefwithvote">Definition in Azure DevOps API docs</a>
 */
public enum AdoReviewVote {
    APPROVED(10, false),
    APPROVED_WITH_SUGGESTIONS(5, false),
    NO_VOTE(0, false),
    WAITING_FOR_AUTHOR(-5, true),
    REJECTED(-10, true),
    ;
    private final int value;
    private final boolean blocking;

    AdoReviewVote(int value, boolean blocking) {
        this.value = value;
        this.blocking = blocking;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    /**
     * @return true if the vote value blocks a pull request from completing, false otherwise
     */
    @JsonIgnore
    public boolean isBlocking() {
        return blocking;
    }
}
