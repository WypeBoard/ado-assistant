package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Position for a thread span within a file.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/get?view=azure-devops-rest-7.1#commentposition">Definition in Azure DevOps API docs</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdoThreadContextPosition {
    private int line;
    private int offset;

    public AdoThreadContextPosition() {
    }

    public AdoThreadContextPosition(int line, int offset) {
        this.line = line;
        this.offset = offset;
    }

    public int getLine() {
        return line;
    }

    public int getOffset() {
        return offset;
    }
}
