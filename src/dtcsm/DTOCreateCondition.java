package dtcsm;

import java.util.List;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:25
 */
public class DTOCreateCondition {

    private List<DTOFieldCreateCondition> dtoFieldCreateConditions;

    private String description;

    private String dtoClassName;

    private String packagePath;

    private boolean isRequest;

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public List<DTOFieldCreateCondition> getDtoFieldCreateConditions() {
        return dtoFieldCreateConditions;
    }

    public void setDtoFieldCreateConditions(List<DTOFieldCreateCondition> dtoFieldCreateConditions) {
        this.dtoFieldCreateConditions = dtoFieldCreateConditions;
    }

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

    public String getDtoClassName() {
        return dtoClassName;
    }

    public void setDtoClassName(String dtoClassName) {
        this.dtoClassName = dtoClassName;
    }
}
