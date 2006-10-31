package warehouse;


/**
 * <b>Element</b>, isAbstract
 */
public interface Element 
{

    /**
     * <b>identifier</b>, multiplicity=(1,1)
     */
    public java.lang.String getIdentifier();

    public void setIdentifier(java.lang.String value);

    /**
     * <b>position</b>, multiplicity=(1,1), isDerived
     */
    public java.lang.String getPosition();

    public void setPosition(java.lang.String value);

    /**
     * <b>container</b>, multiplicity=(0,1)
     */
    public warehouse.Container getContainer();

    public void setContainer(warehouse.Container value);

    /**
     * <b>test</b>, multiplicity=(1,1)
     */
    public void test() ;

}

