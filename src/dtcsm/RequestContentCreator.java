package dtcsm;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class RequestContentCreator {

    private final String requestTemplate = "import lombok.Getter;\n" +
            "import lombok.Setter;\n" +
            "import java.io.Serializable;\n" +
            "import javax.validation.Valid;\n" +
            "import com.tims.common.dto.PaginationDTO;\n" +
            "import com.tims.framework.web.dto.RequestDTO;\n" +
            "import io.swagger.annotations.ApiModelProperty;\n" +
            "\n" +
            "/**\n" +
            " * ##description##查询请求参数\n" +
            " */\n" +
            "public class ##requestClassReplace## extends RequestDTO implements Serializable {\n" +
            "\n" +
            "\n" +
            "    private static final long serialVersionUID = 6386698371808363434L;\n" +
            "\n" +
            "    @Getter\n" +
            "    @Setter\n" +
            "    @Valid\n" +
            "    @ApiModelProperty(value=\"DTO查询条件\")\n" +
            "    private ##requestDTOClassReplace## reqDtos;\n" +
            "\n" +
            "    ##paginationCodeExists##\n" +
            "    @Setter\n" +
            "    @Getter\n" +
            "    @ApiModelProperty(value=\"分页查询条件\")\n" +
            "    private PaginationDTO paginationDTO;\n" +
            "    ##paginationCodeExists##\n" +
            "        \n" +
            "}";

    /**
     * 描述信息
     */
    private final String descriptionReplace = "##description##";

    /**
     * request 类型
     */
    private final String requestClassReplace = "##requestClassReplace##";

    /**
     * request中dto类型 类型
     */
    private final String requestDTOClassReplace = "##requestDTOClassReplace##";

    /**
     * 分页信息代码块
     */
    private final String paginationCodeBlock = "##paginationCodeExists##";

    public static RequestContentCreator requestContentCreator = new RequestContentCreator();

    private RequestContentCreator(){}

    public static String getContent(RequestCreateCondition condition){
        RequestContentCreator requestContentCreator = RequestContentCreator.requestContentCreator;
        String content = requestContentCreator.requestTemplate.replace(requestContentCreator.descriptionReplace, condition.getDescription())
                .replace(requestContentCreator.requestClassReplace, condition.getRequestClassName())
                .replace(requestContentCreator.requestDTOClassReplace, condition.getRequestDTOClassName());
        if(!condition.isHasPaginationCode()){
            content = content.substring(0, content.indexOf(requestContentCreator.paginationCodeBlock)) +
                    content.substring(content.lastIndexOf(requestContentCreator.paginationCodeBlock) + requestContentCreator.paginationCodeBlock.length());
        }
        return content;
    }

}
