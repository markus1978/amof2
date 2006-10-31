package core.basic;


/**
 * <b>Type</b>, isAbstract, superClass = {core.basic.NamedElement}
 */
public interface Type extends core.basic.NamedElement
{

    /**
     * <b>package</b>, multiplicity=(0,1)
     */
    public core.basic.Package getPackage();

    public void setPackage(core.basic.Package value);

}

