package arm.davsoft.staffmanager.enums;

/**
 * Created by david on 8/4/16.
 */
public enum MetaCategory {
    GENDER("C_Gender"),
    PARTICIPATION("C_Participation"),
    ;

    private final String metaCategoryId;

    MetaCategory(String metaCategoryId) {
        this.metaCategoryId = metaCategoryId;
    }

    public String getMetaCategoryId() {
        return metaCategoryId;
    }
}
