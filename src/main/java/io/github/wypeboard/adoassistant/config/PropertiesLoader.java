package io.github.wypeboard.adoassistant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);
    private static PropertiesLoader instance;

    private Properties properties;

    private PropertiesLoader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.error("config.properties not found in resources");
                throw new RuntimeException("config.properties not found in resources");
            }
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error("Failed to load config.properties");
            throw new RuntimeException("Failed to load config.properties", ex);
        }
    }

    public static PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
