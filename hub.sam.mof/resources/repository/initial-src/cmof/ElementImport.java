package cmof;


/**
 * <b>ElementImport</b>, superClass = {cmof.DirectedRelationship}
 * <br>constraint - visibility_public_or_private : 
 * <pre>self.visibility = #public or self.visibility = #private</pre>
 * <br>constraint - imported_element_is_public : 
 * <pre>self.importedElement.visibility.notEmpty() implies self.importedElement.visibility = #public</pre>
 */
public interface ElementImport extends cmof.DirectedRelationship
{

    /**
     * <b>visibility</b>, multiplicity=(1,1)
     */
    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

    /**
     * <b>alias</b>, multiplicity=(0,1)
     */
    public java.lang.String getAlias();

    public void setAlias(java.lang.String value);

    /**
     * <b>importedElement</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.target}
     */
    public cmof.PackageableElement getImportedElement();

    public void setImportedElement(cmof.PackageableElement value);

    /**
     * <b>importingNamespace</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.source, cmof.Element.owner}
     */
    public cmof.Namespace getImportingNamespace();

    public void setImportingNamespace(cmof.Namespace value);

    /**
     * <b>getName</b>, multiplicity=(1,1)
     */
    public java.lang.String getName() ;

}

