package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *  Collection of {@link AdoGitPullRequest}s returned from Azure Devops.
 *
 *  @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-requests/get-pull-requests?view=azure-devops-rest-7.1&tabs=HTTP#gitpullrequest">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequests {
    @JsonProperty("value")
    private List<AdoGitPullRequest> adoGitPullRequestList;

    private int count;

    public AdoGitPullRequests() {
    }

    public List<AdoGitPullRequest> getAdoGitPullRequestList() {
        return adoGitPullRequestList;
    }

    public int getCount() {
        return count;
    }
}
