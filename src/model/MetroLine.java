package model;

/**
 * Enum representing Delhi Metro Rail Corporation (DMRC) metro lines.
 * Each line has a display name and hex color code for UI rendering.
 */
public enum MetroLine {

    YELLOW("Yellow Line", "#FFD700"),
    BLUE("Blue Line", "#0066CC"),
    RED("Red Line", "#EE1C25"),
    VIOLET("Violet Line", "#8B008B"),
    MAGENTA("Magenta Line", "#FF1493"),
    PINK("Pink Line", "#F19CBB"),
    AIRPORT_EXPRESS("Airport Express", "#F28C28");

    private final String displayName;
    private final String hexColor;

    MetroLine(String displayName, String hexColor) {
        this.displayName = displayName;
        this.hexColor = hexColor;
    }

    /** Returns the human-readable display name (e.g., "Yellow Line"). */
    public String getDisplayName() {
        return displayName;
    }

    /** Returns the hex color code for UI rendering (e.g., "#FFD700"). */
    public String getHexColor() {
        return hexColor;
    }
}
