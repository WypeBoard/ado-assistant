package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Information that describe a git pull request iteration, which are created as a result of creating and pushing updates to a pull request.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-iterations/list?view=azure-devops-rest-7.1#gitpullrequestiteration">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequestIteration {
    private Integer id;
    private AdoIdentityRef author;
    private List<AdoGitCommitRef> commits;
    private AdoGitCommitRef commonRefCommit;
    private OffsetDateTime createdDate;
    private OffsetDateTime updatedDate;
    private String description;
    @JsonProperty("hasMoreCommits")
    private boolean hasMoreCommits;
    private String newTargetRefName;
    private String oldTargetRefName;
    private AdoIterationReason reason;
    private AdoGitCommitRef sourceRefCommit;
    private AdoGitCommitRef targetRefCommit;

    public AdoGitPullRequestIteration() {
    }

    public AdoIdentityRef getAuthor() {
        return author;
    }

    public List<AdoGitCommitRef> getCommits() {
        return commits;
    }

    public AdoGitCommitRef getCommonRefCommit() {
        return commonRefCommit;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public OffsetDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasMoreCommits() {
        return hasMoreCommits;
    }

    public int getId() {
        return id;
    }

    public String getNewTargetRefName() {
        return newTargetRefName;
    }

    public String getOldTargetRefName() {
        return oldTargetRefName;
    }

    public AdoIterationReason getReason() {
        return reason;
    }

    public AdoGitCommitRef getSourceRefCommit() {
        return sourceRefCommit;
    }

    public AdoGitCommitRef getTargetRefCommit() {
        return targetRefCommit;
    }
}
