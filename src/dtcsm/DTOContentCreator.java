package dtcsm;

import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class DTOContentCreator {

    private final String dtoHeaderTemplate = "package ##packagePathReplace##;\n" +
            "import lombok.Getter;\n" +
            "import lombok.Setter;\n" +
            "import java.io.Serializable;\n" +
            "import io.swagger.annotations.ApiModel;\n" +
            "import com.tims.framework.web.dto.ResponseDTO;\n" +
            "import com.tims.framework.web.dto.RequestDTO;\n" +
            "import io.swagger.annotations.ApiModelProperty;\n" +
            "\n" +
            "@ApiModel\n" +
            "public class ##dtoClassReplace## extends ##extendClassReplace## implements Serializable {\n";

    private final String fieldTemplate = "\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\t@ApiModelProperty(value=\"##fieldDescriptionReplace##\", dataType=\"##fieldTypeReplace##\")\n" +
            "\tprivate String ##fieldNameReplace##;\n" +
            "\n";

    private final String sortFields = "\n" +
            "\t\n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\t@ApiModelProperty(value=\"排序\",dataType=\"Integer\",example=\"DESC\")\n" +
            "\tprivate String sortType;\n" +
            "\t    \n" +
            "\t@Setter\n" +
            "\t@Getter\n" +
            "\t@ApiModelProperty(value=\"排序字段\",dataType=\"Integer\",example=\"createTime\")\n" +
            "\tprivate String sortValue;";

    private final String dtoEndTemplate = "\n" + "}";

    private final String packagePathReplace = "##packagePathReplace##";

    /**
     * DTO 类型
     */
    private final String dtoClassReplace = "##dtoClassReplace##";

    /**
     * 字段信息
     */
    private final String fieldDescriptionReplace = "##fieldDescriptionReplace##";

    /**
     * 字段类型
     */
    private final String fieldTypeReplace = "##fieldTypeReplace##";

    /**
     * 字段名称
     */
    private final String fieldNameReplace = "##fieldNameReplace##";

    public static DTOContentCreator dtoContentCreator = new DTOContentCreator();

    private DTOContentCreator(){}

    public static String getContent(DTOCreateCondition condition){
        DTOContentCreator dtoContentCreator = DTOContentCreator.dtoContentCreator;
        StringBuilder sb= new StringBuilder();
        String header = dtoContentCreator.dtoHeaderTemplate
                .replace(dtoContentCreator.packagePathReplace, condition.getPackagePath())
                .replace(dtoContentCreator.dtoClassReplace, condition.getDtoClassName());
        if(condition.isRequest()){
            header = header.replace("##extendClassReplace##", "RequestDTO");
        } else {
            header = header.replace("##extendClassReplace##", "ResponseDTO");
        }
        sb.append(header);
        List<DTOFieldCreateCondition> dtoFieldCreateConditions = condition.getDtoFieldCreateConditions();
        if(Objects.nonNull(dtoFieldCreateConditions) && dtoFieldCreateConditions.size() > 0){
            for(DTOFieldCreateCondition dtoFieldCreateCondition : dtoFieldCreateConditions){
                String proStr = dtoContentCreator.fieldTemplate
                        .replace(dtoContentCreator.fieldNameReplace, dtoFieldCreateCondition.getFieldName());
                if(Objects.nonNull(dtoFieldCreateCondition.getFieldDescription())){
                    proStr = proStr.replace(dtoContentCreator.fieldDescriptionReplace, dtoFieldCreateCondition.getFieldDescription());
                } else {
                    proStr = proStr.replace(dtoContentCreator.fieldDescriptionReplace, dtoFieldCreateCondition.getFieldName());
                }
                if(Objects.nonNull(dtoFieldCreateCondition.getFieldType())){
                    proStr = proStr.replace(dtoContentCreator.fieldTypeReplace, dtoFieldCreateCondition.getFieldType());
                } else {
                    proStr = proStr.replace(dtoContentCreator.fieldTypeReplace, "String");
                }
                sb.append(proStr);
            }
        }
        if(condition.isRequest() && condition.getDtoClassName().toLowerCase().contains("query")){
            sb.append(dtoContentCreator.sortFields);
        }
        sb.append(dtoContentCreator.dtoEndTemplate);
        return sb.toString();
    }

}
