package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Comment thread on a pull request. This type is used by all messages that appear in the stream below a pull request
 * description on its page, both text messages a reviewer might post and system messages about pushes, changes to
 * reviewers, etc.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/get?view=azure-devops-rest-7.1#gitpullrequestcommentthread">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoThread {
    private Integer id;
    private AdoThreadContext threadContext;
    private List<AdoThreadComment> comments;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private AdoThreadStatus status;
    private OffsetDateTime publishedDate;
    private OffsetDateTime lastUpdatedDate;
    private final AdoPropertiesCollection properties = new AdoPropertiesCollection();

    public AdoThread() {
    }

    /**
     * Retrieve the comment of a thread with the lowest id.
     * Thread comments are numbered sequentially starting from 1.
     *
     * @return wrapped {@link AdoThreadComment} if found, empty optional otherwise
     */
    @JsonIgnore
    public Optional<AdoThreadComment> getRootComment() {
        return Optional.ofNullable(comments).stream()
                .flatMap(Collection::stream)
                .min(Comparator.comparing(AdoThreadComment::getId));
    }

    /**
     * Checks whether a thread is a text comment, which can be replied to.
     *
     * @return true if the thread contains text comments, false otherwise
     */
    @JsonIgnore
    public boolean hasTextComments() {
        return comments.stream()
                .map(AdoThreadComment::getCommentType)
                .anyMatch(AdoCommentType.TEXT::equals);
    }

    public Integer getId() {
        return id;
    }

    public List<AdoThreadComment> getComments() {
        return comments;
    }

    public AdoThreadContext getThreadContext() {
        return threadContext;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public AdoThreadStatus getStatus() {
        return status;
    }

    public OffsetDateTime getPublishedDate() {
        return publishedDate;
    }

    public OffsetDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public AdoPropertiesCollection getProperties() {
        return properties;
    }
}
