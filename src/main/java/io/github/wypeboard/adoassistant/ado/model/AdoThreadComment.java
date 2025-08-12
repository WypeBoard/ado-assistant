package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.wypeboard.adoassistant.ado.model.api.Identifiable;

import java.util.Optional;

/**
 * Single comment/reply in a comment thread.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/get?view=azure-devops-rest-7.1#comment">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoThreadComment {
    private Integer id;
    private AdoIdentityRef author;
    private Integer parentCommentId;
    private String content;
    private final AdoCommentType commentType = AdoCommentType.TEXT;
    @JsonProperty("isDeleted")
    private boolean isDeleted;

    public AdoThreadComment() {
    }

    private AdoThreadComment(String content) {
        this.content = content;
    }

    /**
     * Create a new thread comment with the given content.
     *
     * @param content string contents to use as the message body
     * @return a new {@link AdoThreadComment}
     */
    public static AdoThreadComment create(String content) {
        return new AdoThreadComment(content);
    }

    /**
     * Set the id of the comment's parent comment (for a reply).
     * This method
     *
     * @param parentCommentId id to set as parent comment
     * @return thread comment with parent id set
     */
    @JsonIgnore
    public AdoThreadComment withParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
        return this;
    }

    /**
     * Checks whether the thread comment is made by a given author and includes the given content.
     *
     * @param id      identity that can leave comments
     * @param content string content to look up in comment content
     * @return true if author and content matches, false otherwise
     */
    @JsonIgnore
    public boolean matchesAuthorAndContent(Identifiable id, String content) {
        boolean authorMatches = Optional.ofNullable(getAuthor())
                .map(AdoIdentityRef::getId)
                .map(id.getId()::equals)
                .orElse(false);

        boolean hasContentMatch = Optional.ofNullable(getContent())
                .map(con -> con.contains(content))
                .orElse(false);
        return authorMatches && hasContentMatch;
    }

    public Integer getId() {
        return id;
    }

    public AdoIdentityRef getAuthor() {
        return author;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public String getContent() {
        return content;
    }

    public AdoCommentType getCommentType() {
        return commentType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
