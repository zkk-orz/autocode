package dtcsm;

import util.StringUtil;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:16
 */
public class ServiceImplContentCreator {

    private final String requestTemplate = "package ##packagePathReplace##;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.List;\n" +
            "import java.util.Objects;\n" +
            "import com.github.pagehelper.Page;\n" +
            "import com.github.pagehelper.PageHelper;\n" +
            "import com.tims.framework.util.SqlSortValUtil;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import ##domainImportReplace##\n" +
            "\n" +
            "@Service\n" +
            "public class ##serviceImplClassReplace## implements ##serviceClassReplace## {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ##mapperClassReplace## ##mapperClassNameReplace##;\n" +
            "\n" +
            "    @Override\n" +
            "    public void create(##domainClassReplace## ##domainClassNameReplace##){\n" +
            "        if(Objects.nonNull(##domainClassNameReplace##)){\n" +
            "            ##mapperClassNameReplace##.create(##domainClassNameReplace##);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public void modify(##domainClassReplace## ##domainClassNameReplace##){\n" +
            "        if(Objects.nonNull(##domainClassNameReplace##) && Objects.nonNull(##domainClassNameReplace##.getId())){\n" +
            "            ##mapperClassNameReplace##.modify(##domainClassNameReplace##);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public void delete(##domainClassReplace## ##domainClassNameReplace##){\n" +
            "        if(Objects.nonNull(##domainClassNameReplace##) && Objects.nonNull(##domainClassNameReplace##.getId())){\n" +
            "            ##mapperClassNameReplace##.delete(##domainClassNameReplace##);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public void changeStatus(##domainClassReplace## ##domainClassNameReplace##){\n" +
            "        if(Objects.nonNull(##domainClassNameReplace##) && Objects.nonNull(##domainClassNameReplace##.getId())){\n" +
            "            ##mapperClassNameReplace##.delete(##domainClassNameReplace##);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public Page<##domainClassReplace##> queryByPage(##queryConditionClassReplace## ##queryConditionClassNameReplace##) {\n" +
            "        if(Objects.nonNull(##queryConditionClassNameReplace##)){\n" +
            "            String sortBy = SqlSortValUtil.assemberSortBy(##queryConditionClassNameReplace##.getSortType(), ##queryConditionClassNameReplace##.getSortValue(), ##domainClassReplace##.class);\n" +
            "            Page<##domainClassReplace##> page = PageHelper.startPage(##queryConditionClassNameReplace##.getPageNumber(), ##queryConditionClassNameReplace##.getPageSize(), sortBy);\n" +
            "            query(##queryConditionClassNameReplace##);\n" +
            "            return page;\n" +
            "        }\n" +
            "        return new Page<>();\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public List<##domainClassReplace##> query(##queryConditionClassReplace## ##queryConditionClassNameReplace##) {\n" +
            "        if(Objects.nonNull(##queryConditionClassNameReplace##)){\n" +
            "            return ##mapperClassNameReplace##.query(##queryConditionClassNameReplace##);\n" +
            "        }\n" +
            "        return new ArrayList<>();\n" +
            "    }\n" +
            "\n" +
            "}";

    private final String packagePathReplace = "##packagePathReplace##";

    /**
     * service 接口类名
     */
    private final String serviceClassReplace = "##serviceClassReplace##";

    /**
     * service实现 接口类名
     */
    private final String serviceImplClassReplace = "##serviceImplClassReplace##";

    /**
     * mapper接口类名
     */
    private final String mapperClassReplace = "##mapperClassReplace##";

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

    public static ServiceImplContentCreator serviceContentCreator = new ServiceImplContentCreator();

    private ServiceImplContentCreator(){}

    public static String getContent(ServiceImplCreateCondition condition){
        ServiceImplContentCreator serviceContentCreator = ServiceImplContentCreator.serviceContentCreator;
        String content = serviceContentCreator.requestTemplate
                .replace(serviceContentCreator.packagePathReplace, condition.getPackagePath())
                .replace(serviceContentCreator.mapperClassReplace, condition.getMapperClassName())
                .replace(serviceContentCreator.serviceClassReplace, condition.getServiceClassName())
                .replace(serviceContentCreator.serviceImplClassReplace, condition.getServiceImplClassName())
                .replace(serviceContentCreator.domainClassReplace, condition.getDomainClassName())
                .replace("##mapperClassNameReplace##", StringUtil.doFirstCharLower(condition.getMapperClassName()))
                .replace(serviceContentCreator.domainClassNameReplace, StringUtil.doFirstCharLower(condition.getDomainClassName()))
                .replace(serviceContentCreator.queryConditionClassReplace, condition.getQueryConditionClassName())
                .replace(serviceContentCreator.queryConditionClassNameReplace, StringUtil.doFirstCharLower(condition.getQueryConditionClassName()))
                .replace("##domainImportReplace##", condition.getDomainImport());
        return content;
    }

}
