package cmof;


/**
 * <b>Parameter</b>, superClass = {core.basic.Parameter, core.abstractions.behavioralfeatures.Parameter, cmof.TypedElement, cmof.MultiplicityElement}
 */
public interface Parameter extends core.basic.Parameter, core.abstractions.behavioralfeatures.Parameter, cmof.TypedElement, cmof.MultiplicityElement
{

    /**
     * <b>direction</b>, multiplicity=(1,1)
     */
    public cmof.ParameterDirectionKind getDirection();

    public void setDirection(cmof.ParameterDirectionKind value);

    /**
     * <b>default</b>, multiplicity=(0,1)
     */
    public java.lang.String getDefault();

    public void setDefault(java.lang.String value);

    /**
     * <b>operation</b>, multiplicity=(0,1), subsettedProperty = {cmof.null.ownerFormalParam}, redefinedProperty = {core.basic.Parameter.operation}
     */
    public cmof.Operation getOperation();

    public void setOperation(cmof.Operation value);

    public void setOperation(core.basic.Operation value);

}

