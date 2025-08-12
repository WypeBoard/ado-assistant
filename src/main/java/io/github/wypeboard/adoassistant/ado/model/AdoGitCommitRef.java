package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of a git commit.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-commits/get-pull-request-commits?view=azure-devops-rest-7.1#gitcommitref">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitCommitRef {
    private String comment;
    private String commitId;
    private String url;

    public AdoGitCommitRef() {
    }

    public String getComment() {
        return comment;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getUrl() {
        return url;
    }
}
