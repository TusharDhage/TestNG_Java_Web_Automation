package constants;

/**
 * Framework-wide constants.
 * Values that change per environment live in .properties files.
 */
public final class Constants {

    private Constants() {}

    // ─── Paths ────────────────────────────────────────────────────────────────
    public static final String SCREENSHOTS_PATH     = "target/screenshots/";
    public static final String REPORTS_PATH         = "reports/";
    public static final String ALLURE_RESULTS_PATH  = "target/allure-results/";
    public static final String TESTDATA_PATH        = "src/test/resources/testdata/";

    // ─── Timeouts (seconds) ───────────────────────────────────────────────────
    public static final int DEFAULT_EXPLICIT_WAIT   = 15;
    public static final int PAGE_LOAD_TIMEOUT       = 30;
    public static final int POLLING_INTERVAL_MS     = 500;

    // ─── Browser ──────────────────────────────────────────────────────────────
    public static final String CHROME  = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE    = "edge";

    // ─── HTTP status codes ────────────────────────────────────────────────────
    public static final int HTTP_OK           = 200;
    public static final int HTTP_CREATED      = 201;
    public static final int HTTP_NO_CONTENT   = 204;
    public static final int HTTP_BAD_REQUEST  = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_NOT_FOUND    = 404;
    public static final int HTTP_SERVER_ERROR = 500;

    // ─── TestNG groups ────────────────────────────────────────────────────────
    public static final String GROUP_SMOKE      = "smoke";
    public static final String GROUP_REGRESSION = "regression";
    public static final String GROUP_SANITY     = "sanity";
    public static final String GROUP_API        = "api";

    // ─── Date formats ─────────────────────────────────────────────────────────
    public static final String DATETIME_FORMAT      = "yyyy-MM-dd_HH-mm-ss";
    public static final String REPORT_TIMESTAMP_FMT = "dd-MMM-yyyy HH:mm:ss";
}
