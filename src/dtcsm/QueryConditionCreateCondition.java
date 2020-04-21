package dtcsm;

import java.util.List;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:25
 */
public class QueryConditionCreateCondition {

    private List<DTOFieldCreateCondition> dtoFieldCreateConditions;

    private String queryConditionClassName;

    private String packagePath;

    public List<DTOFieldCreateCondition> getDtoFieldCreateConditions() {
        return dtoFieldCreateConditions;
    }

    public void setDtoFieldCreateConditions(List<DTOFieldCreateCondition> dtoFieldCreateConditions) {
        this.dtoFieldCreateConditions = dtoFieldCreateConditions;
    }

    public String getQueryConditionClassName() {
        return queryConditionClassName;
    }

    public void setQueryConditionClassName(String queryConditionClassName) {
        this.queryConditionClassName = queryConditionClassName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
}
