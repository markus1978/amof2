package propertyNotificationTest;


/**
 * <b>Container</b>
 */
public interface Container 
{

    /**
     * <b>content</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends propertyNotificationTest.Element> getContent();

    /**
     * <b>orderedContent</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends propertyNotificationTest.Element> getOrderedContent();

}

