package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Change made in a pull request
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-iteration-changes/get?view=azure-devops-rest-7.1&tabs=HTTP#gitpullrequestchange">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequestChange {
    private Integer changeId;
    private Integer changeTrackingId;
    private AdoVersionControlChangeType changeType;
    private AdoGitPullRequestChangeItem item;

    public AdoGitPullRequestChange() {
    }

    public Integer getChangeId() {
        return changeId;
    }

    public Integer getChangeTrackingId() {
        return changeTrackingId;
    }

    public AdoVersionControlChangeType getChangeType() {
        return changeType;
    }

    public AdoGitPullRequestChangeItem getItem() {
        return item;
    }
}
