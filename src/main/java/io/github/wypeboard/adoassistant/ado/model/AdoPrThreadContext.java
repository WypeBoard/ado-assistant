package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Context with details about diffs being viewed at the time of {@link AdoThread} creation.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/create?view=azure-devops-rest-7.1&tabs=HTTP#gitpullrequestcommentthreadcontext">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoPrThreadContext {
    private Integer changeTrackingId;
    private AdoCommentIterationContext iterationContext;

    public AdoPrThreadContext() {
    }

    public static AdoPrThreadContext fromPullRequestChange(AdoGitPullRequestChange pullRequestChange) {
        Integer changeTrackingId = pullRequestChange.getChangeTrackingId();
        AdoPrThreadContext prThreadContext = new AdoPrThreadContext();
        prThreadContext.changeTrackingId = changeTrackingId;
        return prThreadContext;
    }

    @JsonIgnore
    public AdoPrThreadContext atIteration(AdoGitPullRequestIteration iteration) {
        this.iterationContext = AdoCommentIterationContext.atIteration(iteration);
        return this;
    }

    public Integer getChangeTrackingId() {
        return changeTrackingId;
    }

    public AdoCommentIterationContext getIterationContext() {
        return iterationContext;
    }
}
