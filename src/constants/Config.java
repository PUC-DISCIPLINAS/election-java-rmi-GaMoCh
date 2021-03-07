package constants;

import java.util.Optional;

public final class Config {
    public static final String SERVER_NAME = getEnv("SERVER_NAME", "election");
    public static final int SERVER_PORT = Integer.parseInt(getEnv("SERVER_PORT", "1099"));

    public static final String FILE_PATH = getEnv("FILE_PATH", "data/src/candidates.csv");
    public static final String STORAGE_PATH = getEnv("STORAGE_PATH", "data/app/storage.data");

    public static final String FILE_SEPARATOR = getEnv("FILE_SEPARATOR", ";");
    public static final boolean FILE_HAS_HEADER = Boolean.parseBoolean(getEnv("FILE_HAS_HEADER", "true"));

    public static final int TIMEOUT = Integer.parseInt(getEnv("TIMEOUT", "30000"));
    public static final int ATTEMPTS = Integer.parseInt(getEnv("ATTEMPTS", "5"));

    public static final String ALGORITHM = getEnv("ALGORITHM", "MD5");

    private static String getEnv(String environmentVariable, String defaultValue) {
        return Optional.ofNullable(System.getenv(environmentVariable)).orElse(defaultValue);
    }

    private Config() {
    }
}
