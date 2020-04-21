package dtcsm;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiUtil;
import util.StringUtil;

import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/20 13:11
 */
public class ConverterContentCreator {

    private static String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 包路径 替换字符
     */
    private String packagePathReplace = "##packagePathReplace##";

    /**
     * converter类 替换字符
     */
    private String converterClassReplace = "##converterClassReplace##";

    /**
     * 是否存在创建转换 替换字符
     */
    private String createMethodBlockExist = "##createMethodBlockExist##";

    /**
     * domain类名称 替换字符
     */
    private String domainClassReplace = "##domainClassReplace##";

    /**
     * create request 类名称 替换字符
     */
    private String createRequestReplace = "##createRequestReplace##";

    /**
     * create request dto 类名称 替换字符
     */
    private String createRequestDTOReplace = "##createRequestDTOReplace##";

    /**
     * 新增方法的转换代码 替换字符
     */
    private String createGetSetCodeReplace = "##createGetSetCodeReplace##";

    /**
     * 是否存在修改转换 替换字符
     */
    private String modifyMethodBlockExist = "##modifyMethodBlockExist##";

    /**
     * modify request 类名称 替换字符
     */
    private String modifyRequestReplace = "##modifyRequestReplace##";

    /**
     * modify request dto 类名称 替换字符
     */
    private String modifyRequestDTOReplace = "##modifyRequestDTOReplace##";

    /**
     * 修改方法的转换代码 替换字符
     */
    private String modifyGetSetCodeReplace = "##modifyGetSetCodeReplace##";

    /**
     * 是否存在删除转换 替换字符
     */
    private String deleteMethodBlockExist = "##deleteMethodBlockExist##";

    /**
     * delete request 类名称 替换字符
     */
    private String deleteRequestClassReplace = "##deleteRequestClassReplace##";

    /**
     * delete request dto 类名称 替换字符
     */
    private String deleteRequestDTOClassReplace = "##deleteRequestDTOClassReplace##";

    /**
     * 删除方法的转换代码 替换字符
     */
    private String deleteGetSetCodeReplace = "##deleteGetSetCodeReplace##";

    /**
     * 是否存在改变状态转换 替换字符
     */
    private String changeStatusMethodBlockExist = "##changeStatusMethodBlockExist##";

    /**
     * change status request 类名称 替换字符
     */
    private String changeStatusRequestClassReplace = "##changeStatusRequestClassReplace##";

    /**
     * change status request dto 类名称 替换字符
     */
    private String changeStatusRequestDTOClassReplace = "##changeStatusRequestDTOClassReplace##";

    /**
     * 改变状态方法的转换代码 替换字符
     */
    private String changeStatusGetSetCodeReplace = "##changeStatusGetSetCodeReplace##";

    /**
     * 是否存在查询转换 替换字符
     */
    private String queryMethodBlockExist = "##queryMethodBlockExist##";

    /**
     * query condition 类名称 替换字符
     */
    private String queryConditionReplace = "##queryConditionReplace##";

    /**
     * query request 类名称 替换字符
     */
    private String queryRequestClassReplace = "##queryRequestClassReplace##";

    /**
     * query request dto 类名称 替换字符
     */
    private String queryRequestClassDTOReplace = "##queryRequestClassDTOReplace##";

    /**
     * 查询条件的转换代码 替换字符
     */
    private String queryConditionGetSetCodeReplace = "##queryConditionGetSetCodeReplace##";

    /**
     * query response 类名称 替换字符
     */
    private String queryResponseClassReplace = "##queryResponseClassReplace##";

    /**
     * query response dto 类名称 替换字符
     */
    private String queryResponseDTOClassReplace = "##queryResponseDTOClassReplace##";

    /**
     * 查询结果的转换代码 替换字符
     */
    private String queryResultGetSetCodeReplace = "##queryResultGetSetCodeReplace##";

    private final String converterTemplate = "package ##packagePathReplace##;\n" +
        "import java.util.List;\n" +
            "import java.util.Objects;\n" +
            "import lombok.AccessLevel;\n" +
            "import java.util.ArrayList;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "import com.github.pagehelper.Page;\n" +
            "import com.tims.common.dto.PaginationDTO;\n" +
            "import com.tims.common.session.SysContent;\n" +
            "import com.tims.framework.util.StringUtil;\n" +
            "import com.tims.framework.util.ObjectConvertor;\n" +
            "import com.tims.framework.constant.PaginationEnum;\n" +
            "import org.apache.commons.collections4.CollectionUtils;\n" +
            "import ##domainImportReplace##\n" +
            "\n" +
            "@NoArgsConstructor(access=AccessLevel.PRIVATE)\n" +
            "public class ##converterClassReplace## implements ObjectConvertor {\n" +
            "\n" +
            "\t##createMethodBlockExist##\n" +
            "\tpublic static ##domainClassReplace## convertToCreate(##createRequestReplace## request){\n" +
            "\t\tif(Objects.nonNull(request) && Objects.nonNull(request.getReqDtos())){\n" +
            "\t\t\t##createRequestDTOReplace## reqDtos = request.getReqDtos();\n" +
            "\t\t\t##domainClassReplace## domain = new ##domainClassReplace##();\n" +
            "\t\t\t##createGetSetCodeReplace##\n" +
            "\t\t\treturn domain;\n" +
            "\t\t}\n" +
            "\t\treturn null;\n" +
            "\t}\n" +
            "\t##createMethodBlockExist##\n" +
            "\n" +
            "\t##modifyMethodBlockExist##\n" +
            "\tpublic static ##domainClassReplace## convertToModify(##modifyRequestReplace## request){\n" +
            "\t\tif(Objects.nonNull(request) && Objects.nonNull(request.getReqDtos())){\n" +
            "\t\t\t##modifyRequestDTOReplace## reqDtos = request.getReqDtos();\n" +
            "\t\t\t##domainClassReplace## domain = new ##domainClassReplace##();\n" +
            "\t\t\t##modifyGetSetCodeReplace##\n" +
            "\t\t\treturn domain;\n" +
            "\t\t}\n" +
            "\t\treturn null;\n" +
            "\t}\n" +
            "\t##modifyMethodBlockExist##\n" +
            "\n" +
            "\t##queryMethodBlockExist##\n" +
            "\tpublic static ##queryConditionReplace## convertToQuery(##queryRequestClassReplace## request){\n" +
            "\t\tif(Objects.nonNull(request) && Objects.nonNull(request.getReqDtos())){\n" +
            "\t\t\t##queryRequestClassDTOReplace## reqDtos = request.getReqDtos();\n" +
            "\t\t\t##queryConditionReplace## queryCondition = new ##queryConditionReplace##();\n" +
            "\t\t\t##queryConditionGetSetCodeReplace##\n" +
            "\t\t\tqueryCondition.setSortType(reqDtos.getSortType());\n" +
            "\t\t\tqueryCondition.setSortValue(reqDtos.getSortValue());\n" +
            "\t\t\tqueryCondition.setLang(SysContent.getCurrentUserLanguage());\n" +
            "\t\t\tif (request.getPaginationDTO() != null && request.getPaginationDTO().getPageNumber() != null && request.getPaginationDTO().getPageSize() != null) {\n" +
            "\t\t\t\tqueryCondition.setPageNumber(request.getPaginationDTO().getPageNumber());\n" +
            "\t\t\t\tqueryCondition.setPageSize(request.getPaginationDTO().getPageSize());\n" +
            "\t\t\t} else {\n" +
            "\t\t\t\tqueryCondition.setPageNumber(PaginationEnum.DEFAULT_PAGENUMBER.getCode());\n" +
            "\t\t\t\tqueryCondition.setPageSize(PaginationEnum.DEFAULT_PAGESIZE.getCode());\n" +
            "\t\t\t}\n" +
            "\t\t\treturn queryCondition;\n" +
            "\t\t}\n" +
            "\t\treturn null;\n" +
            "\t}\n" +
            "\n" +
            "\tpublic static ##queryResponseClassReplace## convertToQueryResult(Page<##domainClassReplace##> domains){\n" +
            "\t\t##queryResponseClassReplace## response = new ##queryResponseClassReplace##();\n" +
            "\t\tif(CollectionUtils.isNotEmpty(domains)){\n" +
            "\t\t\tList<##queryResponseDTOClassReplace##> data = new ArrayList<>();\n" +
            "\t\t\tfor(##domainClassReplace## domain : domains) {\n" +
            "\t\t\t\t##queryResponseDTOClassReplace## queryRes = new ##queryResponseDTOClassReplace##();\n" +
            "\t\t\t\t##queryResultGetSetCodeReplace##\n" +
            "\t\t\t\tdata.add(queryRes);\n" +
            "\t\t\t}\n" +
            "\t\t\tPaginationDTO paginationDTO = new PaginationDTO();\n" +
            "\t\t\tpaginationDTO.setPageNumber(domains.getPageNum());\n" +
            "\t\t\tpaginationDTO.setPageSize(domains.getPageSize());\n" +
            "\t\t\tpaginationDTO.setTotalCount(domains.getTotal());\n" +
            "\t\t\tresponse.setPaginationDTO(paginationDTO); \n" +
            "\t\t}\n" +
            "\t\treturn response;\n" +
            "\t}\n" +
            "\t##queryMethodBlockExist##\n" +
            "\n" +
            "\t##deleteMethodBlockExist##\n" +
            "\tpublic static ##domainClassReplace## convertToDelete(##deleteRequestClassReplace## request){\n" +
            "\t\tif(Objects.nonNull(request) && Objects.nonNull(request.getReqDtos())){\n" +
            "\t\t\t##deleteRequestDTOClassReplace## reqDtos = request.getReqDtos();\n" +
            "\t\t\t##domainClassReplace## domain = new ##domainClassReplace##();\n" +
            "\t\t\t##deleteGetSetCodeReplace##\n" +
            "\t\t\treturn domain;\n" +
            "\t\t}\n" +
            "\t\treturn null;\n" +
            "\t}\n" +
            "\t##deleteMethodBlockExist##\n" +
            "\n" +
            "\t##changeStatusMethodBlockExist##\n" +
            "\tpublic static ##domainClassReplace## convertToChangeStatus(##changeStatusRequestClassReplace## request){\n" +
            "\t\tif(Objects.nonNull(request) && Objects.nonNull(request.getReqDtos())){\n" +
            "\t\t\t##changeStatusRequestDTOClassReplace## reqDtos = request.getReqDtos();\n" +
            "\t\t\t##domainClassReplace## domain = new ##domainClassReplace##();\n" +
            "\t\t\t##changeStatusGetSetCodeReplace##\n" +
            "\t\t\treturn domain;\n" +
            "\t\t}\n" +
            "\t\treturn null;\n" +
            "\t}\n" +
            "\t##changeStatusMethodBlockExist##\n" +
            "\n" +
            "}";

    public static ConverterContentCreator converterContentCreator = new ConverterContentCreator();

    public static String getContent(ConverterCreateCondition condition){
        ConverterContentCreator converterContentCreator = ConverterContentCreator.converterContentCreator;
        String content = converterContentCreator.converterTemplate
                .replace(converterContentCreator.packagePathReplace, condition.getPackagePath())
                .replace(converterContentCreator.converterClassReplace, condition.getConverterClassName())
                .replace(converterContentCreator.domainClassReplace, condition.getDomainClassName())
                .replace("##domainImportReplace##", condition.getDomainImport());
        if(condition.isHasCreateConverter()){
            content = content.replace(converterContentCreator.createRequestDTOReplace, condition.getCreateRequestDTOClassName())
                    .replace(converterContentCreator.createRequestReplace, condition.getCreateRequestClassName())
                    .replace(converterContentCreator.createGetSetCodeReplace, createGetSetCodeForRequestToDomain(condition.getCreateFieldNames()))
                    .replace(converterContentCreator.createMethodBlockExist, "");
        } else {
            content = content.substring(0, content.indexOf(converterContentCreator.createMethodBlockExist)) +
                    content.substring(content.lastIndexOf(converterContentCreator.createMethodBlockExist) + converterContentCreator.createMethodBlockExist.length());
        }
        if(condition.isHasModifyConverter()){
            content = content.replace(converterContentCreator.modifyRequestDTOReplace, condition.getModifyRequestDTOClassName())
                    .replace(converterContentCreator.modifyRequestReplace, condition.getModifyRequestClassName())
                    .replace(converterContentCreator.modifyGetSetCodeReplace, createGetSetCodeForRequestToDomain(condition.getModifyFieldNames()))
                    .replace(converterContentCreator.modifyMethodBlockExist, "");
        } else {
            content = content.substring(0, content.indexOf(converterContentCreator.modifyMethodBlockExist)) +
                    content.substring(content.lastIndexOf(converterContentCreator.modifyMethodBlockExist) + converterContentCreator.modifyMethodBlockExist.length());
        }
        if(condition.isHasDeleteConverter()){
            content = content.replace(converterContentCreator.deleteRequestDTOClassReplace, condition.getDeleteRequestDTOClassName())
                    .replace(converterContentCreator.deleteRequestClassReplace, condition.getDeleteRequestClassName())
                    .replace(converterContentCreator.deleteGetSetCodeReplace, createGetSetCodeForRequestToDomain(condition.getDeleteFieldNames()))
                    .replace(converterContentCreator.deleteMethodBlockExist, "");
        } else {
            content = content.substring(0, content.indexOf(converterContentCreator.deleteMethodBlockExist)) +
                    content.substring(content.lastIndexOf(converterContentCreator.deleteMethodBlockExist) + converterContentCreator.deleteMethodBlockExist.length());
        }
        if(condition.isHasChangeStatusConverter()){
            content = content.replace(converterContentCreator.changeStatusRequestClassReplace, condition.getChangeStatusRequestClassName())
                    .replace(converterContentCreator.changeStatusRequestDTOClassReplace, condition.getChangeStatusRequestDTOClassName())
                    .replace(converterContentCreator.changeStatusGetSetCodeReplace, createGetSetCodeForRequestToDomain(condition.getChangeStatusFieldNames()))
                    .replace(converterContentCreator.changeStatusMethodBlockExist, "");
        } else {
            content = content.substring(0, content.indexOf(converterContentCreator.changeStatusMethodBlockExist)) +
                    content.substring(content.lastIndexOf(converterContentCreator.changeStatusMethodBlockExist) + converterContentCreator.changeStatusMethodBlockExist.length());
        }
        if(condition.isHasQueryConverter()){
            content = content.replace(converterContentCreator.queryRequestClassDTOReplace, condition.getQueryRequestDTOClassName())
                    .replace(converterContentCreator.queryRequestClassReplace, condition.getQueryRequestClassName())
                    .replace(converterContentCreator.queryConditionGetSetCodeReplace, createGetSetCodeForRequestToQueryCondition(condition.getQueryFieldNames()))
                    .replace(converterContentCreator.queryConditionReplace, condition.getQueryConditionClassName())
                    .replace(converterContentCreator.queryResponseDTOClassReplace, condition.getQueryResponseDTOClassName())
                    .replace(converterContentCreator.queryResponseClassReplace, condition.getQueryResponseClassName())
                    .replace(converterContentCreator.queryResultGetSetCodeReplace, createGetSetCodeForDomainToResponse(condition.getQueryFieldNames()))
                    .replace(converterContentCreator.queryMethodBlockExist, "");
        } else {
            content = content.substring(0, content.indexOf(converterContentCreator.queryMethodBlockExist)) +
                    content.substring(content.lastIndexOf(converterContentCreator.queryMethodBlockExist) + converterContentCreator.queryMethodBlockExist.length());
        }
        return content;
    }

    private static CharSequence createGetSetCodeForDomainToResponse(List<PsiField> fieldNames) {
        StringBuilder sb = new StringBuilder();
        for(PsiField psiField : fieldNames){
            PsiClass fieldTypeClass = PsiUtil.resolveClassInClassTypeOnly(psiField.getType());
            String fieldName = psiField.getName();
            if(Objects.nonNull(fieldTypeClass)){
                if(Objects.equals(fieldTypeClass.getName(), "Integer")){
                    sb.append("queryRes.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(StringUtil.valueOf(domain.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));");
                    sb.append(LINE_SEPARATOR);
                } else if (Objects.equals(fieldTypeClass.getName(), "Date")){
                    sb.append("queryRes.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(DateUtil.toYMDHSString(domain.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));");
                    sb.append(LINE_SEPARATOR);
                } else if (Objects.equals(fieldTypeClass.getName(), "BigDecimal")){
                    sb.append("BigDecimal ").append(StringUtil.doFirstCharLower(fieldName)).append(" = ")
                            .append("domain.get").append(StringUtil.doFirstCharUpper(fieldName)).append("();")
                            .append(LINE_SEPARATOR).append(StringUtil.doFirstCharLower(fieldName)).append(" = Objects.isNull(")
                            .append(StringUtil.doFirstCharLower(fieldName)).append(") ? BigDecimal.ZERO : ")
                            .append(StringUtil.doFirstCharLower(fieldName)).append(";").append(LINE_SEPARATOR)
                            .append("queryRes.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(")
                            .append(StringUtil.doFirstCharLower(fieldName))
                            .append(".setScale(2, BigDecimal.ROUND_CEILING).toString());");
                } else {
                    sb.append("queryRes.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(domain.get").append(StringUtil.doFirstCharUpper(fieldName)).append("());");
                    sb.append(LINE_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    private static CharSequence createGetSetCodeForRequestToDomain(List<PsiField> fieldNames) {
        StringBuilder sb = new StringBuilder();
        for(PsiField psiField : fieldNames){
            PsiClass fieldTypeClass = PsiUtil.resolveClassInClassTypeOnly(psiField.getType());
            String fieldName = psiField.getName();
            if(Objects.nonNull(fieldTypeClass)){
                if(Objects.equals(fieldTypeClass.getName(), "Integer")){
                    sb.append("domain.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(StringUtil.toIntegerAsNull(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));");
                    sb.append(LINE_SEPARATOR);
                } else if (Objects.equals(fieldTypeClass.getName(), "Date")){
                    sb.append("domain.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(DateUtil.parseDate(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));");
                    sb.append(LINE_SEPARATOR);
                } else if (Objects.equals(fieldTypeClass.getName(), "BigDecimal")){
                    sb.append("if(StringUtil.isNotBlank(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName))
                            .append("()").append(")) {").append(LINE_SEPARATOR).append("domain.set")
                            .append(StringUtil.doFirstCharUpper(fieldName)).append("(BigDecimal.valueOf(")
                            .append("reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));").append(LINE_SEPARATOR).append("}");
                } else {
                    sb.append("domain.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("());");
                    sb.append(LINE_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    private static CharSequence createGetSetCodeForRequestToQueryCondition(List<PsiField> fieldNames) {
        StringBuilder sb = new StringBuilder();
        for(PsiField psiField : fieldNames){
            PsiClass fieldTypeClass = PsiUtil.resolveClassInClassTypeOnly(psiField.getType());
            String fieldName = psiField.getName();
            if(Objects.nonNull(fieldTypeClass)){
                if(Objects.equals(fieldTypeClass.getName(), "Integer")){
                    sb.append("queryCondition.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(StringUtil.toIntegerAsNull(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));");
                    sb.append(LINE_SEPARATOR);
                } else if (Objects.equals(fieldTypeClass.getName(), "Date")){
                    sb.append("queryCondition.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(DateUtil.parseDate(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));");
                    sb.append(LINE_SEPARATOR);
                } else if (Objects.equals(fieldTypeClass.getName(), "BigDecimal")){
                    sb.append("if(StringUtil.isNotBlank(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName))
                            .append("()").append(")) {").append(LINE_SEPARATOR).append("queryCondition.set")
                            .append(StringUtil.doFirstCharUpper(fieldName)).append("(BigDecimal.valueOf(")
                            .append("reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("()));").append(LINE_SEPARATOR).append("}");
                } else {
                    sb.append("queryCondition.set").append(StringUtil.doFirstCharUpper(fieldName)).append("(reqDtos.get").append(StringUtil.doFirstCharUpper(fieldName)).append("());");
                    sb.append(LINE_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }
}
