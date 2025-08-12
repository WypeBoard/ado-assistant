package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Context pertaining to the iterations being viewed at the time of creating a {@link AdoThread}.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/create?view=azure-devops-rest-7.1#commentiterationcontext">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoCommentIterationContext {
    private Integer firstComparingIteration;
    private Integer secondComparingIteration;

    public AdoCommentIterationContext() {
    }

    public static AdoCommentIterationContext atIteration(AdoGitPullRequestIteration iteration) {
        return create(iteration.getId(), iteration.getId());
    }

    public static AdoCommentIterationContext create(Integer firstIteration, Integer secondIteration) {
        AdoCommentIterationContext iterationContext = new AdoCommentIterationContext();
        iterationContext.firstComparingIteration = firstIteration;
        iterationContext.secondComparingIteration = secondIteration;
        return iterationContext;
    }

    public Integer getFirstComparingIteration() {
        return firstComparingIteration;
    }

    public Integer getSecondComparingIteration() {
        return secondComparingIteration;
    }
}
