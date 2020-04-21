package dtcsm;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class ResponseContentCreator {

    private final String requestTemplate = "package ##packagePathReplace##;\n" +
            "import lombok.Getter;\n" +
            "import lombok.Setter;\n" +
            "import java.util.List;\n" +
            "import java.io.Serializable;\n" +
            "import io.swagger.annotations.ApiModel;\n" +
            "import com.tims.common.dto.PaginationDTO;\n" +
            "import io.swagger.annotations.ApiModelProperty;\n" +
            "import com.tims.framework.web.dto.ResponseDTO;\n" +
            "\n" +
            "@ApiModel\n" +
            "public class ##responseClassReplace## extends ResponseDTO implements Serializable{\n" +
            "\n" +
            "\t@Getter\n" +
            "\t@Setter\n" +
            "\t@ApiModelProperty(value=\"##description##列表\")\n" +
            "\tprivate List<##responseDTOClassReplace##> data;\n" +
            "\n" +
            "##paginationCodeExists##\n" +
            "\t@Getter\n" +
            "\t@Setter\n" +
            "\t@ApiModelProperty(value=\"分页信息\")\n" +
            "\tprivate PaginationDTO paginationDTO;\n" +
            "##paginationCodeExists##\n" +
            "\t\n" +
            "}\n";

    private final String packagePathReplace = "##packagePathReplace##";

    /**
     * 描述信息
     */
    private final String descriptionReplace = "##description##";

    /**
     * response 类型
     */
    private final String responseClassReplace = "##responseClassReplace##";

    /**
     * response 类型
     */
    private final String responseDTOClassReplace = "##responseDTOClassReplace##";

    /**
     * 分页信息代码块
     */
    private final String paginationCodeBlock = "##paginationCodeExists##";

    public static ResponseContentCreator requestContentCreator = new ResponseContentCreator();

    private ResponseContentCreator(){}

    public static String getContent(ResponseCreateCondition condition){
        ResponseContentCreator requestContentCreator = ResponseContentCreator.requestContentCreator;
        String content = requestContentCreator.requestTemplate.replace(requestContentCreator.descriptionReplace, condition.getDescription())
                .replace(requestContentCreator.responseClassReplace, condition.getResponseClassName())
                .replace(requestContentCreator.responseDTOClassReplace, condition.getResponseDTOClassName())
                .replace(requestContentCreator.packagePathReplace, condition.getPackagePath());
        if(!condition.isHasPaginationCode()){
            content = content.substring(0, content.indexOf(requestContentCreator.paginationCodeBlock)) +
                    content.substring(content.lastIndexOf(requestContentCreator.paginationCodeBlock) + requestContentCreator.paginationCodeBlock.length());
        }
        content = content.replace(requestContentCreator.paginationCodeBlock, "");
        return content;
    }

}
