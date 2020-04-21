package dtcsm;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/20 13:10
 */
public class ServiceImplCreateCondition {

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
     * service类名
     */
    private String serviceClassName;

    /**
     * mapper类名
     */
    private String mapperClassName;

    /**
     * service实现类名
     */
    private String serviceImplClassName;

    /**
     * domain类名称
     */
    private String domainClassName;

    /**
     * 查询条件类名称
     */
    private String queryConditionClassName;

    public String getMapperClassName() {
        return mapperClassName;
    }

    public void setMapperClassName(String mapperClassName) {
        this.mapperClassName = mapperClassName;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getQueryConditionClassName() {
        return queryConditionClassName;
    }

    public void setQueryConditionClassName(String queryConditionClassName) {
        this.queryConditionClassName = queryConditionClassName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getServiceImplClassName() {
        return serviceImplClassName;
    }

    public void setServiceImplClassName(String serviceImplClassName) {
        this.serviceImplClassName = serviceImplClassName;
    }

    public String getDomainClassName() {
        return domainClassName;
    }

    public void setDomainClassName(String domainClassName) {
        this.domainClassName = domainClassName;
    }
}
