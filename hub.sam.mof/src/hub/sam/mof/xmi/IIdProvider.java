package hub.sam.mof.xmi;

/**
 * An IIdProvider provides an id for a given identity. This interface is used by
 * xmi writer. Clients can use implementations of this interface to determine
 * the used xmi:ids on xmi-export.
 */
public interface IIdProvider {
	
	public Object getId(Object id);
}
