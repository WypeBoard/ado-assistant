package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Information about an item in a {@link AdoGitPullRequestChange}.
 * Undocumented in Azure DevOps API Docs.
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public class AdoGitPullRequestChangeItem {
    private String objectId;
    private String originalObjectId;
    private String path;

    public AdoGitPullRequestChangeItem() {
    }

    public String getObjectId() {
        return objectId;
    }

    public String getOriginalObjectId() {
        return originalObjectId;
    }

    public String getPath() {
        return path;
    }
}
