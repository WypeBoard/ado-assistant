package io.github.wypeboard.adoassistant.ado.helpers;

import java.util.Base64;

/**
 * General utilities for string manipulation.
 */
public class StringHelper {


    /**
     * Formats and encodes an authentication token to use in an HTTP header.
     *
     * @param authToken raw token
     * @param tokenType type of token (Basic, Bearer, etc.)
     * @return value suitable for an HTTP Authorization header
     */
    public static String formatAuthToken(String authToken, String tokenType) {
        String prefixedToken = prefixWith(authToken, ":");
        String encodedToken = new String(Base64.getEncoder().encode(prefixedToken.getBytes()));
        return String.format("%s %s", tokenType, encodedToken);
    }

    /**
     * Prefixes the input string with the prefix if it does not already have it.
     *
     * @param input  the input string
     * @param prefix the prefix
     * @return the input with the prefix
     */
    public static String prefixWith(String input, String prefix) {
        if (input.startsWith(prefix)) {
            return input;
        } else {
            return prefix + input;
        }
    }

    /**
     * Strips parts off a branch that are not part of the typical display branch name.
     *
     * @param fullBranchName full branch name, potentially prefixed with refs/heads/origin
     * @return a stripped branch name
     */
    public static String cleanGitBranchName(String fullBranchName) {
        return fullBranchName
                .replace("origin/", "")
                .replace("refs/heads/", "");
    }
}
