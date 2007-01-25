package hub.sam.mof.mas;


/**
 * <b>ConnectionElement</b>, isAbstract
 */
public interface ConnectionElement extends cmof.reflection.Object
{

    /**
     * <b>bendpoints</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<hub.sam.mase.m2model.ActivityEdgeBendpoint> getBendpoints();

}

