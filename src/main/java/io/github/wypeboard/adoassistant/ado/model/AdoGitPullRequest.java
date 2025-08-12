package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.wypeboard.adoassistant.ado.model.api.Identifiable;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * General pull request information.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-requests/get-pull-request?view=azure-devops-rest-7.1#gitpullrequest">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequest {
    private String artifactId;
    private AdoIdentityRef autoCompleteSetBy;
    private AdoIdentityRef closedBy;
    private OffsetDateTime closedDate;
    private int codeReviewId;
    private final List<AdoGitCommitRef> commits = new ArrayList<>();
    private AdoGitPullRequestCompletionOptions completionOptions;
    private OffsetDateTime completionQueueTime;
    private AdoIdentityRef createdBy;
    private OffsetDateTime creationDate;
    private String description;
    @JsonProperty("isDraft")
    private boolean isDraft;
    private AdoGitCommitRef lastMergeCommit;
    private AdoGitCommitRef lastMergeSourceCommit;
    private AdoGitCommitRef lastMergeTargetCommit;
    private String mergeId;
    private String mergeStatus;
    private int pullRequestId;
    private final List<AdoIdentityRefWithVote> reviewers = new ArrayList<>();
    private String sourceRefName;
    private String status;
    @JsonProperty("supportsIterations")
    private boolean supportsIterations;
    private String targetRefName;
    private String title;
    private String url;

    public AdoGitPullRequest() {
    }

    /**
     * Checks whether an identity is a reviewer on the pull request.
     *
     * @param identifiable {@link Identifiable} that's potentially a reviewer
     * @return true if the identity is a reviewer, false otherwise
     */
    @JsonIgnore
    public boolean isReviewer(Identifiable identifiable) {
        return reviewers.stream()
                .map(AdoIdentityRef::getId)
                .anyMatch(identifiable.getId()::equals);
    }

    public String getArtifactId() {
        return artifactId;
    }

    public AdoIdentityRef getAutoCompleteSetBy() {
        return autoCompleteSetBy;
    }

    public AdoIdentityRef getClosedBy() {
        return closedBy;
    }

    public OffsetDateTime getClosedDate() {
        return closedDate;
    }

    public int getCodeReviewId() {
        return codeReviewId;
    }

    public List<AdoGitCommitRef> getCommits() {
        return commits;
    }

    public AdoGitPullRequestCompletionOptions getCompletionOptions() {
        return completionOptions;
    }

    public OffsetDateTime getCompletionQueueTime() {
        return completionQueueTime;
    }

    public AdoIdentityRef getCreatedBy() {
        return createdBy;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public AdoGitCommitRef getLastMergeCommit() {
        return lastMergeCommit;
    }

    public AdoGitCommitRef getLastMergeSourceCommit() {
        return lastMergeSourceCommit;
    }

    public AdoGitCommitRef getLastMergeTargetCommit() {
        return lastMergeTargetCommit;
    }

    public String getMergeId() {
        return mergeId;
    }

    public String getMergeStatus() {
        return mergeStatus;
    }

    public int getPullRequestId() {
        return pullRequestId;
    }

    public List<AdoIdentityRefWithVote> getReviewers() {
        return reviewers;
    }

    public String getSourceRefName() {
        return sourceRefName;
    }

    public String getStatus() {
        return status;
    }

    public boolean supportsIterations() {
        return supportsIterations;
    }

    public String getTargetRefName() {
        return targetRefName;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
