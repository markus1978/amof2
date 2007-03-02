package hub.sam.mof.util;

import cmof.Package;
import cmof.UmlClass;
import cmof.reflection.Extent;
import cmof.reflection.Factory;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.util.AbstractFluxBox;

import java.util.HashMap;
import java.util.Map;

/**
 * A special FluxBox that copies transformed elements from a source extent to a target extent.
 */
public class MofCopyFluxBox extends AbstractFluxBox<cmof.reflection.Object,cmof.reflection.Object,Object> {

    private final Extent target;
    private final Map<Package, Factory> factories = new HashMap<Package, Factory>();
    private final Repository repository;

    public MofCopyFluxBox(Repository repository, Extent source, Extent target) {
        this.repository = repository;
        this.target = target;
    }

    private Factory getFactory(cmof.Package forPackage) {
        Factory result = factories.get(forPackage);
        if (result == null) {
            result = repository.createFactory(target, forPackage);
            factories.put(forPackage, result);
        }
        return result;
    }

    /**
     * This is a callback implementation. It creates the copies. The copies are based on key. Params is ignored.
     * @param key
     *        The object to copy. Must lay in the source extent. Must be a {@link ObjectImpl}.
     * @param params
     *        Is ignored.
     * @return
     *        The copy of key. Lies in the target extent. Has all the values that key has.
     */
    @Override
    protected cmof.reflection.Object createValue(cmof.reflection.Object key, Object params) {
        UmlClass metaClass = key.getMetaClass();
        cmof.Package metaPackage = metaClass.getPackage();
        cmof.reflection.Object result = getFactory(metaPackage).create(metaClass);
        try {
            ((ObjectImpl)key).copyAllValues((ObjectImpl)result, this);
        } catch (MetaModelException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
