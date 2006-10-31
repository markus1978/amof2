package cmof;


/**
 * <b>Type</b>, isAbstract, superClass = {core.basic.Type, core.abstractions.typedelements.Type, cmof.NamedElement, cmof.PackageableElement}
 */
public interface Type extends core.basic.Type, core.abstractions.typedelements.Type, cmof.NamedElement, cmof.PackageableElement
{

    /**
     * <b>package</b>, multiplicity=(0,1), subsettedProperty = {cmof.NamedElement.namespace}, redefinedProperty = {core.basic.Type.package}
     */
    public cmof.Package getPackage();

    public void setPackage(cmof.Package value);

    public void setPackage(core.basic.Package value);

}

