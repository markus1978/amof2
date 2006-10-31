package hub.sam.mof.reflection.server.impl;

import hub.sam.mof.reflection.server.*;
import cmof.reflection.*;

public class ReflectionFactory {
	
	private static final ReflectionFactory singleton = new ReflectionFactory();
	
	public static ReflectionFactory getFactory() {
		return singleton;
	}
	
	protected ReflectionFactory() {
		// emtpy
	}
	
	public ServerExtent createExtent(Extent local) {
		ServerExtentImpl result = new ServerExtentImpl();
		result.create(local);
		return result;
	}
	
	public ServerFactory createFactory(Factory local) {
		ServerFactoryImpl result = new ServerFactoryImpl();
		result.create(local);
		return result;
	}
	
	public ServerObject createObject(cmof.reflection.Object local) {
		ServerObjectImpl result = new ServerObjectImpl();
		result.create(local);
		return result;
	}

	@SuppressWarnings("unchecked")
	public ServerReflectiveCollection createReflectiveCollection(cmof.common.ReflectiveCollection local) {
		ServerReflectiveCollectionImpl result = new ServerReflectiveCollectionImpl();
		result.create(local);
		return result;
	}

	@SuppressWarnings("unchecked")
	public ServerReflectiveSequence createReflectiveSequence(cmof.common.ReflectiveSequence local) {
		ServerReflectiveSequenceImpl result = new ServerReflectiveSequenceImpl();
		result.create(local);
		return result;
	}

	public ServerRepository createRepository() {
		ServerRepositoryImpl result = new ServerRepositoryImpl();
		result.create(hub.sam.mof.Repository.getLocalRepository());
		return result;
	}

}
