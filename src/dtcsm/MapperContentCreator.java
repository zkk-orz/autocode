package dtcsm;

import util.StringUtil;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class MapperContentCreator {

    private final String requestTemplate = "package ##packagePathReplace##;\n" +
            "import java.util.List;\n" +
            "import org.apache.ibatis.annotations.Mapper;\n" +
            "import ##domainImportReplace##\n" +
            "\n" +
            "@Mapper\n" +
            "public interface ##serviceClassReplace## {\n" +
            "\n" +
            "\tvoid create(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "\tvoid modify(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "\tvoid delete(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "\tvoid changeStatus(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "\tList<##domainClassReplace##> query(##queryConditionClassReplace## ##queryConditionClassNameReplace##);\n" +
            "\n" +
            "}";

    private final String packagePathReplace = "##packagePathReplace##";

    /**
     * service 接口类名
     */
    private final String serviceClassReplace = "##serviceClassReplace##";

    /**
     * domain类名
     */
    private final String domainClassReplace = "##domainClassReplace##";

    /**
     * domain变量名
     */
    private final String domainClassNameReplace = "##domainClassNameReplace##";

    /**
     * 查询条件类名
     */
    private final String queryConditionClassReplace = "##queryConditionClassReplace##";

    /**
     * 查询条件变量名
     */
    private final String queryConditionClassNameReplace = "##queryConditionClassNameReplace##";

    public static MapperContentCreator serviceContentCreator = new MapperContentCreator();

    private MapperContentCreator(){}

    public static String getContent(MapperCreateCondition condition){
        MapperContentCreator serviceContentCreator = MapperContentCreator.serviceContentCreator;
        String content = serviceContentCreator.requestTemplate
                .replace(serviceContentCreator.packagePathReplace, condition.getPackagePath())
                .replace(serviceContentCreator.serviceClassReplace, condition.getServiceClassName())
                .replace(serviceContentCreator.domainClassReplace, condition.getDomainClassName())
                .replace(serviceContentCreator.domainClassNameReplace, StringUtil.doFirstCharLower(condition.getDomainClassName()))
                .replace(serviceContentCreator.queryConditionClassReplace, condition.getQueryConditionClassName())
                .replace(serviceContentCreator.queryConditionClassNameReplace, StringUtil.doFirstCharLower(condition.getQueryConditionClassName()))
                .replace("##domainImportReplace##", condition.getDomainImport());
        return content;
    }

}
