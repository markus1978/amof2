package hub.sam.mof.mas;


/**
 * <b>GraphicalElement</b>, isAbstract
 */
public interface GraphicalElement extends cmof.reflection.Object
{

    /**
     * <b>rectangle</b>, multiplicity=(1,1)
     */
    public org.eclipse.draw2d.geometry.Rectangle getRectangle();

    public void setRectangle(org.eclipse.draw2d.geometry.Rectangle value);

}

