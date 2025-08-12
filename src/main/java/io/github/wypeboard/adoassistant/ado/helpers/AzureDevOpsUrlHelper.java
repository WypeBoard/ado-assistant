package io.github.wypeboard.adoassistant.ado.helpers;

import io.github.wypeboard.adoassistant.config.PropertiesConstants;
import io.github.wypeboard.adoassistant.config.PropertiesLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AzureDevOpsUrlHelper {
    private static final String API_URL_FORMAT = "%s/%s?%sapi-version=%s";

    private final String adoApiVersion;
    private final String adoOrganizationApiVersion;
    private final String adoApiProjectUrl;
    private final String adoApiOrganisationUrl;
    private final String adoApiCoreUrl;

    public AzureDevOpsUrlHelper(PropertiesLoader instance) {
        this(instance.get(PropertiesConstants.ADO_URL),
                instance.get(PropertiesConstants.ADO_ORGANIZATION),
                instance.get(PropertiesConstants.ADO_PROJECT),
                instance.getOrDefault(PropertiesConstants.ADO_VERSION_API, "7.1"),
                instance.getOrDefault(PropertiesConstants.ADO_VERSION_ORGANIZATION, "7.0-preview")
        );
    }

    public AzureDevOpsUrlHelper(String adoBaseUrl, String organisation, String projectName, String projectApiVersion, String organizationApiVersion) {
        String baseUrl = String.format("%s/%s", adoBaseUrl, organisation);
        this.adoApiProjectUrl = String.format("%s/%s/_apis", baseUrl, projectName);
        this.adoApiOrganisationUrl = String.format("%s/_apis", baseUrl);
        this.adoApiCoreUrl = String.format("%s/projects/%s", this.adoApiOrganisationUrl, projectName);
        this.adoApiVersion = projectApiVersion;
        this.adoOrganizationApiVersion = organizationApiVersion;
    }

    /**
     * Initialize a URL builder for an Azure DevOps API call with the base url set to the project path;
     * <p><pre>{@code
     * https://{ado server}/{collection}/{project}/_apis
     * }</pre></p>
     *
     * @return a URL builder
     * @see AzureDevOpsUrlHelper#forProjectApi(String, Object...)
     */
    public AdoUrlBuilder forProjectApi() {
        return new AdoUrlBuilder(adoApiProjectUrl, adoApiVersion);
    }

    /**
     * Initialize a URL builder for an Azure DevOps API call with the base url set to the project path,
     * and with component URI and URI parameters given;
     * <p><pre>{@code
     * https://{ado server}/{collection}/{project}/_apis/{component with args}
     * }</pre></p>
     *
     * @param component URI path to a resource in the project API
     * @param args      URI parameters
     * @return a URL builder
     * @see AzureDevOpsUrlHelper#forProjectApi()
     */
    public AdoUrlBuilder forProjectApi(String component, Object... args) {
        return forProjectApi()
                .withComponent(component)
                .withComponentParams(args);
    }

    /**
     * Initialize a URL builder for an Azure DevOps API call with the base url set to the Core path;
     * <p><pre>{@code
     * https://{ado server}/{collection}/_apis/projects/{project}
     * }</pre></p>
     *
     * @return a URL builder
     * @see AzureDevOpsUrlHelper#forCoreApi(String, Object...)
     */
    public AdoUrlBuilder forCoreApi() {
        return new AdoUrlBuilder(adoApiCoreUrl, adoApiVersion);
    }

    /**
     * Initialize a URL builder for an Azure DevOps API call with the base url set to the core path,
     * and with component URI and URI parameters given;
     * <p><pre>{@code
     * https://{ado server}/{collection}/_apis/projects/{project}/{component with args}
     * }</pre></p>
     *
     * @param component URI path to a resource in the project API
     * @param args      URI parameters
     * @return a URL builder
     * @see AzureDevOpsUrlHelper#forCoreApi()
     */
    public AdoUrlBuilder forCoreApi(String component, Object... args) {
        return forCoreApi()
                .withComponent(component)
                .withComponentParams(args);
    }

    /**
     * Initialize a URL builder for an Azure DevOps API call with the base url set to the organisation path;
     * <p><pre>{@code
     * https://{ado server}/{collection}/_apis
     * }</pre></p>
     *
     * @return a URL builder
     * @see AzureDevOpsUrlHelper#forOrganisationApi(String, Object...)
     */
    public AdoUrlBuilder forOrganisationApi() {
        return new AdoUrlBuilder(adoApiOrganisationUrl, adoOrganizationApiVersion);
    }

    /**
     * Initialize a URL builder for an Azure DevOps API call with the base url set to the organisation path,
     * and with component URI and URI parameters given;
     * <p><pre>{@code
     * https://{ado server}/{collection}/_apis/{component with args}
     * }</pre></p>
     *
     * @param component URI path to a resource in the project API
     * @param args      URI parameters
     * @return a URL builder
     * @see AzureDevOpsUrlHelper#forOrganisationApi()
     */
    public AdoUrlBuilder forOrganisationApi(String component, Object... args) {
        return forOrganisationApi()
                .withComponent(component)
                .withComponentParams(args);
    }

    /**
     * Helper class for constructing an Azure DevOps API URL with a fluent API.
     */
    public static class AdoUrlBuilder {
        private final String rootUrl;
        private final String apiVersion;
        private final Map<String, String> queryParams = new HashMap<>();
        private String component;
        private Object[] componentParams;

        private AdoUrlBuilder(String rootUrl, String apiVersion) {
            this.rootUrl = rootUrl;
            this.apiVersion = apiVersion;
        }

        /**
         * Add a URI component template to the builder.
         * <p>
         * The template can include format specifiers for {@link String#format(String, Object...)},
         * and should be used in conjunction with {@link AdoUrlBuilder#withQueryParam(String, String)} if it does.
         * </p>
         *
         * @param componentTemplate string template, optionally with format specifiers
         * @return a URL builder
         * @see AdoUrlBuilder#withComponentParams(Object...)
         */
        public AdoUrlBuilder withComponent(String componentTemplate) {
            this.component = componentTemplate;
            return this;
        }

        /**
         * Add format arguments to be used in conjunction with {@link AdoUrlBuilder#withComponent(String)}.
         *
         * @param componentParams objects to use for string formatting
         * @return a URL builder
         * @see AdoUrlBuilder#withComponent(String)
         */
        public AdoUrlBuilder withComponentParams(Object... componentParams) {
            this.componentParams = componentParams;
            return this;
        }

        /**
         * Add query parameters to the builder.
         *
         * @param queryParams a map of key-value pairs
         * @return a URL builder
         * @see AdoUrlBuilder#withQueryParam(String, String)
         */
        public AdoUrlBuilder withQueryParams(Map<String, String> queryParams) {
            this.queryParams.putAll(queryParams);
            return this;
        }

        /**
         * Add a single query parameter to the builder.
         *
         * @param key   parameter key
         * @param value parameter value
         * @return a URL builder
         */
        public AdoUrlBuilder withQueryParam(String key, String value) {
            this.queryParams.put(key, value);
            return this;
        }

        /**
         * Construct a URL string using the elements added to the builder.
         * If it contains sections of multiple consecutive {@code /} characters with a single {@code /},
         * unless it's preceeded by a {@code :} (to keep, e.g., {@code https://}).
         *
         * @return a URL string
         */
        public String toUrl() {
            String componentPath = String.format(this.component, componentParams);
            String queryString = queryParams.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue() + "&")
                    .collect(Collectors.joining(""));

            return String.format(API_URL_FORMAT, rootUrl, componentPath, queryString, apiVersion)
                    .replaceAll("(?<!:)/+", "/");
        }
    }
}
