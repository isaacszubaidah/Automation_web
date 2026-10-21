package login;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigClass {

    public static class AppConfig {
        private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
        private static AppConfig instance;
        private Properties props;

        private AppConfig(String resourceName) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            props = new Properties();

            try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                if (resourceStream != null) {
                    props.load(resourceStream);
                    logger.info("Properties file '{}' loaded successfully.", resourceName);
                } else {
                    throw new IOException("Properties file not found: " + resourceName);
                }
            } catch (IOException e) {
                logger.error("Failed to load properties file: {}", resourceName, e);
                throw new RuntimeException(e);
            }
        }

        public static synchronized AppConfig getInstance(String resourceName) {
            if (instance == null) {
                instance = new AppConfig(resourceName);
            }
            return instance;
        }

        public String getProperty(String key) {
            return props.getProperty(key);
        }

        public void validateRequiredProperties(String... keys) {
            for (String key : keys) {
                if (props.getProperty(key) == null) {
                    throw new IllegalStateException("Missing required property: " + key);
                }
            }
        }
    }
}