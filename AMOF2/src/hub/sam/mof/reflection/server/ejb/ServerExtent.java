package hub.sam.mof.reflection.server.ejb;

public interface ServerExtent extends javax.ejb.EJBObject, hub.sam.mof.reflection.server.ServerExtent {

	public java.util.List<Object> getLocalExtentId() throws java.rmi.RemoteException;
	
}
