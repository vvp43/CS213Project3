package RUbank;

/**
 * Campus enum defines the name and codes of campuses
 * @author Vinh Pham
 */

public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);

    final int code; //property


    /**
     * Default Constructor
     * @param code property of campus
     */
    Campus(int code) {
        this.code = code;
    }
}