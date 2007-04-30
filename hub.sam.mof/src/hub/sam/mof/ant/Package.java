package hub.sam.mof.ant;

import org.apache.tools.ant.types.DataType;

public class Package extends DataType {
    private String name = null;
    private String nsPrefix = null;
    private String javaPackagePrefix = null;
    
    public String getNsPrefix() {
        return nsPrefix;
    }

    public void setNsPrefix(String nsPrefix) {
        this.nsPrefix = nsPrefix;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getJavaPackagePrefix() {
        return javaPackagePrefix;
    }
    
    public void setJavaPackagePrefix(String prefix) {
        this.javaPackagePrefix = prefix;
    }
}
