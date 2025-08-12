package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pull request completion options.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-requests/get-pull-request?view=azure-devops-rest-7.1#gitpullrequestcompletionoptions">Definition on Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequestCompletionOptions {
    @JsonProperty("bypassPolicy")
    private boolean bypassPolicy;
    private String bypassReason;
    @JsonProperty("deleteSourceBranch")
    private boolean deleteSourceBranch;
    private String mergeCommitMessage;
    private String mergeStrategy;
    @JsonProperty("squashMerge")
    private boolean squashMerge;
    @JsonProperty("triggeredByAutoComplete")
    private boolean triggeredByAutoComplete;

    public AdoGitPullRequestCompletionOptions() {
    }

    public boolean bypassesPolicy() {
        return bypassPolicy;
    }

    public String getBypassReason() {
        return bypassReason;
    }

    public boolean deletesSourceBranch() {
        return deleteSourceBranch;
    }

    public String getMergeCommitMessage() {
        return mergeCommitMessage;
    }

    public String getMergeStrategy() {
        return mergeStrategy;
    }

    public boolean isSquashMerge() {
        return squashMerge;
    }

    public boolean isTriggeredByAutoComplete() {
        return triggeredByAutoComplete;
    }
}
