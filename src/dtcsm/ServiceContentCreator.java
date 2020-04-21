package dtcsm;

import util.StringUtil;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class ServiceContentCreator {

    private final String requestTemplate = "package ##packagePathReplace##;\n" +
            "import ##domainImportReplace##\n" +
            "import java.util.List;\n" +
            "import com.github.pagehelper.Page;\n" +
            "public interface ##serviceClassReplace## {\n" +
            "\n" +
            "    void create(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "    void modify(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "    void delete(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "    void changeStatus(##domainClassReplace## ##domainClassNameReplace##);\n" +
            "\n" +
            "    Page<##domainClassReplace##> queryByPage(##queryConditionClassReplace## ##queryConditionClassNameReplace##);\n" +
            "\n" +
            "    List<##domainClassReplace##> query(##queryConditionClassReplace## ##queryConditionClassNameReplace##);\n" +
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

    public static ServiceContentCreator serviceContentCreator = new ServiceContentCreator();

    private ServiceContentCreator(){}

    public static String getContent(ServiceCreateCondition condition){
        ServiceContentCreator serviceContentCreator = ServiceContentCreator.serviceContentCreator;
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
