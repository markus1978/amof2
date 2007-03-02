package core.abstractions.visibilities;


/**
 * <b>NamedElement</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - visibility_needs_ownership : 
 * <pre>namespace->isEmpty() implies visibility->isEmpty()</pre>
 */
public interface NamedElement extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>visibility</b>, multiplicity=(0,1)
     */
    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

}

