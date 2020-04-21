package dtcsm;

import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.util.PsiUtil;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 12:36
 */
public class FileCreator {

    enum MethodEnum {
        QUERY("query"), CREATE("create"), MODIFY("modify"), DELETE("delete"), CHANGE_STATUS("changeStatus");

        private String code;

        MethodEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    private String domainClass;

    private String domainImport;

    public String getDomainImport() {
        return domainImport;
    }

    public void setDomainImport(String domainImport) {
        this.domainImport = domainImport.substring(domainImport.indexOf("/java/") + 6).replace("/", ".");
    }

    public String getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(String domainClass) {
        this.domainClass = domainClass;
    }

    private Map<PsiField, DomainToCSMJComponentRecord> map = new HashMap<>();

    private String packagePath;

    public FileCreator(Map<PsiField, DomainToCSMJComponentRecord> map, String packagePath) {
        this.map.putAll(map);
        this.packagePath = packagePath.substring(packagePath.indexOf("src") + 4).replace("/", ".");
    }

    private Set<MethodEnum> getMethod(){
        Set<MethodEnum> createMethods = new HashSet<>();
        Collection<DomainToCSMJComponentRecord> records = map.values();
        for(DomainToCSMJComponentRecord record : records){
            if(record.getCreate().isSelected()){
                createMethods.add(MethodEnum.CREATE);
            }
            if(record.getModify().isSelected()){
                createMethods.add(MethodEnum.MODIFY);
            }
            if(record.getDelete().isSelected()){
                createMethods.add(MethodEnum.DELETE);
            }
            if(record.getStatusChange().isSelected()){
                createMethods.add(MethodEnum.CHANGE_STATUS);
            }
            if(record.getQuery().isSelected()){
                createMethods.add(MethodEnum.QUERY);
            }
        }
        return createMethods;
    }

    private String createRequestName(MethodEnum methodEnum, String className){
        String extension = "Request";
        return StringUtil.doFirstCharUpper(className) +
                StringUtil.doFirstCharUpper(methodEnum.getCode()) + extension;
    }

    private String createRequestDTOName(MethodEnum methodEnum, String className){
        String extension = "DTO";
        return StringUtil.doFirstCharUpper(className) +
                StringUtil.doFirstCharUpper(methodEnum.getCode()) + extension;
    }

    private String createResponseName(MethodEnum methodEnum, String className){
        String extension = "Response";
        return StringUtil.doFirstCharUpper(className) +
                StringUtil.doFirstCharUpper(methodEnum.getCode()) + extension;
    }

    private String createResponseDTOName(MethodEnum methodEnum, String className){
        String extension = "ResDTO";
        return StringUtil.doFirstCharUpper(className) +
                StringUtil.doFirstCharUpper(methodEnum.getCode()) + extension;
    }

    private String createControllerName(String className){
        return className + "Controller";
    }

    private String createServiceName(String className){
        return className + "Service";
    }

    private String createMapperName(String className){
        return className + "Mapper";
    }

    private String createServiceImplName(String className){
        return className + "ServiceImpl";
    }

    private String createConverterName(String className){
        return className + "Converter";
    }

    private String createQueryConditionName(String className){
        return className + "QueryCondition";
    }

    public List<FileContentResult> getFileContent(String description, String className) {
        List<FileContentResult> fileContentResults = new ArrayList<>();
        fileContentResults.addAll(getRequestContent(description, className));
        fileContentResults.addAll(getResponseContent(description, className));
        fileContentResults.addAll(getDTOContent(description, className));
        fileContentResults.addAll(getControllerContent(description, className));
        fileContentResults.addAll(getConverterContent(description, className));
        fileContentResults.addAll(getServiceInterface(description, className));
        fileContentResults.addAll(getServiceInterfaceImpl(description, className));
        fileContentResults.addAll(getMapperInterface(description, className));
        return fileContentResults;
    }

    private List<FileContentResult> getMapperInterface(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            MapperCreateCondition mapperCreateCondition = new MapperCreateCondition();
            mapperCreateCondition.setPackagePath(packagePath);
            mapperCreateCondition.setServiceClassName(createMapperName(className));
            mapperCreateCondition.setDomainClassName(domainClass);
            mapperCreateCondition.setDomainImport(getDomainImport());
            mapperCreateCondition.setQueryConditionClassName(createQueryConditionName(className));
            FileContentResult fileContentResult = new FileContentResult();
            fileContentResult.setContent(MapperContentCreator.getContent(mapperCreateCondition));
            fileContentResult.setName(createMapperName(className));
            fileContentResults.add(fileContentResult);
            Messages.showMessageDialog("Mapper 生成" + fileContentResults.size()+ "个文件", "Mapper", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<FileContentResult> getServiceInterfaceImpl(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            ServiceImplCreateCondition serviceCreateCondition = new ServiceImplCreateCondition();
            serviceCreateCondition.setPackagePath(packagePath);
            serviceCreateCondition.setDomainImport(getDomainImport());
            serviceCreateCondition.setServiceImplClassName(createServiceImplName(className));
            serviceCreateCondition.setServiceClassName(createServiceName(className));
            serviceCreateCondition.setDomainClassName(domainClass);
            serviceCreateCondition.setQueryConditionClassName(createQueryConditionName(className));
            serviceCreateCondition.setMapperClassName(createMapperName(className));
            FileContentResult fileContentResult = new FileContentResult();
            fileContentResult.setContent(ServiceImplContentCreator.getContent(serviceCreateCondition));
            fileContentResult.setName(createServiceImplName(className));
            fileContentResults.add(fileContentResult);
            Messages.showMessageDialog("ServiceImpl 生成" + fileContentResults.size()+ "个文件", "ServiceImpl", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<FileContentResult> getServiceInterface(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            ServiceCreateCondition serviceCreateCondition = new ServiceCreateCondition();
            serviceCreateCondition.setPackagePath(packagePath);
            serviceCreateCondition.setServiceClassName(createServiceName(className));
            serviceCreateCondition.setDomainClassName(domainClass);
            serviceCreateCondition.setDomainImport(getDomainImport());
            serviceCreateCondition.setQueryConditionClassName(createQueryConditionName(className));
            FileContentResult fileContentResult = new FileContentResult();
            fileContentResult.setContent(ServiceContentCreator.getContent(serviceCreateCondition));
            fileContentResult.setName(createServiceName(className));
            fileContentResults.add(fileContentResult);
            Messages.showMessageDialog("Service 生成" + fileContentResults.size()+ "个文件", "Service", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<FileContentResult> getConverterContent(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            ConverterCreateCondition converterCreateCondition = new ConverterCreateCondition();
            converterCreateCondition.setPackagePath(packagePath);
            converterCreateCondition.setConverterClassName(createConverterName(className));
            converterCreateCondition.setDomainClassName(domainClass);
            converterCreateCondition.setDomainImport(getDomainImport());
            for(MethodEnum methodEnum : createMethods){
                if(Objects.equals(methodEnum, MethodEnum.CREATE)){
                    converterCreateCondition.setHasCreateConverter(true);
                    converterCreateCondition.setCreateRequestClassName(createRequestName(methodEnum, className));
                    converterCreateCondition.setCreateRequestDTOClassName(createRequestDTOName(methodEnum, className));
                    converterCreateCondition.setCreateFieldNames(getNeedFieldName(methodEnum));
                } else if(Objects.equals(methodEnum, MethodEnum.MODIFY)){
                    converterCreateCondition.setHasModifyConverter(true);
                    converterCreateCondition.setModifyRequestClassName(createRequestName(methodEnum, className));
                    converterCreateCondition.setModifyRequestDTOClassName(createRequestDTOName(methodEnum, className));
                    converterCreateCondition.setModifyFieldNames(getNeedFieldName(methodEnum));
                } else if(Objects.equals(methodEnum, MethodEnum.DELETE)){
                    converterCreateCondition.setHasDeleteConverter(true);
                    converterCreateCondition.setDeleteRequestClassName(createRequestName(methodEnum, className));
                    converterCreateCondition.setDeleteRequestDTOClassName(createRequestDTOName(methodEnum, className));
                    converterCreateCondition.setDeleteFieldNames(getNeedFieldName(methodEnum));
                } else if(Objects.equals(methodEnum, MethodEnum.CHANGE_STATUS)){
                    converterCreateCondition.setHasChangeStatusConverter(true);
                    converterCreateCondition.setChangeStatusRequestClassName(createRequestName(methodEnum, className));
                    converterCreateCondition.setChangeStatusRequestDTOClassName(createRequestDTOName(methodEnum, className));
                    converterCreateCondition.setModifyFieldNames(getNeedFieldName(methodEnum));
                } else if(Objects.equals(methodEnum, MethodEnum.QUERY)){
                    converterCreateCondition.setHasQueryConverter(true);
                    converterCreateCondition.setQueryFieldNames(getNeedFieldName(methodEnum));
                    converterCreateCondition.setQueryConditionClassName(createQueryConditionName(className));
                    converterCreateCondition.setChangeStatusFieldNames(getNeedFieldName(methodEnum));
                    converterCreateCondition.setQueryRequestClassName(createRequestName(methodEnum, className));
                    converterCreateCondition.setQueryRequestDTOClassName(createRequestDTOName(methodEnum, className));
                    converterCreateCondition.setQueryResponseClassName(createResponseName(methodEnum, className));
                    converterCreateCondition.setQueryResponseDTOClassName(createResponseDTOName(methodEnum, className));
                }
            }
            FileContentResult fileContentResult = new FileContentResult();
            fileContentResult.setContent(ConverterContentCreator.getContent(converterCreateCondition));
            fileContentResult.setName(createConverterName(className));
            fileContentResults.add(fileContentResult);
            Messages.showMessageDialog("Converter 生成" + fileContentResults.size()+ "个文件", "Converter", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<String> getNeedFieldName(MethodEnum methodEnum){
        List<String> fieldNames = new ArrayList<>();
        for(Map.Entry<PsiField, DomainToCSMJComponentRecord> entry : this.map.entrySet()){
            PsiField psiField = entry.getKey();
            DomainToCSMJComponentRecord record = entry.getValue();
            if((record.getCreate().isSelected() && Objects.equals(MethodEnum.CREATE, methodEnum)) ||
                    (record.getModify().isSelected() && Objects.equals(MethodEnum.MODIFY, methodEnum)) ||
                    (record.getDelete().isSelected() && Objects.equals(MethodEnum.DELETE, methodEnum)) ||
                    (record.getStatusChange().isSelected() && Objects.equals(MethodEnum.CHANGE_STATUS, methodEnum)) ||
                    (record.getQuery().isSelected() && Objects.equals(MethodEnum.QUERY, methodEnum))){
                fieldNames.add(psiField.getName());
            }
        }
        return fieldNames;
    }

    private List<FileContentResult> getControllerContent(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            ControllerCreateCondition controllerCreateCondition = new ControllerCreateCondition();
            controllerCreateCondition.setName(className);
            controllerCreateCondition.setPackagePath(packagePath);
            controllerCreateCondition.setDescription(description);
            controllerCreateCondition.setControllerClassName(createControllerName(className));
            controllerCreateCondition.setHasAutowiredService(true);
            controllerCreateCondition.setAutowiredService(createServiceName(className));
            controllerCreateCondition.setConverterName(createConverterName(className));
            controllerCreateCondition.setDomainName(domainClass);
            controllerCreateCondition.setDomainImport(domainImport);
            controllerCreateCondition.setQueryCondition(createQueryConditionName(className));
            for(MethodEnum methodEnum : createMethods){
                if(Objects.equals(methodEnum, MethodEnum.CREATE)){
                    controllerCreateCondition.setHasCreateMethod(true);
                    controllerCreateCondition.setCreateRequest(createRequestName(methodEnum, className));
                } else if(Objects.equals(methodEnum, MethodEnum.MODIFY)){
                    controllerCreateCondition.setHasModifyMethod(true);
                    controllerCreateCondition.setModifyRequest(createRequestName(methodEnum, className));
                } else if(Objects.equals(methodEnum, MethodEnum.DELETE)){
                    controllerCreateCondition.setHasDeleteMethod(true);
                    controllerCreateCondition.setDeleteRequest(createRequestName(methodEnum, className));
                } else if(Objects.equals(methodEnum, MethodEnum.CHANGE_STATUS)){
                    controllerCreateCondition.setHasChangeStatusMethod(true);
                    controllerCreateCondition.setChangeStatusRequest(createRequestName(methodEnum, className));
                } else if(Objects.equals(methodEnum, MethodEnum.QUERY)){
                    controllerCreateCondition.setHasQueryMethod(true);
                    controllerCreateCondition.setQueryRequest(createRequestName(methodEnum, className));
                    controllerCreateCondition.setQueryResponse(createResponseName(methodEnum, className));
                }
            }
            FileContentResult fileContentResult = new FileContentResult();
            fileContentResult.setContent(ControllerContentCreator.getContent(controllerCreateCondition));
            fileContentResult.setName(controllerCreateCondition.getControllerClassName());
            fileContentResults.add(fileContentResult);
            Messages.showMessageDialog("Controller 生成" + fileContentResults.size()+ "个文件", "Controller", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<FileContentResult> getDTOContent(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            for(MethodEnum methodEnum : createMethods){
                List<DTOFieldCreateCondition> dtoFieldCreateConditions = getDTOFieldCreateConditions(methodEnum);
                DTOCreateCondition createCondition = new DTOCreateCondition();
                createCondition.setDescription(description);
                createCondition.setPackagePath(packagePath);
                createCondition.setDtoClassName(createRequestDTOName(methodEnum, className));
                createCondition.setDtoFieldCreateConditions(dtoFieldCreateConditions);
                createCondition.setRequest(true);
                String requestName = createRequestDTOName(methodEnum, className);
                createCondition.setDtoClassName(requestName);
                FileContentResult fileContentResult = new FileContentResult();
                fileContentResult.setContent(DTOContentCreator.getContent(createCondition));
                fileContentResult.setName(requestName);
                fileContentResults.add(fileContentResult);
                if(Objects.equals(methodEnum, MethodEnum.QUERY)) {
                    DTOCreateCondition responseDTOCreateCondition = new DTOCreateCondition();
                    responseDTOCreateCondition.setDescription(description);
                    responseDTOCreateCondition.setPackagePath(packagePath);
                    responseDTOCreateCondition.setDtoFieldCreateConditions(dtoFieldCreateConditions);
                    responseDTOCreateCondition.setRequest(false);
                    String responseDTOName = createResponseDTOName(methodEnum, className);
                    responseDTOCreateCondition.setDtoClassName(responseDTOName);
                    FileContentResult responseDTOFileContentResult = new FileContentResult();
                    responseDTOFileContentResult.setContent(DTOContentCreator.getContent(responseDTOCreateCondition));
                    responseDTOFileContentResult.setName(responseDTOName);
                    fileContentResults.add(responseDTOFileContentResult);
                    QueryConditionCreateCondition queryConditionCreateCondition = new QueryConditionCreateCondition();
                    queryConditionCreateCondition.setPackagePath(packagePath);
                    queryConditionCreateCondition.setDtoFieldCreateConditions(dtoFieldCreateConditions);
                    queryConditionCreateCondition.setQueryConditionClassName(createQueryConditionName(className));
                    FileContentResult queryConditionFileContentResult = new FileContentResult();
                    queryConditionFileContentResult.setContent(QueryConditionContentCreator.getContent(queryConditionCreateCondition));
                    queryConditionFileContentResult.setName(createQueryConditionName(className));
                    fileContentResults.add(queryConditionFileContentResult);
                }
            }
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<DTOFieldCreateCondition> getDTOFieldCreateConditions(MethodEnum methodEnum) {
        List<DTOFieldCreateCondition> dtoFieldCreateConditions = new ArrayList<>();
        for(Map.Entry<PsiField, DomainToCSMJComponentRecord> entry : this.map.entrySet()){
            PsiField psiField = entry.getKey();
            DomainToCSMJComponentRecord record = entry.getValue();
            if((record.getCreate().isSelected() && Objects.equals(MethodEnum.CREATE, methodEnum)) ||
                    (record.getModify().isSelected() && Objects.equals(MethodEnum.MODIFY, methodEnum)) ||
                    (record.getDelete().isSelected() && Objects.equals(MethodEnum.DELETE, methodEnum)) ||
                    (record.getStatusChange().isSelected() && Objects.equals(MethodEnum.CHANGE_STATUS, methodEnum)) ||
                    (record.getQuery().isSelected() && Objects.equals(MethodEnum.QUERY, methodEnum))){
                DTOFieldCreateCondition createCondition = new DTOFieldCreateCondition();
                createCondition.setFieldName(psiField.getName());
                String description = record.getTextField().getText();
                createCondition.setFieldDescription((Objects.isNull(description) || description.length() <= 0) ? psiField.getName() : description);
                PsiClassType.ClassResolveResult classResolveResult = PsiUtil.resolveGenericsClassInType(psiField.getType());
                if(Objects.nonNull(classResolveResult.getElement())) {
                    Map<PsiTypeParameter, PsiType> map = classResolveResult.getSubstitutor().getSubstitutionMap();
                    if (map.size() > 0) {
                        for (Map.Entry<PsiTypeParameter, PsiType> typeEntry : map.entrySet()) {
                            PsiClass realReturnClass = PsiUtil.resolveGenericsClassInType(typeEntry.getValue()).getElement();
                            if(Objects.nonNull(realReturnClass)){
                                createCondition.setFieldType(realReturnClass.getName());
                                break;
                            }
                        }
                    }
                }
                dtoFieldCreateConditions.add(createCondition);
            }
        }
        return dtoFieldCreateConditions;
    }

    private List<FileContentResult> getResponseContent(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            for(MethodEnum methodEnum : createMethods){
                if(Objects.equals(methodEnum, MethodEnum.QUERY)){
                    ResponseCreateCondition createCondition = new ResponseCreateCondition();
                    createCondition.setDescription(description);
                    createCondition.setPackagePath(packagePath);
                    String requestName = createResponseName(methodEnum, className);
                    createCondition.setResponseClassName(requestName);
                    String requestDTOName = createResponseDTOName(methodEnum, className);
                    createCondition.setResponseDTOClassName(requestDTOName);
                    createCondition.setHasPaginationCode(true);
                    FileContentResult fileContentResult = new FileContentResult();
                    fileContentResult.setContent(ResponseContentCreator.getContent(createCondition));
                    fileContentResult.setName(requestName);
                    fileContentResults.add(fileContentResult);
                }
            }
            Messages.showMessageDialog("Response 生成" + fileContentResults.size()+ "个文件", "Response", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

    private List<FileContentResult> getRequestContent(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            for(MethodEnum methodEnum : createMethods){
                RequestCreateCondition createCondition = new RequestCreateCondition();
                createCondition.setDescription(description);
                createCondition.setPackagePath(packagePath);
                String requestName = createRequestName(methodEnum, className);
                createCondition.setRequestClassName(requestName);
                String requestDTOName = createRequestDTOName(methodEnum, className);
                createCondition.setRequestDTOClassName(requestDTOName);
                if(Objects.equals(methodEnum, MethodEnum.QUERY)){
                    createCondition.setHasPaginationCode(true);
                }
                FileContentResult fileContentResult = new FileContentResult();
                fileContentResult.setContent(RequestContentCreator.getContent(createCondition));
                fileContentResult.setName(requestName);
                fileContentResults.add(fileContentResult);
            }
            Messages.showMessageDialog("Request 生成" + fileContentResults.size()+ "个文件", "Request", null);
            return fileContentResults;
        }
        return new ArrayList<>();
    }

}
