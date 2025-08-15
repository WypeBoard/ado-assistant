package io.github.wypeboard.adoassistant.ado;

import io.github.wypeboard.adoassistant.ado.model.AdoGitPullRequest;
import io.github.wypeboard.adoassistant.ado.model.AdoThread;
import io.github.wypeboard.adoassistant.config.PropertiesConstants;
import io.github.wypeboard.adoassistant.config.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdoController.class);
    private final DefaultAdoClient adoClient;

    public AdoController() {
        PropertiesLoader instance = PropertiesLoader.getInstance();
        String repository = instance.get(PropertiesConstants.ADO_REPOSITORY);
        String authToken = instance.get(PropertiesConstants.ADO_AUTH_TOKEN);
        adoClient = new DefaultAdoClient(repository, authToken);
    }

    public void fetchAdoData() {
        LOGGER.info("Fetching data from ADO");

        LOGGER.info("Fetching Pull requests...");
        List<AdoGitPullRequest> pullRequests = this.adoClient.getPullRequests();
        LOGGER.info("Fetched {} pull requests", pullRequests.size());

        for (AdoGitPullRequest pullRequest : pullRequests) {
            List<AdoThread> commentThreads = this.adoClient.getCommentThreads(pullRequest.getPullRequestId());
        }
    }
}
