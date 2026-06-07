package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the correct config.properties based on the {@code env} system property.
 *
 * Run with: mvn test -Denv=staging -Dbrowser=firefox
 *
 * Priority: System property → .properties file → RuntimeException
 */
public class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        // reads -Denv=staging from Maven/CLI, defaults to "dev"
        String env = System.getProperty("env", "dev");
        String fileName = env + ".properties";

        try (InputStream is = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                throw new RuntimeException(
                        "Config file not found on classpath: " + fileName
                        + " — add it to src/main/resources/");
            }
            prop.load(is);
            System.out.println("[ConfigReader] Loaded: " + fileName + " (" + prop.size() + " properties)");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + fileName, e);
        }
    }

    private ConfigReader() {}

    /**
     * Returns value for key.
     * System property wins — lets CI inject secrets via -Dkey=value.
     */
    public static String get(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) return sysProp.trim();

        String value = prop.getProperty(key);
        if (value == null || value.isBlank())
            throw new RuntimeException("Property [" + key + "] not found in config.");
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        try { return get(key); }
        catch (RuntimeException e) { return defaultValue; }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(get(key, String.valueOf(defaultValue)));
    }
}
