package hub.sam.mof.reflection.client.impl;

import java.util.*;

import cmof.common.ReflectiveCollection;

public class ClientReflectiveCollectionCollectionImpl<E> extends AbstractBridge
		implements ReflectiveCollection<E> {

	private final Collection remote;
	
	public ClientReflectiveCollectionCollectionImpl(Collection remote) {
		this.remote = remote;
	}
	
	public boolean add(Object element) {
		throw new RuntimeException("Collection is readonly.");
	}

	public boolean contains(Object element) {
		return remote.contains(serverizeLocalValue(element));
	}

	public boolean remove(Object element) {
		throw new RuntimeException("Collection is readonly.");
	}

	public boolean addAll(Iterable elements) {
		throw new RuntimeException("Collection is readonly.");
	}

	public boolean containsAll(Iterable elements) {
		for (Object o: elements) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	public boolean removeAll(Iterable elements) {
		throw new RuntimeException("Collection is readonly.");
	}

	public int size() {
		return remote.size();
	}

	@SuppressWarnings("unchecked")
	public Iterator iterator() {
		return new MyIterator(remote.iterator());
	}
	
	class MyIterator implements Iterator {

		private final Iterator remote;
		
		public MyIterator(Iterator remote) {
			this.remote = remote;
		}
		
		public boolean hasNext() {
			return remote.hasNext();
		}

		public Object next() {
			return deserverizeRemoteValue(remote.next());
		}

		public void remove() {
			throw new RuntimeException("Collection is readonly.");			
		}
		
	}

	public void clear() {
		remote.clear();
	}
}
