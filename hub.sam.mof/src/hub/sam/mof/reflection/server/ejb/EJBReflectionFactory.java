package hub.sam.mof.reflection.server.ejb;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import cmof.reflection.Extent;
import cmof.reflection.Factory;
import hub.sam.mof.reflection.server.impl.*;
import hub.sam.mof.util.AssertionException;
import hub.sam.util.Identity;

public class EJBReflectionFactory extends ReflectionFactory {
	
	private final static EJBReflectionFactory singleton = new EJBReflectionFactory(null);
	
	protected static ReflectionFactory getEJBFactory() {
		return singleton;
	}
	
	private final javax.naming.Context ctx;
	
	@SuppressWarnings("unchecked")
	protected static Context getInitialContext() throws NamingException {
		Hashtable props = new Hashtable();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		Context ctx = new InitialContext(props);
		return ctx;
	}
	
	private EJBReflectionFactory(javax.naming.Context ctx) {
		try {
			if (ctx == null) {
				this.ctx = getInitialContext();
			} else {
				this.ctx = ctx;
			}
		
			init();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private ServerRepositoryHome repositoryHome = null;
	private ServerExtentHome extentHome = null;
	private ServerFactoryHome factoryHome = null;
	private ServerObjectHome objectHome = null;
	private ServerReflectiveCollectionHome reflectiveCollectionHome = null;
	private ServerReflectiveSequenceHome reflectiveSequenceHome = null;
	
	private void init() throws NamingException {
		java.lang.Object o = null;
		
		o = ctx.lookup(ServerRepositoryHome.JNDI_NAME);
		repositoryHome = (ServerRepositoryHome) PortableRemoteObject.narrow(o, ServerRepositoryHome.class);
		
		o = ctx.lookup(ServerExtentHome.JNDI_NAME);
		extentHome = (ServerExtentHome) PortableRemoteObject.narrow(o, ServerExtentHome.class);
		
		o = ctx.lookup(ServerFactoryHome.JNDI_NAME);
		factoryHome = (ServerFactoryHome) PortableRemoteObject.narrow(o, ServerFactoryHome.class);
		
		o = ctx.lookup(ServerObjectHome.JNDI_NAME);
		objectHome = (ServerObjectHome) PortableRemoteObject.narrow(o, ServerObjectHome.class);
		
		o = ctx.lookup(ServerReflectiveCollectionHome.JNDI_NAME);
		reflectiveCollectionHome = (ServerReflectiveCollectionHome) PortableRemoteObject.narrow(o, ServerReflectiveCollectionHome.class);
		
		o = ctx.lookup(ServerReflectiveSequenceHome.JNDI_NAME);
		reflectiveSequenceHome = (ServerReflectiveSequenceHome) PortableRemoteObject.narrow(o, ServerReflectiveSequenceHome.class);

	}
	
	@Override
	public ServerExtent createExtent(Extent local) {
		try {		
			ServerExtent result = this.extentHome.findByPrimaryKey(((Identity)local).getFullId());
			if (result == null) {
				throw new AssertionException();
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public ServerFactory createFactory(Factory local) {
		try {
			ServerFactory result = this.factoryHome.findByPrimaryKey(((Identity)local).getFullId());
			if (result == null) {
				throw new AssertionException();
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public ServerObject createObject(cmof.reflection.Object local) {
		try {
			ServerObject result = this.objectHome.findByPrimaryKey(((Identity)local).getFullId());
			if (result == null) {
				throw new AssertionException();
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServerReflectiveCollection createReflectiveCollection(cmof.common.ReflectiveCollection local) {
		try {			
			ServerReflectiveCollection result = this.reflectiveCollectionHome.findByPrimaryKey(((Identity)local).getFullId());
			if (result == null) {
				throw new AssertionException();
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServerReflectiveSequence createReflectiveSequence(cmof.common.ReflectiveSequence local) {
		try {			
			ServerReflectiveSequence result = this.reflectiveSequenceHome.findByPrimaryKey(((Identity)local).getFullId());
			if (result == null) {
				throw new AssertionException();
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ServerRepository createRepository() {
		try {
			return this.repositoryHome.create();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
