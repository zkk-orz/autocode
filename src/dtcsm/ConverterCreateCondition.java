package dtcsm;

import java.util.List;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/20 13:10
 */
public class ConverterCreateCondition {

    private String domainImport;

    public String getDomainImport() {
        return domainImport;
    }

    public void setDomainImport(String domainImport) {
        this.domainImport = domainImport;
    }

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * converter类名
     */
    private String converterClassName;

    /**
     * 是否存在创建转换
     */
    private boolean hasCreateConverter;

    /**
     * domain类名称
     */
    private String domainClassName;

    /**
     * 创建字段名称列表
     */
    private List<String> createFieldNames;

    /**
     * create request 类名称
     */
    private String createRequestClassName;

    /**
     * create request dto 类名称
     */
    private String createRequestDTOClassName;

    /**
     * 是否存在修改转换
     */
    private boolean hasModifyConverter;

    /**
     * 修改字段名称列表
     */
    private List<String> modifyFieldNames;

    /**
     * create request 类名称
     */
    private String modifyRequestClassName;

    /**
     * create request dto 类名称
     */
    private String modifyRequestDTOClassName;

    /**
     * 是否存在删除转换
     */
    private boolean hasDeleteConverter;

    /**
     * 删除字段名称列表
     */
    private List<String> deleteFieldNames;

    /**
     * delete request 类名称
     */
    private String deleteRequestClassName;

    /**
     * delete request dto 类名称
     */
    private String deleteRequestDTOClassName;

    /**
     * 是否存在修改状态转换
     */
    private boolean hasChangeStatusConverter;

    /**
     * 修改状态字段名称列表
     */
    private List<String> changeStatusFieldNames;

    /**
     * change status request 类名称
     */
    private String changeStatusRequestClassName;

    /**
     * change status request dto 类名称
     */
    private String changeStatusRequestDTOClassName;

    /**
     * 是否存在查询转换
     */
    private boolean hasQueryConverter;

    /**
     * 查询字段名称列表
     */
    private List<String> queryFieldNames;

    /**
     * query condition 类名称
     */
    private String queryConditionClassName;

    /**
     * query request 类名称
     */
    private String queryRequestClassName;

    /**
     * query request dto 类名称
     */
    private String queryRequestDTOClassName;

    /**
     * query response 类名称
     */
    private String queryResponseClassName;

    /**
     * query response dto 类名称
     */
    private String queryResponseDTOClassName;

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getConverterClassName() {
        return converterClassName;
    }

    public void setConverterClassName(String converterClassName) {
        this.converterClassName = converterClassName;
    }

    public boolean isHasCreateConverter() {
        return hasCreateConverter;
    }

    public void setHasCreateConverter(boolean hasCreateConverter) {
        this.hasCreateConverter = hasCreateConverter;
    }

    public String getDomainClassName() {
        return domainClassName;
    }

    public void setDomainClassName(String domainClassName) {
        this.domainClassName = domainClassName;
    }

    public List<String> getCreateFieldNames() {
        return createFieldNames;
    }

    public void setCreateFieldNames(List<String> createFieldNames) {
        this.createFieldNames = createFieldNames;
    }

    public String getCreateRequestClassName() {
        return createRequestClassName;
    }

    public void setCreateRequestClassName(String createRequestClassName) {
        this.createRequestClassName = createRequestClassName;
    }

    public String getCreateRequestDTOClassName() {
        return createRequestDTOClassName;
    }

    public void setCreateRequestDTOClassName(String createRequestDTOClassName) {
        this.createRequestDTOClassName = createRequestDTOClassName;
    }

    public boolean isHasModifyConverter() {
        return hasModifyConverter;
    }

    public void setHasModifyConverter(boolean hasModifyConverter) {
        this.hasModifyConverter = hasModifyConverter;
    }

    public List<String> getModifyFieldNames() {
        return modifyFieldNames;
    }

    public void setModifyFieldNames(List<String> modifyFieldNames) {
        this.modifyFieldNames = modifyFieldNames;
    }

    public String getModifyRequestClassName() {
        return modifyRequestClassName;
    }

    public void setModifyRequestClassName(String modifyRequestClassName) {
        this.modifyRequestClassName = modifyRequestClassName;
    }

    public String getModifyRequestDTOClassName() {
        return modifyRequestDTOClassName;
    }

    public void setModifyRequestDTOClassName(String modifyRequestDTOClassName) {
        this.modifyRequestDTOClassName = modifyRequestDTOClassName;
    }

    public boolean isHasDeleteConverter() {
        return hasDeleteConverter;
    }

    public void setHasDeleteConverter(boolean hasDeleteConverter) {
        this.hasDeleteConverter = hasDeleteConverter;
    }

    public List<String> getDeleteFieldNames() {
        return deleteFieldNames;
    }

    public void setDeleteFieldNames(List<String> deleteFieldNames) {
        this.deleteFieldNames = deleteFieldNames;
    }

    public String getDeleteRequestClassName() {
        return deleteRequestClassName;
    }

    public void setDeleteRequestClassName(String deleteRequestClassName) {
        this.deleteRequestClassName = deleteRequestClassName;
    }

    public String getDeleteRequestDTOClassName() {
        return deleteRequestDTOClassName;
    }

    public void setDeleteRequestDTOClassName(String deleteRequestDTOClassName) {
        this.deleteRequestDTOClassName = deleteRequestDTOClassName;
    }

    public boolean isHasChangeStatusConverter() {
        return hasChangeStatusConverter;
    }

    public void setHasChangeStatusConverter(boolean hasChangeStatusConverter) {
        this.hasChangeStatusConverter = hasChangeStatusConverter;
    }

    public List<String> getChangeStatusFieldNames() {
        return changeStatusFieldNames;
    }

    public void setChangeStatusFieldNames(List<String> changeStatusFieldNames) {
        this.changeStatusFieldNames = changeStatusFieldNames;
    }

    public String getChangeStatusRequestClassName() {
        return changeStatusRequestClassName;
    }

    public void setChangeStatusRequestClassName(String changeStatusRequestClassName) {
        this.changeStatusRequestClassName = changeStatusRequestClassName;
    }

    public String getChangeStatusRequestDTOClassName() {
        return changeStatusRequestDTOClassName;
    }

    public void setChangeStatusRequestDTOClassName(String changeStatusRequestDTOClassName) {
        this.changeStatusRequestDTOClassName = changeStatusRequestDTOClassName;
    }

    public boolean isHasQueryConverter() {
        return hasQueryConverter;
    }

    public void setHasQueryConverter(boolean hasQueryConverter) {
        this.hasQueryConverter = hasQueryConverter;
    }

    public List<String> getQueryFieldNames() {
        return queryFieldNames;
    }

    public void setQueryFieldNames(List<String> queryFieldNames) {
        this.queryFieldNames = queryFieldNames;
    }

    public String getQueryConditionClassName() {
        return queryConditionClassName;
    }

    public void setQueryConditionClassName(String queryConditionClassName) {
        this.queryConditionClassName = queryConditionClassName;
    }

    public String getQueryRequestClassName() {
        return queryRequestClassName;
    }

    public void setQueryRequestClassName(String queryRequestClassName) {
        this.queryRequestClassName = queryRequestClassName;
    }

    public String getQueryRequestDTOClassName() {
        return queryRequestDTOClassName;
    }

    public void setQueryRequestDTOClassName(String queryRequestDTOClassName) {
        this.queryRequestDTOClassName = queryRequestDTOClassName;
    }

    public String getQueryResponseClassName() {
        return queryResponseClassName;
    }

    public void setQueryResponseClassName(String queryResponseClassName) {
        this.queryResponseClassName = queryResponseClassName;
    }

    public String getQueryResponseDTOClassName() {
        return queryResponseDTOClassName;
    }

    public void setQueryResponseDTOClassName(String queryResponseDTOClassName) {
        this.queryResponseDTOClassName = queryResponseDTOClassName;
    }
}
