package gsc;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiReferenceList;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.util.PsiUtil;
import util.StringUtil;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/13 10:34
 */
public class GetSetConvertWrapper {

    private String LINE_SEPARATOR = System.getProperty("line.separator");

    private PsiClass sourceClass;

    private PsiClass targetClass;

    public PsiClass getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(PsiClass sourceClass) {
        this.sourceClass = sourceClass;
    }

    public PsiClass getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(PsiClass targetClass) {
        this.targetClass = targetClass;
    }

    public boolean isValid(){
        return Objects.nonNull(sourceClass) && Objects.nonNull(targetClass);
    }

    public String createCode(){
        String code = createStatement();
        if(!code.endsWith(LINE_SEPARATOR)){
            code = code + LINE_SEPARATOR;
        }
        code = code + createBody();
        if(!code.endsWith(LINE_SEPARATOR)){
            code = code + LINE_SEPARATOR;
        }
        code = code + createEnd();
        return code;
    }

    private String targetParaName = "##targetParaName##";

    private String sourceParaName = "##sourceParaName##";

    private String createEnd(){
        if(isRequest(sourceClass)){
            return "}";
        } else if (isResponse(targetClass)) {
            return "}";
        } else {
            return "";
        }
    }

    private String createBody(){
        if(isRequest(sourceClass)){
            return createRTDB();
        } else if (isResponse(targetClass)) {
            return createDTRB();
        } else {
            return createDTDB();
        }
    }

    /**
     * domain to response body
     */
    private String createDTRB(){
        StringBuilder sb = new StringBuilder();
        PsiField[] psiFields = targetClass.getFields();
        PsiField resultField = null;
        String resultName = "data";
        String listTypeName = "List";
        for (PsiField psiField : psiFields){
            if(Objects.equals(psiField.getName(), resultName)){
                resultField = psiField;
                break;
            }
        }
        if(Objects.nonNull(resultField)){
            PsiClass returnClass = PsiUtil.resolveClassInClassTypeOnly(resultField.getType());
            if(Objects.nonNull(returnClass)){
                PsiClass realReturnClass = null;
                if(Objects.equals(listTypeName, returnClass.getName())){
                    PsiClassType.ClassResolveResult classResolveResult = PsiUtil.resolveGenericsClassInType(resultField.getType());
                    if(Objects.nonNull(classResolveResult.getElement())) {
                        Map<PsiTypeParameter, PsiType> map = classResolveResult.getSubstitutor().getSubstitutionMap();
                        if (map.size() > 0) {
                            for (Map.Entry<PsiTypeParameter, PsiType> entry : map.entrySet()) {
                                realReturnClass = PsiUtil.resolveGenericsClassInType(entry.getValue()).getElement();
                            }
                        }
                    }
                    if(Objects.nonNull(realReturnClass)){
                        String sourceClassName = sourceClass.getName();
                        String sourceClazzParaName = StringUtil.doFirstCharLower(sourceClassName);
                        String realReturnClassName = realReturnClass.getName();
                        String realReturnClassParaName = StringUtil.doFirstCharLower(realReturnClassName);
                        sb.append("if(CollectionUtils.isNotEmpty(pages)) {")
                                .append(LINE_SEPARATOR).append("for(").append(sourceClassName)
                                .append(" ").append(sourceClazzParaName).append(" : pages) {").append(LINE_SEPARATOR);
                        sb.append(realReturnClassName).append(" ").append(realReturnClassParaName).append(" = new ")
                                .append(realReturnClassName).append("();").append(LINE_SEPARATOR);
                        TreeMap<String, PsiField> targetFieldMap = convertFieldsToMap(realReturnClass.getFields());
                        TreeMap<String, PsiField> sourceFieldMap = convertFieldsToMap(sourceClass.getFields());
                        for(Map.Entry<String, PsiField> entry : targetFieldMap.entrySet()){
                            PsiField targetField = entry.getValue();
                            PsiField sourceField = sourceFieldMap.get(entry.getKey());
                            String propertyCode = createPropertyCode(targetField, sourceField);
                            if(Objects.nonNull(propertyCode) && propertyCode.length() > 0){
                                sb.append(propertyCode.replace(targetParaName, realReturnClassParaName)
                                        .replace(sourceParaName, sourceClazzParaName))
                                        .append(LINE_SEPARATOR);
                            }
                        }
                        sb.append("data.add(").append(realReturnClassParaName).append(");");
                        sb.append("}").append(LINE_SEPARATOR);
                        sb.append("}");
                    }
                } else {
                    String sourceClassName = sourceClass.getName();
                    String sourceClazzParaName = StringUtil.doFirstCharLower(sourceClassName);
                    TreeMap<String, PsiField> targetFieldMap = convertFieldsToMap(returnClass.getFields());
                    TreeMap<String, PsiField> sourceFieldMap = convertFieldsToMap(sourceClass.getFields());
                    for(Map.Entry<String, PsiField> entry : targetFieldMap.entrySet()){
                        PsiField targetField = entry.getValue();
                        PsiField sourceField = sourceFieldMap.get(entry.getKey());
                        String propertyCode = createPropertyCode(targetField, sourceField);
                        if(Objects.nonNull(propertyCode) && propertyCode.length() > 0){
                            sb.append(propertyCode.replace(targetParaName, resultField.getName())
                                    .replace(sourceParaName, sourceClazzParaName))
                                    .append(LINE_SEPARATOR);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * request to domain body
     */
    private String createRTDB(){
        StringBuilder sb = new StringBuilder();
        String targetClazzName = targetClass.getName();
        String targetClazzParaName = StringUtil.doFirstCharLower(targetClazzName);
        String sourceRealName = "reqDtos";
        String langName = "lang";
        String pageSizeName = "pageNumber";
        String pageNumberName = "pageSize";
        PsiClass sourceParaPsiClass = null;
        PsiField[] psiFields = sourceClass.getFields();
        for(PsiField psiField : psiFields){
            if(Objects.equals(psiField.getName(), sourceRealName)){
                sourceParaPsiClass = PsiUtil.resolveClassInClassTypeOnly(psiField.getType());
                break;
            }
        }
        if(Objects.nonNull(sourceParaPsiClass)){
            String sourceParaPsiClassName = sourceParaPsiClass.getName();
//            String sourceParaPsiClassParaName = StringUtil.doFirstCharLower(sourceParaPsiClassName);
            sb.append(sourceParaPsiClassName).append(" reqDtos = request.getReqDtos();").append(LINE_SEPARATOR);
            TreeMap<String, PsiField> targetFieldMap = convertFieldsToMap(targetClass.getFields());
            TreeMap<String, PsiField> sourceFieldMap = convertFieldsToMap(sourceParaPsiClass.getFields());
            boolean pageCreateFlag = false;
            for(Map.Entry<String, PsiField> entry : targetFieldMap.entrySet()){
                PsiField targetField = entry.getValue();
                if(Objects.equals(targetField.getName(), langName)){
                    sb.append(targetClazzParaName).append(".setLang(SysContent.getCurrentUserLanguage());").append(LINE_SEPARATOR);
                } else if(Objects.equals(targetField.getName(), pageSizeName) || Objects.equals(targetField.getName(), pageNumberName)){
                        pageCreateFlag = true;
                } else {
                    PsiField sourceField = sourceFieldMap.get(entry.getKey());
                    String propertyCode = createPropertyCode(targetField, sourceField);
                    if(Objects.nonNull(propertyCode) && propertyCode.length() > 0){
                        sb.append(propertyCode.replace(targetParaName, targetClazzParaName).replace(sourceParaName, sourceRealName))
                                .append(LINE_SEPARATOR);
                    }
                }
            }
            if(pageCreateFlag){
                sb.append("PaginationDTO paginationDTO = request.getPaginationDTO();").append(LINE_SEPARATOR);
                sb.append("if(Objects.nonNull(paginationDTO) && Objects.nonNull(paginationDTO.getPageNumber()) " +
                        "&& Objects.nonNull(paginationDTO.getPageSize())) {").append(LINE_SEPARATOR);
                sb.append(targetClazzParaName).append(".setPageNumber(paginationDTO.getPageNumber());").append(LINE_SEPARATOR);
                sb.append(targetClazzParaName).append(".setPageSize(paginationDTO.getPageSize());").append(LINE_SEPARATOR);
                sb.append("}else {").append(LINE_SEPARATOR);
                sb.append(targetClazzParaName).append(".setPageNumber(PaginationEnum.DEFAULT_PAGENUMBER.getCode());").append(LINE_SEPARATOR);
                sb.append(targetClazzParaName).append(".setPageSize(PaginationEnum.DEFAULT_PAGESIZE.getCode());").append(LINE_SEPARATOR);
                sb.append("}").append(LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

    /**
     * domain to domain body
     */
    private String createDTDB(){
        String targetClazzName = targetClass.getName();
        String targetClazzParaName = StringUtil.doFirstCharLower(targetClazzName);
        String sourceClassName = sourceClass.getName();
        String sourceClazzParaName = StringUtil.doFirstCharLower(sourceClassName);
        TreeMap<String, PsiField> targetFieldMap = convertFieldsToMap(targetClass.getFields());
        TreeMap<String, PsiField> sourceFieldMap = convertFieldsToMap(sourceClass.getFields());
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, PsiField> entry : targetFieldMap.entrySet()){
            PsiField targetField = entry.getValue();
            PsiField sourceField = sourceFieldMap.get(entry.getKey());
            String propertyCode = createPropertyCode(targetField, sourceField);
            if(Objects.nonNull(propertyCode) && propertyCode.length() > 0){
                sb.append(propertyCode.replace(targetParaName, targetClazzParaName).replace(sourceParaName, sourceClazzParaName))
                        .append(LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

    private String createPropertyCode(PsiField target, PsiField source){
        if(Objects.nonNull(target) && Objects.nonNull(source)){
            PsiClass targetPsiType = PsiUtil.resolveClassInClassTypeOnly(target.getType());
            PsiClass sourcePsiType = PsiUtil.resolveClassInClassTypeOnly(source.getType());
            if(Objects.nonNull(targetPsiType) && Objects.nonNull(sourcePsiType)){
                String intType = "Integer";
                String stringType = "String";
                String bigDecimalType = "BigDecimal";
                String dateType = "Date";
                String listType = "List";
                if(Objects.equals(targetPsiType.getName(), listType) && Objects.equals(sourcePsiType.getName(), listType)){
                    //TODO
                } else {
                    if(Objects.equals(targetPsiType.getName(), sourcePsiType.getName())){
                        return targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "());";
                    } else if (Objects.equals(sourcePsiType.getName(), intType) && Objects.equals(targetPsiType.getName(), stringType)){
                        return targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(StringUtil.valueOf(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "()));";
                    } else if (Objects.equals(sourcePsiType.getName(), stringType) && Objects.equals(targetPsiType.getName(), intType)){
                        return targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(StringUtil.toIntegerAsNull(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "()));";
                    } else if (Objects.equals(sourcePsiType.getName(), bigDecimalType) && Objects.equals(targetPsiType.getName(), stringType)){
                        String paraName = StringUtil.doFirstCharLower(source.getName());
                        return "BigDecimal " + paraName + " = " + sourceParaName +
                                ".get" + StringUtil.doFirstCharUpper(source.getName()) + "();"
                                + LINE_SEPARATOR + paraName + " = Objects.isNull(" + paraName + ") ? BigDecimal.ZERO : " + paraName + ";"
                                + LINE_SEPARATOR +
                                targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(" + paraName + ".setScale(2, BigDecimal.ROUND_CEILING).toString());";
                    } else if (Objects.equals(sourcePsiType.getName(), stringType) && Objects.equals(targetPsiType.getName(), bigDecimalType)){
                        return "if(StringUtil.isNotBlank(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "()" + ")) {"
                                + LINE_SEPARATOR
                                + targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(BigDecimal.valueOf(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "()));"
                                + LINE_SEPARATOR + "}";
                    } else if (Objects.equals(sourcePsiType.getName(), dateType) && Objects.equals(targetPsiType.getName(), stringType)){
                        return targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(DateUtil.toYMDHSString(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "()));";
                    } else if (Objects.equals(sourcePsiType.getName(), stringType) && Objects.equals(targetPsiType.getName(), dateType)){
                        return targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName())
                                + "(DateUtil.parseDate(" + sourceParaName + ".get" + StringUtil.doFirstCharUpper(source.getName()) + "()));";
                    }
                }
            }
        } else if (Objects.nonNull(target)){
            return "//TODO" + LINE_SEPARATOR + targetParaName + ".set" + StringUtil.doFirstCharUpper(target.getName()) + "();";
        }
        return null;
    }

    private TreeMap<String, PsiField> convertFieldsToMap(PsiField[] psiFields){
        TreeMap<String, PsiField> fieldMap = new TreeMap<>();
        if(Objects.nonNull(psiFields) && psiFields.length > 0){
            for(PsiField psiField : psiFields){
                fieldMap.put(psiField.getName(), psiField);
            }
        }
        return fieldMap;
    }

    private String createStatement(){
        if(isRequest(sourceClass)){
            return createRTDS();
        } else if (isResponse(targetClass)) {
            return createDTRS();
        } else {
            return createDTDS();
        }
    }

    /**
     * domain to response statement
     */
    private String createDTRS(){
        String targetClazzName = targetClass.getName();
        String targetClazzParaName = StringUtil.doFirstCharLower(targetClazzName);
        StringBuilder sb = new StringBuilder();
        String resultName = "data";
        String pagination = "paginationDTO";
        String listTypeName = "List";
        sb.append(targetClazzName).append(" ").append(targetClazzParaName)
                .append(" = new ").append(targetClazzName).append("();").append(LINE_SEPARATOR);
        PsiField[] psiFields = targetClass.getFields();
        for(PsiField psiField : psiFields){
            PsiClass fieldClass = PsiUtil.resolveClassInClassTypeOnly(psiField.getType());
            if(Objects.nonNull(fieldClass)){
                if(Objects.equals(psiField.getName(), resultName)){
                    if(Objects.equals(fieldClass.getName(), listTypeName)){
                        PsiClassType.ClassResolveResult classResolveResult = PsiUtil.resolveGenericsClassInType(psiField.getType());
                        if(Objects.nonNull(classResolveResult.getElement())){
                            Map<PsiTypeParameter, PsiType> map = classResolveResult.getSubstitutor().getSubstitutionMap();
                            if(map.size() > 0){
                                String gTypeName = null;
                                for(Map.Entry<PsiTypeParameter, PsiType> entry : map.entrySet()){
                                    PsiClass gTypeClass = PsiUtil.resolveGenericsClassInType(entry.getValue()).getElement();
                                    if(Objects.equals(entry.getKey().getName(), "E") && Objects.nonNull(gTypeClass)){
                                        gTypeName = gTypeClass.getName();
                                    }
                                }
                                if(Objects.nonNull(gTypeName)){
                                    sb.append(fieldClass.getName()).append("<").append(gTypeName).append(">")
                                            .append(" ").append(psiField.getName()).append(" = ")
                                            .append("new ").append("ArrayList<>").append("();").append(LINE_SEPARATOR);
                                    sb.append(targetClazzParaName).append(".setData(data);").append(LINE_SEPARATOR);
                                }
                            }
                        }
                    } else {
                        sb.append(fieldClass.getName()).append(" ").append(psiField.getName()).append(" = ")
                                .append("new ").append(fieldClass.getName()).append("()").append(LINE_SEPARATOR);
                        sb.append(targetClazzParaName).append(".setData(data);").append(LINE_SEPARATOR);
                    }
                } else if(Objects.equals(psiField.getName(), pagination)){
                    sb.append("PaginationDTO paginationDto = new PaginationDTO();").append(LINE_SEPARATOR);
                    sb.append("paginationDto.setPageNumber(pages.getPageNum());").append(LINE_SEPARATOR);
                    sb.append("paginationDto.setPageSize(pages.getPageSize());").append(LINE_SEPARATOR);
                    sb.append("paginationDto.setTotalCount(pages.getTotal());").append(LINE_SEPARATOR);
                    sb.append(targetClazzParaName).append(".setPaginationDTO(paginationDto);").append(LINE_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    /**
     * request to domain statement
     */
    private String createRTDS(){
        String targetClazzName = targetClass.getName();
        String targetClazzParaName = StringUtil.doFirstCharLower(targetClazzName);
        return targetClazzName + " " + targetClazzParaName + " = new " +
                targetClazzName + "();" +
                LINE_SEPARATOR +
                "if(Objects.nonNull(request) && Objects.nonNull(request.getReqDtos())){";
    }

    /**
     * domain to domain statement
     */
    private String createDTDS() {
        String targetClazzName = targetClass.getName();
        String targetClazzParaName = StringUtil.doFirstCharLower(targetClazzName);
        return targetClazzName + " " + targetClazzParaName + " = new " + targetClazzName +
                "();";
    }

    private boolean isRequest(PsiClass psiClass){
        PsiReferenceList psiReferenceList = psiClass.getExtendsList();
        if(Objects.nonNull(psiReferenceList)){
            PsiClassType[] psiClassTypes= psiReferenceList.getReferencedTypes();
            if(psiClassTypes.length > 0){
                String requestClassName = "RequestDTO";
                for(PsiClassType psiClassType : psiClassTypes){
                    if(Objects.equals(psiClassType.getClassName(), requestClassName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isResponse(PsiClass psiClass){
        PsiReferenceList psiReferenceList = psiClass.getExtendsList();
        if(Objects.nonNull(psiReferenceList)){
            PsiClassType[] psiClassTypes= psiReferenceList.getReferencedTypes();
            if(psiClassTypes.length > 0){
                String responseClassName = "ResponseDTO";
                for(PsiClassType psiClassType : psiClassTypes){
                    if(Objects.equals(psiClassType.getClassName(), responseClassName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
