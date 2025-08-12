package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Context about file position in which a {@link AdoThread} is posted. A thread can be associated with a span of text
 * in a file, either as "left" (git target contents) or "right" (git source contents).
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/get?view=azure-devops-rest-7.1#gitpullrequestcommentthreadcontext">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoThreadContext {
    private String filePath;
    private AdoThreadContextPosition leftFileStart;
    private AdoThreadContextPosition leftFileEnd;
    private AdoThreadContextPosition rightFileStart;
    private AdoThreadContextPosition rightFileEnd;

    public AdoThreadContext() {
    }

    public AdoThreadContext(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Create a new {@link AdoThreadContext} with the file path set.
     *
     * @param filePath path to the file in which to place the thread
     * @return new {@link AdoThreadContext}
     * @see AdoThreadContext#withLeftFileStart(int, int)
     * @see AdoThreadContext#withLeftFileEnd(int, int)
     * @see AdoThreadContext#withRightFileStart(int, int)
     * @see AdoThreadContext#withRightFileEnd(int, int)
     */
    public static AdoThreadContext create(String filePath) {
        return new AdoThreadContext(filePath);
    }

    /**
     * Sets the position of the first character of the thread's span in left file.
     *
     * @param line   line number
     * @param offset column number
     * @return thread context with property set
     */
    @JsonIgnore
    public AdoThreadContext withLeftFileStart(int line, int offset) {
        this.leftFileStart = new AdoThreadContextPosition(line, offset);
        return this;
    }

    /**
     * Sets the position of the last character of the thread's span in left file.
     *
     * @param line   line number
     * @param offset column number
     * @return thread context with property set
     */
    @JsonIgnore
    public AdoThreadContext withLeftFileEnd(int line, int offset) {
        this.leftFileEnd = new AdoThreadContextPosition(line, offset);
        return this;
    }

    /**
     * Sets the position of the first character of the thread's span in right file.
     *
     * @param line   line number
     * @param offset column number
     * @return thread context with property set
     */
    @JsonIgnore
    public AdoThreadContext withRightFileStart(int line, int offset) {
        this.rightFileStart = new AdoThreadContextPosition(line, offset);
        return this;
    }

    /**
     * Sets the position of the last character of the thread's span in right file.
     *
     * @param line   line number
     * @param offset column number
     * @return thread context with property set
     */
    @JsonIgnore
    public AdoThreadContext withRightFileEnd(int line, int offset) {
        this.rightFileEnd = new AdoThreadContextPosition(line, offset);
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public AdoThreadContextPosition getLeftFileStart() {
        return leftFileStart;
    }

    public AdoThreadContextPosition getLeftFileEnd() {
        return leftFileEnd;
    }

    public AdoThreadContextPosition getRightFileStart() {
        return rightFileStart;
    }

    public AdoThreadContextPosition getRightFileEnd() {
        return rightFileEnd;
    }
}
