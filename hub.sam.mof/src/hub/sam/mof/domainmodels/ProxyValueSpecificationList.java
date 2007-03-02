package hub.sam.mof.domainmodels;

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.mofinstancemodel.MofClassInstance;
import hub.sam.mof.mofinstancemodel.MofValueSpecificationList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

class ProxyValueSpecificationList extends MofValueSpecificationList {

    List<Object> listValues = null;
    Collection<Object> collectionValues;
    final ProxyModelContext context;
    final ProxyStructureSlot slot;
    final boolean isSetProperty;

    ProxyValueSpecificationList(MofClassInstance owner, ProxyStructureSlot slot,
                                ProxyModelContext context, Object values) {
        super(owner, slot, null); // TODO: if qualified attributes are used for ProxyObjects too.
        this.context = context;
        this.slot = slot;
        if (slot.getProperty().getUpper() > 1) {
            if (values.getClass().isArray()) {
                this.listValues = Arrays.asList((Object[])values);
                this.collectionValues = listValues;
            } else {
                if (values.getClass().isAssignableFrom(List.class)) {
                    listValues = (List<Object>)values;
                } else {
                    this.collectionValues = (Collection<Object>)values;
                }
            }
            isSetProperty = false;
        } else {
            this.listValues = new Vector<Object>();
            if (values != null) {
                this.listValues.add(values);
            }
            isSetProperty = true;
        }
        if (collectionValues == null) {
            collectionValues = listValues;
        }
    }

    @Override
    public boolean add(Object o) {
        return collectionValues.add(context.disMantle(o));
    }

    @Override
    public boolean remove(Object o) {
        return collectionValues.remove(context.disMantle(o));
    }

    @Override
    public synchronized ValueSpecification<UmlClass,Property,java.lang.Object> set(int index, Object o) {
        ValueSpecification<UmlClass,Property,java.lang.Object> result;
        if (isSetProperty) {
            result = context.mantle(slot.getProxyValues());
            slot.setProxyValue(context.disMantle(o));
        } else {
            result = context.mantle(listValues.set(index, context.disMantle(o)));
        }
        return result;
    }

    @Override
    public void add(int index, Object o) {
        listValues.add(index, context.disMantle(o));
    }

    @Override
    public ValueSpecification<UmlClass,Property,java.lang.Object> remove(int index) {
        return context.mantle(listValues.remove(index));
    }

    @Override
    public MofValueSpecificationList subList(int fromIndex, int toIndex) {
        return new ProxyValueSpecificationList(owner, slot, context, listValues);
    }

    @Override
    public Iterator<ValueSpecification<UmlClass, Property, Object>> iterator() {
        return new MyIterator(listValues.iterator());
    }

    @Override
    public void clear() {
        collectionValues.clear();
    }

    @Override
    public boolean contains(Object element) {
        return collectionValues.contains(context.disMantle(element));
    }

    @Override
    public ValueSpecification<UmlClass, Property, Object> get(int index) {
        return context.mantle(listValues.get(index));
    }

    @Override
    public int size() {
        return collectionValues.size();
    }

    final class MyIterator implements Iterator {
        private final Iterator base;

        MyIterator(Iterator base) {
            super();
            this.base = base;
        }

        public boolean hasNext() {
            return base.hasNext();
        }

        public Object next() {
            return context.mantle(base.next());
        }

        public void remove() {
            base.remove();
        }
    }
}
