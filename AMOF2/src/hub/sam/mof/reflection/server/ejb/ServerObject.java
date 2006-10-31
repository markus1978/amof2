package hub.sam.mof.reflection.server.ejb;

import javax.ejb.EJBObject;

public interface ServerObject extends EJBObject,
		hub.sam.mof.reflection.server.ServerObject {

	public java.util.List<Object> getLocalObjectId() throws java.rmi.RemoteException;
	
}
