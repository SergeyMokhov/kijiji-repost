package core;

/**
 * This is an utility clas that holds methods to do API calls To the DX and receive / set system properties.
 */
public final class ApiController {

    public static String getBaseURL() {
        return new StringBuilder("http://").append(getPropertyValue("selenium.host", "localhost")).append(":").append(getPropertyValue("selenium.port", "80")).append(getPropertyValue("selenium.context", "")).toString();
    }

    public static String getPath(String path) {
        return ApiController.getBaseURL() + path;
    }

    public static String getPropertyValue(String propertyName, String defaultValue) {
        String value = System.getProperty(propertyName);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        } else {
            return value;
        }
    }
}
