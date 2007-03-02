package core.basic;


/**
 * <b>Property</b>, superClass = {core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement}
 */
public interface Property extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    /**
     * <b>isReadOnly</b>, multiplicity=(1,1)
     */
    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

    /**
     * <b>default</b>, multiplicity=(0,1)
     */
    public java.lang.String getDefault();

    public void setDefault(java.lang.String value);

    /**
     * <b>isComposite</b>, multiplicity=(1,1)
     */
    public boolean isComposite();

    public void setIsComposite(boolean value);

    /**
     * <b>isDerived</b>, multiplicity=(1,1)
     */
    public boolean isDerived();

    public void setIsDerived(boolean value);

    /**
     * <b>class</b>, multiplicity=(0,1)
     */
    public core.basic.UmlClass getUmlClass();

    public void setUmlClass(core.basic.UmlClass value);

    /**
     * <b>opposite</b>, multiplicity=(0,1)
     */
    public core.basic.Property getOpposite();

    public void setOpposite(core.basic.Property value);

}

