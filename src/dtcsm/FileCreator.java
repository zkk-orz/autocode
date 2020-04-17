package dtcsm;

import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiField;
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

    private Map<PsiField, DomainToCSMJComponentRecord> map = new HashMap<>();

    public FileCreator(Map<PsiField, DomainToCSMJComponentRecord> map) {
        this.map.putAll(map);;
    }

    private Set<MethodEnum> getMethod(){
        Set<MethodEnum> createMethods = new HashSet<>();
        Collection<DomainToCSMJComponentRecord> records = map.values();
        for(DomainToCSMJComponentRecord record : records){
            if(record.getCreate().isSelected()){
                Messages.showMessageDialog(MethodEnum.CREATE.getCode(), MethodEnum.CREATE.name(), null);
                createMethods.add(MethodEnum.CREATE);
            }
            if(record.getModify().isSelected()){
                Messages.showMessageDialog(MethodEnum.MODIFY.getCode(), MethodEnum.MODIFY.name(), null);
                createMethods.add(MethodEnum.MODIFY);
            }
            if(record.getDelete().isSelected()){
                Messages.showMessageDialog(MethodEnum.DELETE.getCode(), MethodEnum.DELETE.name(), null);
                createMethods.add(MethodEnum.DELETE);
            }
            if(record.getStatusChange().isSelected()){
                Messages.showMessageDialog(MethodEnum.CHANGE_STATUS.getCode(), MethodEnum.CHANGE_STATUS.name(), null);
                createMethods.add(MethodEnum.CHANGE_STATUS);
            }
            if(record.getQuery().isSelected()){
                Messages.showMessageDialog(MethodEnum.QUERY.getCode(), MethodEnum.QUERY.name(), null);
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

    public List<FileContentResult> getRequestContent(String description, String className) {
        Set<MethodEnum> createMethods = getMethod();
        if(createMethods.size() > 0){
            List<FileContentResult> fileContentResults = new ArrayList<>();
            for(MethodEnum methodEnum : createMethods){
                RequestCreateCondition createCondition = new RequestCreateCondition();
                createCondition.setDescription(description);
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
                Messages.showMessageDialog(requestName, methodEnum.name(), null);
                fileContentResults.add(fileContentResult);
            }
            return fileContentResults;
        }
        return new ArrayList<>();
    }

}
