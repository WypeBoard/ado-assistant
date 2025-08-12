package io.github.wypeboard.adoassistant.ado;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.wypeboard.adoassistant.ado.helpers.SerializationHelper;
import io.github.wypeboard.adoassistant.ado.helpers.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Set;

/**
 * HTTP connection handler
 */
public class AzureDevOpsConnector {
    private static final Set<Integer> OK_HTTP_CODES = Set.of(
            HttpURLConnection.HTTP_OK,
            HttpURLConnection.HTTP_CREATED,
            HttpURLConnection.HTTP_ACCEPTED,
            HttpURLConnection.HTTP_NOT_AUTHORITATIVE,
            HttpURLConnection.HTTP_NO_CONTENT,
            HttpURLConnection.HTTP_RESET,
            HttpURLConnection.HTTP_PARTIAL);
    private final String authToken;
    private static final Logger LOGGER = LoggerFactory.getLogger(AzureDevOpsConnector.class);
    private final boolean logRequests;

    public AzureDevOpsConnector(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            LOGGER.warn("No auth token provided");
        }
        this.authToken = authToken;
        this.logRequests = true;
    }

    private HttpURLConnection getHttpURLConnection(String requestMethod, String urlString, String contentType) {
        final HttpURLConnection uc;
        try {
            URL url = new URL(urlString);
            uc = (HttpURLConnection) url.openConnection();
            // PATCH requires special handling: https://bugs.openjdk.org/browse/JDK-7016595
            if ("PATCH".equals(requestMethod)) {
                uc.setRequestMethod("POST");
                uc.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            } else {
                uc.setRequestMethod(requestMethod);
            }
            uc.setDoOutput(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        uc.setRequestProperty("Authorization", StringHelper.formatAuthToken(authToken, "Basic"));
        uc.setRequestProperty("Content-Type", contentType);
        uc.setRequestProperty("Accept", "application/json");
        return uc;
    }

    /**
     * Send a request and receive the response as raw bytes.
     * @param requestMethod HTTP method
     * @param requestUrl URL to call
     * @return response bytes
     */
    public byte[] sendRequest(String requestMethod, String requestUrl) {
        return sendRequest(requestMethod, requestUrl, null);
    }

    /**
     * Send a request and receive the response as raw bytes.
     * @param requestMethod HTTP method
     * @param requestUrl URL to call
     * @param jsonBody request body
     * @return response bytes
     */
    public byte[] sendRequest(String requestMethod, String requestUrl, String jsonBody) {
        return sendRequest(requestMethod, requestUrl, jsonBody, "application/json; utf-8");
    }

    private byte[] sendRequest(String requestMethod, String requestUrl, String jsonBody, String contentType) {
        if (logRequests) {
            LOGGER.info("Request: {} {}\nBody: {}\nContent-Type: {}", requestMethod, requestUrl, jsonBody, contentType);
        }
        final HttpURLConnection uc = getHttpURLConnection(requestMethod, requestUrl, contentType);

        try {
            if (jsonBody != null) {
                OutputStream writer = uc.getOutputStream();
                writer.write(jsonBody.getBytes());
                writer.flush();
            }

            if (!OK_HTTP_CODES.contains(uc.getResponseCode())) {
                LOGGER.error("Got HTTP response {} {} for request to URL {} with request body:\n{}", uc.getResponseCode(), uc.getResponseMessage(), requestUrl, jsonBody);
                throw new RuntimeException(MessageFormat.format("Got non-OK HTTP status {0} {1} with response body:\n{2}", uc.getResponseCode(), uc.getResponseMessage(), new String(uc.getErrorStream().readAllBytes(), StandardCharsets.UTF_8)));
            }

            return uc.getInputStream().readAllBytes();
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Send a request and parse the received response as a given class.
     * @param requestMethod HTTP method
     * @param requestUrl URL to call
     * @param responseType class to parse the response into
     * @return parsed response
     * @param <T> class to deserialize response into
     */
    public <T> T sendRequestAndParseResponse(String requestMethod, String requestUrl, Class<T> responseType) {
        return sendRequestAndParseResponse(requestMethod, requestUrl, responseType, null);
    }

    /**
     * Send a request and parse the received response as a given class.
     * @param requestMethod HTTP method
     * @param requestUrl URL to call
     * @param responseType class to parse the response into
     * @param jsonBody request body
     * @return parsed response
     * @param <T> class to deserialize response into
     */
    public <T> T sendRequestAndParseResponse(String requestMethod, String requestUrl, Class<T> responseType, String jsonBody) {
        try {
            byte[] bytes = sendRequest(requestMethod, requestUrl, jsonBody);
            return SerializationHelper.getObjectMapper().readValue(bytes, responseType);
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Send a request and parse the received response as a given type.
     * @param requestMethod HTTP method
     * @param requestUrl URL to call
     * @param responseType type reference to parse the response as
     * @return parsed response
     * @param <T> class to deserialize response into
     */
    public <T> T sendRequestAndParseResponse(String requestMethod, String requestUrl, TypeReference<T> responseType) {
        return sendRequestAndParseResponse(requestMethod, requestUrl, responseType, null);
    }

    /**
     * Send a request and parse the received response as a given type.
     * @param requestMethod HTTP method
     * @param requestUrl URL to call
     * @param responseType type reference to parse the response as
     * @param jsonBody request body
     * @return parsed response
     * @param <T> class to deserialize response into
     */
    public <T> T sendRequestAndParseResponse(String requestMethod, String requestUrl, TypeReference<T> responseType, String jsonBody) {
        return sendRequestAndParseResponse(requestMethod, requestUrl, responseType, jsonBody, "application/json; utf-8");
    }

    /**
     * Send a JSON Patch request and parse the received response as a given type.
     * @param requestUrl URL to call
     * @param responseType type reference to parse the response as
     * @param jsonBody request body
     * @return parsed response
     * @param <T> class to deserialize response into
     * @see io.github.wypeboard.adoassistant.ado.model.requests.JsonPatch
     * @see <a href="https://jsonpatch.com/">jsonpatch.com</a>
     */
    public <T> T sendJsonPatchRequestAndParseResponse(String requestUrl, TypeReference<T> responseType, String jsonBody) {
        return sendRequestAndParseResponse("PATCH", requestUrl, responseType, jsonBody, "application/json-patch+json; utf-8");
    }

    private <T> T sendRequestAndParseResponse(String requestMethod, String requestUrl, TypeReference<T> responseType, String jsonBody, String contentType) {
        try {
            byte[] bytes = sendRequest(requestMethod, requestUrl, jsonBody, contentType);
            return SerializationHelper.getObjectMapper().readValue(bytes, responseType);
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: ", e);
            throw new RuntimeException(e);
        }
    }
}
