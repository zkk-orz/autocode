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

    public String createStatement(){
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
                            sb.append(classResolveResult.getElement().getName());
                            sb.append(classResolveResult.getCurrentFileResolveScope().getText());
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
                    sb.append("paginationDto.setPageNumber(page.getPageNum());").append(LINE_SEPARATOR);
                    sb.append("paginationDto.setPageSize(page.getPageSize());").append(LINE_SEPARATOR);
                    sb.append("paginationDto.setTotalCount(page.getTotal());").append(LINE_SEPARATOR);
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
