package dtcsm;

import com.intellij.psi.PsiFile;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/17 11:24
 */
public class ControllerCreateCondition {

    private String name;

    private String description;

    private String controllerClassName;

    private boolean hasAutowiredService;

    private String autowiredService;

    private boolean hasCreateMethod;

    private String createRequest;

    private boolean hasModifyMethod;

    private String modifyRequest;

    private boolean hasDeleteMethod;

    private String deleteRequest;

    private boolean hasChangeStatusMethod;

    private String changeStatusRequest;

    private boolean hasQueryMethod;

    private String queryRequest;

    private String queryResponse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public boolean isHasAutowiredService() {
        return hasAutowiredService;
    }

    public void setHasAutowiredService(boolean hasAutowiredService) {
        this.hasAutowiredService = hasAutowiredService;
    }

    public String getAutowiredService() {
        return autowiredService;
    }

    public void setAutowiredService(String autowiredService) {
        this.autowiredService = autowiredService;
    }

    public boolean isHasCreateMethod() {
        return hasCreateMethod;
    }

    public void setHasCreateMethod(boolean hasCreateMethod) {
        this.hasCreateMethod = hasCreateMethod;
    }

    public String getCreateRequest() {
        return createRequest;
    }

    public void setCreateRequest(String createRequest) {
        this.createRequest = createRequest;
    }

    public boolean isHasModifyMethod() {
        return hasModifyMethod;
    }

    public void setHasModifyMethod(boolean hasModifyMethod) {
        this.hasModifyMethod = hasModifyMethod;
    }

    public String getModifyRequest() {
        return modifyRequest;
    }

    public void setModifyRequest(String modifyRequest) {
        this.modifyRequest = modifyRequest;
    }

    public boolean isHasDeleteMethod() {
        return hasDeleteMethod;
    }

    public void setHasDeleteMethod(boolean hasDeleteMethod) {
        this.hasDeleteMethod = hasDeleteMethod;
    }

    public String getDeleteRequest() {
        return deleteRequest;
    }

    public void setDeleteRequest(String deleteRequest) {
        this.deleteRequest = deleteRequest;
    }

    public boolean isHasChangeStatusMethod() {
        return hasChangeStatusMethod;
    }

    public void setHasChangeStatusMethod(boolean hasChangeStatusMethod) {
        this.hasChangeStatusMethod = hasChangeStatusMethod;
    }

    public String getChangeStatusRequest() {
        return changeStatusRequest;
    }

    public void setChangeStatusRequest(String changeStatusRequest) {
        this.changeStatusRequest = changeStatusRequest;
    }

    public boolean isHasQueryMethod() {
        return hasQueryMethod;
    }

    public void setHasQueryMethod(boolean hasQueryMethod) {
        this.hasQueryMethod = hasQueryMethod;
    }

    public String getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(String queryRequest) {
        this.queryRequest = queryRequest;
    }

    public String getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(String queryResponse) {
        this.queryResponse = queryResponse;
    }
}
