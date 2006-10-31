package core.abstractions.changeabilities;


/**
 * <b>StructuralFeature</b>, isAbstract, superClass = {core.abstractions.structuralfeatures.StructuralFeature}
 */
public interface StructuralFeature extends core.abstractions.structuralfeatures.StructuralFeature
{

    /**
     * <b>isReadOnly</b>, multiplicity=(1,1)
     */
    public boolean isReadOnly();

    public void setIsReadOnly(boolean value);

}

