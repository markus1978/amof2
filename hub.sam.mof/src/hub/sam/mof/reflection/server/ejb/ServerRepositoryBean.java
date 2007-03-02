package hub.sam.mof.reflection.server.ejb;

import java.rmi.RemoteException;

import hub.sam.mof.reflection.server.impl.ReflectionFactory;
import hub.sam.mof.reflection.server.impl.ServerRepositoryImpl;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="ServerRepository"
 *           display-name="Name for ServerRepository"
 *           description="Description for ServerRepository"
 *           jndi-name="ejb/ServerRepository"
 *           type="Stateful"
 *           view-type="remote"
 */
public class ServerRepositoryBean extends ServerRepositoryImpl implements SessionBean {

	public void setSessionContext(SessionContext arg0)
		throws EJBException,
		RemoteException {
		// empty
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// empty	
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// empty
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// empty
	}

	@Override
	protected ReflectionFactory getFactory() {
		return EJBReflectionFactory.getEJBFactory();
	}
	
	private static hub.sam.mof.Repository repository = hub.sam.mof.Repository.getLocalRepository();
	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		super.create(repository);
	}
}
