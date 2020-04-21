package dtcsm;

import util.StringUtil;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 10:09
 */
public class ControllerContentCreator {

    public static ControllerContentCreator controllerContentCreator = new ControllerContentCreator();

    private ControllerContentCreator(){}

    private final String packagePathReplace = "##packagePathReplace##";

    /**
     * 名称替换符
     */
    private final String nameReplace = "##name##";

    /**
     * swagger Api 中的描述替换符
     */
    private final String descriptionReplace = "##description##";

    /**
     * controller 类名
     */
    private final String controllerClassNameReplace = "##controllerClassNameReplace##";

    /**
     * 注入service的类
     */
    private final String autowiredServiceClassReplace = "##autowiredServiceClassReplace##";

    /**
     * 注入service的名称
     */
    private final String autowiredServiceNameReplace = "##autowiredServiceNameReplace##";

    /**
     * 创建方法request的类
     */
    private final String createRequestClassReplace = "##createRequestClassReplace##";

    /**
     * 创建方法request的名称
     */
    private final String createRequestNameReplace = "##createRequestNameReplace##";

    /**
     * 修改方法request的类
     */
    private final String modifyRequestClassReplace = "##modifyRequestClassReplace##";

    /**
     * 修改方法request的名称
     */
    private final String modifyRequestNameReplace = "##modifyRequestNameReplace##";

    /**
     * 查询方法request的类
     */
    private final String queryRequestClassReplace = "##queryRequestClassReplace##";

    /**
     * 查询方法request的名称
     */
    private final String queryRequestNameReplace = "##queryRequestNameReplace##";

    /**
     * 查询方法response的类
     */
    private final String queryResponseClassReplace = "##queryResponseClassReplace##";

    /**
     * 查询方法response的名称
     */
    private final String queryResponseNameReplace = "##queryResponsetNameReplace##";

    /**
     * 删除方法request的类
     */
    private final String deleteRequestClassReplace = "##deleteRequestClassReplace##";

    /**
     * 删除方法request的名称
     */
    private final String deleteRequestNameReplace = "##deleteRequestNameReplace##";

    /**
     * 修改状态方法request的类
     */
    private final String changeStatusRequestClassReplace = "##changeStatusRequestClassReplace##";

    /**
     * 修改状态方法request的名称
     */
    private final String changeStatusRequestNameReplace = "##changeStatusRequestNameReplace##";

    /**
     * service注入代码块
     */
    private final String autowiredCodeBlock = "##autowiredServiceExist##";

    /**
     * 查询方法代码块
     */
    private final String queryMethodBlock = "##queryMethodBlockExist##";

    /**
     * 新建方法代码块
     */
    private final String createMethodBlock = "##createMethodBlockExist##";

    /**
     * 修改方法代码块
     */
    private final String modifyMethodBlock = "##modifyMethodBlockExist##";

    /**
     * 删除方法代码块
     */
    private final String deleteMethodBlock = "##deleteMethodBlockExist##";

    /**
     * 改变状态方法代码块
     */
    private final String changeStatusCodeBlock = "##changeStatusCodeBlockExist##";

    /**
     * converter 名称
     */
    private final String converterNameReplace = "##converterNameReplace##";

    /**
     * domain 名称
     */
    private final String domainNameReplace = "##domainNameReplace##";

    /**
     * 查询条件
     */
    private final String queryConditionReplace = "##queryConditionReplace##";

    private final String controllerTemplate = "package ##packagePathReplace##;\n" +
            "import javax.validation.Valid;\n" +
            "import io.swagger.annotations.Api;\n" +
            "import com.github.pagehelper.Page;\n" +
            "import com.tims.common.web.BaseController;\n" +
            "import io.swagger.annotations.ApiOperation;\n" +
            "import com.tims.framework.web.dto.ResponseDTO;\n" +
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.ResponseBody;\n" +
            "import org.springframework.web.bind.annotation.RequestMethod;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "import ##domainImportReplace##\n" +
            "\n" +
            "@RestController\n" +
            "@RequestMapping(value = \"/##name##\")\n" +
            "@Api(tags=\"##controllerClassNameReplace##\", description=\"##description##管理\")\n" +
            "public class ##controllerClassNameReplace## extends BaseController{\n" +
            "\n" +
            "    ##autowiredServiceExist##\n" +
            "    @Autowired\n" +
            "    private ##autowiredServiceClassReplace## ##autowiredServiceNameReplace##;\n" +
            "    ##autowiredServiceExist##\n" +
            "\n" +
            "    ##createMethodBlockExist##\n" +
            "    @RequestMapping(value = \"/create\", method = { RequestMethod.POST })\n" +
            "    @ResponseBody\n" +
            "    @ApiOperation(value = \"创建##description##\")\n" +
            "    public ResponseDTO create(@RequestBody @Valid ##createRequestClassReplace## ##createRequestNameReplace##) throws Exception {\n" +
            "        ##domainNameReplace## domain = ##converterNameReplace##.convertToCreate(##createRequestNameReplace##);\n" +
            "        ##autowiredServiceNameReplace##.create(domain);\n" +
            "        return new ResponseDTO();\n" +
            "    }\n" +
            "    ##createMethodBlockExist##\n" +
            "\n" +
            "    ##modifyMethodBlockExist##\n" +
            "    @RequestMapping(value = \"/modify\", method = { RequestMethod.POST })\n" +
            "    @ResponseBody\n" +
            "    @ApiOperation(value = \"修改##description##\")\n" +
            "    public ResponseDTO update(@RequestBody @Valid ##modifyRequestClassReplace## ##modifyRequestNameReplace##) throws Exception {\n" +
            "        ##domainNameReplace## domain = ##converterNameReplace##.convertToModify(##modifyRequestNameReplace##);\n" +
            "        ##autowiredServiceNameReplace##.modify(domain);\n" +
            "        return new ResponseDTO();\n" +
            "    }\n" +
            "    ##modifyMethodBlockExist##\n" +
            "\n" +
            "    ##queryMethodBlockExist##\n" +
            "    @RequestMapping(value = \"/query\", method = { RequestMethod.POST })\n" +
            "    @ResponseBody\n" +
            "    @ApiOperation(value = \"查询##description##\")\n" +
            "    public ##queryResponseClassReplace## query(@RequestBody @Valid ##queryRequestClassReplace## ##queryRequestNameReplace##) throws Exception {\n" +
            "        ##queryConditionReplace## condition = ##converterNameReplace##.convertToQuery(##queryRequestNameReplace##);\n" +
            "        Page<##domainNameReplace##> result = ##autowiredServiceNameReplace##.queryByPage(condition);\n" +
            "        ##queryResponseClassReplace## ##queryResponsetNameReplace## = ##converterNameReplace##.convertToQueryResult(result);\n" +
            "        return ##queryResponsetNameReplace##;\n" +
            "    }\n" +
            "    ##queryMethodBlockExist##\n" +
            "\n" +
            "    ##deleteMethodBlockExist##\n" +
            "    @RequestMapping(value = \"/delete\", method = { RequestMethod.POST })\n" +
            "    @ResponseBody\n" +
            "    @ApiOperation(value = \"删除##description##\")\n" +
            "    public ResponseDTO delete(@RequestBody @Valid ##deleteRequestClassReplace## ##deleteRequestNameReplace##) throws Exception {\n" +
            "        ##domainNameReplace## domain = ##converterNameReplace##.convertToDelete(##deleteRequestNameReplace##);\n" +
            "        ##autowiredServiceNameReplace##.delete(domain);\n" +
            "        return new ResponseDTO();\n" +
            "    }\n" +
            "    ##deleteMethodBlockExist##\n" +
            "\n" +
            "    ##changeStatusCodeBlockExist##\n" +
            "    @RequestMapping(value = \"/status/change\", method = { RequestMethod.POST })\n" +
            "    @ResponseBody\n" +
            "    @ApiOperation(value = \"改变##description##状态\")\n" +
            "    public ResponseDTO changeStatus(@RequestBody @Valid ##changeStatusRequestClassReplace## ##changeStatusRequestNameReplace##) throws Exception {\n" +
            "        ##domainNameReplace## domain = ##converterNameReplace##.convertToChangeStatus(##changeStatusRequestNameReplace##);\n" +
            "        ##autowiredServiceNameReplace##.changeStatus(domain);\n" +
            "        return new ResponseDTO();\n" +
            "    }\n" +
            "    ##changeStatusCodeBlockExist##\n" +
            "\n" +
            "}\n";

    public static String getContent(ControllerCreateCondition condition){
        ControllerContentCreator controllerContentCreator = ControllerContentCreator.controllerContentCreator;
        String content = controllerContentCreator.controllerTemplate.replace(controllerContentCreator.nameReplace, condition.getName())
                .replace(controllerContentCreator.descriptionReplace, condition.getDescription())
                .replace(controllerContentCreator.controllerClassNameReplace, condition.getControllerClassName())
                .replace(controllerContentCreator.packagePathReplace, condition.getPackagePath())
                .replace(controllerContentCreator.converterNameReplace, condition.getConverterName())
                .replace(controllerContentCreator.domainNameReplace, condition.getDomainName())
                .replace("##domainImportReplace##", condition.getDomainImport());
        if(!condition.isHasAutowiredService()){
            content = content.substring(0, content.indexOf(controllerContentCreator.autowiredCodeBlock)) +
                    content.substring(content.lastIndexOf(controllerContentCreator.autowiredCodeBlock) + controllerContentCreator.autowiredCodeBlock.length());
        } else {
            content = content.replace(controllerContentCreator.autowiredServiceClassReplace, condition.getAutowiredService())
                    .replace(controllerContentCreator.autowiredServiceNameReplace, StringUtil.doFirstCharLower(condition.getAutowiredService()))
                    .replace(controllerContentCreator.autowiredCodeBlock, "");
        }
        if(!condition.isHasCreateMethod()){
            content = content.substring(0, content.indexOf(controllerContentCreator.createMethodBlock)) +
                    content.substring(content.lastIndexOf(controllerContentCreator.createMethodBlock) + controllerContentCreator.createMethodBlock.length());
        } else {
            content = content.replace(controllerContentCreator.createRequestClassReplace, condition.getCreateRequest())
                    .replace(controllerContentCreator.createRequestNameReplace, StringUtil.doFirstCharLower(condition.getCreateRequest()))
                    .replace(controllerContentCreator.createMethodBlock, "");
        }
        if(!condition.isHasModifyMethod()){
            content = content.substring(0, content.indexOf(controllerContentCreator.modifyMethodBlock)) +
                    content.substring(content.lastIndexOf(controllerContentCreator.modifyMethodBlock) + controllerContentCreator.modifyMethodBlock.length());
        } else {
            content = content.replace(controllerContentCreator.modifyRequestClassReplace, condition.getModifyRequest())
                    .replace(controllerContentCreator.modifyRequestNameReplace, StringUtil.doFirstCharLower(condition.getModifyRequest()))
                    .replace(controllerContentCreator.modifyMethodBlock, "");
        }
        if(!condition.isHasDeleteMethod()){
            content = content.substring(0, content.indexOf(controllerContentCreator.deleteMethodBlock)) +
                    content.substring(content.lastIndexOf(controllerContentCreator.deleteMethodBlock) + controllerContentCreator.deleteMethodBlock.length());
        } else {
            content = content.replace(controllerContentCreator.deleteRequestClassReplace, condition.getDeleteRequest())
                    .replace(controllerContentCreator.deleteRequestNameReplace, StringUtil.doFirstCharLower(condition.getDeleteRequest()))
                    .replace(controllerContentCreator.deleteMethodBlock, "");
        }
        if(!condition.isHasChangeStatusMethod()){
            content = content.substring(0, content.indexOf(controllerContentCreator.changeStatusCodeBlock)) +
                    content.substring(content.lastIndexOf(controllerContentCreator.changeStatusCodeBlock) + controllerContentCreator.changeStatusCodeBlock.length());
        } else {
            content = content.replace(controllerContentCreator.changeStatusRequestClassReplace, condition.getChangeStatusRequest())
                    .replace(controllerContentCreator.changeStatusRequestNameReplace, StringUtil.doFirstCharLower(condition.getChangeStatusRequest()))
                    .replace(controllerContentCreator.changeStatusCodeBlock, "");
        }
        if(!condition.isHasQueryMethod()){
            content = content.substring(0, content.indexOf(controllerContentCreator.queryMethodBlock)) +
                    content.substring(content.lastIndexOf(controllerContentCreator.queryMethodBlock) + controllerContentCreator.queryMethodBlock.length());
        } else {
            content = content.replace(controllerContentCreator.queryRequestClassReplace, condition.getQueryRequest())
                    .replace(controllerContentCreator.queryConditionReplace, condition.getQueryCondition())
                    .replace(controllerContentCreator.queryRequestNameReplace, StringUtil.doFirstCharLower(condition.getQueryRequest()))
                    .replace(controllerContentCreator.queryResponseClassReplace, condition.getQueryResponse())
                    .replace(controllerContentCreator.queryResponseNameReplace, StringUtil.doFirstCharLower(condition.getQueryResponse()))
                    .replace(controllerContentCreator.queryMethodBlock, "");
        }
        return content;
    }

}
