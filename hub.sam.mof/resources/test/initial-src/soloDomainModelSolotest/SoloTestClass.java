package soloDomainModelSolotest;


/**
 * <b>SoloTestClass</b>
 */
public interface SoloTestClass 
{

    /**
     * <b>Parameter</b>, multiplicity=(0,1), isReadOnly
     */
    public java.lang.String getParameter();

    /**
     * <b>primitiveValueTest</b>, multiplicity=(1,1)
     */
    public java.lang.String primitiveValueTest() ;

    /**
     * <b>parameter</b>, multiplicity=(1,1)
     */
    public void parameter(java.lang.String parameter) ;

    /**
     * <b>setParameter</b>, multiplicity=(1,1)
     */
    public void setParameter(java.lang.String parameter) ;

    /**
     * <b>objectValue</b>, multiplicity=(1,1)
     */
    public soloDomainModelSolotest.SoloTestClass objectValue(soloDomainModelSolotest.SoloTestClass identity) ;

}

