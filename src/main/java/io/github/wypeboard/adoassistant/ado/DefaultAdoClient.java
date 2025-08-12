package io.github.wypeboard.adoassistant.ado;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.wypeboard.adoassistant.ado.helpers.AzureDevOpsUrlHelper;
import io.github.wypeboard.adoassistant.ado.model.AdoConnectionData;
import io.github.wypeboard.adoassistant.ado.model.AdoGitPullRequest;
import io.github.wypeboard.adoassistant.ado.model.AdoGitPullRequestChange;
import io.github.wypeboard.adoassistant.ado.model.AdoGitPullRequestChanges;
import io.github.wypeboard.adoassistant.ado.model.AdoGitPullRequestIteration;
import io.github.wypeboard.adoassistant.ado.model.AdoIdentityRefWithVote;
import io.github.wypeboard.adoassistant.ado.model.AdoPropertiesCollection;
import io.github.wypeboard.adoassistant.ado.model.AdoTeamMember;
import io.github.wypeboard.adoassistant.ado.model.AdoThread;
import io.github.wypeboard.adoassistant.ado.model.requests.Wrapped;
import io.github.wypeboard.adoassistant.config.PropertiesLoader;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultAdoClient {
    private static final String CONNECTION_DATA_ROUTE = "/ConnectionData";
    private static final String REPOSITORIES_ROUTE = "/git/repositories/%s";
    private static final String PULL_REQUESTS_ROUTE = REPOSITORIES_ROUTE + "/pullrequests/%d";
    private static final String PR_PROPERTIES_ROUTE = PULL_REQUESTS_ROUTE + "/properties";
    private static final String PR_ITERATIONS_ROUTE = PULL_REQUESTS_ROUTE + "/iterations";
    private static final String PR_ITERATION_ROUTE = PR_ITERATIONS_ROUTE + "/%d";
    private static final String PR_CHANGES_ROUTE = PR_ITERATION_ROUTE + "/changes";
    private static final String PR_REVIEWERS_ROUTE = PULL_REQUESTS_ROUTE + "/reviewers";
    private static final String PR_REVIEWER_ROUTE = PR_REVIEWERS_ROUTE + "/%s";
    private static final String PR_THREADS_ROUTE = PULL_REQUESTS_ROUTE + "/threads";
    private static final String PR_THREAD_ROUTE = PR_THREADS_ROUTE + "/%d";
    private static final String PR_THREAD_COMMENTS_ROUTE = PR_THREAD_ROUTE + "/comments";
    private static final String PR_THREAD_COMMENT_ROUTE = PR_THREAD_COMMENTS_ROUTE + "/%d";
    private static final String ITEMS_ROUTE = REPOSITORIES_ROUTE + "/items";
    private static final String BUILD_ROUTE = "/build/builds/%s";
    private static final String BUILD_PROPERTIES_ROUTE = BUILD_ROUTE + "/properties";

    private static final TypeReference<Wrapped<AdoPropertiesCollection>> PROPERTIES_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<Wrapped<List<AdoGitPullRequestIteration>>> PR_ITERATION_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<Wrapped<List<AdoIdentityRefWithVote>>> REVIEWERS_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<Wrapped<List<AdoThread>>> THREADS_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<Wrapped<List<AdoTeamMember>>> TEAM_MEMBERS_TYPE = new TypeReference<>() {
    };

    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String POST = "POST";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";
    // Arbitrarily large value for the offset when placing a line comment.
    // A comment placed on a line with 1000 characters or more will only show as marking the first 999.
    public static final int BIG_OFFSET = 999;

    private final String repositoryId;
    private final AzureDevOpsConnector connector;
    private final AzureDevOpsUrlHelper urlHelper;

    public DefaultAdoClient(String repositoryId, String authToken) {
        this.connector = new AzureDevOpsConnector(authToken);
        this.repositoryId = repositoryId;
        this.urlHelper = new AzureDevOpsUrlHelper(PropertiesLoader.getInstance());
    }

    public AdoConnectionData getConnectionData() {
        String requestUrl = urlHelper.forOrganisationApi(CONNECTION_DATA_ROUTE).toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, AdoConnectionData.class);
    }

    public AdoGitPullRequest getPullRequest(int pullRequestId) {
        String requestUrl = urlHelper.forProjectApi(PULL_REQUESTS_ROUTE, repositoryId, pullRequestId)
                .withQueryParam("includeCommits", Boolean.TRUE.toString())
                .toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, AdoGitPullRequest.class);
    }

    public AdoPropertiesCollection getPullRequestProperties(int pullRequestId) {
        String requestUrl = urlHelper.forProjectApi(PR_PROPERTIES_ROUTE, repositoryId, pullRequestId).toUrl();
        return getProperties(requestUrl);
    }

    public List<AdoGitPullRequestIteration> getPullRequestIterations(int pullRequestId) {
        String requestUrl = urlHelper.forProjectApi(PR_ITERATIONS_ROUTE, repositoryId, pullRequestId).toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, PR_ITERATION_TYPE).getValue();
    }

    public List<AdoGitPullRequestChange> getChangesForIteration(int pullRequestId, AdoGitPullRequestIteration iteration) {
        String requestUrl = urlHelper.forProjectApi(PR_CHANGES_ROUTE, repositoryId, pullRequestId, iteration.getId()).toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, AdoGitPullRequestChanges.class).getChangeEntries();
    }

    public AdoPropertiesCollection getBuildProperties(int buildId) {
        String requestUrl = urlHelper.forProjectApi(BUILD_PROPERTIES_ROUTE, buildId).toUrl();
        return getProperties(requestUrl);
    }

    private AdoPropertiesCollection getProperties(String requestUrl) {
        return connector.sendRequestAndParseResponse(GET, requestUrl, PROPERTIES_TYPE).getValue();
    }

    public List<AdoIdentityRefWithVote> getReviewers(int pullRequestId) {
        String requestUrl = urlHelper.forProjectApi(PR_REVIEWERS_ROUTE, repositoryId, pullRequestId).toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, REVIEWERS_TYPE).getValue();
    }

    public List<AdoThread> getCommentThreads(int pullRequestId) {
        String requestUrl = urlHelper.forProjectApi(PR_THREADS_ROUTE, repositoryId, pullRequestId).toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, THREADS_TYPE)
                .getValue().stream()
                .filter(thread -> !thread.isDeleted())
                .filter(AdoThread::hasTextComments)
                .collect(Collectors.toList());
    }


    public List<AdoTeamMember> getMembersByTeamDisplayName(String teamName) {
        String encodedName = URLEncoder.encode(teamName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        String requestUrl = urlHelper.forCoreApi("/teams/%s/members", encodedName).toUrl();
        return connector.sendRequestAndParseResponse(GET, requestUrl, TEAM_MEMBERS_TYPE, null).getValue();
    }
}
