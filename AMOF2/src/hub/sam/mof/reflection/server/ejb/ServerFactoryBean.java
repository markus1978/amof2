package hub.sam.mof.reflection.server.ejb;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import hub.sam.mof.reflection.server.impl.ReflectionFactory;
import hub.sam.mof.reflection.server.impl.ServerFactoryImpl;


/**
 * @ejb.bean name="ServerFactory"
 *           display-name="Name for ServerFactory"
 *           description="Description for ServerFactory"
 *           jndi-name="ejb/ServerFactory"
 *           type="BMP"
 *           view-type="remote"
 *           primkey-field = "identifier"
 */
public class ServerFactoryBean extends ServerFactoryImpl implements javax.ejb.EntityBean {
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
		create((cmof.reflection.Factory)hub.sam.mof.Repository.getLocalRepository().resolveFullId(id));
	}
	

	public List<Object> ejbFindByPrimaryKey(List<Object> id) {
		if (hub.sam.mof.Repository.getLocalRepository().resolveFullId(id) != null) {
			return id;	
		} else {
			return null;
		}
	}	
	
	@Override
	protected ReflectionFactory getFactory() {
		return EJBReflectionFactory.getEJBFactory();
	}
}
