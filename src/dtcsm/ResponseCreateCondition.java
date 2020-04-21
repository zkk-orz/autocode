package dtcsm;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:25
 */
public class ResponseCreateCondition {

    private String description;

    private String responseClassName;

    private String responseDTOClassName;

    private boolean hasPaginationCode;

    private String packagePath;

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseClassName() {
        return responseClassName;
    }

    public void setResponseClassName(String responseClassName) {
        this.responseClassName = responseClassName;
    }

    public String getResponseDTOClassName() {
        return responseDTOClassName;
    }

    public void setResponseDTOClassName(String responseDTOClassName) {
        this.responseDTOClassName = responseDTOClassName;
    }

    public boolean isHasPaginationCode() {
        return hasPaginationCode;
    }

    public void setHasPaginationCode(boolean hasPaginationCode) {
        this.hasPaginationCode = hasPaginationCode;
    }
}
