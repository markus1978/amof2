package hub.sam.mof.reflection.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import java.util.*;

import hub.sam.mof.reflection.server.impl.*;


/**
 * @ejb.bean name="ServerExtent"
 *           display-name="Name for ServerExtent"
 *           description="Description for ServerExtent"
 *           jndi-name="ejb/ServerExtent"
 *           type="BMP"
 *           view-type="remote"
 *           primkey-field = "identifier"
 *           
 */
public class ServerExtentBean extends ServerExtentImpl implements javax.ejb.EntityBean {
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
		create((cmof.reflection.Extent)hub.sam.mof.Repository.getLocalRepository().resolveFullId(id));
	}
	

	public List<Object> ejbFindByPrimaryKey(List<Object> id) {
		if (hub.sam.mof.Repository.getLocalRepository().resolveFullId(id) != null) {
			return id;	
		} else {
			return null;
		}
	}
	
	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
	public List<Object> getLocalExtentId() {
		return ((hub.sam.util.Identity)localExtent).getFullId();
	}
	
	@Override
	protected ReflectionFactory getFactory() {
		return EJBReflectionFactory.getEJBFactory();
	}	
}
