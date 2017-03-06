package arm.davsoft.staffmanager.enums;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 8/4/15 <br/>
 * <b>Time:</b> 7:00 PM <br/>
 */
public enum ErrorCode {
    UNKNOWN_EXCEPTION("ERR:502", "Unknown Error."),
    LOG4J_PROP_MISSING("L4G:404", "Log4j property file not found."),
    DB_NO_CONNECTION("DB:503", "Could not connect to DB."),
    ;

    private String code;
    private String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }
    public String getDescription() {
        return this.description;
    }
}
