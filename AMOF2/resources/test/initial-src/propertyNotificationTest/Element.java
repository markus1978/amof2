package propertyNotificationTest;


/**
 * <b>Element</b>
 */
public interface Element 
{

    /**
     * <b>identifier</b>, multiplicity=(1,1)
     */
    public java.lang.String getIdentifier();

    public void setIdentifier(java.lang.String value);

    /**
     * <b>container</b>, multiplicity=(0,1)
     */
    public propertyNotificationTest.Container getContainer();

    public void setContainer(propertyNotificationTest.Container value);

    /**
     * <b>orderedContainer</b>, multiplicity=(0,1)
     */
    public propertyNotificationTest.Container getOrderedContainer();

    public void setOrderedContainer(propertyNotificationTest.Container value);

}

