package hub.sam.mof.util;

import java.util.Collection;
import java.util.Iterator;

import cmof.common.ReflectiveCollection;

public class CollectionWrapper<E> implements Collection<E> {
    
    private final ReflectiveCollection<? extends E> reflectiveCollection;
    
    public CollectionWrapper(ReflectiveCollection<? extends E> reflectiveCollection) {
        this.reflectiveCollection = reflectiveCollection;
    }
    
    public boolean contains(Object obj) {
        return reflectiveCollection.contains(obj);
    }
    
    public boolean containsAll(Collection<?> c) {
        return reflectiveCollection.containsAll(c);
    }
    
    public boolean remove(Object obj) {
        return reflectiveCollection.remove(obj);
    }
    
    public boolean removeAll(Collection<?> c) {
        return reflectiveCollection.removeAll(c);
    }
    
    public boolean add(E obj) {
        return reflectiveCollection.add(obj);
    }
    
    public boolean addAll(Collection<? extends E> c) {
        return reflectiveCollection.addAll(c);
    }
    
    public Iterator<E> iterator() {
        return (Iterator<E>) reflectiveCollection.iterator();
    }
    
    public int size() {
        return reflectiveCollection.size();
    }
    
    public boolean isEmpty() {
        return reflectiveCollection.size() == 0;
    }
    
    public void clear() {
        reflectiveCollection.clear();
    }

    public boolean retainAll(Collection<?> collection) {
        // empty sets
        if (collection.isEmpty() || isEmpty()) return false;
        // equal sets
        if (collection.size() == size() && containsAll(collection)) return false;
        
        // in any other case we will modify the set
        for(Object obj: collection) {
            if (!contains(obj)) {
                remove(obj);
            }
        }
        return true;
    }
    
    public Object[] toArray() {
        Object[] objectArray = new Object[size()];
        
        int i=0;
        for(Object object: reflectiveCollection) {
            objectArray[i++] = object;
        }
        
        return objectArray;
    }
    
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) {
            a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        Iterator<E> it = iterator();
        Object[] result = a;
        for (int i=0; i < size; i++) {
            result[i] = it.next();
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }
    
}
