package fi.derpnet.derpbot.bean;

/**
 *
 * @author Thomas
 */
public class Location {

    private double[] coordinates;
    private String description;
    private String code;

    /**
     * @return the coordinates
     */
    public double[] getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startCode
     */
    public String getCode() {
        return code;
    }

    /**
     * @param startCode the startCode to set
     */
    public void setCode(String startCode) {
        this.code = startCode;
    }
}
