package io.github.wypeboard.adoassistant.ado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Status of a comment thread, which is settable on the pull request page via a dropdown on the root comment.
 *
 * @see <a href="https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/get?view=azure-devops-rest-7.1#commentthreadstatus">Definition in Azure DevOps API docs</a>
 */
public enum AdoThreadStatus {
    ACTIVE("active", true),
    PENDING("pending", true),
    FIXED("fixed", false), // displays as "Resolved"
    WONT_FIX("wontFix", false),
    UNKNOWN("unknown", true),
    CLOSED("closed", false),
    // the API documentation also lists a "byDesign" value, but this gets displayed as "Unknown" and is therefore omitted here
    // https://learn.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-threads/create?view=azure-devops-rest-7.1&tabs=HTTP#commentthreadstatus
    ;
    private final String value;
    private final boolean open;

    AdoThreadStatus(String value, boolean open) {
        this.value = value;
        this.open = open;
    }

    /**
     * @return set of statuses that are considered open
     * @see AdoThreadStatus#isOpen()
     */
    public static Set<AdoThreadStatus> openStatuses() {
        return Arrays.stream(values())
                .filter(AdoThreadStatus::isOpen)
                .collect(Collectors.toSet());
    }

    /**
     * @return set of statuses that are not considered open
     * @see AdoThreadStatus#isOpen()
     */
    public static Set<AdoThreadStatus> resolvedStatuses() {
        return Arrays.stream(values())
                .filter(status -> !status.isOpen())
                .collect(Collectors.toSet());
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Denotes whether the status is open. An open status will block resolution of a pull request if the branch policy
     * "Check for comment resolution" is on, and will show a "Resolve" button on the comment thread.
     * A non-open status will instead show a "Reactivate" button.
     *
     * @return true if the status is considered open, false otherwise
     */
    @JsonIgnore
    public boolean isOpen() {
        return open;
    }
}
