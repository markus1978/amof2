package warehouse;


/**
 * <b>Element</b>, isAbstract
 */
public interface Element extends cmof.reflection.Object
{

    /**
     * <b>identifier</b>, multiplicity=(1,1)
     */
    public java.lang.String getIdentifier();

    public void setIdentifier(java.lang.String value);

    /**
     * <b>foo</b>, multiplicity=(1,1)
     */
    public java.lang.String getFoo();

    public void setFoo(java.lang.String value);

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

    /**
     * <b>foo</b>, multiplicity=(1,1)
     */
    public void fooOperation() ;

}

