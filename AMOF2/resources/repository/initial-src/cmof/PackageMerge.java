package cmof;


/**
 * <b>PackageMerge</b>, superClass = {cmof.DirectedRelationship}
 */
public interface PackageMerge extends cmof.DirectedRelationship
{

    /**
     * <b>mergingPackage</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.source, cmof.Element.owner}
     */
    public cmof.Package getMergingPackage();

    public void setMergingPackage(cmof.Package value);

    /**
     * <b>mergedPackage</b>, multiplicity=(1,1), subsettedProperty = {cmof.DirectedRelationship.target}
     */
    public cmof.Package getMergedPackage();

    public void setMergedPackage(cmof.Package value);

}

