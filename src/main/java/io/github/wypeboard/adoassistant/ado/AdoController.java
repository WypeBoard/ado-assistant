package io.github.wypeboard.adoassistant.ado;

import io.github.wypeboard.adoassistant.config.PropertiesConstants;
import io.github.wypeboard.adoassistant.config.PropertiesLoader;

public class AdoController {

    private final DefaultAdoClient adoClient;

    public AdoController() {
        PropertiesLoader instance = PropertiesLoader.getInstance();
        String repository = instance.get(PropertiesConstants.ADO_REPOSITORY);
        String authToken = instance.get(PropertiesConstants.ADO_AUTH_TOKEN);
        adoClient = new DefaultAdoClient(repository, authToken);
    }

    public void fetchAdoData() {

    }
}
