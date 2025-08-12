package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced {@link AdoIdentityRef} representing a pull request reviewer.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-reviewers/get?view=azure-devops-rest-7.1&tabs=HTTP#identityrefwithvote">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoIdentityRefWithVote extends AdoIdentityRef {
    @JsonProperty("hasDeclined")
    private boolean hasDeclined;
    @JsonProperty("isContainer")
    private boolean isContainer;
    @JsonProperty("isFlagged")
    private boolean isFlagged;
    @JsonProperty("isRequired")
    private boolean isRequired;
    private AdoReviewVote vote;
    private final List<AdoIdentityRefWithVote> votedFor = new ArrayList<>();

    public AdoIdentityRefWithVote() {
    }

    public boolean hasDeclined() {
        return hasDeclined;
    }

    public boolean isContainer() {
        return isContainer;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public AdoReviewVote getVote() {
        return vote;
    }

    public List<AdoIdentityRefWithVote> getVotedFor() {
        return votedFor;
    }
}
