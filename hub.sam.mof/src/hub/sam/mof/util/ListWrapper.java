package hub.sam.mof.util;

import cmof.common.ReflectiveSequence;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;

public class ListWrapper<E> extends CollectionWrapper<E> implements List<E> {
    
    private final ReflectiveSequence<? extends E> reflectiveSequence;

    public ListWrapper(ReflectiveSequence<? extends E> reflectiveSequence) {
        super(reflectiveSequence);
        this.reflectiveSequence = reflectiveSequence;
    }
    
    public void add(int index, E element) {
        reflectiveSequence.add(index, element);
    }
    
    public boolean addAll(int index, Collection<? extends E> elements) {
        return reflectiveSequence.addAll(index, elements);
    }
    
    public E get(int index) {
        return reflectiveSequence.get(index);
    }
    
    public E set(int index, E element) {
        return reflectiveSequence.set(index, element);
    }
    
    public E remove(int index) {
        return reflectiveSequence.remove(index);
    }
    
    public List<E> subList(int fromIndex, int toIndex) {
        return new ListWrapper<E>(reflectiveSequence.subList(fromIndex, toIndex));
    }
    
    public int indexOf(Object specificObject) {
        int i=0;
        for(Object o: reflectiveSequence) {
            if (o.equals(specificObject)) return i;
            i++;
        }
        return -1;
    }

    public int lastIndexOf(Object specificObject) {
        int i=0;
        int last=-1;
        for(Object o: reflectiveSequence) {
            if (o.equals(specificObject)) last=i;
            i++;
        }
        return last;
    }
    
    private class Itr implements Iterator<E> {
        protected int cursor = 0;
        protected int lastRet = -1;
        
        public boolean hasNext() {
            return cursor != size();
        }
        
        public E next() {
            E next = get(cursor);
            lastRet = cursor++;
            return next;
        }
        
        public void remove() {
            if (lastRet == -1)
                throw new IllegalStateException();

            ListWrapper.this.remove(lastRet);
            if (lastRet < cursor)
                cursor--;
            lastRet = -1;
        }
    }
    
    private class ListItr extends Itr implements ListIterator<E> {
        
        public ListItr(int index) {
            cursor = index;
        }
        
        public boolean hasPrevious() {
            return cursor != 0;
        }        
        
        public E previous() {
            int i = cursor - 1;
            E previous = get(i);
            lastRet = cursor = i;
            return previous;
        }
        
        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(E o) {
            if (lastRet == -1)
            throw new IllegalStateException();

            ListWrapper.this.set(lastRet, o);
        }
        
        public void add(E o) {
            ListWrapper.this.add(cursor++, o);
            lastRet = -1;
        }
        
    }
    
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }
    
    public ListIterator<E> listIterator(int index) {
        if (index<0 || index>size()) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        return new ListItr(index);
    }
    
}
