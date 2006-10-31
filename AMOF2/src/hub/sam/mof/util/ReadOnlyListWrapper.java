/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof.util;

import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalAccessException;

import java.util.Iterator;
import java.util.List;

public class ReadOnlyListWrapper<E,O> implements ReflectiveSequence<E> {

    private final List<? extends O> wrapped;
    private Wrapper<E,O> wrapper;
    protected void setWrapper(Wrapper<E,O> wrapper) {
        this.wrapper = wrapper;
    }
    public ReadOnlyListWrapper(List<? extends O> wrapped, Wrapper<E,O> wrapper) {
        super();
        this.wrapped = wrapped;
        this.wrapper = wrapper;
    }

    public void add(int index, Object element) {
        throw new IllegalAccessException();
    }
    public boolean addAll(int index, Iterable<? extends Object> elements) {
        throw new IllegalAccessException();
    }
    public E get(int index) {
        return wrapper.getE(wrapped.get(index));
    }
    public E remove(int index) {
        throw new IllegalAccessException();
    }
    public E set(int index, Object element) {
        throw new IllegalAccessException();
    }
    public ReflectiveSequence<E> subList(int from, int to) {
        return new ReadOnlyListWrapper<E,O>(wrapped.subList(from,to), wrapper);
    }
    public boolean add(Object element) {
        throw new cmof.exception.IllegalAccessException();
    }
    public boolean addAll(Iterable<? extends Object> elements) {
        throw new IllegalAccessException();
    }
    @SuppressWarnings("unchecked")
    public boolean contains(Object element) {
        return wrapped.contains(wrapper.getO((E)element));
    }
    public boolean containsAll(Iterable<? extends Object> elements) {
        throw new IllegalAccessException();
    }
    public boolean remove(Object element) {
        throw new IllegalAccessException();
    }
    public boolean removeAll(Iterable<? extends Object> elements) {
        throw new IllegalAccessException();
    }
    public int size() {
        if (wrapped == null) {
            return 0;
        } else {
            return wrapped.size();
        }
    }
    public Iterator<E> iterator() {
        return new MyIterator(wrapped.iterator());
    }

    class MyIterator implements Iterator<E> {
        private final Iterator<? extends O> wrapped;
        MyIterator(Iterator<? extends O> wrapped) {
            super();
            this.wrapped = wrapped;
        }
        public boolean hasNext() {
            return wrapped.hasNext();
        }
        public E next() {
            return wrapper.getE(wrapped.next());
        }
        public void remove() {
            throw new IllegalAccessException();
        }
    }

    @Override
    public String toString() {
        return wrapped.toString();
    }

    public void clear() {
        wrapped.clear();
    }
}
