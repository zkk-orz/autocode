package dtcsm;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:25
 */
public class RequestCreateCondition {

    private String description;

    private String requestClassName;

    private String requestDTOClassName;

    private boolean hasPaginationCode;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestClassName() {
        return requestClassName;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
    }

    public String getRequestDTOClassName() {
        return requestDTOClassName;
    }

    public void setRequestDTOClassName(String requestDTOClassName) {
        this.requestDTOClassName = requestDTOClassName;
    }

    public boolean isHasPaginationCode() {
        return hasPaginationCode;
    }

    public void setHasPaginationCode(boolean hasPaginationCode) {
        this.hasPaginationCode = hasPaginationCode;
    }
}
