package dtcsm;

import com.intellij.openapi.ui.Messages;

import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class QueryConditionContentCreator {

    private final String dtoHeaderTemplate = "package ##packagePathReplace##;\n" +
            "import lombok.Getter;\n" +
            "import lombok.Setter;\n" +
            "\n" +
            "public class ##queryConditionReplace## {\n";

    private final String fieldTemplate = "\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\tprivate ##queryConditionFieldTypeReplace## ##queryConditionFieldNameReplace##;";

    private final String dtoEndTemplate = "\n" +
            "\t\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\tprivate String sortType;\n" +
            "\t\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\tprivate String sortValue;\n" +
            "\t\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\tprivate Integer pageNumber;\n" +
            "\t\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\tprivate Integer pageSize;\n" +
            "\t\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\tprivate String lang;" +
            "}";

    private final String packagePathReplace = "##packagePathReplace##";

    /**
     * query condition 类型
     */
    private final String queryConditionReplace = "##queryConditionReplace##";

    /**
     * 字段信息
     */
    private final String queryConditionFieldTypeReplace = "##queryConditionFieldTypeReplace##";

    /**
     * 字段类型
     */
    private final String queryConditionFieldNameReplace = "##queryConditionFieldNameReplace##";

    public static QueryConditionContentCreator queryConditionContentCreator = new QueryConditionContentCreator();

    private QueryConditionContentCreator(){}

    public static String getContent(QueryConditionCreateCondition condition){
        QueryConditionContentCreator queryConditionContentCreator = QueryConditionContentCreator.queryConditionContentCreator;
        StringBuilder sb= new StringBuilder();
        String header = queryConditionContentCreator.dtoHeaderTemplate
                .replace(queryConditionContentCreator.packagePathReplace, condition.getPackagePath())
                .replace(queryConditionContentCreator.queryConditionReplace, condition.getQueryConditionClassName());
        sb.append(header);
        List<DTOFieldCreateCondition> dtoFieldCreateConditions = condition.getDtoFieldCreateConditions();
        if(Objects.nonNull(dtoFieldCreateConditions) && dtoFieldCreateConditions.size() > 0){
            for(DTOFieldCreateCondition dtoFieldCreateCondition : dtoFieldCreateConditions){
                String proStr = queryConditionContentCreator.fieldTemplate
                        .replace(queryConditionContentCreator.queryConditionFieldNameReplace, dtoFieldCreateCondition.getFieldName());
                if(Objects.nonNull(dtoFieldCreateCondition.getFieldType())){
                    proStr = proStr.replace(queryConditionContentCreator.queryConditionFieldTypeReplace, dtoFieldCreateCondition.getFieldType());
                } else {
                    proStr = proStr.replace(queryConditionContentCreator.queryConditionFieldTypeReplace, "String");
                }
                sb.append(proStr);
            }
        }
        sb.append(queryConditionContentCreator.dtoEndTemplate);
        Messages.showMessageDialog(sb.toString(), "Condition", null);
        return sb.toString();
    }

}
