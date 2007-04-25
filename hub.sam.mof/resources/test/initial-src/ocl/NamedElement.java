package ocl;


/**
 * <b>NamedElement</b>
 * <br>constraint - qualifiedName : 
 * <pre>if namespace.isEmtpy() then
    name
else 
   namespace.qualifiedName + "::" + name
endif</pre>
 */
public interface NamedElement extends cmof.reflection.Object
{

    /**
     * <b>qualifiedName</b>, multiplicity=(1,1), isDerived
     */
    public java.lang.String getQualifiedName();

    public void setQualifiedName(java.lang.String value);

    /**
     * <b>name</b>, multiplicity=(1,1)
     */
    public java.lang.String getName();

    public void setName(java.lang.String value);

    /**
     * <b>namespace</b>, multiplicity=(0,1)
     */
    public ocl.Namespace getNamespace();

    public void setNamespace(ocl.Namespace value);

    /**
     * <b>calculateFullName</b>, multiplicity=(1,1), isQuery
     */
    public java.lang.String calculateFullName() ;

    /**
     * <b>calculateFullName</b>, multiplicity=(1,1), isQuery
     */
    public java.lang.String calculateFullName(java.lang.String separator) ;

}

