package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Collection of {@link AdoGitPullRequestChange}s returned from Azure Devops.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-iteration-changes/get?view=azure-devops-rest-7.1&tabs=HTTP#gitpullrequestchanges">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequestChanges {
    private List<AdoGitPullRequestChange> changeEntries;

    public AdoGitPullRequestChanges() {
    }

    public List<AdoGitPullRequestChange> getChangeEntries() {
        return changeEntries;
    }
}
