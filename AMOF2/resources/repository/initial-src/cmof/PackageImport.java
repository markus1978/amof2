package cmof;


/**
 * <b>PackageImport</b>, superClass = {cmof.DirectedRelationship}
 * <br>constraint - public_or_private : 
 * <pre>self.visibility = #public or self.visibility = #private</pre>
 */
public interface PackageImport extends cmof.DirectedRelationship
{

    /**
     * <b>visibility</b>, multiplicity=(1,1)
     */
    public core.abstractions.visibilities.VisibilityKind getVisibility();

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value);

    /**
     * <b>importedPackage</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.target}
     */
    public cmof.Package getImportedPackage();

    public void setImportedPackage(cmof.Package value);

    /**
     * <b>importingNamespace</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.source, cmof.Element.owner}
     */
    public cmof.Namespace getImportingNamespace();

    public void setImportingNamespace(cmof.Namespace value);

}

