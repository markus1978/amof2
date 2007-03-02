package hub.sam.mof.reflection.server.ejb;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import hub.sam.mof.reflection.server.impl.ReflectionFactory;
import hub.sam.mof.reflection.server.impl.ServerObjectImpl;
import hub.sam.util.Identity;


/**
 * @ejb.bean name="ServerObject"
 *           display-name="Name for ServerObject"
 *           description="Description for ServerObject"
 *           jndi-name="ejb/ServerObject"
 *           type="BMP"
 *           view-type="remote"
 *           primkey-field = "identifier"
 */
public class ServerObjectBean extends ServerObjectImpl implements javax.ejb.EntityBean {
	private EntityContext context = null;
	private List<Object> id = null;
	
	public List<Object> getIdentifier() {
		return id;
	}
	
	public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
		context = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		context = null;		
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// empty	
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// empty
		
	}

	public void ejbStore() throws EJBException, RemoteException {
		// empty
		
	}
	
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		// empty		
	}
	
		
	@SuppressWarnings("unchecked")
	public void ejbLoad() throws EJBException, RemoteException {
		id = (List<Object>)context.getPrimaryKey();
		create((cmof.reflection.Object)hub.sam.mof.Repository.getLocalRepository().resolveFullId(id));
	}
	

	public List<Object> ejbFindByPrimaryKey(List<Object> id) {
		if (hub.sam.mof.Repository.getLocalRepository().resolveFullId(id) != null) {
			return id;	
		} else {
			return null;
		}
	}
	

	@Override
	public boolean ejbEquals(Object element) throws RemoteException {
		if (element instanceof hub.sam.mof.reflection.server.ejb.ServerObject) {
			return ((Identity)localObject).getFullId().equals(((hub.sam.mof.reflection.server.ejb.ServerObject)element).getPrimaryKey());
		} else {
			return false;
		}
	}
	
	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
	public List<Object> getLocalObjectId() {
		return ((hub.sam.util.Identity)localObject).getFullId();
	}
		
	@Override
	protected ReflectionFactory getFactory() {
		return EJBReflectionFactory.getEJBFactory();
	}
}
