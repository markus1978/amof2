package core.basic;


/**
 * <b>Parameter</b>, superClass = {core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement}
 */
public interface Parameter extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    /**
     * <b>operation</b>, multiplicity=(0,1)
     */
    public core.basic.Operation getOperation();

    public void setOperation(core.basic.Operation value);

}

